package it.nextworks.tmf_offering_catalog.services;

import it.nextworks.tmf_offering_catalog.common.exception.CategoryAlreadyExistingException;
import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.common.ServiceCandidateRef;
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

    public ServiceCategory create(ServiceCategoryCreate serviceCategoryCreate) throws CategoryAlreadyExistingException {

        log.info("Received request to create a Service Category.");

        String name = serviceCategoryCreate.getName();
        if(serviceCategoryRepository.findByName(name).isPresent())
            throw new CategoryAlreadyExistingException("Service Category with name " + name + " already exists.");

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
                .name(name)
                .parentId(serviceCategoryCreate.getParentId())
                .serviceCandidate(serviceCategoryCreate.getServiceCandidate())
                .validFor(serviceCategoryCreate.getValidFor())
                .version(serviceCategoryCreate.getVersion());

        final OffsetDateTime lastUpdate = serviceCategoryCreate.getLastUpdate();
        if(lastUpdate != null)
            serviceCategory.setLastUpdate(lastUpdate.toString());
        else
            serviceCategory.setLastUpdate(OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")).toString());

        serviceCategoryRepository.save(serviceCategory);

        log.info("Service Category created with id " + id + ".");

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

    @Transactional
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

    /* Method used to retrieve NSD Resource Category in order to be associated to a Service Candidate */
    @Transactional
    public ServiceCategory findByName(String name) throws NotExistingEntityException {

        log.info("Received request to retrieve Service Category by name");

        Optional<ServiceCategory> opt = serviceCategoryRepository.findByName(name);
        if(!opt.isPresent())
            throw new NotExistingEntityException("Service Category with name " + name + " not found in DB.");

        ServiceCategory sc = opt.get();
        sc.setCategory((List<ServiceCategoryRef>) Hibernate.unproxy(sc.getCategory()));
        sc.setServiceCandidate((List<ServiceCandidateRef>) Hibernate.unproxy(sc.getServiceCandidate()));

        return sc;
    }

    public ServiceCategory patch(String id, ServiceCategoryUpdate serviceCategoryUpdate, String lastUpdate)
        throws NotExistingEntityException {

        log.info("Received request to patch Service Category with id " + id + ".");

        Optional<ServiceCategory> toUpdate = serviceCategoryRepository.findByServiceCategoryId(id);
        if(!toUpdate.isPresent())
            throw new NotExistingEntityException("Service Category with id " + id + " not found in DB.");

        ServiceCategory serviceCategory = toUpdate.get();

        serviceCategory.setBaseType(serviceCategoryUpdate.getBaseType());
        serviceCategory.setSchemaLocation(serviceCategoryUpdate.getSchemaLocation());
        serviceCategory.setType(serviceCategoryUpdate.getType());

        final List<ServiceCategoryRef> category = serviceCategoryUpdate.getCategory();
        if(serviceCategory.getCategory() == null)
            serviceCategory.setCategory(category);
        else if(category != null) {
            serviceCategory.getCategory().clear();
            serviceCategory.getCategory().addAll(category);
        }
        else
            serviceCategory.getCategory().clear();

        serviceCategory.setDescription(serviceCategoryUpdate.getDescription());
        serviceCategory.setIsRoot(serviceCategoryUpdate.isIsRoot());
        serviceCategory.setLastUpdate(lastUpdate);
        serviceCategory.setLifecycleStatus(serviceCategoryUpdate.getLifecycleStatus());
        serviceCategory.setName(serviceCategoryUpdate.getName());
        serviceCategory.setParentId(serviceCategoryUpdate.getParentId());

        final List<ServiceCandidateRef> serviceCandidate = serviceCategoryUpdate.getServiceCandidate();
        if(serviceCategory.getServiceCandidate() == null)
            serviceCategory.setServiceCandidate(serviceCandidate);
        else if(serviceCandidate != null) {
            serviceCategory.getServiceCandidate().clear();
            serviceCategory.getServiceCandidate().addAll(serviceCandidate);
        }
        else
            serviceCategory.getServiceCandidate().clear();

        serviceCategory.setValidFor(serviceCategoryUpdate.getValidFor());
        serviceCategory.setVersion(serviceCategoryUpdate.getVersion());

        serviceCategoryRepository.save(serviceCategory);

        log.info("Service Category " + id + " patched.");

        return serviceCategory;
    }

    @Transactional
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

    public ServiceCategory save(ServiceCategory sc) {
        return serviceCategoryRepository.save(sc);
    }
}
