package it.nextworks.tmf_offering_catalog.services;

import it.nextworks.tmf_offering_catalog.common.exception.DIDGenerationRequestException;
import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.common.PlaceRef;
import it.nextworks.tmf_offering_catalog.information_models.common.ResourceCandidateRef;
import it.nextworks.tmf_offering_catalog.information_models.common.ServiceCandidateRef;
import it.nextworks.tmf_offering_catalog.information_models.common.TimePeriod;
import it.nextworks.tmf_offering_catalog.information_models.product.*;
import it.nextworks.tmf_offering_catalog.repo.ProductOfferingRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
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

    public ProductOffering create(ProductOfferingCreate productOfferingCreate)
            throws IOException, DIDGenerationRequestException {

        log.info("Received request to create a Product Offering.");

        final String id = UUID.randomUUID().toString();

        /* We will need to move the call to ID&P in case the CommunicationService will have
         * to update the productOffering to insert the DID (now external in the product_offering_states table)
         */
        log.info("Requesting DID via CommunicationService to ID&P.");

        try {
            communicationService.requestDID(id);
        } catch (IOException e) {
            throw e;
        } catch (DIDGenerationRequestException e) {
            throw e;
        }

        log.info("DID successfully requested via CommunicationService to ID&P.");

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

        log.info("Product Offering created with id " + productOffering.getId() + ".");

        return productOffering;
    }

    public void delete(String id) throws NotExistingEntityException {

        log.info("Received request to delete Product Offering with id " + id + ".");

        Optional<ProductOffering> toDelete = productOfferingRepository.findByProductOfferingId(id);
        if(!toDelete.isPresent())
            throw new NotExistingEntityException("Product Offering with id " + id + " not found in DB.");

        productOfferingRepository.delete(toDelete.get());

        log.info("Product Offering " + id + " deleted.");
    }

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

    public ProductOffering patch(String id, ProductOfferingUpdate productOfferingUpdate)
            throws NotExistingEntityException {

        log.info("Received request to patch Product Offering with id " + id + ".");

        Optional<ProductOffering> toUpdate = productOfferingRepository.findByProductOfferingId(id);
        if(!toUpdate.isPresent())
            throw new NotExistingEntityException("Product Offering with id " + id + " not found in DB.");

        ProductOffering productOffering = toUpdate.get();

        final String baseType = productOfferingUpdate.getBaseType();
        if(baseType != null)
            productOffering.setBaseType(baseType);

        final String schemaLocation = productOfferingUpdate.getSchemaLocation();
        if(schemaLocation != null)
            productOffering.setSchemaLocation(schemaLocation);

        final String type = productOfferingUpdate.getType();
        if(type != null)
            productOffering.setType(type);

        final List<AgreementRef> agreement = productOfferingUpdate.getAgreement();
        if(agreement != null) {
            if(productOffering.getAgreement() != null) {
                productOffering.getAgreement().clear();
                productOffering.getAgreement().addAll(agreement);
            }
            else
                productOffering.setAgreement(agreement);
        }
        else
            productOffering.setAgreement((List<AgreementRef>) Hibernate.unproxy(productOffering.getAgreement()));

        final List<AttachmentRefOrValue> attachment = productOfferingUpdate.getAttachment();
        if(attachment != null) {
            if(productOffering.getAttachment() != null) {
                productOffering.getAttachment().clear();
                productOffering.getAttachment().addAll(attachment);
            }
            else
                productOffering.setAttachment(attachment);
        }
        else
            productOffering.setAttachment((List<AttachmentRefOrValue>)
                    Hibernate.unproxy(productOffering.getAttachment()));

        final List<BundledProductOffering> bundledProductOffering = productOfferingUpdate.getBundledProductOffering();
        if(bundledProductOffering != null) {
            if(productOffering.getBundledProductOffering() != null) {
                productOffering.getBundledProductOffering().clear();
                productOffering.getBundledProductOffering().addAll(bundledProductOffering);
            }
            else
                productOffering.setBundledProductOffering(bundledProductOffering);
        }
        else {
            productOffering.setBundledProductOffering((List<BundledProductOffering>)
                    Hibernate.unproxy(productOffering.getBundledProductOffering()));
            if(productOffering.getBundledProductOffering() != null) {
                for (BundledProductOffering bpo : productOffering.getBundledProductOffering())
                    bpo.setBundledProductOfferingOption((BundledProductOfferingOption)
                            Hibernate.unproxy(bpo.getBundledProductOfferingOption()));
            }
        }

        final List<CategoryRef> category = productOfferingUpdate.getCategory();
        if(category != null) {
            if(productOffering.getCategory() != null) {
                productOffering.getCategory().clear();
                productOffering.getCategory().addAll(category);
            }
            else
                productOffering.setCategory(category);
        }
        else
            productOffering.setCategory((List<CategoryRef>) Hibernate.unproxy(productOffering.getCategory()));

        final List<ChannelRef> channel = productOfferingUpdate.getChannel();
        if(channel != null) {
            if(productOffering.getChannel() != null) {
                productOffering.getChannel().clear();
                productOffering.getChannel().addAll(channel);
            }
            else
                productOffering.setChannel(channel);
        }
        else
            productOffering.setChannel((List<ChannelRef>) Hibernate.unproxy(productOffering.getChannel()));

        final String description = productOfferingUpdate.getDescription();
        if(description != null)
            productOffering.setDescription(description);

        final Boolean isBundle = productOfferingUpdate.isIsBundle();
        if(isBundle != null)
            productOffering.setIsBundle(isBundle);

        final Boolean isSellable = productOfferingUpdate.isIsSellable();
        if(isSellable != null)
            productOffering.setIsSellable(isSellable);

        productOffering.setLastUpdate(OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")).toString());

        final String lifecycleStatus = productOfferingUpdate.getLifecycleStatus();
        if(lifecycleStatus != null)
            productOffering.setLifecycleStatus(lifecycleStatus);

        final List<MarketSegmentRef> marketSegment = productOfferingUpdate.getMarketSegment();
        if(marketSegment != null) {
            if(productOffering.getMarketSegment() != null) {
                productOffering.getMarketSegment().clear();
                productOffering.getMarketSegment().addAll(marketSegment);
            }
            else
                productOffering.setMarketSegment(marketSegment);
        }
        else
            productOffering.setMarketSegment((List<MarketSegmentRef>)
                    Hibernate.unproxy(productOffering.getMarketSegment()));

        final String name = productOfferingUpdate.getName();
        if(name != null)
            productOffering.setName(name);

        final List<PlaceRef> place = productOfferingUpdate.getPlace();
        if(place != null) {
            if(productOffering.getPlace() != null) {
                productOffering.getPlace().clear();
                productOffering.getPlace().addAll(place);
            }
            else
                productOffering.setPlace(place);
        }
        else
            productOffering.setPlace((List<PlaceRef>) Hibernate.unproxy(productOffering.getPlace()));

        final List<ProductSpecificationCharacteristicValueUse> prodSpecCharValueUse =
                productOfferingUpdate.getProdSpecCharValueUse();
        if(prodSpecCharValueUse != null) {
            if(productOffering.getProdSpecCharValueUse() != null) {
                productOffering.getProdSpecCharValueUse().clear();
                productOffering.getProdSpecCharValueUse().addAll(prodSpecCharValueUse);
            }
            else
                productOffering.setProdSpecCharValueUse(prodSpecCharValueUse);
        }
        else {
            productOffering.setProdSpecCharValueUse((List<ProductSpecificationCharacteristicValueUse>)
                    Hibernate.unproxy(productOffering.getProdSpecCharValueUse()));
            if(productOffering.getProdSpecCharValueUse() != null) {
                for (ProductSpecificationCharacteristicValueUse pscvu : productOffering.getProdSpecCharValueUse()) {
                    pscvu.setProductSpecCharacteristicValue((List<ProductSpecificationCharacteristicValue>)
                            Hibernate.unproxy(pscvu.getProductSpecCharacteristicValue()));

                    pscvu.setProductSpecification((ProductSpecificationRef)
                            Hibernate.unproxy(pscvu.getProductSpecification()));
                    if(pscvu.getProductSpecification() != null)
                        pscvu.getProductSpecification().setTargetProductSchema((TargetProductSchema)
                                Hibernate.unproxy(pscvu.getProductSpecification().getTargetProductSchema()));
                }
            }
        }

        final List<ProductOfferingPriceRef> productOfferingPrice = productOfferingUpdate.getProductOfferingPrice();
        if(productOfferingPrice != null) {
            if(productOffering.getProductOfferingPrice() != null) {
                productOffering.getProductOfferingPrice().clear();
                productOffering.getProductOfferingPrice().addAll(productOfferingPrice);
            }
            else
                productOffering.setProductOfferingPrice(productOfferingPrice);
        }
        else
            productOffering.setProductOfferingPrice((List<ProductOfferingPriceRef>)
                    Hibernate.unproxy(productOffering.getProductOfferingPrice()));

        final List<ProductOfferingTerm> productOfferingTerm = productOfferingUpdate.getProductOfferingTerm();
        if(productOfferingTerm != null) {
            if(productOffering.getProductOfferingTerm() != null) {
                productOffering.getProductOfferingTerm().clear();
                productOffering.getProductOfferingTerm().addAll(productOfferingTerm);
            }
            else
                productOffering.setProductOfferingTerm(productOfferingTerm);
        }
        else
            productOffering.setProductOfferingTerm((List<ProductOfferingTerm>)
                    Hibernate.unproxy(productOffering.getProductOfferingTerm()));

        final ProductSpecificationRef productSpecification = productOfferingUpdate.getProductSpecification();
        if(productSpecification != null)
            productOffering.setProductSpecification(productSpecification);
        else {
            productOffering.setProductSpecification((ProductSpecificationRef)
                    Hibernate.unproxy(productOffering.getProductSpecification()));
            if(productOffering.getProductSpecification() != null)
                productOffering.getProductSpecification().setTargetProductSchema((TargetProductSchema)
                        Hibernate.unproxy(productOffering.getProductSpecification().getTargetProductSchema()));
        }

        final ResourceCandidateRef resourceCandidate = productOfferingUpdate.getResourceCandidate();
        if(resourceCandidate != null)
            productOffering.setResourceCandidate(resourceCandidate);
        else
            productOffering.setResourceCandidate((ResourceCandidateRef)
                    Hibernate.unproxy(productOffering.getResourceCandidate()));

        final ServiceCandidateRef serviceCandidate = productOfferingUpdate.getServiceCandidate();
        if(serviceCandidate != null)
            productOffering.setServiceCandidate(serviceCandidate);
        else
            productOffering.setServiceCandidate((ServiceCandidateRef)
                    Hibernate.unproxy(productOffering.getServiceCandidate()));

        final SLARef serviceLevelAgreement = productOfferingUpdate.getServiceLevelAgreement();
        if(serviceLevelAgreement != null)
            productOffering.setServiceLevelAgreement(serviceLevelAgreement);
        else
            productOffering.setServiceLevelAgreement((SLARef)
                    Hibernate.unproxy(productOffering.getServiceLevelAgreement()));

        final String statusReason = productOfferingUpdate.getStatusReason();
        if(statusReason != null)
            productOffering.setStatusReason(statusReason);

        final TimePeriod validFor = productOfferingUpdate.getValidFor();
        if(validFor != null)
            productOffering.setValidFor(validFor);

        final String version = productOfferingUpdate.getVersion();
        if(version != null)
            productOffering.setVersion(version);

        productOfferingRepository.save(productOffering);

        log.info("Product Offering " + id + " patched.");

        return productOffering;
    }

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
