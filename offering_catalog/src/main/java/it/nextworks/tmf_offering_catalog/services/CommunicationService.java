package it.nextworks.tmf_offering_catalog.services;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.tmf_offering_catalog.common.exception.*;
import it.nextworks.tmf_offering_catalog.information_models.common.PlaceRef;
import it.nextworks.tmf_offering_catalog.information_models.common.ResourceSpecificationRef;
import it.nextworks.tmf_offering_catalog.information_models.common.ServiceSpecificationRef;
import it.nextworks.tmf_offering_catalog.information_models.product.*;
import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceSpecification;
import it.nextworks.tmf_offering_catalog.information_models.service.ServiceSpecification;
import it.nextworks.tmf_offering_catalog.repo.ProductOfferingStatusRepository;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
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
public class CommunicationService {

    public class Claims {}

    public class Offer {

        @JsonProperty("token")
        private String token;

        @JsonProperty("type")
        private String type;

        @JsonProperty("claims")
        private List<Claims> claims;

        @JsonProperty("handler_url")
        private String handlerUrl;

        @JsonCreator
        public Offer(@JsonProperty("token") String token,
                     @JsonProperty("type") String type,
                     @JsonProperty("claims") List<Claims> claims,
                     @JsonProperty("handler_url") String handlerUrl) {
            this.token      = token;
            this.type       = type;
            this.claims     = claims;
            this.handlerUrl = handlerUrl;
        }
    }

    public class ClassificationWrapper {

        @JsonProperty("productOffering")
        private final ProductOffering productOffering;

        @JsonProperty("did")
        private final String did;

        @JsonProperty("productOfferingPrices")
        private final List<ProductOfferingPrice> productOfferingPrices;

        @JsonProperty("productSpecification")
        private final ProductSpecification productSpecification;

        @JsonProperty("resourceSpecifications")
        private final List<ResourceSpecification> resourceSpecifications;

        @JsonProperty("serviceSpecifications")
        private final List<ServiceSpecification> serviceSpecifications;

        @JsonProperty("geographicAddresses")
        private final List<GeographicAddress> geographicAddresses;

        @JsonCreator
        public ClassificationWrapper(@JsonProperty("productOffering") ProductOffering productOffering,
                                     @JsonProperty("did") String did,
                                     @JsonProperty("productOfferingPrices") List<ProductOfferingPrice> productOfferingPrices,
                                     @JsonProperty("productSpecification") ProductSpecification productSpecification,
                                     @JsonProperty("resourceSpecifications") List<ResourceSpecification> resourceSpecifications,
                                     @JsonProperty("serviceSpecifications") List<ServiceSpecification> serviceSpecifications,
                                     @JsonProperty("geographicAddresses") List<GeographicAddress> geographicAddresses) {
            this.productOffering        = productOffering;
            this.did                    = did;
            this.productOfferingPrices  = productOfferingPrices;
            this.productSpecification   = productSpecification;
            this.resourceSpecifications = resourceSpecifications;
            this.serviceSpecifications  = serviceSpecifications;
            this.geographicAddresses    = geographicAddresses;
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

        @JsonProperty("productOfferingPrices")
        private final List<ProductOfferingPrice> productOfferingPrices;

        @JsonProperty("productSpecification")
        private final ProductSpecification productSpecification;

        @JsonProperty("resourceSpecifications")
        private final List<ResourceSpecification> resourceSpecifications;

        @JsonProperty("serviceSpecifications")
        private final List<ServiceSpecification> serviceSpecifications;

        @JsonProperty("geographicAddresses")
        private final List<GeographicAddress> geographicAddresses;

        @JsonCreator
        public PublicationWrapper(@JsonProperty("productOffering") ProductOffering productOffering,
                                  @JsonProperty("invitations") Map<String, Invitation> invitations,
                                  @JsonProperty("verifiableCredentials")
                                          Collection<VerifiableCredential> verifiableCredentials,
                                  @JsonProperty("did") String did,
                                  @JsonProperty("productOfferingPrices") List<ProductOfferingPrice> productOfferingPrices,
                                  @JsonProperty("productSpecification") ProductSpecification productSpecification,
                                  @JsonProperty("resourceSpecifications") List<ResourceSpecification> resourceSpecifications,
                                  @JsonProperty("serviceSpecifications") List<ServiceSpecification> serviceSpecifications,
                                  @JsonProperty("geographicAddresses") List<GeographicAddress> geographicAddresses) {
            this.productOffering        = productOffering;
            this.invitations            = invitations;
            this.verifiableCredentials  = verifiableCredentials;
            this.did                    = did;
            this.productOfferingPrices  = productOfferingPrices;
            this.productSpecification   = productSpecification;
            this.resourceSpecifications = resourceSpecifications;
            this.serviceSpecifications  = serviceSpecifications;
            this.geographicAddresses = geographicAddresses;
        }
    }

    private static final Logger log = LoggerFactory.getLogger(CommunicationService.class);

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
    @Value("${sc_lcm.sc_lcm_request_path}")
    private String scLcmRequestPath;

    @Value("${skip_sc_lcm_post}")
    private boolean skipSCLCMPost;
    @Value("${skip_srsd_post}")
    private boolean skipSRSDPost;

    private final ObjectMapper objectMapper;

    @Autowired
    private ProductOfferingStatusRepository productOfferingStatusRepository;

    @Autowired
    private ProductOfferingService productOfferingService;

    @Autowired
    private ProductOfferingPriceService productOfferingPriceService;

    @Autowired
    private ProductSpecificationService productSpecificationService;

    @Autowired
    private ResourceSpecificationService resourceSpecificationService;

    @Autowired
    private ServiceSpecificationService serviceSpecificationService;

    @Autowired
    private GeographicAddressService geographicAddressService;

    @Autowired
    private ClassifyAndPublishProductOfferingService classifyAndPublishProductOfferingService;

    @Autowired
    public CommunicationService(ObjectMapper objectMapper) { this.objectMapper = objectMapper; }

    public void requestDID(String catalogId, String token) throws IOException, DIDGenerationRequestException {

        log.info("Sending create DID request to ID&P.");

        String request = protocol + didServiceHostname + ":" + didServicePort + requestPath;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(request);

        Offer requestOffer =
                new Offer(token, "ProductOffer", new ArrayList<>(),
                        protocol + hostname + ":" + port +
                                "/tmf-api/productCatalogManagement/v4/productOffering/did/" + catalogId);
        String roJson = objectMapper.writeValueAsString(requestOffer);

        StringEntity stringEntity = new StringEntity(roJson);

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

        CloseableHttpResponse response;
        try {
            response = httpClient.execute(httpPost);
        } catch(IOException e) {
            productOfferingStatusRepository.delete(productOfferingStatus);
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
            productOfferingStatusRepository.delete(productOfferingStatus);
            throw new DIDGenerationRequestException("Create DID request via CommunicationService not accepted by ID&P");
        }

        log.info("Create DID request accepted by ID&P.");
    }

    public void handleDIDReceiving(String catalogId, String did)
            throws NotExistingEntityException, DIDAlreadyRequestedForProductException, JsonProcessingException {

        log.info("Updating status of Product Offering " + catalogId + " with the received DID " + did + ".");

        ProductOffering po = productOfferingService.get(catalogId);

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

        log.info("Status of Product Offering " + catalogId + " updated with DID " + did + ".");

        if(skipSRSDPost && skipSCLCMPost) {
            log.info("Skipping POST request to Smart Resource & Service Discovery.");
            log.info("Skipping POST request to Smart Contract Lifecycle Manager.");
            return;
        }

        // Construct the payloads for the classify and publish POST requests

        List<ProductOfferingPrice> productOfferingPrices = getProductOfferingPrices(po);
        ProductSpecification productSpecification = getProductSpecification(po);
        List<ResourceSpecification> resourceSpecifications = getResourceSpecifications(productSpecification);
        List<ServiceSpecification> serviceSpecifications = getServiceSpecifications(productSpecification);
        List<GeographicAddress> geographicAddresses = getGeographicAddresses(po);

        String cwJson = null;
        if(!skipSRSDPost)
            cwJson = objectMapper.writeValueAsString(new ClassificationWrapper(po, did, productOfferingPrices,
                    productSpecification, resourceSpecifications, serviceSpecifications, geographicAddresses));

        String pwJson = null;
        if(!skipSCLCMPost)
            pwJson = objectMapper.writeValueAsString(new PublicationWrapper(po, null, null,
                    did, productOfferingPrices, productSpecification, resourceSpecifications, serviceSpecifications, geographicAddresses));

        classifyAndPublishProductOfferingService.classifyAndPublish(catalogId, cwJson, pwJson);
    }

    private List<ProductOfferingPrice> getProductOfferingPrices(ProductOffering po) {
        if(po.getProductOfferingPrice() == null)
            return null;

        List<ProductOfferingPrice> productOfferingPrices = new ArrayList<>();

        List<ProductOfferingPriceRef> productOfferingPriceRefs = po.getProductOfferingPrice();
        for(ProductOfferingPriceRef priceRef : productOfferingPriceRefs) {
            String id = priceRef.getId();
            try {
                productOfferingPrices.add(productOfferingPriceService.get(id));
            } catch (NotExistingEntityException e) {
                log.warn("ProductOfferingPrice with id " + id + " not found in DB.");
            }
        }

        return productOfferingPrices;
    }

    private ProductSpecification getProductSpecification(ProductOffering po) {
        if(po.getProductSpecification() == null)
            return null;

        ProductSpecification productSpecification = null;
        String id = po.getProductSpecification().getId();
        try {
            productSpecification =  productSpecificationService.get(id);
        } catch (NotExistingEntityException e) {
            log.warn("ProductSpecification with id " + id + " not found in DB.");
        }

        return productSpecification;
    }

    private List<ResourceSpecification> getResourceSpecifications(ProductSpecification ps) {
        if(ps == null || ps.getResourceSpecification() == null)
            return null;

        List<ResourceSpecification> resourceSpecifications = new ArrayList<>();

        List<ResourceSpecificationRef> resourceSpecificationRefs = ps.getResourceSpecification();
        for(ResourceSpecificationRef specificationRef : resourceSpecificationRefs) {
            String id = specificationRef.getId();
            try {
                resourceSpecifications.add(resourceSpecificationService.get(id));
            } catch (NotExistingEntityException e) {
                log.warn("ResourceSpecification with id " + id + " not found in DB.");
            }
        }

        return resourceSpecifications;
    }

    private List<ServiceSpecification> getServiceSpecifications(ProductSpecification ps) {
        if(ps == null || ps.getServiceSpecification() == null)
            return null;

        List<ServiceSpecification> serviceSpecifications = new ArrayList<>();

        List<ServiceSpecificationRef> serviceSpecificationRefs = ps.getServiceSpecification();
        for(ServiceSpecificationRef specificationRef : serviceSpecificationRefs) {
            String id = specificationRef.getId();
            try {
                serviceSpecifications.add(serviceSpecificationService.get(id));
            } catch (NotExistingEntityException e) {
                log.warn("ServiceSpecification with id " + id + " not found in DB.");
            }
        }

        return serviceSpecifications;
    }

    private List<GeographicAddress> getGeographicAddresses(ProductOffering productOffering) {
        List<PlaceRef> placeRefs = productOffering.getPlace();
        List<GeographicAddress> geographicAddresses = new ArrayList<>();
        placeRefs.forEach((placeRef) -> {
            String id = placeRef.getId();
            try {
                geographicAddresses.add(geographicAddressService.get(id));
            } catch (NotExistingEntityException e) {
                log.warn("GeographicAddress with id " + id + " not found in DB.");
            }
        });
        return geographicAddresses;
    }

    public void deleteProductOffering(String catalogId)
            throws NotExistingEntityException, IOException, ProductOfferingDeleteScLCMException,
            ProductOfferingInPublicationException {

        log.info("Sending delete Product Offering request.");

        Optional<ProductOfferingStatus> toDelete = productOfferingStatusRepository.findById(catalogId);
        if(!toDelete.isPresent())
            throw new NotExistingEntityException("Product Offering Status for id " + catalogId + " not found in DB.");

        ProductOfferingStatus productOfferingStatus = toDelete.get();
        ProductOfferingStatesEnum productOfferingStatesEnum = productOfferingStatus.getStatus();
        if(productOfferingStatesEnum == ProductOfferingStatesEnum.DID_REQUESTED ||
                productOfferingStatesEnum == ProductOfferingStatesEnum.STORED_WITH_DID ||
                productOfferingStatesEnum == ProductOfferingStatesEnum.CLASSIFIED)
            throw new ProductOfferingInPublicationException("Cannot delete Product Offering " + catalogId + " while in publication.");
        else if(productOfferingStatesEnum == ProductOfferingStatesEnum.CLASSIFICATION_FAILED ||
                productOfferingStatesEnum == ProductOfferingStatesEnum.PUBLISHING_FAILED) {
            productOfferingStatusRepository.delete(productOfferingStatus);

            log.info("Delete Product Offering request accepted.");
            return;
        }


        String request = protocol + scLcmHostname + ":" + scLcmPort + scLcmRequestPath + catalogId;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(request);

        httpDelete.setHeader("Accept", "application/json");

        CloseableHttpResponse response = httpClient.execute(httpDelete);

        if(response.getStatusLine().getStatusCode() != 200)
            throw new ProductOfferingDeleteScLCMException("The Smart Contract LCM entity did not accept the delete request.");

        productOfferingStatusRepository.delete(productOfferingStatus);

        log.info("Delete Product Offering request accepted.");
    }
}
