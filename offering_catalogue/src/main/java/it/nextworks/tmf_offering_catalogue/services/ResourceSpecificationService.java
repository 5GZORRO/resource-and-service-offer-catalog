package it.nextworks.tmf_offering_catalogue.services;

import it.nextworks.tmf_offering_catalogue.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalogue.information_models.AttachmentRef;
import it.nextworks.tmf_offering_catalogue.information_models.RelatedParty;
import it.nextworks.tmf_offering_catalogue.information_models.TimePeriod;
import it.nextworks.tmf_offering_catalogue.information_models.resource.*;
import it.nextworks.tmf_offering_catalogue.repo.ResourceSpecificationRepository;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.threeten.bp.OffsetDateTime;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ResourceSpecificationService {

    private static final Logger log = LoggerFactory.getLogger(ResourceSpecificationService.class);

    private static final String protocol = "http://";
    @Value("${server.hostname}")
    private String hostname;
    @Value("${server.port}")
    private String port;
    private static final String path = "/tmf-api/resourceCatalogManagement/v2/resourceSpecification/";

    @Autowired
    private ResourceSpecificationRepository resourceSpecificationRepository;

    public ResourceSpecification create(ResourceSpecificationCreate resourceSpecificationCreate) {

        log.info("Received request to create a Resource Specification.");

        final String id = UUID.randomUUID().toString();
        ResourceSpecification resourceSpecification = new ResourceSpecification()
                .baseType(resourceSpecificationCreate.getBaseType())
                .schemaLocation(resourceSpecificationCreate.getSchemaLocation())
                .type(resourceSpecificationCreate.getType())
                .attachment(resourceSpecificationCreate.getAttachment())
                .category(resourceSpecificationCreate.getCategory())
                .description(resourceSpecificationCreate.getDescription())
                .feature(resourceSpecificationCreate.getFeature())
                .href(protocol + hostname + ":" + port + path + id)
                .id(id)
                .isBundle(resourceSpecificationCreate.isIsBundle())
                .lifecycleStatus(resourceSpecificationCreate.getLifecycleStatus())
                .name(resourceSpecificationCreate.getName())
                .relatedParty(resourceSpecificationCreate.getRelatedParty())
                .resourceSpecCharacteristic(resourceSpecificationCreate.getResourceSpecCharacteristic())
                .resourceSpecRelationship(resourceSpecificationCreate.getResourceSpecRelationship())
                .targetResourceSchema(resourceSpecificationCreate.getTargetResourceSchema())
                .validFor(resourceSpecificationCreate.getValidFor())
                .version(resourceSpecificationCreate.getVersion());

        final OffsetDateTime lastUpdate = resourceSpecificationCreate.getLastUpdate();
        if(lastUpdate != null)
            resourceSpecification.setLastUpdate(lastUpdate.toString());

        resourceSpecificationRepository.save(resourceSpecification);

        log.info("Resource Specification created with id " + resourceSpecification.getId() + ".");

        return resourceSpecification;
    }

    public void delete(String id) throws NotExistingEntityException {

        log.info("Received request to delete Resource Specification with id " + id + ".");

        Optional<ResourceSpecification> toDelete = resourceSpecificationRepository.findByResourceSpecificationId(id);
        if(!toDelete.isPresent())
            throw new NotExistingEntityException("Resource Specification with id " + id + " not found in DB.");

        resourceSpecificationRepository.delete(toDelete.get());

        log.info("Resource Specification " + id + " deleted.");
    }

    public List<ResourceSpecification> list() {

        log.info("Received request to retrieve all Resource Specifications.");

        List<ResourceSpecification> resourceSpecifications = resourceSpecificationRepository.findAll();
        for(ResourceSpecification rs : resourceSpecifications) {
            rs.setAttachment((List<AttachmentRef>) Hibernate.unproxy(rs.getAttachment()));
            rs.setFeature((List<Feature>) Hibernate.unproxy(rs.getFeature()));
            rs.setRelatedParty((List<RelatedParty>) Hibernate.unproxy(rs.getRelatedParty()));

            rs.setResourceSpecCharacteristic((List<ResourceSpecCharacteristic>)
                    Hibernate.unproxy(rs.getResourceSpecCharacteristic()));
            for(ResourceSpecCharacteristic rsc : rs.getResourceSpecCharacteristic()) {
                rsc.setResourceSpecCharRelationship((List<ResourceSpecCharRelationship>)
                        Hibernate.unproxy(rsc.getResourceSpecCharRelationship()));
                rsc.setResourceSpecCharacteristicValue((List<ResourceSpecCharacteristicValue>)
                        Hibernate.unproxy(rsc.getResourceSpecCharacteristicValue()));
            }

            rs.setResourceSpecRelationship((List<ResourceSpecRelationship>)
                    Hibernate.unproxy(rs.getResourceSpecRelationship()));
            rs.setTargetResourceSchema((TargetResourceSchemaRef) Hibernate.unproxy(rs.getTargetResourceSchema()));
        }

        log.info("Resource Specifications retrieved.");

        return resourceSpecifications;
    }

    public ResourceSpecification patch(String id, ResourceSpecificationUpdate resourceSpecificationUpdate)
            throws NotExistingEntityException {

        log.info("Received request to patch Resource Specification with id " + id + ".");

        Optional<ResourceSpecification> toUpdate = resourceSpecificationRepository.findByResourceSpecificationId(id);
        if(!toUpdate.isPresent())
            throw new NotExistingEntityException("Resource Specification with id " + id + " not found in DB.");

        ResourceSpecification resourceSpecification = toUpdate.get();

        final String baseType = resourceSpecificationUpdate.getBaseType();
        if(baseType != null)
            resourceSpecification.setBaseType(baseType);

        final String schemaLocation = resourceSpecificationUpdate.getSchemaLocation();
        if(schemaLocation != null)
            resourceSpecification.setSchemaLocation(schemaLocation);

        final List<AttachmentRef> attachment = resourceSpecificationUpdate.getAttachment();
        if(attachment != null) {
            resourceSpecification.getAttachment().clear();
            resourceSpecification.getAttachment().addAll(attachment);
        }
        else
            resourceSpecification.setAttachment((List<AttachmentRef>)
                    Hibernate.unproxy(resourceSpecification.getAttachment()));

        final String category = resourceSpecificationUpdate.getCategory();
        if(category != null)
            resourceSpecification.setCategory(category);

        final String description = resourceSpecificationUpdate.getDescription();
        if(description != null)
            resourceSpecification.setDescription(description);

        final List<Feature> feature = resourceSpecificationUpdate.getFeature();
        if(feature != null) {
            resourceSpecification.getFeature().clear();
            resourceSpecification.getFeature().addAll(feature);
        }
        else
            resourceSpecification.setFeature((List<Feature>) Hibernate.unproxy(resourceSpecification.getFeature()));

        final Boolean isBundle = resourceSpecificationUpdate.isIsBundle();
        if(isBundle != null)
            resourceSpecification.setIsBundle(isBundle);

        final String lifecycleStatus = resourceSpecificationUpdate.getLifecycleStatus();
        if(lifecycleStatus != null)
            resourceSpecification.setLifecycleStatus(lifecycleStatus);

        final String name = resourceSpecificationUpdate.getName();
        if(name != null)
            resourceSpecification.setName(name);

        final List<RelatedParty> relatedParty = resourceSpecificationUpdate.getRelatedParty();
        if(relatedParty != null) {
            resourceSpecification.getRelatedParty().clear();
            resourceSpecification.getRelatedParty().addAll(relatedParty);
        }
        else
            resourceSpecification.setRelatedParty((List<RelatedParty>)
                    Hibernate.unproxy(resourceSpecification.getRelatedParty()));

        final List<ResourceSpecCharacteristic> resourceSpecCharacteristic =
                resourceSpecificationUpdate.getResourceSpecCharacteristic();
        if(resourceSpecCharacteristic != null) {
            resourceSpecification.getResourceSpecCharacteristic().clear();
            resourceSpecification.getResourceSpecCharacteristic().addAll(resourceSpecCharacteristic);
        }
        else {
            resourceSpecification.setResourceSpecCharacteristic((List<ResourceSpecCharacteristic>)
                    Hibernate.unproxy(resourceSpecification.getResourceSpecCharacteristic()));
            for(ResourceSpecCharacteristic rsc : resourceSpecification.getResourceSpecCharacteristic()) {
                rsc.setResourceSpecCharRelationship((List<ResourceSpecCharRelationship>)
                        Hibernate.unproxy(rsc.getResourceSpecCharRelationship()));
                rsc.setResourceSpecCharacteristicValue((List<ResourceSpecCharacteristicValue>)
                        Hibernate.unproxy(rsc.getResourceSpecCharacteristicValue()));
            }
        }

        final List<ResourceSpecRelationship> resourceSpecRelationship =
                resourceSpecificationUpdate.getResourceSpecRelationship();
        if(resourceSpecRelationship != null) {
            resourceSpecification.getResourceSpecRelationship().clear();
            resourceSpecification.getResourceSpecRelationship().addAll(resourceSpecRelationship);
        }
        else
            resourceSpecification.setResourceSpecRelationship((List<ResourceSpecRelationship>)
                    Hibernate.unproxy(resourceSpecification.getResourceSpecRelationship()));

        final TargetResourceSchemaRef targetResourceSchema = resourceSpecificationUpdate.getTargetResourceSchema();
        if(targetResourceSchema != null)
            resourceSpecification.setTargetResourceSchema(targetResourceSchema);
        else
            resourceSpecification.setTargetResourceSchema((TargetResourceSchemaRef)
                    Hibernate.unproxy(resourceSpecification.getTargetResourceSchema()));

        final TimePeriod validFor = resourceSpecificationUpdate.getValidFor();
        if(validFor != null)
            resourceSpecification.setValidFor(validFor);

        final String version = resourceSpecificationUpdate.getVersion();
        if(version != null)
            resourceSpecification.setVersion(version);

        resourceSpecificationRepository.save(resourceSpecification);

        log.info("Resource Specification " + id + " patched.");

        return resourceSpecification;
    }

    public ResourceSpecification get(String id) throws NotExistingEntityException {

        log.info("Received request to retrieve Resource Specification with id " + id + ".");

        Optional<ResourceSpecification> retrieved = resourceSpecificationRepository.findByResourceSpecificationId(id);
        if(!retrieved.isPresent())
            throw new NotExistingEntityException("Resource Specification with id " + id + " not found in DB.");

        ResourceSpecification rs = retrieved.get();

        rs.setAttachment((List<AttachmentRef>) Hibernate.unproxy(rs.getAttachment()));
        rs.setFeature((List<Feature>) Hibernate.unproxy(rs.getFeature()));
        rs.setRelatedParty((List<RelatedParty>) Hibernate.unproxy(rs.getRelatedParty()));

        rs.setResourceSpecCharacteristic((List<ResourceSpecCharacteristic>)
                Hibernate.unproxy(rs.getResourceSpecCharacteristic()));
        for(ResourceSpecCharacteristic rsc : rs.getResourceSpecCharacteristic()) {
            rsc.setResourceSpecCharRelationship((List<ResourceSpecCharRelationship>)
                    Hibernate.unproxy(rsc.getResourceSpecCharRelationship()));
            rsc.setResourceSpecCharacteristicValue((List<ResourceSpecCharacteristicValue>)
                    Hibernate.unproxy(rsc.getResourceSpecCharacteristicValue()));
        }

        rs.setResourceSpecRelationship((List<ResourceSpecRelationship>)
                Hibernate.unproxy(rs.getResourceSpecRelationship()));
        rs.setTargetResourceSchema((TargetResourceSchemaRef) Hibernate.unproxy(rs.getTargetResourceSchema()));

        log.info("Resource Specification " + id + " retrieved.");

        return rs;
    }
}
