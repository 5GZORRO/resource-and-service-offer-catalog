package it.nextworks.tmf_offering_catalog.services;

import it.nextworks.tmf_offering_catalog.common.exception.*;
import it.nextworks.tmf_offering_catalog.information_models.common.*;
import it.nextworks.tmf_offering_catalog.information_models.party.OrganizationWrapper;
import it.nextworks.tmf_offering_catalog.information_models.product.*;
import it.nextworks.tmf_offering_catalog.repo.ProductOfferingRepository;
import it.nextworks.tmf_offering_catalog.rest.Filter;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.threeten.bp.OffsetDateTime;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductOfferingService {

    private static final Logger log = LoggerFactory.getLogger(ProductOfferingService.class);

    private static final String protocol = "http://";
    @Value("${server.hostname}")
    private String hostname;
    @Value("${server.port}")
    private String port;
    private static final String path = "/tmf-api/productCatalogManagement/v4/productOffering/";

    @Autowired
    private CommunicationService communicationService;

    @Autowired
    private ProductOfferingRepository productOfferingRepository;

    @Autowired
    private OrganizationService organizationService;

    public ProductOffering create(ProductOfferingCreate productOfferingCreate)
            throws IOException, DIDGenerationRequestException, StakeholderNotRegisteredException {

        log.info("Received request to create a Product Offering.");

        OrganizationWrapper ow;
        try {
            ow = organizationService.get();
        } catch (NotExistingEntityException e) {
            throw new StakeholderNotRegisteredException(e.getMessage());
        }

        final String id = UUID.randomUUID().toString();
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
                .lifecycleStatus(productOfferingCreate.getLifecycleStatus())
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

        final OffsetDateTime lastUpdate = productOfferingCreate.getLastUpdate();
        if(lastUpdate != null)
            productOffering.setLastUpdate(lastUpdate.toString());

        productOfferingRepository.save(productOffering);

        log.info("Requesting DID via CommunicationService to ID&P.");

        try {
            communicationService.requestDID(id, ow.getToken());
        } catch (DIDGenerationRequestException e) {
            productOfferingRepository.delete(productOffering);
            throw e;
        }

        log.info("DID successfully requested via CommunicationService to ID&P.");
        log.info("Product Offering created with id " + id + ".");

        return productOffering;
    }

    public void delete(String id) throws NotExistingEntityException, ProductOfferingDeleteScLCMException,
            ProductOfferingInPublicationException, IOException {

        log.info("Received request to delete Product Offering with id " + id + ".");

        Optional<ProductOffering> toDelete = productOfferingRepository.findByProductOfferingId(id);
        if(!toDelete.isPresent())
            throw new NotExistingEntityException("Product Offering with id " + id + " not found in DB.");

        communicationService.deleteProductOffering(id);

        productOfferingRepository.delete(toDelete.get());

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

    public ProductOffering patch(String id, ProductOfferingUpdate productOfferingUpdate, String lastUpdate)
            throws NotExistingEntityException {

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
        if(productOffering.getCategory() == null)
            productOffering.setCategory(category);
        else if(category != null) {
            productOffering.getCategory().clear();
            productOffering.getCategory().addAll(category);
        }
        else
            productOffering.getCategory().clear();

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
}
