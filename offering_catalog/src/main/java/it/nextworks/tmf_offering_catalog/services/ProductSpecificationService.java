package it.nextworks.tmf_offering_catalog.services;

import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.common.*;
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

        log.info("Product Specification created with id " + id + ".");

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
            if(ps.getProductSpecCharacteristic() != null) {
                for (ProductSpecificationCharacteristic psc : ps.getProductSpecCharacteristic()) {
                    psc.setProductSpecCharRelationship((List<ProductSpecificationCharacteristicRelationship>)
                            Hibernate.unproxy(psc.getProductSpecCharRelationship()));
                    psc.setProductSpecCharacteristicValue((List<ProductSpecificationCharacteristicValue>)
                            Hibernate.unproxy(psc.getProductSpecCharacteristicValue()));
                }
            }

            ps.setProductSpecificationRelationship((List<ProductSpecificationRelationship>)
                    Hibernate.unproxy(ps.getProductSpecificationRelationship()));
            ps.setRelatedParty((List<RelatedParty>) Hibernate.unproxy(ps.getRelatedParty()));
            ps.setResourceSpecification((List<ResourceSpecificationRef>)
                    Hibernate.unproxy(ps.getResourceSpecification()));

            ps.setServiceSpecification((List<ServiceSpecificationRef>) Hibernate.unproxy(ps.getServiceSpecification()));
            if(ps.getServiceSpecification() != null) {
                for (ServiceSpecificationRef ssr : ps.getServiceSpecification())
                    ssr.setTargetServiceSchema((TargetServiceSchema) Hibernate.unproxy(ssr.getTargetServiceSchema()));
            }

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

        productSpecification.setBaseType(productSpecificationUpdate.getBaseType());
        productSpecification.setSchemaLocation(productSpecificationUpdate.getSchemaLocation());
        productSpecification.setType(productSpecificationUpdate.getType());

        final List<AttachmentRefOrValue> attachment = productSpecificationUpdate.getAttachment();
        if(productSpecification.getAttachment() == null)
            productSpecification.setAttachment(attachment);
        else if(attachment != null) {
            productSpecification.getAttachment().clear();
            productSpecification.getAttachment().addAll(attachment);
        }
        else
            productSpecification.getAttachment().clear();

        productSpecification.setBrand(productSpecificationUpdate.getBrand());

        final List<BundledProductSpecification> bundledProductSpecification =
                productSpecificationUpdate.getBundledProductSpecification();
        if(productSpecification.getBundledProductSpecification() == null)
            productSpecification.setBundledProductSpecification(bundledProductSpecification);
        else if(bundledProductSpecification != null) {
            productSpecification.getBundledProductSpecification().clear();
            productSpecification.getBundledProductSpecification().addAll(bundledProductSpecification);
        }
        else
            productSpecification.getBundledProductSpecification().clear();

        productSpecification.setDescription(productSpecificationUpdate.getDescription());
        productSpecification.setIsBundle(productSpecificationUpdate.isIsBundle());
        productSpecification.setLastUpdate(OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")).toString());
        productSpecification.setLifecycleStatus(productSpecificationUpdate.getLifecycleStatus());
        productSpecification.setName(productSpecificationUpdate.getName());
        productSpecification.setProductNumber(productSpecificationUpdate.getProductNumber());

        final List<ProductSpecificationCharacteristic> productSpecCharacteristic =
                productSpecificationUpdate.getProductSpecCharacteristic();
        if(productSpecification.getProductSpecCharacteristic() == null)
            productSpecification.setProductSpecCharacteristic(productSpecCharacteristic);
        else if(productSpecCharacteristic != null) {
            productSpecification.getProductSpecCharacteristic().clear();
            productSpecification.getProductSpecCharacteristic().addAll(productSpecCharacteristic);
        }
        else
            productSpecification.getProductSpecCharacteristic().clear();

        final List<ProductSpecificationRelationship> productSpecificationRelationship =
                productSpecificationUpdate.getProductSpecificationRelationship();
        if(productSpecification.getProductSpecificationRelationship() == null)
            productSpecification.setProductSpecificationRelationship(productSpecificationRelationship);
        else if(productSpecificationRelationship != null) {
            productSpecification.getProductSpecificationRelationship().clear();
            productSpecification.getProductSpecificationRelationship().addAll(productSpecificationRelationship);
        }
        else
            productSpecification.getProductSpecificationRelationship().clear();

        final List<RelatedParty> relatedParty = productSpecificationUpdate.getRelatedParty();
        if(productSpecification.getRelatedParty() == null)
            productSpecification.setRelatedParty(relatedParty);
        else if(relatedParty != null) {
            productSpecification.getRelatedParty().clear();
            productSpecification.getRelatedParty().addAll(relatedParty);
        }
        else
            productSpecification.getRelatedParty().clear();

        final List<ResourceSpecificationRef> resourceSpecification =
                productSpecificationUpdate.getResourceSpecification();
        if(productSpecification.getResourceSpecification() == null)
            productSpecification.setResourceSpecification(resourceSpecification);
        else if(resourceSpecification != null) {
            productSpecification.getResourceSpecification().clear();
            productSpecification.getResourceSpecification().addAll(resourceSpecification);
        }
        else
            productSpecification.getResourceSpecification().clear();

        final List<ServiceSpecificationRef> serviceSpecification = productSpecificationUpdate.getServiceSpecification();
        if(productSpecification.getServiceSpecification() == null)
            productSpecification.setServiceSpecification(serviceSpecification);
        else if(serviceSpecification != null) {
            productSpecification.getServiceSpecification().clear();
            productSpecification.getServiceSpecification().addAll(serviceSpecification);
        }
        else
            productSpecification.getServiceSpecification().clear();

        productSpecification.setTargetProductSchema(productSpecificationUpdate.getTargetProductSchema());
        productSpecification.setValidFor(productSpecificationUpdate.getValidFor());
        productSpecification.setVersion(productSpecificationUpdate.getVersion());

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
        if(ps.getProductSpecCharacteristic() != null) {
            for (ProductSpecificationCharacteristic psc : ps.getProductSpecCharacteristic()) {
                psc.setProductSpecCharRelationship((List<ProductSpecificationCharacteristicRelationship>)
                        Hibernate.unproxy(psc.getProductSpecCharRelationship()));
                psc.setProductSpecCharacteristicValue((List<ProductSpecificationCharacteristicValue>)
                        Hibernate.unproxy(psc.getProductSpecCharacteristicValue()));
            }
        }

        ps.setProductSpecificationRelationship((List<ProductSpecificationRelationship>)
                Hibernate.unproxy(ps.getProductSpecificationRelationship()));
        ps.setRelatedParty((List<RelatedParty>) Hibernate.unproxy(ps.getRelatedParty()));
        ps.setResourceSpecification((List<ResourceSpecificationRef>)
                Hibernate.unproxy(ps.getResourceSpecification()));

        ps.setServiceSpecification((List<ServiceSpecificationRef>) Hibernate.unproxy(ps.getServiceSpecification()));
        if(ps.getServiceSpecification() != null) {
            for (ServiceSpecificationRef ssr : ps.getServiceSpecification())
                ssr.setTargetServiceSchema((TargetServiceSchema) Hibernate.unproxy(ssr.getTargetServiceSchema()));
        }

        ps.setTargetProductSchema((TargetProductSchema) Hibernate.unproxy(ps.getTargetProductSchema()));

        log.info("Product Specification " + id + " retrieved.");

        return ps;
    }
}
