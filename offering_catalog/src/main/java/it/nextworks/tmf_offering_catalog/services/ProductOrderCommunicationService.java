package it.nextworks.tmf_offering_catalog.services;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.tmf_offering_catalog.common.exception.DIDAlreadyRequestedForProductException;
import it.nextworks.tmf_offering_catalog.common.exception.DIDGenerationRequestException;
import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.common.exception.ProductOrderDeleteScLCMException;
import it.nextworks.tmf_offering_catalog.information_models.party.Organization;
import it.nextworks.tmf_offering_catalog.information_models.party.OrganizationWrapper;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOffering;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductSpecification;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrder;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrderItem;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrderStatesEnum;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrderStatus;
import it.nextworks.tmf_offering_catalog.repo.ProductOrderStatusRepository;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class ProductOrderCommunicationService {

    public static class Order {

        @JsonProperty("token")
        private final String token;

        @JsonProperty("type")
        private final String type;

        @JsonProperty("claims")
        private final List<?> claims;

        @JsonProperty("handler_url")
        private final String handlerUrl;

        @JsonCreator
        public Order(@JsonProperty("token") String token,
                     @JsonProperty("type") String type,
                     @JsonProperty("claims") List<?> claims,
                     @JsonProperty("handler_url") String handlerUrl) {
            this.token = token;
            this.type = type;
            this.claims = claims;
            this.handlerUrl = handlerUrl;
        }
    }

    public static class PublicationWrapper {

        @JsonProperty("productOrder")
        private final ProductOrder productOrder;

        @JsonProperty("invitations")
        private final Map<String, ?> invitations;

        @JsonProperty("verifiableCredentials")
        private final Collection<?> verifiableCredentials;

        @JsonProperty("did")
        private final String did;

        @JsonProperty("supplierDid")
        private final String supplierDid;

        @JsonCreator
        public PublicationWrapper(@JsonProperty("productOrder") ProductOrder productOrder,
                                  @JsonProperty("invitations") Map<String, ?> invitations,
                                  @JsonProperty("verifiableCredentials") Collection<?> verifiableCredentials,
                                  @JsonProperty("did") String did,
                                  @JsonProperty("supplierDid") String supplierDid) {
            this.productOrder = productOrder;
            this.invitations = invitations;
            this.verifiableCredentials = verifiableCredentials;
            this.did = did;
            this.supplierDid = supplierDid;
        }
    }

    private static final Logger log = LoggerFactory.getLogger(ProductOrderCommunicationService.class);

    private static final String protocol = "http://";

    @Value("${did_service.hostname}")
    private String didServiceHostname;
    @Value("${did_service.port}")
    private String didServicePort;
    @Value("${did_service.request_path}")
    private String requestPath;

    @Value("${server.hostname}")
    private String hostname;
    @Value("${server.port}")
    private String port;

    @Value("${sc_lcm.hostname}")
    private String scLcmHostname;
    @Value("${sc_lcm.port}")
    private String scLcmPort;
    @Value("${sc_lcm.product_order.sc_lcm_request_path}")
    private String scLcmRequestPath;

    @Value("${skip_sc_lcm_post}")
    private boolean skipSCLCMPost;

    private final ObjectMapper objectMapper;

    @Autowired
    private ProductOrderStatusRepository productOrderStatusRepository;

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private ProductOfferingPriceService productOfferingPriceService;

    @Autowired
    private ProductOfferingService productOfferingService;

    @Autowired
    private PublishProductOrderService publishProductOrderService;

    @Autowired
    private ProductSpecificationService productSpecificationService;

    @Autowired
    public ProductOrderCommunicationService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void requestDID(String catalogId, String token) throws IOException, DIDGenerationRequestException {

        log.info("Sending create DID request to ID&P.");

        String request = protocol + didServiceHostname + ":" + didServicePort + requestPath;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(request);

        ProductOrderCommunicationService.Order requestOrder = new ProductOrderCommunicationService.Order(
                token, "ProductOffer", new ArrayList<>(), protocol + hostname + ":" + port + "/tmf-api/productOrderingManagement/v4/productOrder/did/" + catalogId
        );
        String roJson = objectMapper.writeValueAsString(requestOrder);

        StringEntity stringEntity = new StringEntity(roJson);

        httpPost.setEntity(stringEntity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        /* Persist productOfferingStatus before sending DID request in order to avoid locking and waiting on condition
         * for productOfferingStatus itself being available for updates.
         */
        ProductOrderStatus productOrderStatus = new ProductOrderStatus()
                .catalogId(catalogId)
                .did(null)
                .status(ProductOrderStatesEnum.DID_REQUESTED);
        productOrderStatusRepository.save(productOrderStatus);

        CloseableHttpResponse response;
        try {
            response = httpClient.execute(httpPost);
        } catch(IOException e) {
            productOrderStatusRepository.delete(productOrderStatus);
            throw new DIDGenerationRequestException("ID&P Unreachable");
        }

        if(response.getStatusLine().getStatusCode() != 201) {
            HttpEntity httpEntity = response.getEntity();
            String responseString;

            if(httpEntity != null) {
                responseString = EntityUtils.toString(httpEntity, "UTF-8");
                log.error("Create DID request not accepted by ID&P; " + responseString);
            }

            // Delete persisted productOfferingStatus if ID&P didn't accept the DID creation request.
            productOrderStatusRepository.delete(productOrderStatus);
            throw new DIDGenerationRequestException("Create DID request via CommunicationService not accepted by ID&P");
        }

        log.info("Create DID request accepted by ID&P.");
    }

    public void handleDIDReceiving(String catalogId, String did) throws IOException, NotExistingEntityException, DIDAlreadyRequestedForProductException {

        log.info("Updating status of Product Order " + catalogId + " with the received DID " + did + ".");

        ProductOrder productOrder = productOrderService.get(catalogId);

        Optional<ProductOrderStatus> toUpdate = productOrderStatusRepository.findById(catalogId);
        if(!toUpdate.isPresent())
            throw new NotExistingEntityException("Product Order Status for id " + catalogId + " not found in DB.");

        ProductOrderStatus productOrderStatus = toUpdate.get();

        if(productOrderStatus.getDid() != null)
            throw new DIDAlreadyRequestedForProductException("Product Offering with id " + catalogId + " already has a DID.");

        productOrderStatus.setDid(did);
        productOrderStatus.setStatus(ProductOrderStatesEnum.STORED_WITH_DID);

        productOrderStatusRepository.save(productOrderStatus);

        log.info("Status of Product Order " + catalogId + " updated with DID " + did + ".");
        ProductOrderItem productOrderItem = productOrder.getProductOrderItem().get(0);
        ProductOffering productOffering = productOfferingService.get(productOrderItem.getProductOffering().getId());
        ProductSpecification productSpecification = productSpecificationService.get(productOffering.getProductSpecification().getId());
        String stakeholderDid = productSpecification.getRelatedParty().get(0).getExtendedInfo();

        String pwJson = null;
        if (!skipSCLCMPost) {
            pwJson = objectMapper.writeValueAsString(new PublicationWrapper(
                    productOrder,
                    null,
                    null,
                    did,
                    stakeholderDid
            ));
        }

        publishProductOrderService.publish(productOrder.getId(), pwJson);
    }

    public void deleteProductOrder(String catalogId) throws NotExistingEntityException, IOException, ProductOrderDeleteScLCMException {

        log.info("Sending delete Product Order request.");

        Optional<ProductOrderStatus> toDelete = productOrderStatusRepository.findById(catalogId);
        if (!toDelete.isPresent())
            throw new NotExistingEntityException("Product Order Status for id " + catalogId + " not found in DB.");

        ProductOrderStatus productOrderStatus = toDelete.get();
        ProductOrderStatesEnum productOrderStatesEnum = productOrderStatus.getStatus();
        if (productOrderStatesEnum == ProductOrderStatesEnum.PUBLISHING_FAILED) {
            productOrderStatusRepository.delete(productOrderStatus);

            log.info("Delete Product Order request accepted.");
            return;
        }

        String request = protocol + scLcmHostname + ":" + scLcmPort + scLcmRequestPath + catalogId;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(request);

        httpDelete.setHeader("Accept", "application/json");

        CloseableHttpResponse response = httpClient.execute(httpDelete);

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new ProductOrderDeleteScLCMException("The Smart Contract LCM entity did not accept the delete request.");
        }

        productOrderStatusRepository.delete(productOrderStatus);

        log.info("Delete Product Order request accepted.");
    }

    public void endProductOrder(String catalogId) throws NotExistingEntityException, IOException, ProductOrderDeleteScLCMException {

        log.info("Sending end Product Order request.");

        Optional<ProductOrderStatus> toEnd = productOrderStatusRepository.findById(catalogId);
        if (!toEnd.isPresent())
            throw new NotExistingEntityException("Product Order Status for id " + catalogId + " not found in DB.");

        ProductOrderStatus productOrderStatus = toEnd.get();
        ProductOrderStatesEnum productOrderStatesEnum = productOrderStatus.getStatus();
        if (productOrderStatesEnum == ProductOrderStatesEnum.PUBLISHING_FAILED) {
            productOrderStatusRepository.delete(productOrderStatus);

            log.info("Delete Product Order request accepted.");
            return;
        }


        String request = protocol + scLcmHostname + ":" + scLcmPort + scLcmRequestPath + "end?orderId=" + catalogId;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(request);

        httpPut.setHeader("Accept", "application/json");

        CloseableHttpResponse response = httpClient.execute(httpPut);

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new ProductOrderDeleteScLCMException("The Smart Contract LCM entity did not accept the end request.");
        }

        //productOrderStatusRepository.delete(productOrderStatus);

        log.info("End Product Order request accepted.");
    }
}
