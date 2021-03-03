package it.nextworks.tmf_offering_catalog.services;

import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.ResourceCandidateRef;
import it.nextworks.tmf_offering_catalog.information_models.TimePeriod;
import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceCategory;
import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceCategoryCreate;
import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceCategoryRef;
import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceCategoryUpdate;
import it.nextworks.tmf_offering_catalog.repo.ResourceCategoryRepository;
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
public class ResourceCategoryService {

    private static final Logger log = LoggerFactory.getLogger(ResourceCategoryService.class);

    private static final String protocol = "http://";
    @Value("${server.hostname}")
    private String hostname;
    @Value("${server.port}")
    private String port;
    private static final String path = "/tmf-api/resourceCatalogManagement/v2/resourceCategory/";

    @Autowired
    private ResourceCategoryRepository resourceCategoryRepository;

    public ResourceCategory create(ResourceCategoryCreate resourceCategoryCreate) {

        log.info("Received request to create a Resource Category.");

        final String id = UUID.randomUUID().toString();
        ResourceCategory resourceCategory = new ResourceCategory()
                .baseType(resourceCategoryCreate.getBaseType())
                .schemaLocation(resourceCategoryCreate.getSchemalLocation())
                .type(resourceCategoryCreate.getType())
                .category(resourceCategoryCreate.getCategory())
                .description(resourceCategoryCreate.getDescription())
                .href(protocol + hostname + ":" + port + path + id)
                .id(id)
                .isRoot(resourceCategoryCreate.isIsRoot())
                .lifecycleStatus(resourceCategoryCreate.getLifecycleStatus())
                .name(resourceCategoryCreate.getName())
                .parentId(resourceCategoryCreate.getParentId())
                .resourceCandidate(resourceCategoryCreate.getResourceCandidate())
                .validFor(resourceCategoryCreate.getValidFor())
                .version(resourceCategoryCreate.getVersion());

        final OffsetDateTime lastUpdate = resourceCategoryCreate.getLastUpdate();
        if(lastUpdate != null)
            resourceCategory.setLastUpdate(lastUpdate.toString());

        resourceCategoryRepository.save(resourceCategory);

        log.info("Resource Category created with id " + resourceCategory.getId() + ".");

        return resourceCategory;
    }

    public void delete(String id) throws NotExistingEntityException {

        log.info("Received request to delete Resource Category with id " + id + ".");

        Optional<ResourceCategory> toDelete = resourceCategoryRepository.findByResourceCategoryId(id);
        if(!toDelete.isPresent())
            throw new NotExistingEntityException("Resource Category with id " + id + " not found in DB.");

        resourceCategoryRepository.delete(toDelete.get());

        log.info("Resource Category " + id + " deleted.");
    }

    public List<ResourceCategory> list() {

        log.info("Received request to retrieve all Resource Categories.");

        List<ResourceCategory> resourceCategories = resourceCategoryRepository.findAll();
        for(ResourceCategory rc : resourceCategories) {
            rc.setCategory((List<ResourceCategoryRef>) Hibernate.unproxy(rc.getCategory()));
            rc.setResourceCandidate((List<ResourceCandidateRef>) Hibernate.unproxy(rc.getResourceCandidate()));
        }

        log.info("Resource Categories retrieved.");

        return resourceCategories;
    }

    public ResourceCategory patch(String id, ResourceCategoryUpdate resourceCategoryUpdate)
        throws NotExistingEntityException {

        log.info("Received request to patch Resource Category with id " + id + ".");

        Optional<ResourceCategory> toUpdate = resourceCategoryRepository.findByResourceCategoryId(id);
        if(!toUpdate.isPresent())
            throw new NotExistingEntityException("Resource Category with id " + id + " not found in DB.");

        ResourceCategory resourceCategory = toUpdate.get();

        final String baseType = resourceCategoryUpdate.getBaseType();
        if(baseType != null)
            resourceCategory.setBaseType(baseType);

        final String schemaLocation = resourceCategoryUpdate.getSchemalLocation();
        if(schemaLocation != null)
            resourceCategory.setSchemaLocation(schemaLocation);

        final String type = resourceCategoryUpdate.getType();
        if(type != null)
            resourceCategory.setType(type);

        final List<ResourceCategoryRef> category = resourceCategoryUpdate.getCategory();
        if(category != null) {
            resourceCategory.getCategory().clear();
            resourceCategory.getCategory().addAll(category);
        }
        else
            resourceCategory.setCategory((List<ResourceCategoryRef>) Hibernate.unproxy(resourceCategory.getCategory()));

        final String description = resourceCategoryUpdate.getDescription();
        if(description != null)
            resourceCategory.setDescription(description);

        final Boolean isRoot = resourceCategoryUpdate.isIsRoot();
        if(isRoot != null)
            resourceCategory.setIsRoot(isRoot);

        resourceCategory.setLastUpdate(OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")).toString());

        final String lifecycleStatus = resourceCategoryUpdate.getLifecycleStatus();
        if(lifecycleStatus != null)
            resourceCategory.setLifecycleStatus(lifecycleStatus);

        final String name = resourceCategoryUpdate.getName();
        if(name != null)
            resourceCategory.setName(name);

        final String parentId = resourceCategoryUpdate.getParentId();
        if(parentId != null)
            resourceCategory.setParentId(parentId);

        final List<ResourceCandidateRef> resourceCandidate = resourceCategoryUpdate.getResourceCandidate();
        if(resourceCandidate != null) {
            resourceCategory.getResourceCandidate().clear();
            resourceCategory.getResourceCandidate().addAll(resourceCandidate);
        }
        else
            resourceCategory.setResourceCandidate((List<ResourceCandidateRef>)
                    Hibernate.unproxy(resourceCategory.getResourceCandidate()));

        final TimePeriod validFor = resourceCategoryUpdate.getValidFor();
        if(validFor != null)
            resourceCategory.setValidFor(validFor);

        final String version = resourceCategoryUpdate.getVersion();
        if(version != null)
            resourceCategory.setVersion(version);

        resourceCategoryRepository.save(resourceCategory);

        log.info("Resource Category " + id + " patched.");

        return resourceCategory;
    }

    public ResourceCategory get(String id) throws NotExistingEntityException {

        log.info("Received request to retrieve Resource Category with id " + id + ".");

        Optional<ResourceCategory> retrieved = resourceCategoryRepository.findByResourceCategoryId(id);
        if(!retrieved.isPresent())
            throw new NotExistingEntityException("Resource Category with id " + id + " not found in DB.");

        ResourceCategory rc = retrieved.get();

        rc.setCategory((List<ResourceCategoryRef>) Hibernate.unproxy(rc.getCategory()));
        rc.setResourceCandidate((List<ResourceCandidateRef>) Hibernate.unproxy(rc.getResourceCandidate()));

        log.info("Resource Category " + id + " retrieved.");

        return rc;
    }
}
