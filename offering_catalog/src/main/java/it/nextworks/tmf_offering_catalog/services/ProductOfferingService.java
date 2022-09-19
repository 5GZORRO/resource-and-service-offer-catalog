package it.nextworks.tmf_offering_catalog.services;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.tmf_offering_catalog.common.exception.*;
import it.nextworks.tmf_offering_catalog.information_models.common.*;
import it.nextworks.tmf_offering_catalog.information_models.party.OrganizationWrapper;
import it.nextworks.tmf_offering_catalog.information_models.product.*;
import it.nextworks.tmf_offering_catalog.information_models.product.sla.ServiceLevelAgreement;
import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceSpecification;
import it.nextworks.tmf_offering_catalog.information_models.service.ServiceSpecification;
import it.nextworks.tmf_offering_catalog.repo.ProductOfferingRepository;
import it.nextworks.tmf_offering_catalog.repo.ProductOfferingStatusRepository;
import it.nextworks.tmf_offering_catalog.rest.Filter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.threeten.bp.Instant;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneId;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class ProductOfferingService {

    private static final Logger log = LoggerFactory.getLogger(ProductOfferingService.class);

    private static final String protocol = "http://";
    @Value("${server.hostname}")
    private String hostname;
    @Value("${server.port}")
    private String port;
    private static final String path = "/tmf-api/productCatalogManagement/v4/productOffering/";

    @Value("${sc_lcm.hostname}")
    private String scLcmHostname;
    @Value("${sc_lcm.port}")
    private String scLcmPort;
    @Value("${sc_lcm.product_offer.sc_lcm_request_path}")
    private String scLcmRequestPath;

    @Autowired
    private CommunicationService communicationService;

    @Autowired
    private ProductOfferingRepository productOfferingRepository;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private ProductOfferingStatusRepository productOfferingStatusRepository;

    @Autowired
    private ProductOfferingPriceService productOfferingPriceService;

    @Autowired
    private CategoryService categoryService;

    private void updateCategory(List<CategoryRef> categoryRefs, String href, String id, String name)
            throws NullIdentifierException, NotExistingEntityException {

        if(categoryRefs != null) {
            List<Category> categories = new ArrayList<>();
            for(CategoryRef categoryRef : categoryRefs) {
                String crId = categoryRef.getId();
                if(crId == null)
                    throw new NullIdentifierException("Referenced Category with null identifier not allowed.");

                categories.add(categoryService.get(crId));
            }

            for(Category category : categories) {

                String cId = category.getId();
                log.info("Updating Category " + cId + ".");

                category.getProductOffering().add(new ProductOfferingRef()
                        .href(href)
                        .id(id)
                        .name(name));

                categoryService.save(category);

                log.info("Category " + cId + " updated.");
            }
        }
    }

    public ProductOffering create(ProductOfferingCreate productOfferingCreate, Boolean skipIDP)
            throws IOException, DIDGenerationRequestException, StakeholderNotRegisteredException,
            NotExistingEntityException, NullIdentifierException, DIDAlreadyRequestedForProductException, ScLcmRequestException {

        final String id = UUID.randomUUID().toString();
        log.info("Storing Product Offering " + id + ".");

        OrganizationWrapper ow;
        try {
            ow = organizationService.get();
        } catch (NotExistingEntityException e) {
            throw new StakeholderNotRegisteredException(e.getMessage());
        }

        ProductOffering productOffering = new ProductOffering()
                .baseType(productOfferingCreate.getBaseType())
                .schemaLocation(productOfferingCreate.getSchemaLocation())
                .type(productOfferingCreate.getType())
                .agreement(productOfferingCreate.getAgreement())
                .attachment(productOfferingCreate.getAttachment())
                .bundledProductOffering(productOfferingCreate.getBundledProductOffering())
                .category(productOfferingCreate.getCategory())
                .channel(productOfferingCreate.getChannel())
                .description(productOfferingCreate.getDescription())
                .href(protocol + hostname + ":" + port + path + id)
                .id(id)
                .isBundle(productOfferingCreate.isIsBundle())
                .isSellable(productOfferingCreate.isIsSellable())
                .marketSegment(productOfferingCreate.getMarketSegment())
                .name(productOfferingCreate.getName())
                .place(productOfferingCreate.getPlace())
                .prodSpecCharValueUse(productOfferingCreate.getProdSpecCharValueUse())
                .productOfferingPrice(productOfferingCreate.getProductOfferingPrice())
                .productOfferingTerm(productOfferingCreate.getProductOfferingTerm())
                .productSpecification(productOfferingCreate.getProductSpecification())
                .resourceCandidate(productOfferingCreate.getResourceCandidate())
                .serviceCandidate(productOfferingCreate.getServiceCandidate())
                .serviceLevelAgreement(productOfferingCreate.getServiceLevelAgreement())
                .statusReason(productOfferingCreate.getStatusReason())
                .validFor(productOfferingCreate.getValidFor())
                .version(productOfferingCreate.getVersion());

        final String lifecycleStatus = productOfferingCreate.getLifecycleStatus();
        if(lifecycleStatus == null)
            productOffering.setLifecycleStatus(LifecycleStatusEnumEnum.ACTIVE.toString());
        else {
            LifecycleStatusEnumEnum poLifecycleStatus = LifecycleStatusEnumEnum.fromValue(lifecycleStatus);
            if(poLifecycleStatus == null)
                productOffering.setLifecycleStatus(LifecycleStatusEnumEnum.ACTIVE.toString());
            else
                productOffering.setLifecycleStatus(poLifecycleStatus.toString());
        }

        final OffsetDateTime lastUpdate = productOfferingCreate.getLastUpdate();
        if(lastUpdate != null)
            productOffering.setLastUpdate(lastUpdate.toString());
        else
            productOffering.setLastUpdate(OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")).toString());

        updateCategory(productOffering.getCategory(), productOffering.getHref(), id, productOffering.getName());

        //check if the offer validity period >= price validity period
        try{
            if (productOffering.getProductOfferingPrice().size()>0 && productOffering.getProductOfferingPrice().get(0).getId() !=null){

                ProductOfferingPrice productOfferingPrice = productOfferingPriceService.get(productOffering.getProductOfferingPrice().get(0).getId());

                Date offerStartDate = new Date(OffsetDateTime.parse(productOffering.getValidFor().getStartDateTime()).toInstant().toEpochMilli());
                Date offerEndDate = new Date(OffsetDateTime.parse(productOffering.getValidFor().getEndDateTime()).toInstant().toEpochMilli());
                Date offerPriceStartDate = new Date(OffsetDateTime.parse(productOfferingPrice.getValidFor().getStartDateTime()).toInstant().toEpochMilli());
                Date offerPriceEndDate = new Date(OffsetDateTime.parse(productOfferingPrice.getValidFor().getEndDateTime()).toInstant().toEpochMilli());
                if ((offerStartDate.equals(offerPriceStartDate) || offerStartDate.after(offerPriceStartDate)) &&
                        (offerEndDate.equals(offerPriceEndDate) || offerEndDate.before(offerPriceEndDate))){

                }else{
                    log.info("Invalid offering price period to create product offer");
                    throw new NotExistingEntityException("Invalid offering price period to create product offer");
                }
            }
        }catch(Exception e){
            throw new NullIdentifierException(e.getMessage());
        }

        productOfferingRepository.save(productOffering);
        log.info("Product Offering " + id + " stored in Catalog.");

        if(skipIDP != null) {
            if(skipIDP) {
                ProductOfferingStatus productOfferingStatus = new ProductOfferingStatus()
                        .catalogId(id)
                        .did(null)
                        .status(ProductOfferingStatesEnum.DID_REQUESTED);
                productOfferingStatusRepository.save(productOfferingStatus);

                communicationService.handleDIDReceiving(id, UUID.randomUUID().toString());
            } else {
                log.info("Requesting DID via CommunicationService to ID&P for Product Offering " + id + ".");

                try {
                    communicationService.requestDID(id, ow.getToken());
                } catch (DIDGenerationRequestException e) {
                    productOfferingRepository.delete(productOffering);
                    throw e;
                }

                log.info("DID successfully requested via CommunicationService to ID&P.");
            }
        } else {
            log.info("Requesting DID via CommunicationService to ID&P for Product Offering " + id + ".");

            try {
                communicationService.requestDID(id, ow.getToken());
            } catch (DIDGenerationRequestException e) {
                productOfferingRepository.delete(productOffering);
                throw e;
            }

            log.info("DID successfully requested via CommunicationService to ID&P.");
        }

        log.info("Product Offering created with id " + id + ".");

        return productOffering;
    }

    private void updateCategoryDelete(List<CategoryRef> categoryRefs, String id)
            throws NullIdentifierException, NotExistingEntityException {

        if(categoryRefs != null) {
            List<Category> categories = new ArrayList<>();
            for(CategoryRef categoryRef : categoryRefs) {
                String crId = categoryRef.getId();
                if(crId == null)
                    throw new NullIdentifierException("Referenced Category with null identifier not allowed.");

                categories.add(categoryService.get(crId));
            }

            for(Category category : categories) {

                String cId = category.getId();
                log.info("Updating Category " + cId + ".");

                List<ProductOfferingRef> productOfferingRefs = category.getProductOffering();
                if(productOfferingRefs != null)
                    productOfferingRefs.removeIf(por -> por.getId().equals(id));

                categoryService.save(category);

                log.info("Category " + cId + " updated.");
            }
        }
    }

    public void delete(String id) throws NotExistingEntityException, ProductOfferingDeleteScLCMException,
            ProductOfferingInPublicationException, IOException, NullIdentifierException {

        log.info("Received request to delete Product Offering with id " + id + ".");

        Optional<ProductOffering> toDelete = productOfferingRepository.findByProductOfferingId(id);
        if(!toDelete.isPresent())
            throw new NotExistingEntityException("Product Offering with id " + id + " not found in DB.");

        ProductOffering po = toDelete.get();

        updateCategoryDelete(po.getCategory(), id);

        communicationService.deleteProductOffering(id);

        productOfferingRepository.delete(po);

        log.info("Product Offering " + id + " deleted.");
    }

    @Transactional
    public List<ProductOffering> list() {

        log.info("Received request to retrieve all Product Offerings.");

        List<ProductOffering> productOfferings = productOfferingRepository.findAll();
        for(ProductOffering po : productOfferings) {
            po.setAgreement((List<AgreementRef>) Hibernate.unproxy(po.getAgreement()));
            po.setAttachment((List<AttachmentRefOrValue>) Hibernate.unproxy(po.getAttachment()));

            po.setBundledProductOffering((List<BundledProductOffering>)
                    Hibernate.unproxy(po.getBundledProductOffering()));
            if(po.getBundledProductOffering() != null) {
                for (BundledProductOffering bpo : po.getBundledProductOffering())
                    bpo.setBundledProductOfferingOption((BundledProductOfferingOption)
                            Hibernate.unproxy(bpo.getBundledProductOfferingOption()));
            }

            po.setCategory((List<CategoryRef>) Hibernate.unproxy(po.getCategory()));
            po.setChannel((List<ChannelRef>) Hibernate.unproxy(po.getChannel()));
            po.setMarketSegment((List<MarketSegmentRef>) Hibernate.unproxy(po.getMarketSegment()));
            po.setPlace((List<PlaceRef>) Hibernate.unproxy(po.getPlace()));

            po.setProdSpecCharValueUse((List<ProductSpecificationCharacteristicValueUse>)
                    Hibernate.unproxy(po.getProdSpecCharValueUse()));
            if(po.getProdSpecCharValueUse() != null) {
                for (ProductSpecificationCharacteristicValueUse pscvu : po.getProdSpecCharValueUse()) {
                    pscvu.setProductSpecCharacteristicValue((List<ProductSpecificationCharacteristicValue>)
                            Hibernate.unproxy(pscvu.getProductSpecCharacteristicValue()));

                    pscvu.setProductSpecification((ProductSpecificationRef)
                            Hibernate.unproxy(pscvu.getProductSpecification()));
                    if(pscvu.getProductSpecification() != null)
                        pscvu.getProductSpecification().setTargetProductSchema((TargetProductSchema)
                                Hibernate.unproxy(pscvu.getProductSpecification().getTargetProductSchema()));
                }
            }

            po.setProductOfferingPrice((List<ProductOfferingPriceRef>) Hibernate.unproxy(po.getProductOfferingPrice()));
            po.setProductOfferingTerm((List<ProductOfferingTerm>) Hibernate.unproxy(po.getProductOfferingTerm()));

            po.setProductSpecification((ProductSpecificationRef) Hibernate.unproxy(po.getProductSpecification()));
            if(po.getProductSpecification() != null)
                po.getProductSpecification().setTargetProductSchema((TargetProductSchema)
                        Hibernate.unproxy(po.getProductSpecification().getTargetProductSchema()));

            po.setResourceCandidate((ResourceCandidateRef) Hibernate.unproxy(po.getResourceCandidate()));
            po.setServiceCandidate((ServiceCandidateRef) Hibernate.unproxy(po.getServiceCandidate()));
            po.setServiceLevelAgreement((SLARef) Hibernate.unproxy(po.getServiceLevelAgreement()));
        }

        log.info("Product Offerings retrieved.");

        return productOfferings;
    }

    @Transactional
    public List<ProductOffering> filteredList(Filter filter) {

        log.info("Received request to retrieve Product Offerings using filters.");

        List<ProductOffering> productOfferings = productOfferingRepository.filteredFindAll(filter);
        for(ProductOffering po : productOfferings) {
            po.setAgreement((List<AgreementRef>) Hibernate.unproxy(po.getAgreement()));
            po.setAttachment((List<AttachmentRefOrValue>) Hibernate.unproxy(po.getAttachment()));

            po.setBundledProductOffering((List<BundledProductOffering>)
                    Hibernate.unproxy(po.getBundledProductOffering()));
            if (po.getBundledProductOffering() != null) {
                for (BundledProductOffering bpo : po.getBundledProductOffering())
                    bpo.setBundledProductOfferingOption((BundledProductOfferingOption)
                            Hibernate.unproxy(bpo.getBundledProductOfferingOption()));
            }

            po.setCategory((List<CategoryRef>) Hibernate.unproxy(po.getCategory()));
            po.setChannel((List<ChannelRef>) Hibernate.unproxy(po.getChannel()));
            po.setMarketSegment((List<MarketSegmentRef>) Hibernate.unproxy(po.getMarketSegment()));
            po.setPlace((List<PlaceRef>) Hibernate.unproxy(po.getPlace()));

            po.setProdSpecCharValueUse((List<ProductSpecificationCharacteristicValueUse>)
                    Hibernate.unproxy(po.getProdSpecCharValueUse()));
            if (po.getProdSpecCharValueUse() != null) {
                for (ProductSpecificationCharacteristicValueUse pscvu : po.getProdSpecCharValueUse()) {
                    pscvu.setProductSpecCharacteristicValue((List<ProductSpecificationCharacteristicValue>)
                            Hibernate.unproxy(pscvu.getProductSpecCharacteristicValue()));

                    pscvu.setProductSpecification((ProductSpecificationRef)
                            Hibernate.unproxy(pscvu.getProductSpecification()));
                    if (pscvu.getProductSpecification() != null)
                        pscvu.getProductSpecification().setTargetProductSchema((TargetProductSchema)
                                Hibernate.unproxy(pscvu.getProductSpecification().getTargetProductSchema()));
                }
            }

            po.setProductOfferingPrice((List<ProductOfferingPriceRef>) Hibernate.unproxy(po.getProductOfferingPrice()));
            po.setProductOfferingTerm((List<ProductOfferingTerm>) Hibernate.unproxy(po.getProductOfferingTerm()));

            po.setProductSpecification((ProductSpecificationRef) Hibernate.unproxy(po.getProductSpecification()));
            if (po.getProductSpecification() != null)
                po.getProductSpecification().setTargetProductSchema((TargetProductSchema)
                        Hibernate.unproxy(po.getProductSpecification().getTargetProductSchema()));

            po.setResourceCandidate((ResourceCandidateRef) Hibernate.unproxy(po.getResourceCandidate()));
            po.setServiceCandidate((ServiceCandidateRef) Hibernate.unproxy(po.getServiceCandidate()));
            po.setServiceLevelAgreement((SLARef) Hibernate.unproxy(po.getServiceLevelAgreement()));
        }

        log.info("Product Offerings retrieved.");

        return productOfferings;
    }

    private void updateCategoryPatch(List<CategoryRef> oldCategoryRefs,
                                     List<CategoryRef> newCategoryRefs,
                                     String href,
                                     String id,
                                     String name) throws NullIdentifierException, NotExistingEntityException {

        List<Category> oldCategories = new ArrayList<>();
        if(oldCategoryRefs != null) {
            for(CategoryRef categoryRef : oldCategoryRefs) {
                String crId = categoryRef.getId();
                if(crId == null)
                    throw new NullIdentifierException("Referenced Category with null identifier not allowed.");

                oldCategories.add(categoryService.get(crId));
            }
        }

        List<Category> newCategories = new ArrayList<>();
        if(newCategoryRefs != null) {
            for(CategoryRef categoryRef : newCategoryRefs) {
                String crId = categoryRef.getId();
                if(crId == null)
                    throw new NullIdentifierException("Referenced Category with null identifier not allowed.");

                newCategories.add(categoryService.get(crId));
            }
        }

        for(Category category : oldCategories) {

            String cId = category.getId();
            log.info("Updating Category " + cId + ".");

            List<ProductOfferingRef> productOfferingRefs = category.getProductOffering();
            if(productOfferingRefs != null)
                productOfferingRefs.removeIf(por -> por.getId().equals(id));

            categoryService.save(category);

            log.info("Category " + cId + " updated.");
        }

        for(Category category : newCategories) {

            String cId = category.getId();
            log.info("Updating Category " + cId + ".");

            category.getProductOffering().add(new ProductOfferingRef()
                    .href(href)
                    .id(id)
                    .name(name));

            categoryService.save(category);

            log.info("Category " + cId + " updated.");
        }
    }

    public ProductOffering patch(String id, ProductOfferingUpdate productOfferingUpdate, String lastUpdate)
            throws NotExistingEntityException, NullIdentifierException {

        log.info("Received request to patch Product Offering with id " + id + ".");

        Optional<ProductOffering> toUpdate = productOfferingRepository.findByProductOfferingId(id);
        if(!toUpdate.isPresent())
            throw new NotExistingEntityException("Product Offering with id " + id + " not found in DB.");

        ProductOffering productOffering = toUpdate.get();

        productOffering.setBaseType(productOfferingUpdate.getBaseType());
        productOffering.setSchemaLocation(productOfferingUpdate.getSchemaLocation());
        productOffering.setType(productOfferingUpdate.getType());

        final List<AgreementRef> agreement = productOfferingUpdate.getAgreement();
        if(productOffering.getAgreement() == null)
            productOffering.setAgreement(agreement);
        else if(agreement != null) {
            productOffering.getAgreement().clear();
            productOffering.getAgreement().addAll(agreement);
        }
        else
            productOffering.getAgreement().clear();

        final List<AttachmentRefOrValue> attachment = productOfferingUpdate.getAttachment();
        if(productOffering.getAttachment() == null)
            productOffering.setAttachment(attachment);
        else if(attachment != null) {
            productOffering.getAttachment().clear();
            productOffering.getAttachment().addAll(attachment);
        }
        else
            productOffering.getAttachment().clear();

        final List<BundledProductOffering> bundledProductOffering = productOfferingUpdate.getBundledProductOffering();
        if(productOffering.getBundledProductOffering() == null)
            productOffering.setBundledProductOffering(bundledProductOffering);
        else if(bundledProductOffering != null) {
            productOffering.getBundledProductOffering().clear();
            productOffering.getBundledProductOffering().addAll(bundledProductOffering);
        }
        else
            productOffering.getBundledProductOffering().clear();

        final List<CategoryRef> category = productOfferingUpdate.getCategory();
        if(productOffering.getCategory() == null) {
            updateCategory(category, productOffering.getHref(), productOffering.getId(), productOffering.getName());
            productOffering.setCategory(category);
        }
        else if(category != null) {
            updateCategoryPatch(productOffering.getCategory(), category, productOffering.getHref(),
                    productOffering.getId(), productOffering.getName());

            productOffering.getCategory().clear();
            productOffering.getCategory().addAll(category);
        }
        else {
            updateCategoryDelete(productOffering.getCategory(), productOffering.getId());
            productOffering.getCategory().clear();
        }

        final List<ChannelRef> channel = productOfferingUpdate.getChannel();
        if(productOffering.getChannel() == null)
            productOffering.setChannel(channel);
        else if(channel != null) {
            productOffering.getChannel().clear();
            productOffering.getChannel().addAll(channel);
        }
        else
            productOffering.getChannel().clear();

        productOffering.setDescription(productOfferingUpdate.getDescription());
        productOffering.setIsBundle(productOfferingUpdate.isIsBundle());
        productOffering.setIsSellable(productOfferingUpdate.isIsSellable());
        productOffering.setLastUpdate(lastUpdate);
        productOffering.setLifecycleStatus(productOfferingUpdate.getLifecycleStatus());

        final List<MarketSegmentRef> marketSegment = productOfferingUpdate.getMarketSegment();
        if(productOffering.getMarketSegment() == null)
            productOffering.setMarketSegment(marketSegment);
        else if(marketSegment != null) {
            productOffering.getMarketSegment().clear();
            productOffering.getMarketSegment().addAll(marketSegment);
        }
        else
            productOffering.getMarketSegment().clear();

        productOffering.setName(productOfferingUpdate.getName());

        final List<PlaceRef> place = productOfferingUpdate.getPlace();
        if(productOffering.getPlace() == null)
            productOffering.setPlace(place);
        else if(place != null) {
            productOffering.getPlace().clear();
            productOffering.getPlace().addAll(place);
        }
        else
            productOffering.getPlace().clear();

        final List<ProductSpecificationCharacteristicValueUse> prodSpecCharValueUse =
                productOfferingUpdate.getProdSpecCharValueUse();
        if(productOffering.getProdSpecCharValueUse() == null)
            productOffering.setProdSpecCharValueUse(prodSpecCharValueUse);
        else if(prodSpecCharValueUse != null) {
            productOffering.getProdSpecCharValueUse().clear();
            productOffering.getProdSpecCharValueUse().addAll(prodSpecCharValueUse);
        }
        else
            productOffering.getProdSpecCharValueUse().clear();

        final List<ProductOfferingPriceRef> productOfferingPrice = productOfferingUpdate.getProductOfferingPrice();
        if(productOffering.getProductOfferingPrice() == null)
            productOffering.setProductOfferingPrice(productOfferingPrice);
        else if(productOfferingPrice != null) {
            productOffering.getProductOfferingPrice().clear();
            productOffering.getProductOfferingPrice().addAll(productOfferingPrice);
        }
        else
            productOffering.getProductOfferingPrice().clear();

        final List<ProductOfferingTerm> productOfferingTerm = productOfferingUpdate.getProductOfferingTerm();
        if(productOffering.getProductOfferingTerm() == null)
            productOffering.setProductOfferingTerm(productOfferingTerm);
        else if(productOfferingTerm != null) {
            productOffering.getProductOfferingTerm().clear();
            productOffering.getProductOfferingTerm().addAll(productOfferingTerm);
        }
        else
            productOffering.getProductOfferingTerm().clear();

        productOffering.setProductSpecification(productOfferingUpdate.getProductSpecification());
        productOffering.setResourceCandidate(productOfferingUpdate.getResourceCandidate());
        productOffering.setServiceCandidate(productOfferingUpdate.getServiceCandidate());
        productOffering.setServiceLevelAgreement(productOfferingUpdate.getServiceLevelAgreement());
        productOffering.setStatusReason(productOfferingUpdate.getStatusReason());
        productOffering.setValidFor(productOfferingUpdate.getValidFor());
        productOffering.setVersion(productOfferingUpdate.getVersion());

        productOfferingRepository.save(productOffering);

////////Send update to SCLM
        try {
            String json = "";
            String request = protocol + scLcmHostname + ":" + scLcmPort + scLcmRequestPath + id;
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPut httpPut = new HttpPut(request);
            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-type", "application/json");

            // Construct the payloads for the classify and publish POST requests
            String pwJson = communicationService.createPatchJSON(productOffering, UUID.randomUUID().toString());

            StringEntity stringEntity = new StringEntity(pwJson);
            httpPut.setEntity(stringEntity);

            CloseableHttpResponse response = httpClient.execute(httpPut);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new ProductOfferingInPublicationException("The Smart Contract LCM entity did not accept the update request.");
            }
        }catch(Exception e){
            log.info("Error while accessing SCLM to update offering with id " + id);
        }
////////

        log.info("Product Offering " + id + " patched.");

        return productOffering;
    }

    @Transactional
    public ProductOffering get(String id) throws NotExistingEntityException {

        log.info("Received request to retrieve Product Offering with id " + id + ".");

        Optional<ProductOffering> retrieved = productOfferingRepository.findByProductOfferingId(id);
        if(!retrieved.isPresent())
            throw new NotExistingEntityException("Product Offering with id " + id + " not found in DB.");

        ProductOffering po = retrieved.get();

        po.setAgreement((List<AgreementRef>) Hibernate.unproxy(po.getAgreement()));
        po.setAttachment((List<AttachmentRefOrValue>) Hibernate.unproxy(po.getAttachment()));

        po.setBundledProductOffering((List<BundledProductOffering>)
                Hibernate.unproxy(po.getBundledProductOffering()));
        if(po.getBundledProductOffering() != null) {
            for (BundledProductOffering bpo : po.getBundledProductOffering())
                bpo.setBundledProductOfferingOption((BundledProductOfferingOption)
                        Hibernate.unproxy(bpo.getBundledProductOfferingOption()));
        }

        po.setCategory((List<CategoryRef>) Hibernate.unproxy(po.getCategory()));
        po.setChannel((List<ChannelRef>) Hibernate.unproxy(po.getChannel()));
        po.setMarketSegment((List<MarketSegmentRef>) Hibernate.unproxy(po.getMarketSegment()));
        po.setPlace((List<PlaceRef>) Hibernate.unproxy(po.getPlace()));

        po.setProdSpecCharValueUse((List<ProductSpecificationCharacteristicValueUse>)
                Hibernate.unproxy(po.getProdSpecCharValueUse()));
        if(po.getProdSpecCharValueUse() != null) {
            for (ProductSpecificationCharacteristicValueUse pscvu : po.getProdSpecCharValueUse()) {
                pscvu.setProductSpecCharacteristicValue((List<ProductSpecificationCharacteristicValue>)
                        Hibernate.unproxy(pscvu.getProductSpecCharacteristicValue()));

                pscvu.setProductSpecification((ProductSpecificationRef)
                        Hibernate.unproxy(pscvu.getProductSpecification()));
                if(pscvu.getProductSpecification() != null)
                    pscvu.getProductSpecification().setTargetProductSchema((TargetProductSchema)
                            Hibernate.unproxy(pscvu.getProductSpecification().getTargetProductSchema()));
            }
        }

        po.setProductOfferingPrice((List<ProductOfferingPriceRef>) Hibernate.unproxy(po.getProductOfferingPrice()));
        po.setProductOfferingTerm((List<ProductOfferingTerm>) Hibernate.unproxy(po.getProductOfferingTerm()));

        po.setProductSpecification((ProductSpecificationRef) Hibernate.unproxy(po.getProductSpecification()));
        if(po.getProductSpecification() != null)
            po.getProductSpecification().setTargetProductSchema((TargetProductSchema)
                    Hibernate.unproxy(po.getProductSpecification().getTargetProductSchema()));

        po.setResourceCandidate((ResourceCandidateRef) Hibernate.unproxy(po.getResourceCandidate()));
        po.setServiceCandidate((ServiceCandidateRef) Hibernate.unproxy(po.getServiceCandidate()));
        po.setServiceLevelAgreement((SLARef) Hibernate.unproxy(po.getServiceLevelAgreement()));

        log.info("Product Offering " + id + " retrieved.");

        return po;
    }

    @Transactional
    public ProductOffering getByDID(String did) throws NotExistingEntityException {

        log.info("Received request to retrieve Product Offering with DID " + did + ".");

        Optional<ProductOfferingStatus> opt = productOfferingStatusRepository.findByDid(did);
        if(!opt.isPresent())
            throw new NotExistingEntityException("Product Offering with DID " + did + " not found in DB.");

        String catalogId = opt.get().getCatalogId();
        Optional<ProductOffering> retrieved = productOfferingRepository.findByProductOfferingId(catalogId);
        if(!retrieved.isPresent())
            throw new NotExistingEntityException("Product Offering with DID and catalog ID <" + did +
                    ", " + catalogId + "> not found in DB.");

        ProductOffering po = retrieved.get();

        po.setAgreement((List<AgreementRef>) Hibernate.unproxy(po.getAgreement()));
        po.setAttachment((List<AttachmentRefOrValue>) Hibernate.unproxy(po.getAttachment()));

        po.setBundledProductOffering((List<BundledProductOffering>)
                Hibernate.unproxy(po.getBundledProductOffering()));
        if(po.getBundledProductOffering() != null) {
            for (BundledProductOffering bpo : po.getBundledProductOffering())
                bpo.setBundledProductOfferingOption((BundledProductOfferingOption)
                        Hibernate.unproxy(bpo.getBundledProductOfferingOption()));
        }

        po.setCategory((List<CategoryRef>) Hibernate.unproxy(po.getCategory()));
        po.setChannel((List<ChannelRef>) Hibernate.unproxy(po.getChannel()));
        po.setMarketSegment((List<MarketSegmentRef>) Hibernate.unproxy(po.getMarketSegment()));
        po.setPlace((List<PlaceRef>) Hibernate.unproxy(po.getPlace()));

        po.setProdSpecCharValueUse((List<ProductSpecificationCharacteristicValueUse>)
                Hibernate.unproxy(po.getProdSpecCharValueUse()));
        if(po.getProdSpecCharValueUse() != null) {
            for (ProductSpecificationCharacteristicValueUse pscvu : po.getProdSpecCharValueUse()) {
                pscvu.setProductSpecCharacteristicValue((List<ProductSpecificationCharacteristicValue>)
                        Hibernate.unproxy(pscvu.getProductSpecCharacteristicValue()));

                pscvu.setProductSpecification((ProductSpecificationRef)
                        Hibernate.unproxy(pscvu.getProductSpecification()));
                if(pscvu.getProductSpecification() != null)
                    pscvu.getProductSpecification().setTargetProductSchema((TargetProductSchema)
                            Hibernate.unproxy(pscvu.getProductSpecification().getTargetProductSchema()));
            }
        }

        po.setProductOfferingPrice((List<ProductOfferingPriceRef>) Hibernate.unproxy(po.getProductOfferingPrice()));
        po.setProductOfferingTerm((List<ProductOfferingTerm>) Hibernate.unproxy(po.getProductOfferingTerm()));

        po.setProductSpecification((ProductSpecificationRef) Hibernate.unproxy(po.getProductSpecification()));
        if(po.getProductSpecification() != null)
            po.getProductSpecification().setTargetProductSchema((TargetProductSchema)
                    Hibernate.unproxy(po.getProductSpecification().getTargetProductSchema()));

        po.setResourceCandidate((ResourceCandidateRef) Hibernate.unproxy(po.getResourceCandidate()));
        po.setServiceCandidate((ServiceCandidateRef) Hibernate.unproxy(po.getServiceCandidate()));
        po.setServiceLevelAgreement((SLARef) Hibernate.unproxy(po.getServiceLevelAgreement()));

        log.info("Product Offering with DID " + did + " retrieved.");

        return po;
    }

    public void obsoleteProductOfferingLifeCycleStatus(ProductOffering productOffering)
            throws NotExistingEntityException {

        log.info("Received request to obsolete Product Offering Life Cycle Status with catalog id " + productOffering.getId() + ".");

        productOffering.setLifecycleStatus(LifecycleStatusEnumEnum.OBSOLETE.toString());
        productOfferingRepository.save(productOffering);

        log.info("Product Offering Life Cycle status with catalog id " + productOffering.getId() + " to obsolete.");
    }
}
