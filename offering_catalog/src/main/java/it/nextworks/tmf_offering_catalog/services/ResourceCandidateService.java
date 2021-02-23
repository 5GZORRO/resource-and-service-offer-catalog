package it.nextworks.tmf_offering_catalog.services;

import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.ResourceSpecificationRef;
import it.nextworks.tmf_offering_catalog.information_models.TimePeriod;
import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceCandidate;
import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceCandidateCreate;
import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceCandidateUpdate;
import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceCategoryRef;
import it.nextworks.tmf_offering_catalog.repo.ResourceCandidateRepository;
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

    public ResourceCandidate create(ResourceCandidateCreate resourceCandidateCreate) {

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

        resourceCandidateRepository.save(resourceCandidate);

        log.info("Resource Candidate created with id " + resourceCandidate.getId() + ".");

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

    public ResourceCandidate patch(String id, ResourceCandidateUpdate resourceCandidateUpdate)
            throws NotExistingEntityException {

        log.info("Received request to patch Resource Candidate with id " + id + ".");

        Optional<ResourceCandidate> toUpdate = resourceCandidateRepository.findByResourceCandidateId(id);
        if(!toUpdate.isPresent())
            throw new NotExistingEntityException("Resource Candidate with id " + id + " not found in DB.");

        ResourceCandidate resourceCandidate = toUpdate.get();

        final String baseType = resourceCandidateUpdate.getBaseType();
        if(baseType != null)
            resourceCandidate.setBaseType(baseType);

        final String schemaLocation = resourceCandidateUpdate.getSchemaLocation();
        if(schemaLocation != null)
            resourceCandidate.setSchemaLocation(schemaLocation);

        final String type = resourceCandidateUpdate.getType();
        if(type != null)
            resourceCandidate.setType(type);

        final List<ResourceCategoryRef> category = resourceCandidateUpdate.getCategory();
        if(category != null) {
            resourceCandidate.getCategory().clear();
            resourceCandidate.getCategory().addAll(category);
        }
        else
            resourceCandidate.setCategory((List<ResourceCategoryRef>)
                    Hibernate.unproxy(resourceCandidate.getCategory()));

        final String description = resourceCandidateUpdate.getDescription();
        if(description != null)
            resourceCandidate.setDescription(description);

        final OffsetDateTime lastUpdate = resourceCandidateUpdate.getLastUpdate();
        if(lastUpdate != null)
            resourceCandidate.setLastUpdate(lastUpdate.toString());

        final String lifecycleStatus = resourceCandidateUpdate.getLifecycleStatus();
        if(lifecycleStatus != null)
            resourceCandidate.setLifecycleStatus(lifecycleStatus);

        final String name = resourceCandidateUpdate.getName();
        if(name != null)
            resourceCandidate.setName(name);

        final ResourceSpecificationRef resourceSpecification = resourceCandidateUpdate.getResourceSpecification();
        if(resourceSpecification != null)
            resourceCandidate.setResourceSpecification(resourceSpecification);
        else
            resourceCandidate.setResourceSpecification((ResourceSpecificationRef)
                    Hibernate.unproxy(resourceCandidate.getResourceSpecification()));

        final TimePeriod validFor = resourceCandidateUpdate.getValidFor();
        if(validFor != null)
            resourceCandidate.setValidFor(validFor);

        final String version = resourceCandidateUpdate.getVersion();
        if(version != null)
            resourceCandidate.setVersion(version);

        resourceCandidateRepository.save(resourceCandidate);

        log.info("Resource Candidate " + id + " patched.");

        return resourceCandidate;
    }

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
