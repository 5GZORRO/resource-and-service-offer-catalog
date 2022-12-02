package it.nextworks.tmf_offering_catalog.components;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.common.RelatedParty;
import it.nextworks.tmf_offering_catalog.information_models.kafka.ExternalProductOrder;
import it.nextworks.tmf_offering_catalog.information_models.party.OrganizationWrapper;
import it.nextworks.tmf_offering_catalog.information_models.product.*;
import it.nextworks.tmf_offering_catalog.information_models.product.order.*;
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
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ExternalProductOrderConsumer {

    private static final Logger log = LoggerFactory.getLogger(ExternalProductOrderConsumer.class);

    private static final String protocol = "http://";
    @Value("${server.hostname}")
    private String hostname;
    @Value("${server.port}")
    private String port;
    private static final String path = "/tmf-api/productOrderingManagement/v4/productOrder/";
    @Value("${sc_lcm.hostname}")
    private String scLcmHostname;
    @Value("${sc_lcm.port}")
    private String scLcmPort;
    @Value("${sc_lcm.derivative_issue.sc_lcm_request_path}")
    private String scLcmRequestPath;

    @Value("${ingress:}")
    private String ingres;

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

        syncProductOrder(productOrder, did, externalProductOrder.isDeleted());

    }

    private void syncProductOrder(ProductOrder productOrder, String did, boolean deleted) throws NotExistingEntityException, IOException {
        Optional<ProductOrderStatus> optionalProductOrderStatus = productOrderStatusRepository.findByProductOrderStatusDid(did);

        log.info("Syncing Product Order " + did + ".");

        if (!optionalProductOrderStatus.isPresent()) {
            productOrder = productOrderRepository.save(productOrder);
            productOrder.setHref(StringUtils.hasText(ingres) ? ingres : (protocol + hostname + ":" + port) + path + productOrder.getId());
            ProductOrderStatus productOrderStatus = new ProductOrderStatus()
                .catalogId(productOrder.getId())
                .did(did)
                .status(ProductOrderStatesEnum.EXTERNAL);
            productOrderStatusRepository.save(productOrderStatus);

            log.info("Synced Product Order " + productOrder.getId() + " (new).");

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
            return;
        }

        if (optionalProductOrderStatus.get().getStatus() != ProductOrderStatesEnum.EXTERNAL && !deleted) {
            log.info("Product Order " + did + " not external, skip.");
            return;
        }

        checkOrphansProductOrder(productOrder);

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
            productOrderService.patch(did, productOrderUpdate, deleted);
        } catch (NotExistingEntityException e) {
            log.error("External " + e.getMessage());
        }

        log.info("Synced Product Order " + optionalProductOrderStatus.get().getCatalogId() + " (update).");

    }

    private void checkOrphansProductOrder(ProductOrder productOrder) {

        final List<AgreementRef> agreement = productOrder.getAgreement();
        if(productOrder.getAgreement() == null){
            productOrder.setAgreement(new ArrayList<>());
        }

        final BillingAccountRef billingAccount = productOrder.getBillingAccount();
        if(productOrder.getBillingAccount() == null){
            productOrder.setBillingAccount(new BillingAccountRef());
        }

        final List<RelatedChannel> channel = productOrder.getChannel();
        if(productOrder.getChannel() == null){
            productOrder.setChannel(new ArrayList<>());
        }

        final List<Note> note = productOrder.getNote();
        if(productOrder.getNote() == null){
            productOrder.setNote(new ArrayList<>());
        }

        final List<OrderPrice> orderTotalPrice = productOrder.getOrderTotalPrice();
        if(productOrder.getOrderTotalPrice() == null){
            productOrder.setOrderTotalPrice(new ArrayList<>());
        }

        final List<PaymentRef> payment = productOrder.getPayment();
        if(productOrder.getPayment() == null){
            productOrder.setPayment(new ArrayList<>());
        }

        final List<ProductOfferingQualificationRef> productOfferingQualification = productOrder.getProductOfferingQualification();
        if(productOrder.getProductOfferingQualification() == null){
            productOrder.setProductOfferingQualification(new ArrayList<>());
        }

        final List<ProductOrderItem> productOrderItem = productOrder.getProductOrderItem();
        if(productOrder.getProductOrderItem() == null){
            productOrder.setProductOrderItem(new ArrayList<>());
        }

        final List<QuoteRef> quote = productOrder.getQuote();
        if(productOrder.getQuote() == null){
            productOrder.setQuote(new ArrayList<>());
        }

        final List<RelatedParty> relatedParty = productOrder.getRelatedParty();
        if(productOrder.getRelatedParty() == null){
            productOrder.setRelatedParty(new ArrayList<>());
        }

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
