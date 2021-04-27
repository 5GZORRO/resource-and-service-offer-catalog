package it.nextworks.tmf_offering_catalog.components;

import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.kafka.*;
import it.nextworks.tmf_offering_catalog.information_models.product.*;
import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceSpecification;
import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceSpecificationUpdate;
import it.nextworks.tmf_offering_catalog.information_models.service.ServiceSpecification;
import it.nextworks.tmf_offering_catalog.information_models.service.ServiceSpecificationUpdate;
import it.nextworks.tmf_offering_catalog.repo.*;
import it.nextworks.tmf_offering_catalog.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.threeten.bp.OffsetDateTime;

import java.util.List;
import java.util.Optional;

@Component
public class ExternalProductOfferingConsumer {

    private static final Logger log = LoggerFactory.getLogger(ExternalProductOfferingConsumer.class);

    @Autowired
    private ProductOfferingPriceRepository productOfferingPriceRepository;

    @Autowired
    private ExternalProductOfferingPriceRepository externalProductOfferingPriceRepository;

    @Autowired
    private ProductOfferingPriceService productOfferingPriceService;

    @Autowired
    private ProductSpecificationRepository productSpecificationRepository;

    @Autowired
    private ExternalProductSpecificationRepository externalProductSpecificationRepository;

    @Autowired
    private ProductSpecificationService productSpecificationService;

    @Autowired
    private ResourceSpecificationRepository resourceSpecificationRepository;

    @Autowired
    private ExternalResourceSpecificationRepository externalResourceSpecificationRepository;

    @Autowired
    private ResourceSpecificationService resourceSpecificationService;

    @Autowired
    private ServiceSpecificationRepository serviceSpecificationRepository;

    @Autowired
    private ExternalServiceSpecificationRepository externalServiceSpecificationRepository;

    @Autowired
    private ServiceSpecificationService serviceSpecificationService;

    @Autowired
    private ProductOfferingRepository productOfferingRepository;

    @Autowired
    private ProductOfferingStatusRepository productOfferingStatusRepository;

    @Autowired
    private ProductOfferingService productOfferingService;

    @KafkaListener(
            topics = "operatora-dlt-product-offerings",
            containerFactory = "kafkaListenerContainerFactory")
    public void listener(ExternalProductOffering externalProductOffering) {

        log.info("Receiving External Product Offering");

        if(externalProductOffering == null) {
            log.warn("Received empty External Product Offering.");
            return;
        }

        ProductOffering po = externalProductOffering.getProductOffering();
        if(po == null) {
            log.warn("Received empty Product Offering in External Product Offering.");
            return;
        }

        String did = externalProductOffering.getDid();
        if(did == null) {
            log.warn("Received empty DID in External Product Offering.");
            return;
        }

        // TODO check RETIRE enum type

        syncProductOfferingPrices(externalProductOffering.getProductOfferingPrices());
        syncProductSpecification(externalProductOffering.getProductSpecification());
        syncResourceSpecifications(externalProductOffering.getResourceSpecifications());
        syncServiceSpecifications(externalProductOffering.getServiceSpecifications());
        syncProductOffering(po, did);
    }

    private void syncProductOfferingPrices(List<ProductOfferingPrice> productOfferingPrices) {
        if(productOfferingPrices == null)
            return;

        for(ProductOfferingPrice pop : productOfferingPrices) {
            String id = pop.getId();

            log.info("Syncing Product Offering Price " + id + ".");

            Optional<ProductOfferingPrice> optionalPop =
                    productOfferingPriceRepository.findByProductOfferingPriceId(id);
            if(!optionalPop.isPresent()) {
                productOfferingPriceRepository.save(pop);
                externalProductOfferingPriceRepository.save(new ExternalProductOfferingPrice(id));

                log.info("Synced Product Offering Price " + id + " (new).");
                continue;
            }

            Optional<ExternalProductOfferingPrice> ePop = externalProductOfferingPriceRepository.findById(id);
            if(!ePop.isPresent()) {
                log.info("Product Offering Price " + id + " not external, skip.");
                continue;
            }

            String popLastUpdate = pop.getLastUpdate();
            OffsetDateTime receivedLastUpdate = OffsetDateTime.parse(popLastUpdate);
            OffsetDateTime storedLastUpdate = OffsetDateTime.parse(optionalPop.get().getLastUpdate());

            if(receivedLastUpdate.compareTo(storedLastUpdate) <= 0) {
                log.info("Product Offering Price " + id + " up to date, skip.");
                continue;
            }

            ProductOfferingPriceUpdate popUpdate = new ProductOfferingPriceUpdate()
                    .baseType(pop.getBaseType())
                    .schemaLocation(pop.getSchemaLocation())
                    .type(pop.getType())
                    .bundledPopRelationship(pop.getBundledPopRelationship())
                    .constraint(pop.getConstraint())
                    .description(pop.getDescription())
                    .isBundle(pop.isIsBundle())
                    .lifecycleStatus(pop.getLifecycleStatus())
                    .name(pop.getName())
                    .percentage(pop.getPercentage())
                    .place(pop.getPlace())
                    .popRelationship(pop.getPopRelationship())
                    .price(pop.getPrice())
                    .priceType(pop.getPriceType())
                    .pricingLogicAlgorithm(pop.getPricingLogicAlgorithm())
                    .prodSpecCharValueUse(pop.getProdSpecCharValueUse())
                    .productOfferingTerm(pop.getProductOfferingTerm())
                    .recurringChargePeriodLength(pop.getRecurringChargePeriodLength())
                    .recurringChargePeriodType(pop.getRecurringChargePeriodType())
                    .tax(pop.getTax())
                    .unitOfMeasure(pop.getUnitOfMeasure())
                    .validFor(pop.getValidFor())
                    .version(pop.getVersion());
            try {
                productOfferingPriceService.patch(id, popUpdate, popLastUpdate);
            } catch (NotExistingEntityException e) {
                log.error("External " + e.getMessage());
            }

            log.info("Synced Product Offering Price " + id + " (update).");
        }
    }

    private void syncProductSpecification(ProductSpecification ps) {
        if(ps == null)
            return;

        String id = ps.getId();

        log.info("Syncing Product Specification " + id + ".");

        Optional<ProductSpecification> optionalPs = productSpecificationRepository.findByProductSpecificationId(id);
        if(!optionalPs.isPresent()) {
            productSpecificationRepository.save(ps);
            externalProductSpecificationRepository.save(new ExternalProductSpecification(id));

            log.info("Synced Product Specification " + id + " (new).");
            return;
        }

        Optional<ExternalProductSpecification> ePs = externalProductSpecificationRepository.findById(id);
        if(!ePs.isPresent()) {
            log.info("Product Specification " + id + " not external, skip.");
            return;
        }

        String psLastUpdate = ps.getLastUpdate();
        OffsetDateTime receivedLastUpdate = OffsetDateTime.parse(psLastUpdate);
        OffsetDateTime storedLastUpdate = OffsetDateTime.parse(optionalPs.get().getLastUpdate());

        if(receivedLastUpdate.compareTo(storedLastUpdate) <= 0) {
            log.info("Product Specification " + id + " up to date, skip.");
            return;
        }

        ProductSpecificationUpdate psUpdate = new ProductSpecificationUpdate()
                .baseType(ps.getBaseType())
                .schemaLocation(ps.getSchemaLocation())
                .type(ps.getType())
                .attachment(ps.getAttachment())
                .brand(ps.getBrand())
                .bundledProductSpecification(ps.getBundledProductSpecification())
                .description(ps.getDescription())
                .isBundle(ps.isIsBundle())
                .lifecycleStatus(ps.getLifecycleStatus())
                .name(ps.getName())
                .productNumber(ps.getProductNumber())
                .productSpecCharacteristic(ps.getProductSpecCharacteristic())
                .productSpecificationRelationship(ps.getProductSpecificationRelationship())
                .relatedParty(ps.getRelatedParty())
                .resourceSpecification(ps.getResourceSpecification())
                .serviceSpecification(ps.getServiceSpecification())
                .targetProductSchema(ps.getTargetProductSchema())
                .validFor(ps.getValidFor())
                .version(ps.getVersion());
        try {
            productSpecificationService.patch(id, psUpdate, psLastUpdate);
        } catch (NotExistingEntityException e) {
            log.error("External " + e.getMessage());
        }

        log.info("Synced Product Specification " + id + " (update).");
    }

    private void syncResourceSpecifications(List<ResourceSpecification> resourceSpecifications) {
        if(resourceSpecifications == null)
            return;

        for(ResourceSpecification rs : resourceSpecifications) {
            String id = rs.getId();

            log.info("Syncing Resource Specification " + id + ".");

            Optional<ResourceSpecification> optionalRs =
                    resourceSpecificationRepository.findByResourceSpecificationId(id);
            if(!optionalRs.isPresent()) {
                resourceSpecificationRepository.save(rs);
                externalResourceSpecificationRepository.save(new ExternalResourceSpecification(id));

                log.info("Synced Resource Specification " + id + " (new).");
                continue;
            }

            Optional<ExternalResourceSpecification> eRs = externalResourceSpecificationRepository.findById(id);
            if(!eRs.isPresent()) {
                log.info("Resource Specification " + id + " not external, skip.");
                continue;
            }

            String rsLastUpdate = rs.getLastUpdate();
            OffsetDateTime receivedLastUpdate = OffsetDateTime.parse(rsLastUpdate);
            OffsetDateTime storedLastUpdate = OffsetDateTime.parse(optionalRs.get().getLastUpdate());

            if(receivedLastUpdate.compareTo(storedLastUpdate) <= 0) {
                log.info("Resource Specification " + id + " up to date, skip.");
                continue;
            }

            ResourceSpecificationUpdate rsUpdate = new ResourceSpecificationUpdate()
                    .baseType(rs.getBaseType())
                    .schemaLocation(rs.getSchemaLocation())
                    .attachment(rs.getAttachment())
                    .category(rs.getCategory())
                    .description(rs.getDescription())
                    .feature(rs.getFeature())
                    .isBundle(rs.isIsBundle())
                    .lifecycleStatus(rs.getLifecycleStatus())
                    .name(rs.getName())
                    .relatedParty(rs.getRelatedParty())
                    .resourceSpecCharacteristic(rs.getResourceSpecCharacteristic())
                    .resourceSpecRelationship(rs.getResourceSpecRelationship())
                    .targetResourceSchema(rs.getTargetResourceSchema())
                    .validFor(rs.getValidFor())
                    .version(rs.getVersion());
            try {
                resourceSpecificationService.patch(id, rsUpdate, rsLastUpdate);
            } catch (NotExistingEntityException e) {
                log.error("External " + e.getMessage());
            }

            log.info("Synced Resource Specification " + id + " (update).");
        }
    }

    private void syncServiceSpecifications(List<ServiceSpecification> serviceSpecifications) {
        if(serviceSpecifications == null)
            return;

        for(ServiceSpecification ss: serviceSpecifications) {
            String id = ss.getId();

            log.info("Syncing Service Specification " + id + ".");

            Optional<ServiceSpecification> optionalSs = serviceSpecificationRepository.findByServiceSpecificationId(id);
            if(!optionalSs.isPresent()) {
                serviceSpecificationRepository.save(ss);
                externalServiceSpecificationRepository.save(new ExternalServiceSpecification(id));

                log.info("Synced Service Specification " + id + " (new).");
                continue;
            }

            Optional<ExternalServiceSpecification> eSs = externalServiceSpecificationRepository.findById(id);
            if(!eSs.isPresent()) {
                log.info("Service Specification " + id + " not external, skip.");
                continue;
            }

            String ssLastUpdate = ss.getLastUpdate();
            OffsetDateTime receivedLastUpdate = OffsetDateTime.parse(ssLastUpdate);
            OffsetDateTime storedLastUpdate = OffsetDateTime.parse(optionalSs.get().getLastUpdate());

            if(receivedLastUpdate.compareTo(storedLastUpdate) <= 0) {
                log.info("Service Specification " + id + " up to date, skip.");
                continue;
            }

            ServiceSpecificationUpdate ssUpdate = new ServiceSpecificationUpdate()
                 .baseType(ss.getBaseType())
                 .schemaLocation(ss.getSchemaLocation())
                 .type(ss.getType())
                 .attachment(ss.getAttachment())
                 .description(ss.getDescription())
                 .isBundle(ss.isIsBundle())
                 .lifecycleStatus(ss.getLifecycleStatus())
                 .name(ss.getName())
                 .relatedParty(ss.getRelatedParty())
                 .resourceSpecification(ss.getResourceSpecification())
                 .serviceLevelSpecification(ss.getServiceLevelSpecification())
                 .serviceSpecCharacteristic(ss.getServiceSpecCharacteristic())
                 .serviceSpecRelationship(ss.getServiceSpecRelationship())
                 .targetServiceSchema(ss.getTargetServiceSchema())
                 .validFor(ss.getValidFor())
                 .version(ss.getVersion());
            try {
                serviceSpecificationService.patch(id, ssUpdate, ssLastUpdate);
            } catch (NotExistingEntityException e) {
                log.error("External " + e.getMessage());
            }

            log.info("Synced Service Specification " + id + " (update).");
        }
    }

    private void syncProductOffering(ProductOffering po, String did) {
        String id = po.getId();

        log.info("Syncing Product Offering " + id + ".");

        Optional<ProductOffering> optionalPo = productOfferingRepository.findByProductOfferingId(id);
        if(!optionalPo.isPresent()) {
            productOfferingRepository.save(po);

            ProductOfferingStatus s = new ProductOfferingStatus()
                    .catalogId(id)
                    .did(did)
                    .status(ProductOfferingStatesEnum.EXTERNAL);
            productOfferingStatusRepository.save(s);

            log.info("Synced Product Offering " + id + " (new).");
            return;
        }

        Optional<ProductOfferingStatus> optionalPos = productOfferingStatusRepository.findById(id);
        if(!optionalPos.isPresent()) {
            log.error("Product Offering Status " + id + " not found in DB.");
            return;
        }
        if(optionalPos.get().getStatus() != ProductOfferingStatesEnum.EXTERNAL) {
            log.info("Product Offering " + id + " not external, skip.");
            return;
        }

        String poLastUpdate = po.getLastUpdate();
        OffsetDateTime receivedLastUpdate = OffsetDateTime.parse(poLastUpdate);
        OffsetDateTime storedLastUpdate = OffsetDateTime.parse(optionalPo.get().getLastUpdate());

        if(receivedLastUpdate.compareTo(storedLastUpdate) <= 0) {
            log.info("Product Offering " + id + " up to date, skip.");
            return;
        }

        ProductOfferingUpdate poUpdate = new ProductOfferingUpdate()
                .baseType(po.getBaseType())
                .schemaLocation(po.getSchemaLocation())
                .type(po.getType())
                .agreement(po.getAgreement())
                .attachment(po.getAttachment())
                .bundledProductOffering(po.getBundledProductOffering())
                .category(po.getCategory())
                .channel(po.getChannel())
                .description(po.getDescription())
                .isBundle(po.isIsBundle())
                .isSellable(po.isIsSellable())
                .lifecycleStatus(po.getLifecycleStatus())
                .marketSegment(po.getMarketSegment())
                .name(po.getName())
                .place(po.getPlace())
                .prodSpecCharValueUse(po.getProdSpecCharValueUse())
                .productOfferingPrice(po.getProductOfferingPrice())
                .productOfferingTerm(po.getProductOfferingTerm())
                .productSpecification(po.getProductSpecification())
                .resourceCandidate(po.getResourceCandidate())
                .serviceCandidate(po.getServiceCandidate())
                .serviceLevelAgreement(po.getServiceLevelAgreement())
                .statusReason(po.getStatusReason())
                .validFor(po.getValidFor())
                .version(po.getVersion());
        try {
            productOfferingService.patch(id, poUpdate, poLastUpdate);
        } catch (NotExistingEntityException e) {
            log.error("External " + e.getMessage());
        }

        log.info("Synced Product Offering " + id + " (update).");
    }
}
