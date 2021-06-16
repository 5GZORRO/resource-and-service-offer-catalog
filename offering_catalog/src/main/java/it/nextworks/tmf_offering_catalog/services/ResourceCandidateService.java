package it.nextworks.tmf_offering_catalog.services;

import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.common.exception.NullIdentifierException;
import it.nextworks.tmf_offering_catalog.information_models.common.ResourceCandidateRef;
import it.nextworks.tmf_offering_catalog.information_models.common.ResourceSpecificationRef;
import it.nextworks.tmf_offering_catalog.information_models.resource.*;
import it.nextworks.tmf_offering_catalog.repo.ResourceCandidateRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResourceCandidateService {

    private static final Logger log = LoggerFactory.getLogger(ResourceCandidateService.class);

    private static final String protocol = "http://";
    @Value("${server.hostname}")
    private String hostname;
    @Value("${server.port}")
    private String port;
    private static final String path = "/tmf-api/resourceCatalogManagement/v2/resourceCandidate/";

    @Autowired
    private ResourceCandidateRepository resourceCandidateRepository;

    @Autowired
    private ResourceCategoryService resourceCategoryService;

    private void updateResourceCategory(List<ResourceCategoryRef> resourceCategoryRefs,
                                        String href, String id, String name)
            throws NullIdentifierException, NotExistingEntityException {

        if(resourceCategoryRefs != null) {
            // Check that the references are well-formatted and exist in the DB
            List<ResourceCategory> resourceCategories = new ArrayList<>();
            for(ResourceCategoryRef resourceCategoryRef : resourceCategoryRefs) {
                String rcrId = resourceCategoryRef.getId();
                if(rcrId == null)
                    throw new NullIdentifierException("Referenced Resource Category with null identified not allowed.");

                resourceCategories.add(resourceCategoryService.get(rcrId));
            }

            for(ResourceCategory resourceCategory : resourceCategories) {

                String rcrId = resourceCategory.getId();
                log.info("Updating Resource Category " + rcrId + ".");

                resourceCategory.getResourceCandidate().add(new ResourceCandidateRef()
                        .href(href)
                        .id(id)
                        .lastUpdate(OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")).toString())
                        .name(name));

                resourceCategoryService.save(resourceCategory);

                log.info("Resource Category " + rcrId + " updated.");
            }
        }
    }

    public ResourceCandidate create(ResourceCandidateCreate resourceCandidateCreate)
            throws NotExistingEntityException, NullIdentifierException {

        log.info("Received request to create a Resource Candidate.");

        final String id = UUID.randomUUID().toString();
        ResourceCandidate resourceCandidate = new ResourceCandidate()
                .baseType(resourceCandidateCreate.getBaseType())
                .schemaLocation(resourceCandidateCreate.getSchemaLocation())
                .type(resourceCandidateCreate.getType())
                .category(resourceCandidateCreate.getCategory())
                .description(resourceCandidateCreate.getDescription())
                .href(protocol + hostname + ":" + port + path + id)
                .id(id)
                .lifecycleStatus(resourceCandidateCreate.getLifecycleStatus())
                .name(resourceCandidateCreate.getName())
                .resourceSpecification(resourceCandidateCreate.getResourceSpecification())
                .validFor(resourceCandidateCreate.getValidFor())
                .version(resourceCandidateCreate.getVersion());

        final OffsetDateTime lastUpdate = resourceCandidateCreate.getLastUpdate();
        if(lastUpdate != null)
            resourceCandidate.setLastUpdate(lastUpdate.toString());
        else
            resourceCandidate.setLastUpdate(OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")).toString());

        updateResourceCategory(resourceCandidate.getCategory(), resourceCandidate.getHref(),
                id, resourceCandidate.getName());

        resourceCandidateRepository.save(resourceCandidate);

        log.info("Resource Candidate created with id " + id + ".");

        return resourceCandidate;
    }

    private void updateResourceCategoryDelete(List<ResourceCategoryRef> resourceCategoryRefs, String id)
            throws NullIdentifierException, NotExistingEntityException {

        if(resourceCategoryRefs != null) {
            List<ResourceCategory> resourceCategories = new ArrayList<>();
            for(ResourceCategoryRef resourceCategoryRef : resourceCategoryRefs) {
                String rcrId = resourceCategoryRef.getId();
                if(rcrId == null)
                    throw new NullIdentifierException("Referenced Resource Category with null identifier not allowed.");

                resourceCategories.add(resourceCategoryService.get(rcrId));
            }

            for(ResourceCategory resourceCategory : resourceCategories) {

                String rcrId = resourceCategory.getId();
                log.info("Updating Resource Category " + rcrId + ".");

                List<ResourceCandidateRef> resourceCandidateRefs = resourceCategory.getResourceCandidate();
                if(resourceCandidateRefs != null)
                    resourceCandidateRefs.removeIf(rcr -> rcr.getId().equals(id));

                resourceCategoryService.save(resourceCategory);

                log.info("Resource Category " + rcrId + " updated.");
            }
        }
    }

    public void delete(String id) throws NotExistingEntityException, NullIdentifierException {

        log.info("Received request to delete Resource Candidate with id " + id + ".");

        Optional<ResourceCandidate> toDelete = resourceCandidateRepository.findByResourceCandidateId(id);
        if(!toDelete.isPresent())
            throw new NotExistingEntityException("Resource Candidate with id " + id + " not found in DB.");

        ResourceCandidate rc = toDelete.get();

        updateResourceCategoryDelete(rc.getCategory(), rc.getId());

        resourceCandidateRepository.delete(rc);

        log.info("Resource Candidate " + id + " deleted.");
    }

    @Transactional
    public List<ResourceCandidate> list() {

        log.info("Received request to retrieve all Resource Candidates.");

        List<ResourceCandidate> resourceCandidates = resourceCandidateRepository.findAll();
        for(ResourceCandidate rc : resourceCandidates) {
            rc.setCategory((List<ResourceCategoryRef>) Hibernate.unproxy(rc.getCategory()));
            rc.setResourceSpecification((ResourceSpecificationRef) Hibernate.unproxy(rc.getResourceSpecification()));
        }

        log.info("Resource Candidates retrieved.");

        return resourceCandidates;
    }

    private void updateResourceCategoryPatch(List<ResourceCategoryRef> oldResourceCategoryRefs,
                                             List<ResourceCategoryRef> newResourceCategoryRefs,
                                             String href,
                                             String id,
                                             String name)
            throws NullIdentifierException, NotExistingEntityException {

        List<ResourceCategory> oldResourceCategories = new ArrayList<>();
        if(oldResourceCategoryRefs != null) {
            for(ResourceCategoryRef resourceCategoryRef : oldResourceCategoryRefs) {
                String rcrId = resourceCategoryRef.getId();
                if (rcrId == null)
                    throw new NullIdentifierException("Referenced Resource Category with null identifier not allowed.");

                oldResourceCategories.add(resourceCategoryService.get(rcrId));
            }
        }

        List<ResourceCategory> newResourceCategories = new ArrayList<>();
        if(newResourceCategoryRefs != null) {
            for(ResourceCategoryRef resourceCategoryRef : newResourceCategoryRefs) {
                String rcrId = resourceCategoryRef.getId();
                if (rcrId == null)
                    throw new NullIdentifierException("Referenced Resource Category with null identifier not allowed.");

                newResourceCategories.add(resourceCategoryService.get(rcrId));
            }
        }

        for(ResourceCategory resourceCategory : oldResourceCategories) {

            String rcrId = resourceCategory.getId();
            log.info("Updating Resource Category " + rcrId + ".");

            List<ResourceCandidateRef> resourceCandidateRefs = resourceCategory.getResourceCandidate();
            if(resourceCandidateRefs != null)
                resourceCandidateRefs.removeIf(rcr -> rcr.getId().equals(id));

            resourceCategoryService.save(resourceCategory);

            log.info("Resource Category " + rcrId + " updated.");
        }

        for(ResourceCategory resourceCategory : newResourceCategories) {

            String rcrId = resourceCategory.getId();
            log.info("Updating Resource Category " + rcrId + ".");

            resourceCategory.getResourceCandidate().add(new ResourceCandidateRef()
                    .href(href)
                    .id(id)
                    .lastUpdate(OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")).toString())
                    .name(name));

            resourceCategoryService.save(resourceCategory);

            log.info("Resource Category " + rcrId + " updated.");
        }
    }

    public ResourceCandidate patch(String id, ResourceCandidateUpdate resourceCandidateUpdate, String lastUpdate)
            throws NotExistingEntityException, NullIdentifierException {

        log.info("Received request to patch Resource Candidate with id " + id + ".");

        Optional<ResourceCandidate> toUpdate = resourceCandidateRepository.findByResourceCandidateId(id);
        if(!toUpdate.isPresent())
            throw new NotExistingEntityException("Resource Candidate with id " + id + " not found in DB.");

        ResourceCandidate resourceCandidate = toUpdate.get();

        final List<ResourceCategoryRef> category = resourceCandidateUpdate.getCategory();
        if(resourceCandidate.getCategory() == null) {
            updateResourceCategory(category, resourceCandidate.getHref(),
                    resourceCandidate.getId(), resourceCandidate.getName());
            resourceCandidate.setCategory(category);
        } else if(category != null) {
            updateResourceCategoryPatch(resourceCandidate.getCategory(),
                    category, resourceCandidate.getHref(), resourceCandidate.getId(),
                    resourceCandidate.getName());

            resourceCandidate.getCategory().clear();
            resourceCandidate.getCategory().addAll(category);
        } else {
            updateResourceCategoryDelete(resourceCandidate.getCategory(), resourceCandidate.getId());
            resourceCandidate.getCategory().clear();
        }

        resourceCandidate.setBaseType(resourceCandidateUpdate.getBaseType());
        resourceCandidate.setSchemaLocation(resourceCandidateUpdate.getSchemaLocation());
        resourceCandidate.setType(resourceCandidateUpdate.getType());
        resourceCandidate.setDescription(resourceCandidateUpdate.getDescription());
        resourceCandidate.setLastUpdate(resourceCandidateUpdate.getLastUpdate().toString());
        resourceCandidate.setLastUpdate(lastUpdate);
        resourceCandidate.setLifecycleStatus(resourceCandidateUpdate.getLifecycleStatus());
        resourceCandidate.setName(resourceCandidateUpdate.getName());
        resourceCandidate.setResourceSpecification(resourceCandidateUpdate.getResourceSpecification());
        resourceCandidate.setValidFor(resourceCandidateUpdate.getValidFor());
        resourceCandidate.setVersion(resourceCandidateUpdate.getVersion());

        resourceCandidateRepository.save(resourceCandidate);

        log.info("Resource Candidate " + id + " patched.");

        return resourceCandidate;
    }

    @Transactional
    public ResourceCandidate get(String id) throws NotExistingEntityException {

        log.info("Received request to retrieve Resource Candidate with id " + id + ".");

        Optional<ResourceCandidate> retrieved = resourceCandidateRepository.findByResourceCandidateId(id);
        if(!retrieved.isPresent())
            throw new NotExistingEntityException("Resource Candidate with id " + id + " not found in DB.");

        ResourceCandidate rc = retrieved.get();

        rc.setCategory((List<ResourceCategoryRef>) Hibernate.unproxy(rc.getCategory()));
        rc.setResourceSpecification((ResourceSpecificationRef) Hibernate.unproxy(rc.getResourceSpecification()));

        log.info("Resource Candidate " + id + " retrieved.");

        return rc;
    }
}
