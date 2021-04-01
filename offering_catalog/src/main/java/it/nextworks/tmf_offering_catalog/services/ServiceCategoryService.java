package it.nextworks.tmf_offering_catalog.services;

import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.common.ServiceCandidateRef;
import it.nextworks.tmf_offering_catalog.information_models.common.TimePeriod;
import it.nextworks.tmf_offering_catalog.information_models.service.ServiceCategory;
import it.nextworks.tmf_offering_catalog.information_models.service.ServiceCategoryCreate;
import it.nextworks.tmf_offering_catalog.information_models.service.ServiceCategoryRef;
import it.nextworks.tmf_offering_catalog.information_models.service.ServiceCategoryUpdate;
import it.nextworks.tmf_offering_catalog.repo.ServiceCategoryRepository;
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
public class ServiceCategoryService {

    private static final Logger log = LoggerFactory.getLogger(ServiceCategoryService.class);

    private static final String protocol = "http://";
    @Value("${server.hostname}")
    private String hostname;
    @Value("${server.port}")
    private String port;
    private static final String path = "/tmf-api/serviceCatalogManagement/v4/serviceCategory/";

    @Autowired
    private ServiceCategoryRepository serviceCategoryRepository;

    public ServiceCategory create(ServiceCategoryCreate serviceCategoryCreate) {

        log.info("Received request to create a Service Category.");

        final String id = UUID.randomUUID().toString();
        ServiceCategory serviceCategory = new ServiceCategory()
                .baseType(serviceCategoryCreate.getBaseType())
                .schemaLocation(serviceCategoryCreate.getSchemaLocation())
                .type(serviceCategoryCreate.getType())
                .category(serviceCategoryCreate.getCategory())
                .description(serviceCategoryCreate.getDescription())
                .href(protocol + hostname + ":" + port + path + id)
                .id(id)
                .isRoot(serviceCategoryCreate.isIsRoot())
                .lifecycleStatus(serviceCategoryCreate.getLifecycleStatus())
                .name(serviceCategoryCreate.getName())
                .parentId(serviceCategoryCreate.getParentId())
                .serviceCandidate(serviceCategoryCreate.getServiceCandidate())
                .validFor(serviceCategoryCreate.getValidFor())
                .version(serviceCategoryCreate.getVersion());

        final OffsetDateTime lastUpdate = serviceCategoryCreate.getLastUpdate();
        if(lastUpdate != null)
            serviceCategory.setLastUpdate(lastUpdate.toString());

        serviceCategoryRepository.save(serviceCategory);

        log.info("Service Category created with id " + serviceCategory.getId() + ".");

        return serviceCategory;
    }

    public void delete(String id) throws NotExistingEntityException {

        log.info("Received request to delete Service Category with id " + id + ".");

        Optional<ServiceCategory> toDelete = serviceCategoryRepository.findByServiceCategoryId(id);
        if(!toDelete.isPresent())
            throw new NotExistingEntityException("Service Category with id " + id + " not found in DB.");

        serviceCategoryRepository.delete(toDelete.get());

        log.info("Service Category " + id + " deleted.");
    }

    public List<ServiceCategory> list() {

        log.info("Received request to retrieve all Service Categories.");

        List<ServiceCategory> serviceCategories = serviceCategoryRepository.findAll();
        for(ServiceCategory sc : serviceCategories) {
            sc.setCategory((List<ServiceCategoryRef>) Hibernate.unproxy(sc.getCategory()));
            sc.setServiceCandidate((List<ServiceCandidateRef>) Hibernate.unproxy(sc.getServiceCandidate()));
        }

        log.info("Service Categories retrieved.");

        return serviceCategories;
    }

    public ServiceCategory patch(String id, ServiceCategoryUpdate serviceCategoryUpdate)
        throws NotExistingEntityException {

        log.info("Received request to patch Service Category with id " + id + ".");

        Optional<ServiceCategory> toUpdate = serviceCategoryRepository.findByServiceCategoryId(id);
        if(!toUpdate.isPresent())
            throw new NotExistingEntityException("Service Category with id " + id + " not found in DB.");

        ServiceCategory serviceCategory = toUpdate.get();

        final String baseType = serviceCategoryUpdate.getBaseType();
        if(baseType != null)
            serviceCategory.setBaseType(baseType);

        final String schemaLocation = serviceCategoryUpdate.getSchemaLocation();
        if(schemaLocation != null)
            serviceCategory.setSchemaLocation(schemaLocation);

        final String type = serviceCategoryUpdate.getType();
        if(type != null)
            serviceCategory.setType(type);

        final List<ServiceCategoryRef> category = serviceCategoryUpdate.getCategory();
        if(category != null) {
            if(serviceCategory.getCategory() != null) {
                serviceCategory.getCategory().clear();
                serviceCategory.getCategory().addAll(category);
            }
            else
                serviceCategory.setCategory(category);
        }
        else
            serviceCategory.setCategory((List<ServiceCategoryRef>) Hibernate.unproxy(serviceCategory.getCategory()));

        final String description = serviceCategoryUpdate.getDescription();
        if(description != null)
            serviceCategory.setDescription(description);

        final Boolean isRoot = serviceCategoryUpdate.isIsRoot();
        if(isRoot != null)
            serviceCategory.setIsRoot(isRoot);

        serviceCategory.setLastUpdate(OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")).toString());

        final String lifecycleStatus = serviceCategoryUpdate.getLifecycleStatus();
        if(lifecycleStatus != null)
            serviceCategory.setLifecycleStatus(lifecycleStatus);

        final String name = serviceCategoryUpdate.getName();
        if(name != null)
            serviceCategory.setName(name);

        final String parentId = serviceCategoryUpdate.getParentId();
        if(parentId != null)
            serviceCategory.setParentId(parentId);

        final List<ServiceCandidateRef> serviceCandidate = serviceCategoryUpdate.getServiceCandidate();
        if(serviceCandidate != null) {
            if(serviceCategory.getServiceCandidate() != null) {
                serviceCategory.getServiceCandidate().clear();
                serviceCategory.getServiceCandidate().addAll(serviceCandidate);
            }
            else
                serviceCategory.setServiceCandidate(serviceCandidate);
        }
        else
            serviceCategory.setServiceCandidate((List<ServiceCandidateRef>)
                    Hibernate.unproxy(serviceCategory.getServiceCandidate()));

        final TimePeriod validFor = serviceCategoryUpdate.getValidFor();
        if(validFor != null)
            serviceCategory.setValidFor(validFor);

        final String version = serviceCategoryUpdate.getVersion();
        if(version != null)
            serviceCategory.setVersion(version);

        serviceCategoryRepository.save(serviceCategory);

        log.info("Service Category " + id + " patched.");

        return serviceCategory;
    }

    public ServiceCategory get(String id) throws NotExistingEntityException {

        log.info("Received request to retrieve Service Category with id " + id + ".");

        Optional<ServiceCategory> retrieved = serviceCategoryRepository.findByServiceCategoryId(id);
        if(!retrieved.isPresent())
            throw new NotExistingEntityException("Service Category with id " + id + " not found in DB.");

        ServiceCategory sc = retrieved.get();

        sc.setCategory((List<ServiceCategoryRef>) Hibernate.unproxy(sc.getCategory()));
        sc.setServiceCandidate((List<ServiceCandidateRef>) Hibernate.unproxy(sc.getServiceCandidate()));

        log.info("Service Category " + id + " retrieved.");

        return sc;
    }
}
