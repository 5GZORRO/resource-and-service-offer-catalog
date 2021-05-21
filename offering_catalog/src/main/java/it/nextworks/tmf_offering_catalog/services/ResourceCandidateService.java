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

    public ResourceCandidate create(ResourceCandidateCreate resourceCandidateCreate) throws NotExistingEntityException, NullIdentifierException {

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

        List<ResourceCategoryRef> resourceCategoryRefs = resourceCandidate.getCategory();
        if(resourceCategoryRefs != null) {
            for(ResourceCategoryRef resourceCategoryRef : resourceCategoryRefs) {

                String RcrId = resourceCategoryRef.getId();
                if(RcrId == null)
                    throw new NullIdentifierException("Referenced Resource Category with null identified not allowed.");

                ResourceCategory rCategory = resourceCategoryService.get(RcrId);

                rCategory.getResourceCandidate().add(new ResourceCandidateRef()
                        .href(resourceCandidate.getHref())
                        .id(id)
                        .lastUpdate(OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")).toString())
                        .name(resourceCandidate.getName()));

                resourceCategoryService.save(rCategory);
            }
        }

        resourceCandidateRepository.save(resourceCandidate);

        log.info("Resource Candidate created with id " + id + ".");

        return resourceCandidate;
    }

    public void delete(String id) throws NotExistingEntityException {

        log.info("Received request to delete Resource Candidate with id " + id + ".");

        Optional<ResourceCandidate> toDelete = resourceCandidateRepository.findByResourceCandidateId(id);
        if(!toDelete.isPresent())
            throw new NotExistingEntityException("Resource Candidate with id " + id + " not found in DB.");

        resourceCandidateRepository.delete(toDelete.get());

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

    public ResourceCandidate patch(String id, ResourceCandidateUpdate resourceCandidateUpdate, String lastUpdate)
            throws NotExistingEntityException {

        log.info("Received request to patch Resource Candidate with id " + id + ".");

        Optional<ResourceCandidate> toUpdate = resourceCandidateRepository.findByResourceCandidateId(id);
        if(!toUpdate.isPresent())
            throw new NotExistingEntityException("Resource Candidate with id " + id + " not found in DB.");

        ResourceCandidate resourceCandidate = toUpdate.get();

        resourceCandidate.setBaseType(resourceCandidateUpdate.getBaseType());
        resourceCandidate.setSchemaLocation(resourceCandidateUpdate.getSchemaLocation());
        resourceCandidate.setType(resourceCandidateUpdate.getType());

        final List<ResourceCategoryRef> category = resourceCandidateUpdate.getCategory();
        if(resourceCandidate.getCategory() == null)
            resourceCandidate.setCategory(category);
        else if(category != null) {
            resourceCandidate.getCategory().clear();
            resourceCandidate.getCategory().addAll(category);
        }
        else
            resourceCandidate.getCategory().clear();

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
