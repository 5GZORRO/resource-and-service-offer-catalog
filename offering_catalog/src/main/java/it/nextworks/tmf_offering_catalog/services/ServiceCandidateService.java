package it.nextworks.tmf_offering_catalog.services;

import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.common.exception.NullIdentifierException;
import it.nextworks.tmf_offering_catalog.information_models.common.ServiceCandidateRef;
import it.nextworks.tmf_offering_catalog.information_models.common.ServiceSpecificationRef;
import it.nextworks.tmf_offering_catalog.information_models.common.TargetServiceSchema;
import it.nextworks.tmf_offering_catalog.information_models.service.*;
import it.nextworks.tmf_offering_catalog.repo.ServiceCandidateRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceCandidateService {

    private static final Logger log = LoggerFactory.getLogger(ServiceCandidateService.class);

    private static final String protocol = "http://";
    @Value("${server.hostname}")
    private String hostname;
    @Value("${server.port}")
    private String port;
    private static final String path = "/tmf-api/serviceCatalogManagement/v4/serviceCandidate/";

    @Value("${ingress:}")
    private String ingres;

    @Autowired
    private ServiceCandidateRepository serviceCandidateRepository;

    @Autowired
    private ServiceCategoryService serviceCategoryService;

    private void updateServiceCategory(List<ServiceCategoryRef> serviceCategoryRefs,
                                       String href, String id, String name)
            throws NullIdentifierException, NotExistingEntityException {

        if(serviceCategoryRefs != null) {
            // Check that the references are well-formatted and exist in the DB
            List<ServiceCategory> serviceCategories = new ArrayList<>();
            for(ServiceCategoryRef serviceCategoryRef : serviceCategoryRefs) {
                String scrId = serviceCategoryRef.getId();
                if(scrId == null)
                    throw new NullIdentifierException("Referenced Service Category with null identifier not allowed.");

                serviceCategories.add(serviceCategoryService.get(scrId));
            }

            for(ServiceCategory serviceCategory : serviceCategories) {

                String scrId = serviceCategory.getId();
                log.info("Updating Service Category " + scrId + ".");

                serviceCategory.getServiceCandidate().add(new ServiceCandidateRef()
                        .href(href)
                        .id(id)
                        .lastUpdate(OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")).toString())
                        .name(name));

                serviceCategoryService.save(serviceCategory);

                log.info("Service Category " + scrId + " updated.");
            }
        }
    }

    public ServiceCandidate create(ServiceCandidateCreate serviceCandidateCreate)
            throws NullIdentifierException, NotExistingEntityException {

        log.info("Received request to create a Service Specification.");

        final String id = UUID.randomUUID().toString();
        ServiceCandidate serviceCandidate = new ServiceCandidate()
                .baseType(serviceCandidateCreate.getBaseType())
                .schemaLocation(serviceCandidateCreate.getSchemaLocation())
                .type(serviceCandidateCreate.getType())
                .category(serviceCandidateCreate.getCategory())
                .description(serviceCandidateCreate.getDescription())
                .href(StringUtils.hasText(ingres) ? ingres : (protocol + hostname + ":" + port) + path + id)
                .id(id)
                .lifecycleStatus(serviceCandidateCreate.getLifecycleStatus())
                .name(serviceCandidateCreate.getName())
                .serviceSpecification(serviceCandidateCreate.getServiceSpecification())
                .validFor(serviceCandidateCreate.getValidFor())
                .version(serviceCandidateCreate.getVersion());

        final OffsetDateTime lastUpdate = serviceCandidateCreate.getLastUpdate();
        if(lastUpdate != null)
            serviceCandidate.setLastUpdate(lastUpdate.toString());
        else
            serviceCandidate.setLastUpdate(OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")).toString());

        updateServiceCategory(serviceCandidate.getCategory(), serviceCandidate.getHref(),
                id, serviceCandidate.getName());

        serviceCandidateRepository.save(serviceCandidate);

        log.info("Service Candidate created with id " + id + ".");

        return serviceCandidate;
    }

    private void updateServiceCategoryDelete(List<ServiceCategoryRef> serviceCategoryRefs, String id)
            throws NullIdentifierException, NotExistingEntityException {

        if(serviceCategoryRefs != null) {
            List<ServiceCategory> serviceCategories = new ArrayList<>();
            for(ServiceCategoryRef serviceCategoryRef : serviceCategoryRefs) {
                String scrId = serviceCategoryRef.getId();
                if(scrId == null)
                    throw new NullIdentifierException("Referenced Service Category with null identified not allowed.");

                serviceCategories.add(serviceCategoryService.get(scrId));
            }

            for(ServiceCategory serviceCategory : serviceCategories) {

                String scrId = serviceCategory.getId();
                log.info("Updating Service Category " + scrId + ".");

                List<ServiceCandidateRef> serviceCandidateRefs = serviceCategory.getServiceCandidate();
                if(serviceCandidateRefs != null)
                    serviceCandidateRefs.removeIf(scr -> scr.getId().equals(id));

                serviceCategoryService.save(serviceCategory);

                log.info("Service Category " + scrId + " updated.");
            }
        }
    }

    public void delete(String id) throws NotExistingEntityException, NullIdentifierException {

        log.info("Received request to delete Service Candidate with id " + id + ".");

        Optional<ServiceCandidate> toDelete = serviceCandidateRepository.findByServiceCandidateId(id);
        if(!toDelete.isPresent())
            throw new NotExistingEntityException("Service Candidate with id " + id + " not found in DB.");

        ServiceCandidate sc = toDelete.get();

        updateServiceCategoryDelete(sc.getCategory(), sc.getId());

        serviceCandidateRepository.delete(sc);

        log.info("Service Candidate " + id + " deleted");
    }

    @Transactional
    public List<ServiceCandidate> list() {

        log.info("Received request to retrieve all Service Candidates.");

        List<ServiceCandidate> serviceCandidates = serviceCandidateRepository.findAll();
        for(ServiceCandidate sc : serviceCandidates) {
            sc.setCategory((List<ServiceCategoryRef>) Hibernate.unproxy(sc.getCategory()));
            sc.setServiceSpecification((ServiceSpecificationRef) Hibernate.unproxy(sc.getServiceSpecification()));

            ServiceSpecificationRef ssr = sc.getServiceSpecification();
            if(ssr != null)
                ssr.setTargetServiceSchema((TargetServiceSchema) Hibernate.unproxy(ssr.getTargetServiceSchema()));
        }

        log.info("Service Candidates retrieved.");

        return serviceCandidates;
    }

    private void updateServiceCategoryPatch(List<ServiceCategoryRef> oldServiceCategoryRefs,
                                            List<ServiceCategoryRef> newServiceCategoryRefs,
                                            String href,
                                            String id,
                                            String name)
            throws NullIdentifierException, NotExistingEntityException {

        List<ServiceCategory> oldServiceCategories = new ArrayList<>();
        if(oldServiceCategoryRefs != null) {
            for (ServiceCategoryRef serviceCategoryRef : oldServiceCategoryRefs) {
                String scrId = serviceCategoryRef.getId();
                if (scrId == null)
                    throw new NullIdentifierException("Referenced Service Category with null identified not allowed.");

                oldServiceCategories.add(serviceCategoryService.get(scrId));
            }
        }

        List<ServiceCategory> newServiceCategories = new ArrayList<>();
        if(newServiceCategoryRefs != null) {
            for(ServiceCategoryRef serviceCategoryRef : newServiceCategoryRefs) {
                String scrId = serviceCategoryRef.getId();
                if (scrId == null)
                    throw new NullIdentifierException("Referenced Service Category with null identifier not allowed.");

                newServiceCategories.add(serviceCategoryService.get(scrId));
            }
        }

        for(ServiceCategory serviceCategory : oldServiceCategories) {

            String scrId = serviceCategory.getId();
            log.info("Updating Service Category " + scrId + ".");

            List<ServiceCandidateRef> serviceCandidateRefs = serviceCategory.getServiceCandidate();
            if(serviceCandidateRefs != null)
                serviceCandidateRefs.removeIf(scr -> scr.getId().equals(id));

            serviceCategoryService.save(serviceCategory);

            log.info("Service Category " + scrId + " updated.");
        }

        for(ServiceCategory serviceCategory : newServiceCategories) {

            String scrId = serviceCategory.getId();
            log.info("Updating Service Category " + scrId + ".");

            serviceCategory.getServiceCandidate().add(new ServiceCandidateRef()
                    .href(href)
                    .id(id)
                    .lastUpdate(OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")).toString())
                    .name(name));

            serviceCategoryService.save(serviceCategory);

            log.info("Service Category " + scrId + " updated.");
        }
    }

    public ServiceCandidate patch(String id, ServiceCandidateUpdate serviceCandidateUpdate, String lastUpdate)
            throws NotExistingEntityException, NullIdentifierException {

        log.info("Received request to patch Service Candidate with id " + id + ".");

        Optional<ServiceCandidate> toUpdate = serviceCandidateRepository.findByServiceCandidateId(id);
        if(!toUpdate.isPresent())
            throw new NotExistingEntityException("Service Candidate with id " + id + " not found in DB.");

        ServiceCandidate serviceCandidate = toUpdate.get();

        final List<ServiceCategoryRef> category = serviceCandidateUpdate.getCategory();
        if(serviceCandidate.getCategory() == null) {
            updateServiceCategory(category, serviceCandidate.getHref(),
                    serviceCandidate.getId(), serviceCandidate.getName());
            serviceCandidate.setCategory(category);
        } else if(category != null) {
            updateServiceCategoryPatch(serviceCandidate.getCategory(),
                    category, serviceCandidate.getHref(), serviceCandidate.getId(),
                    serviceCandidate.getName());

            serviceCandidate.getCategory().clear();
            serviceCandidate.getCategory().addAll(category);
        } else {
            updateServiceCategoryDelete(serviceCandidate.getCategory(), serviceCandidate.getId());
            serviceCandidate.getCategory().clear();
        }

        serviceCandidate.setBaseType(serviceCandidateUpdate.getBaseType());
        serviceCandidate.setSchemaLocation(serviceCandidateUpdate.getSchemaLocation());
        serviceCandidate.setType(serviceCandidateUpdate.getType());
        serviceCandidate.setDescription(serviceCandidateUpdate.getDescription());
        serviceCandidate.setLastUpdate(lastUpdate);
        serviceCandidate.setLifecycleStatus(serviceCandidateUpdate.getLifecycleStatus());
        serviceCandidate.setName(serviceCandidateUpdate.getName());
        serviceCandidate.setServiceSpecification(serviceCandidateUpdate.getServiceSpecification());
        serviceCandidate.setValidFor(serviceCandidateUpdate.getValidFor());
        serviceCandidate.setVersion(serviceCandidateUpdate.getVersion());

        serviceCandidateRepository.save(serviceCandidate);

        log.info("Service Candidate " + id + " patched.");

        return serviceCandidate;
    }

    @Transactional
    public ServiceCandidate get(String id) throws NotExistingEntityException {

        log.info("Received request to retrieve Service Candidate with id " + id + ".");

        Optional<ServiceCandidate> retrieved = serviceCandidateRepository.findByServiceCandidateId(id);
        if(!retrieved.isPresent())
            throw new NotExistingEntityException("Service Candidate with id " + id + " not found in DB.");

        ServiceCandidate sc = retrieved.get();

        sc.setCategory((List<ServiceCategoryRef>) Hibernate.unproxy(sc.getCategory()));
        sc.setServiceSpecification((ServiceSpecificationRef) Hibernate.unproxy(sc.getServiceSpecification()));

        ServiceSpecificationRef ssr = sc.getServiceSpecification();
        if(ssr != null)
            ssr.setTargetServiceSchema((TargetServiceSchema) Hibernate.unproxy(ssr.getTargetServiceSchema()));

        log.info("Service Candidate " + id + " retrieved.");

        return sc;
    }
}
