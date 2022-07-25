package it.nextworks.tmf_offering_catalog.components;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.common.RelatedParty;
import it.nextworks.tmf_offering_catalog.information_models.kafka.ExternalProductOrder;
import it.nextworks.tmf_offering_catalog.information_models.party.OrganizationWrapper;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingStatus;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrder;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrderStatesEnum;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrderStatus;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrderUpdate;
import it.nextworks.tmf_offering_catalog.repo.ProductOfferingStatusRepository;
import it.nextworks.tmf_offering_catalog.repo.ProductOrderRepository;
import it.nextworks.tmf_offering_catalog.repo.ProductOrderStatusRepository;
import it.nextworks.tmf_offering_catalog.services.OrganizationService;
import it.nextworks.tmf_offering_catalog.services.ProductOrderService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class ExternalProductOrderConsumer {

    private static final Logger log = LoggerFactory.getLogger(ExternalProductOrderConsumer.class);

    private static final String protocol = "http://";
    @Value("${sc_lcm.hostname}")
    private String scLcmHostname;
    @Value("${sc_lcm.port}")
    private String scLcmPort;
    @Value("${sc_lcm.derivative_issue.sc_lcm_request_path}")
    private String scLcmRequestPath;

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Autowired
    private ProductOrderStatusRepository productOrderStatusRepository;

    @Autowired
    private ProductOfferingStatusRepository productOfferingStatusRepository;

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private OrganizationService organizationService;

    private OrganizationWrapper organization;

    private final ObjectMapper objectMapper;

    @Autowired
    public ExternalProductOrderConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(
            topics = "#{@topicOrders}",
            containerFactory = "kafkaOrderListenerContainerFactory"
    )
    public void listener(ExternalProductOrder externalProductOrder) throws NotExistingEntityException, IOException {

        log.info("Receiving External Product Order.");

        if (externalProductOrder == null) {
            log.warn("Received empty External Product Order.");
            return;
        }

        ProductOrder productOrder = externalProductOrder.getProductOrder();
        if (productOrder == null) {
            log.warn("Received empty Product Order in External Product Order.");
            return;
        }

        String did = externalProductOrder.getDid();
        if (did == null) {
            log.warn("Received empty DID in External Product Order.");
            return;
        }

        syncProductOrder(productOrder, did);
    }

    private void syncProductOrder(ProductOrder productOrder, String did) throws NotExistingEntityException, IOException {
        String id = productOrder.getId();

        log.info("Syncing Product Order " + id + ".");

        Optional<ProductOrder> optionalProductOrder = productOrderRepository.findByProductOrderId(id);

        if (!optionalProductOrder.isPresent()) {
            productOrderRepository.save(productOrder);
            ProductOrderStatus productOrderStatus = new ProductOrderStatus()
                    .catalogId(id)
                    .did(did)
                    .status(ProductOrderStatesEnum.EXTERNAL);
            productOrderStatusRepository.save(productOrderStatus);
            log.info("Synced Product Order " + id + " (new).");
            return;
        }

        Optional<ProductOrderStatus> optionalProductOrderStatus = productOrderStatusRepository.findById(id);
        if (!optionalProductOrderStatus.isPresent()) {
            log.error("Product Order Status " + id + " not found in DB.");
            return;
        }
        if (optionalProductOrderStatus.get().getStatus() != ProductOrderStatesEnum.EXTERNAL) {
            log.info("Product Order " + id + " not external, skip.");
            return;
        }

        ProductOrderUpdate productOrderUpdate = new ProductOrderUpdate()
                .baseType(productOrder.getBaseType())
                .schemaLocation(productOrder.getSchemaLocation())
                .type(productOrder.getType())
                .agreement(productOrder.getAgreement())
                .billingAccount(productOrder.getBillingAccount())
                .cancellationDate(productOrder.getCancellationDate())
                .cancellationReason(productOrder.getCancellationReason())
                .category(productOrder.getCategory())
                .channel(productOrder.getChannel())
                .description(productOrder.getDescription())
                .externalId(productOrder.getExternalId())
                .note(productOrder.getNote())
                .notificationContact(productOrder.getNotificationContact())
                .orderTotalPrice(productOrder.getOrderTotalPrice())
                .payment(productOrder.getPayment())
                .priority(productOrder.getPriority())
                .productOfferingQualification(productOrder.getProductOfferingQualification())
                .productOrderItem(productOrder.getProductOrderItem())
                .quote(productOrder.getQuote())
                .relatedParty(productOrder.getRelatedParty())
                .requestedCompletionDate(productOrder.getRequestedCompletionDate())
                .requestedStartDate(productOrder.getRequestedStartDate());

        try {
            productOrderService.patch(id, productOrderUpdate);
        } catch (NotExistingEntityException e) {
            log.error("External " + e.getMessage());
        }

        try {
            organization = organizationService.get();
        } catch (NotExistingEntityException e) {
            log.error(e.getMessage());
            return;
        }

        RelatedParty seller = null;
        RelatedParty buyer = null;
        for (RelatedParty relatedParty : productOrder.getRelatedParty()) {
            if (isSellerParty(relatedParty)) {
                seller = relatedParty;
            } else {
                buyer = relatedParty;
            }
        }

        if (seller == null || buyer == null) {
            throw new NotExistingEntityException("RelatedParty missing.");
        }

        if (amISellerParty(seller) && isSpectrumOrder(productOrder)) {
            issueDerivativeSpectoken(productOrder, buyer);
            log.info("Derivative Spectoken issued.");
        }

        log.info("Synced Product Order " + id + " (update).");

    }

    private boolean isSellerParty(RelatedParty relatedParty) {
        return "Seller".equals(relatedParty.getRole());
    }

    private boolean amISellerParty(RelatedParty relatedParty) {
        return relatedParty.getExtendedInfo().equals(organization.getStakeholderDID());
    }

    private boolean isSpectrumOrder(ProductOrder productOrder) {
        return "Spectrum".equals(productOrder.getCategory());
    }

    private static class IssueDerivativeSpectokenRequest {

        @JsonProperty("offerDid")
        private final String offerDid;

        @JsonProperty("ownerDid")
        private final String ownerDid;

        @JsonCreator
        public IssueDerivativeSpectokenRequest(@JsonProperty("offerDid") String offerDid, @JsonProperty("ownerDid") String ownerDid) {
            this.offerDid = offerDid;
            this.ownerDid = ownerDid;
        }
    }

    private void issueDerivativeSpectoken(ProductOrder productOrder, RelatedParty buyer) throws NotExistingEntityException, IOException {
        String offerCatalogId = productOrder.getProductOrderItem().get(0).getProductOffering().getId();
        Optional<ProductOfferingStatus> productOfferingStatusOptional = productOfferingStatusRepository.findById(offerCatalogId);
        if (!productOfferingStatusOptional.isPresent()) {
            throw new NotExistingEntityException("Product Offering Status for id " + offerCatalogId + " not found in DB.");
        }
        ProductOfferingStatus productOfferingStatus = productOfferingStatusOptional.get();
        IssueDerivativeSpectokenRequest issueDerivativeSpectokenRequest = new IssueDerivativeSpectokenRequest(productOfferingStatus.getDid(), buyer.getExtendedInfo());
        String roJson = objectMapper.writeValueAsString(issueDerivativeSpectokenRequest);

        String request = protocol + scLcmHostname + ":" + scLcmPort + scLcmRequestPath;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(request);
        StringEntity stringEntity = new StringEntity(roJson);

        httpPost.setEntity(stringEntity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        CloseableHttpResponse response = httpClient.execute(httpPost);

    }

}
