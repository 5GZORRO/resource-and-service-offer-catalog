package it.nextworks.tmf_offering_catalog.services;

import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.common.exception.StakeholderNotRegisteredException;
import it.nextworks.tmf_offering_catalog.information_models.common.AttachmentRef;
import it.nextworks.tmf_offering_catalog.information_models.common.LifecycleStatusEnumEnum;
import it.nextworks.tmf_offering_catalog.information_models.common.RelatedParty;
import it.nextworks.tmf_offering_catalog.information_models.party.Organization;
import it.nextworks.tmf_offering_catalog.information_models.party.OrganizationWrapper;
import it.nextworks.tmf_offering_catalog.information_models.resource.*;
import it.nextworks.tmf_offering_catalog.repo.ResourceSpecificationRepository;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.threeten.bp.Instant;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneId;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResourceSpecificationService {

    private static final Logger log = LoggerFactory.getLogger(ResourceSpecificationService.class);

    private static final String protocol = "http://";
    @Value("${server.hostname}")
    private String hostname;
    @Value("${server.port}")
    private String port;
    private static final String path = "/tmf-api/resourceCatalogManagement/v2/resourceSpecification/";

    @Value("${ingress:}")
    private String ingres;

    @Autowired
    private ResourceSpecificationRepository resourceSpecificationRepository;

    @Autowired
    private OrganizationService organizationService;

    public ResourceSpecification create(ResourceSpecificationCreate resourceSpecificationCreate) throws StakeholderNotRegisteredException {

        log.info("Received request to create a Resource Specification.");

        OrganizationWrapper ow;
        try {
            ow = organizationService.get();
        } catch (NotExistingEntityException e) {
            throw new StakeholderNotRegisteredException(e.getMessage());
        }
        Organization org = ow.getOrganization();

        final String id = UUID.randomUUID().toString();
        ResourceSpecification resourceSpecification = new ResourceSpecification()
                .baseType(resourceSpecificationCreate.getBaseType())
                .schemaLocation(resourceSpecificationCreate.getSchemaLocation())
                .type(resourceSpecificationCreate.getType())
                .attachment(resourceSpecificationCreate.getAttachment())
                .category(resourceSpecificationCreate.getCategory())
                .description(resourceSpecificationCreate.getDescription())
                .feature(resourceSpecificationCreate.getFeature())
                .href(StringUtils.hasText(ingres) ? ingres : (protocol + hostname + ":" + port) + path + id)
                .id(id)
                .isBundle(resourceSpecificationCreate.isIsBundle())
                .lifecycleStatus(resourceSpecificationCreate.getLifecycleStatus())
                .name(resourceSpecificationCreate.getName())
                .resourceSpecCharacteristic(resourceSpecificationCreate.getResourceSpecCharacteristic())
                .resourceSpecRelationship(resourceSpecificationCreate.getResourceSpecRelationship())
                .targetResourceSchema(resourceSpecificationCreate.getTargetResourceSchema())
                .validFor(resourceSpecificationCreate.getValidFor())
                .version(resourceSpecificationCreate.getVersion());

        final String lifecycleStatus = resourceSpecificationCreate.getLifecycleStatus();
        if(lifecycleStatus == null)
            resourceSpecification.setLifecycleStatus(LifecycleStatusEnumEnum.ACTIVE.toString());
        else {
            LifecycleStatusEnumEnum rsLifecycleStatus = LifecycleStatusEnumEnum.fromValue(lifecycleStatus);
            if(rsLifecycleStatus == null)
                resourceSpecification.setLifecycleStatus(LifecycleStatusEnumEnum.ACTIVE.toString());
            else
                resourceSpecification.setLifecycleStatus(rsLifecycleStatus.toString());
        }

        final OffsetDateTime lastUpdate = resourceSpecificationCreate.getLastUpdate();
        if(lastUpdate != null)
            resourceSpecification.setLastUpdate(lastUpdate.toString());
        else
            resourceSpecification.setLastUpdate(OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")).toString());

        List<RelatedParty> relatedParties = resourceSpecificationCreate.getRelatedParty();
        if(relatedParties != null)
            resourceSpecification.setRelatedParty(relatedParties);
        else {
            RelatedParty relatedParty = new RelatedParty()
                    .href(org.getHref())
                    .id(org.getId())
                    .role(org.getTradingName())
                    .name(org.getName())
                    .extendedInfo(ow.getStakeholderDID());
            resourceSpecification.setRelatedParty(Collections.singletonList(relatedParty));
        }

        resourceSpecificationRepository.save(resourceSpecification);

        log.info("Resource Specification created with id " + id + ".");

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

    @Transactional
    public List<ResourceSpecification> list() {

        log.info("Received request to retrieve all Resource Specifications.");

        List<ResourceSpecification> resourceSpecifications = resourceSpecificationRepository.findAll();
        for(ResourceSpecification rs : resourceSpecifications) {
            rs.setAttachment((List<AttachmentRef>) Hibernate.unproxy(rs.getAttachment()));
            rs.setFeature((List<Feature>) Hibernate.unproxy(rs.getFeature()));
            rs.setRelatedParty((List<RelatedParty>) Hibernate.unproxy(rs.getRelatedParty()));

            rs.setResourceSpecCharacteristic((List<ResourceSpecCharacteristic>)
                    Hibernate.unproxy(rs.getResourceSpecCharacteristic()));
            if(rs.getResourceSpecCharacteristic() != null) {
                for (ResourceSpecCharacteristic rsc : rs.getResourceSpecCharacteristic()) {
                    rsc.setResourceSpecCharRelationship((List<ResourceSpecCharRelationship>)
                            Hibernate.unproxy(rsc.getResourceSpecCharRelationship()));
                    rsc.setResourceSpecCharacteristicValue((List<ResourceSpecCharacteristicValue>)
                            Hibernate.unproxy(rsc.getResourceSpecCharacteristicValue()));
                }
            }

            rs.setResourceSpecRelationship((List<ResourceSpecRelationship>)
                    Hibernate.unproxy(rs.getResourceSpecRelationship()));
            rs.setTargetResourceSchema((TargetResourceSchemaRef) Hibernate.unproxy(rs.getTargetResourceSchema()));
        }

        log.info("Resource Specifications retrieved.");

        return resourceSpecifications;
    }

    public ResourceSpecification patch(String id, ResourceSpecificationUpdate resourceSpecificationUpdate, String lastUpdate)
            throws NotExistingEntityException {

        log.info("Received request to patch Resource Specification with id " + id + ".");

        Optional<ResourceSpecification> toUpdate = resourceSpecificationRepository.findByResourceSpecificationId(id);
        if(!toUpdate.isPresent())
            throw new NotExistingEntityException("Resource Specification with id " + id + " not found in DB.");

        ResourceSpecification resourceSpecification = toUpdate.get();

        resourceSpecification.setBaseType(resourceSpecificationUpdate.getBaseType());
        resourceSpecification.setSchemaLocation(resourceSpecificationUpdate.getSchemaLocation());

        final List<AttachmentRef> attachment = resourceSpecificationUpdate.getAttachment();
        if(resourceSpecification.getAttachment() == null)
            resourceSpecification.setAttachment(attachment);
        else if(attachment != null) {
            resourceSpecification.getAttachment().clear();
            resourceSpecification.getAttachment().addAll(attachment);
        }
        else
            resourceSpecification.getAttachment().clear();

        resourceSpecification.setCategory(resourceSpecificationUpdate.getCategory());
        resourceSpecification.setDescription(resourceSpecificationUpdate.getDescription());

        final List<Feature> feature = resourceSpecificationUpdate.getFeature();
        if(resourceSpecification.getFeature() == null)
            resourceSpecification.setFeature(feature);
        else if(feature != null) {
            resourceSpecification.getFeature().clear();
            resourceSpecification.getFeature().addAll(feature);
        }
        else
            resourceSpecification.getFeature().clear();

        resourceSpecification.setIsBundle(resourceSpecificationUpdate.isIsBundle());
        resourceSpecification.setLastUpdate(lastUpdate);
        resourceSpecification.setLifecycleStatus(resourceSpecificationUpdate.getLifecycleStatus());
        resourceSpecification.setName(resourceSpecificationUpdate.getName());

        final List<RelatedParty> relatedParty = resourceSpecificationUpdate.getRelatedParty();
        if(resourceSpecification.getRelatedParty() == null)
            resourceSpecification.setRelatedParty(relatedParty);
        else if(relatedParty != null) {
            resourceSpecification.getRelatedParty().clear();
            resourceSpecification.getRelatedParty().addAll(relatedParty);
        }
        else
            resourceSpecification.getRelatedParty().clear();

        final List<ResourceSpecCharacteristic> resourceSpecCharacteristic =
                resourceSpecificationUpdate.getResourceSpecCharacteristic();
        if(resourceSpecification.getResourceSpecCharacteristic() == null)
            resourceSpecification.setResourceSpecCharacteristic(resourceSpecCharacteristic);
        else if(resourceSpecCharacteristic != null) {
            resourceSpecification.getResourceSpecCharacteristic().clear();
            resourceSpecification.getResourceSpecCharacteristic().addAll(resourceSpecCharacteristic);
        }
        else
            resourceSpecification.getResourceSpecCharacteristic().clear();

        final List<ResourceSpecRelationship> resourceSpecRelationship =
                resourceSpecificationUpdate.getResourceSpecRelationship();
        if(resourceSpecification.getResourceSpecRelationship() == null)
            resourceSpecification.setResourceSpecRelationship(resourceSpecRelationship);
        else if(resourceSpecRelationship != null) {
            resourceSpecification.getResourceSpecRelationship().clear();
            resourceSpecification.getResourceSpecRelationship().addAll(resourceSpecRelationship);
        }
        else
            resourceSpecification.getResourceSpecRelationship().clear();

        resourceSpecification.setTargetResourceSchema(resourceSpecificationUpdate.getTargetResourceSchema());
        resourceSpecification.setValidFor(resourceSpecificationUpdate.getValidFor());
        resourceSpecification.setVersion(resourceSpecificationUpdate.getVersion());

        resourceSpecificationRepository.save(resourceSpecification);

        log.info("Resource Specification " + id + " patched.");

        return resourceSpecification;
    }

    @Transactional
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
        if(rs.getResourceSpecCharacteristic() != null) {
            for (ResourceSpecCharacteristic rsc : rs.getResourceSpecCharacteristic()) {
                rsc.setResourceSpecCharRelationship((List<ResourceSpecCharRelationship>)
                        Hibernate.unproxy(rsc.getResourceSpecCharRelationship()));
                rsc.setResourceSpecCharacteristicValue((List<ResourceSpecCharacteristicValue>)
                        Hibernate.unproxy(rsc.getResourceSpecCharacteristicValue()));
            }
        }

        rs.setResourceSpecRelationship((List<ResourceSpecRelationship>)
                Hibernate.unproxy(rs.getResourceSpecRelationship()));
        rs.setTargetResourceSchema((TargetResourceSchemaRef) Hibernate.unproxy(rs.getTargetResourceSchema()));

        log.info("Resource Specification " + id + " retrieved.");

        return rs;
    }
}
