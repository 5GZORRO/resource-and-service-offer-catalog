package it.nextworks.tmf_offering_catalog.services;

import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.*;
import it.nextworks.tmf_offering_catalog.information_models.product.*;
import it.nextworks.tmf_offering_catalog.repo.ProductSpecificationRepository;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ProductSpecificationService {

    private static final Logger log = LoggerFactory.getLogger(ProductSpecificationService.class);

    private static final String protocol = "http://";
    @Value("${server.hostname}")
    private String hostname;
    @Value("${server.port}")
    private String port;
    private static final String path = "/tmf-api/productCatalogManagement/v4/productSpecification/";

    @Autowired
    private ProductSpecificationRepository productSpecificationRepository;

    public ProductSpecification create(ProductSpecificationCreate productSpecificationCreate) {

        log.info("Received request to create a Product Specification.");

        final String id = UUID.randomUUID().toString();
        ProductSpecification productSpecification = new ProductSpecification()
                .baseType(productSpecificationCreate.getBaseType())
                .schemaLocation(productSpecificationCreate.getSchemaLocation())
                .type(productSpecificationCreate.getType())
                .attachment(productSpecificationCreate.getAttachment())
                .brand(productSpecificationCreate.getBrand())
                .bundledProductSpecification(productSpecificationCreate.getBundledProductSpecification())
                .description(productSpecificationCreate.getDescription())
                .href(protocol + hostname + ":" + port + path + id)
                .id(id)
                .isBundle(productSpecificationCreate.isIsBundle())
                .lifecycleStatus(productSpecificationCreate.getLifecycleStatus())
                .name(productSpecificationCreate.getName())
                .productNumber(productSpecificationCreate.getProductNumber())
                .productSpecCharacteristic(productSpecificationCreate.getProductSpecCharacteristic())
                .productSpecificationRelationship(productSpecificationCreate.getProductSpecificationRelationship())
                .relatedParty(productSpecificationCreate.getRelatedParty())
                .resourceSpecification(productSpecificationCreate.getResourceSpecification())
                .serviceSpecification(productSpecificationCreate.getServiceSpecification())
                .targetProductSchema(productSpecificationCreate.getTargetProductSchema())
                .validFor(productSpecificationCreate.getValidFor())
                .version(productSpecificationCreate.getVersion());

        final OffsetDateTime lastUpdate = productSpecificationCreate.getLastUpdate();
        if(lastUpdate != null)
            productSpecification.setLastUpdate(lastUpdate.toString());

        productSpecificationRepository.save(productSpecification);

        log.info("Product Specification created with id " + productSpecification.getId() + ".");

        return productSpecification;
    }

    public void delete(String id) throws NotExistingEntityException {

        log.info("Received request to delete Product Specification with id " + id + ".");

        Optional<ProductSpecification> toDelete = productSpecificationRepository.findByProductSpecificationId(id);
        if(!toDelete.isPresent())
            throw new NotExistingEntityException("Product Specification with id " + id + " not found in DB.");

        productSpecificationRepository.delete(toDelete.get());

        log.info("Product Specification " + id + " deleted.");
    }

    public List<ProductSpecification> list() {

        log.info("Received request to retrieve all Product Specifications.");

        List<ProductSpecification> productSpecifications = productSpecificationRepository.findAll();
        for(ProductSpecification ps : productSpecifications) {
            ps.setAttachment((List<AttachmentRefOrValue>) Hibernate.unproxy(ps.getAttachment()));
            ps.setBundledProductSpecification((List<BundledProductSpecification>)
                    Hibernate.unproxy(ps.getBundledProductSpecification()));

            ps.setProductSpecCharacteristic((List<ProductSpecificationCharacteristic>)
                    Hibernate.unproxy(ps.getProductSpecCharacteristic()));
            for(ProductSpecificationCharacteristic psc : ps.getProductSpecCharacteristic()) {
                psc.setProductSpecCharRelationship((List<ProductSpecificationCharacteristicRelationship>)
                        Hibernate.unproxy(psc.getProductSpecCharRelationship()));
                psc.setProductSpecCharacteristicValue((List<ProductSpecificationCharacteristicValue>)
                        Hibernate.unproxy(psc.getProductSpecCharacteristicValue()));
            }

            ps.setProductSpecificationRelationship((List<ProductSpecificationRelationship>)
                    Hibernate.unproxy(ps.getProductSpecificationRelationship()));
            ps.setRelatedParty((List<RelatedParty>) Hibernate.unproxy(ps.getRelatedParty()));
            ps.setResourceSpecification((List<ResourceSpecificationRef>)
                    Hibernate.unproxy(ps.getResourceSpecification()));

            ps.setServiceSpecification((List<ServiceSpecificationRef>) Hibernate.unproxy(ps.getServiceSpecification()));
            for(ServiceSpecificationRef ssr : ps.getServiceSpecification())
                ssr.setTargetServiceSchema((TargetServiceSchema) Hibernate.unproxy(ssr.getTargetServiceSchema()));

            ps.setTargetProductSchema((TargetProductSchema) Hibernate.unproxy(ps.getTargetProductSchema()));
        }

        log.info("Product Specifications retrieved.");

        return productSpecifications;
    }

    public ProductSpecification patch(String id, ProductSpecificationUpdate productSpecificationUpdate)
        throws NotExistingEntityException {

        log.info("Received request to patch Product Specification with id " + id + ".");

        Optional<ProductSpecification> toUpdate = productSpecificationRepository.findByProductSpecificationId(id);
        if(!toUpdate.isPresent())
            throw new NotExistingEntityException("Product Specification with id " + id + " not found in DB.");

        ProductSpecification productSpecification = toUpdate.get();

        final String baseType = productSpecificationUpdate.getBaseType();
        if(baseType != null)
            productSpecification.setBaseType(baseType);

        final String schemaLocation = productSpecificationUpdate.getSchemaLocation();
        if(schemaLocation != null)
            productSpecification.setSchemaLocation(schemaLocation);

        final String type = productSpecificationUpdate.getType();
        if(type != null)
            productSpecification.setType(type);

        final List<AttachmentRefOrValue> attachment = productSpecificationUpdate.getAttachment();
        if(attachment != null) {
            productSpecification.getAttachment().clear();
            productSpecification.getAttachment().addAll(attachment);
        }
        else
            productSpecification.setAttachment((List<AttachmentRefOrValue>)
                    Hibernate.unproxy(productSpecification.getAttachment()));

        final String brand = productSpecificationUpdate.getBrand();
        if(brand != null)
            productSpecification.setBrand(brand);

        final List<BundledProductSpecification> bundledProductSpecification =
                productSpecificationUpdate.getBundledProductSpecification();
        if(bundledProductSpecification != null) {
            productSpecification.getBundledProductSpecification().clear();
            productSpecification.getBundledProductSpecification().addAll(bundledProductSpecification);
        }
        else
            productSpecification.setBundledProductSpecification((List<BundledProductSpecification>)
                    Hibernate.unproxy(productSpecification.getBundledProductSpecification()));

        final String description = productSpecificationUpdate.getDescription();
        if(description != null)
            productSpecification.setDescription(description);

        final Boolean isBundle = productSpecificationUpdate.isIsBundle();
        if(isBundle != null)
            productSpecification.setIsBundle(isBundle);

        productSpecification.setLastUpdate(OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")).toString());

        final String lifecycleStatus = productSpecificationUpdate.getLifecycleStatus();
        if(lifecycleStatus != null)
            productSpecification.setLifecycleStatus(lifecycleStatus);

        final String name = productSpecificationUpdate.getName();
        if(name != null)
            productSpecification.setName(name);

        final String productNumber = productSpecificationUpdate.getProductNumber();
        if(productNumber != null)
            productSpecification.setProductNumber(productNumber);

        final List<ProductSpecificationCharacteristic> productSpecCharacteristic =
                productSpecificationUpdate.getProductSpecCharacteristic();
        if(productSpecCharacteristic != null) {
            productSpecification.getProductSpecCharacteristic().clear();
            productSpecification.getProductSpecCharacteristic().addAll(productSpecCharacteristic);
        }
        else {
            productSpecification.setProductSpecCharacteristic((List<ProductSpecificationCharacteristic>)
                    Hibernate.unproxy(productSpecification.getProductSpecCharacteristic()));
            for(ProductSpecificationCharacteristic psc : productSpecification.getProductSpecCharacteristic()) {
                psc.setProductSpecCharRelationship((List<ProductSpecificationCharacteristicRelationship>)
                        Hibernate.unproxy(psc.getProductSpecCharRelationship()));
                psc.setProductSpecCharacteristicValue((List<ProductSpecificationCharacteristicValue>)
                        Hibernate.unproxy(psc.getProductSpecCharacteristicValue()));
            }
        }

        final List<ProductSpecificationRelationship> productSpecificationRelationship =
                productSpecificationUpdate.getProductSpecificationRelationship();
        if(productSpecificationRelationship != null) {
            productSpecification.getProductSpecificationRelationship().clear();
            productSpecification.getProductSpecificationRelationship().addAll(productSpecificationRelationship);
        }
        else
            productSpecification.setProductSpecificationRelationship((List<ProductSpecificationRelationship>)
                    Hibernate.unproxy(productSpecification.getProductSpecificationRelationship()));

        final List<RelatedParty> relatedParty = productSpecificationUpdate.getRelatedParty();
        if(relatedParty != null) {
            productSpecification.getRelatedParty().clear();
            productSpecification.getRelatedParty().addAll(relatedParty);
        }
        else
            productSpecification.setRelatedParty((List<RelatedParty>)
                    Hibernate.unproxy(productSpecification.getRelatedParty()));

        final List<ResourceSpecificationRef> resourceSpecification =
                productSpecificationUpdate.getResourceSpecification();
        if(resourceSpecification != null) {
            productSpecification.getResourceSpecification().clear();
            productSpecification.getResourceSpecification().addAll(resourceSpecification);
        }
        else
            productSpecification.setResourceSpecification((List<ResourceSpecificationRef>)
                    Hibernate.unproxy(productSpecification.getResourceSpecification()));

        final List<ServiceSpecificationRef> serviceSpecification = productSpecificationUpdate.getServiceSpecification();
        if(serviceSpecification != null) {
            productSpecification.getServiceSpecification().clear();
            productSpecification.getServiceSpecification().addAll(serviceSpecification);
        }
        else {
            productSpecification.setServiceSpecification((List<ServiceSpecificationRef>)
                    Hibernate.unproxy(productSpecification.getServiceSpecification()));
            for(ServiceSpecificationRef ssr : productSpecification.getServiceSpecification())
                ssr.setTargetServiceSchema((TargetServiceSchema) Hibernate.unproxy(ssr.getTargetServiceSchema()));
        }

        final TargetProductSchema targetProductSchema = productSpecificationUpdate.getTargetProductSchema();
        if(targetProductSchema != null)
            productSpecification.setTargetProductSchema(targetProductSchema);
        else
            productSpecification.setTargetProductSchema((TargetProductSchema)
                    Hibernate.unproxy(productSpecification.getTargetProductSchema()));

        final TimePeriod validFor = productSpecificationUpdate.getValidFor();
        if(validFor != null)
            productSpecification.setValidFor(validFor);

        final String version = productSpecificationUpdate.getVersion();
        if(version != null)
            productSpecification.setVersion(version);

        productSpecificationRepository.save(productSpecification);

        log.info("Product Specification " + id + " patched.");

        return productSpecification;
    }

    public ProductSpecification get(String id) throws NotExistingEntityException {

        log.info("Received request to retrieve Product Specification with id " + id + ".");

        Optional<ProductSpecification> retrieved = productSpecificationRepository.findByProductSpecificationId(id);
        if(!retrieved.isPresent())
            throw new NotExistingEntityException("Product Specification with id " + id + " not found in DB.");

        ProductSpecification ps = retrieved.get();

        ps.setAttachment((List<AttachmentRefOrValue>) Hibernate.unproxy(ps.getAttachment()));
        ps.setBundledProductSpecification((List<BundledProductSpecification>)
                Hibernate.unproxy(ps.getBundledProductSpecification()));

        ps.setProductSpecCharacteristic((List<ProductSpecificationCharacteristic>)
                Hibernate.unproxy(ps.getProductSpecCharacteristic()));
        for(ProductSpecificationCharacteristic psc : ps.getProductSpecCharacteristic()) {
            psc.setProductSpecCharRelationship((List<ProductSpecificationCharacteristicRelationship>)
                    Hibernate.unproxy(psc.getProductSpecCharRelationship()));
            psc.setProductSpecCharacteristicValue((List<ProductSpecificationCharacteristicValue>)
                    Hibernate.unproxy(psc.getProductSpecCharacteristicValue()));
        }

        ps.setProductSpecificationRelationship((List<ProductSpecificationRelationship>)
                Hibernate.unproxy(ps.getProductSpecificationRelationship()));
        ps.setRelatedParty((List<RelatedParty>) Hibernate.unproxy(ps.getRelatedParty()));
        ps.setResourceSpecification((List<ResourceSpecificationRef>)
                Hibernate.unproxy(ps.getResourceSpecification()));

        ps.setServiceSpecification((List<ServiceSpecificationRef>) Hibernate.unproxy(ps.getServiceSpecification()));
        for(ServiceSpecificationRef ssr : ps.getServiceSpecification())
            ssr.setTargetServiceSchema((TargetServiceSchema) Hibernate.unproxy(ssr.getTargetServiceSchema()));

        ps.setTargetProductSchema((TargetProductSchema) Hibernate.unproxy(ps.getTargetProductSchema()));

        log.info("Product Specification " + id + " retrieved.");

        return ps;
    }
}
