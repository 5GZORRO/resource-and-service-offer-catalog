package it.nextworks.tmf_offering_catalog.services;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.tmf_offering_catalog.common.exception.DIDAlreadyRequestedForProductException;
import it.nextworks.tmf_offering_catalog.common.exception.DIDGenerationRequestException;
import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.components.ClassifyAndPublishProductOfferingTask;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOffering;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingStatesEnum;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingStatus;
import it.nextworks.tmf_offering_catalog.repo.ProductOfferingStatusRepository;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class CommunicationService {

    public class Claims {}

    public class Offer {

        @JsonProperty("type")
        private String type;
        @JsonProperty("claims")
        private Claims claims;
        @JsonProperty("handler_url")
        private String handlerUrl;

        @JsonCreator
        public Offer(@JsonProperty("type") String type,
                     @JsonProperty("claims") Claims claims,
                     @JsonProperty("handler_url") String handlerUrl) {
            this.type = type;
            this.claims = claims;
            this.handlerUrl = handlerUrl;
        }
    }

    public class ClassificationWrapper {

        @JsonProperty("productOffering")
        private final ProductOffering productOffering;
        @JsonProperty("did")
        private final String did;

        @JsonCreator
        public ClassificationWrapper(@JsonProperty("productOffering") ProductOffering productOffering,
                                     @JsonProperty("did") String did) {
            this.productOffering = productOffering;
            this.did = did;
        }
    }

    public class Invitation {}

    public class VerifiableCredential {}

    public class PublicationWrapper {

        @JsonProperty("productOffering")
        private final ProductOffering productOffering;
        @JsonProperty("invitations")
        private final Map<String, Invitation> invitations;
        @JsonProperty("verifiableCredentials")
        private final Collection<VerifiableCredential> verifiableCredentials;
        @JsonProperty("did")
        private final String did;

        @JsonCreator
        public PublicationWrapper(@JsonProperty("productOffering") ProductOffering productOffering,
                                  @JsonProperty("invitations") Map<String, Invitation> invitations,
                                  @JsonProperty("verifiableCredentials")
                                          Collection<VerifiableCredential> verifiableCredentials,
                                  @JsonProperty("did") String did) {
            this.productOffering       = productOffering;
            this.invitations           = invitations;
            this.verifiableCredentials = verifiableCredentials;
            this.did = did;
        }
    }

    private static final Logger log = LoggerFactory.getLogger(CommunicationService.class);

    private static final String protocol = "http://";
    @Value("${did_service.hostname}")
    private String didServiceHostname;
    @Value("${did_service.port}")
    private String didServicePort;
    private static final String requestPath = "/holder/create_did";

    @Value("${did_service.token}")
    private String token;

    @Value("${server.hostname}")
    private String hostname;
    @Value("${server.port}")
    private String port;

    private final ObjectMapper objectMapper;

    @Autowired
    private ProductOfferingStatusRepository productOfferingStatusRepository;

    @Autowired
    private ProductOfferingService productOfferingService;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private ApplicationContext ctx;

    @Autowired
    public CommunicationService(ObjectMapper objectMapper) { this.objectMapper = objectMapper; }

    public void requestDID(String catalogId) throws IOException, DIDGenerationRequestException {

        log.info("Sending create DID request to ID&P.");

        String request = protocol + didServiceHostname + ":" + didServicePort + requestPath;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(request);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("token", token));

        Offer requestOffer =
                new Offer(null, null,
                        protocol + hostname + ":" + port +
                                "/tmf-api/productCatalogManagement/v4/productOffering/did/" + catalogId);
        String roJson = objectMapper.writeValueAsString(requestOffer);

        StringEntity stringEntity = new StringEntity(roJson);

        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        httpPost.setEntity(stringEntity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        /* Persist productOfferingStatus before sending DID request in order to avoid locking and waiting on condition
         * for productOfferingStatus itself being available for updates.
         */
        ProductOfferingStatus productOfferingStatus = new ProductOfferingStatus()
                .catalogId(catalogId)
                .did(null)
                .status(ProductOfferingStatesEnum.DID_REQUESTED);
        productOfferingStatusRepository.save(productOfferingStatus);

        CloseableHttpResponse response = httpClient.execute(httpPost);

        if(response.getStatusLine().getStatusCode() != 200) {
            // Delete persisted productOfferingStatus if ID&P didn't accept the DID creation request.
            productOfferingStatusRepository.delete(productOfferingStatus);

            throw new DIDGenerationRequestException("Create DID request via CommunicationService not accepted by ID&P");
        }

        log.info("Create DID request accepted by ID&P.");
    }

    public void handleDIDReceiving(String catalogId, String did)
            throws NotExistingEntityException, DIDAlreadyRequestedForProductException, JsonProcessingException {

        log.info("Updating status of Product Offering" + catalogId + " with the received DID " + did + ".");

        ProductOffering po;
        try {
            po = productOfferingService.get(catalogId);
        } catch (NotExistingEntityException e) {
            throw e;
        }

        Optional<ProductOfferingStatus> toUpdate = productOfferingStatusRepository.findById(catalogId);
        if(!toUpdate.isPresent())
            throw new NotExistingEntityException("Product Offering Status for id " + catalogId + " not found in DB.");

        ProductOfferingStatus productOfferingStatus = toUpdate.get();

        if(productOfferingStatus.getDid() != null)
            throw new DIDAlreadyRequestedForProductException("Product Offering with id " +
                    catalogId + " already has a DID.");

        productOfferingStatus.setDid(did);
        productOfferingStatus.setStatus(ProductOfferingStatesEnum.STORED_WITH_DID);

        productOfferingStatusRepository.save(productOfferingStatus);

        ClassificationWrapper classificationWrapper = new ClassificationWrapper(po, did);
        String cwJson = objectMapper.writeValueAsString(classificationWrapper);
        log.info(cwJson);

        PublicationWrapper publicationWrapper = new PublicationWrapper(po, null, null, did);
        String pwJson = objectMapper.writeValueAsString(publicationWrapper);
        log.info(pwJson);

        taskExecutor.execute(ctx.getBean(ClassifyAndPublishProductOfferingTask.class)
                .catalogId(po.getId()).cwJson(cwJson).pwJson(pwJson));

        log.info("Status of Product Offering " + catalogId + " updated.");
    }
}
