package it.nextworks.tmf_offering_catalog.services;

import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.common.*;
import it.nextworks.tmf_offering_catalog.information_models.service.*;
import it.nextworks.tmf_offering_catalog.repo.ServiceSpecificationRepository;
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
public class ServiceSpecificationService {

    private static final Logger log = LoggerFactory.getLogger(ServiceSpecificationService.class);

    private static final String protocol = "http://";
    @Value("${server.hostname}")
    private String hostname;
    @Value("${server.port}")
    private String port;
    private static final String path = "/tmf-api/serviceCatalogManagement/v4/serviceSpecification/";

    @Autowired
    private ServiceSpecificationRepository serviceSpecificationRepository;

    public ServiceSpecification create(ServiceSpecificationCreate serviceSpecificationCreate) {

        log.info("Received request to create a Service Specification.");

        final String id = UUID.randomUUID().toString();
        ServiceSpecification serviceSpecification = new ServiceSpecification()
                .baseType(serviceSpecificationCreate.getBaseType())
                .schemaLocation(serviceSpecificationCreate.getSchemaLocation())
                .type(serviceSpecificationCreate.getType())
                .attachment(serviceSpecificationCreate.getAttachment())
                .description(serviceSpecificationCreate.getDescription())
                .href(protocol + hostname + ":" + port + path + id)
                .id(id)
                .isBundle(serviceSpecificationCreate.isIsBundle())
                .lifecycleStatus(serviceSpecificationCreate.getLifecycleStatus())
                .name(serviceSpecificationCreate.getName())
                .relatedParty(serviceSpecificationCreate.getRelatedParty())
                .resourceSpecification(serviceSpecificationCreate.getResourceSpecification())
                .serviceLevelSpecification(serviceSpecificationCreate.getServiceLevelSpecification())
                .serviceSpecCharacteristic(serviceSpecificationCreate.getServiceSpecCharacteristic())
                .serviceSpecRelationship(serviceSpecificationCreate.getServiceSpecRelationship())
                .targetServiceSchema(serviceSpecificationCreate.getTargetServiceSchema())
                .validFor(serviceSpecificationCreate.getValidFor())
                .version(serviceSpecificationCreate.getVersion());

        final OffsetDateTime lastUpdate = serviceSpecificationCreate.getLastUpdate();
        if(lastUpdate != null)
            serviceSpecification.setLastUpdate(lastUpdate.toString());

        serviceSpecificationRepository.save(serviceSpecification);

        log.info("Service Specification created with id " + id + ".");

        return serviceSpecification;
    }

    public void delete(String id) throws NotExistingEntityException {

        log.info("Received request to delete Service Specification with id " + id + ".");

        Optional<ServiceSpecification> toDelete = serviceSpecificationRepository.findByServiceSpecificationId(id);
        if(!toDelete.isPresent())
            throw new NotExistingEntityException("Service Specification with id " + id + " not found in DB.");

        serviceSpecificationRepository.delete(toDelete.get());

        log.info("Service Specification " + id + " deleted.");
    }

    @Transactional
    public List<ServiceSpecification> list() {

        log.info("Received request to retrieve all Service Specifications.");

        List<ServiceSpecification> serviceSpecifications = serviceSpecificationRepository.findAll();
        for(ServiceSpecification ss : serviceSpecifications) {
            ss.setAttachment((List<AttachmentRef>) Hibernate.unproxy(ss.getAttachment()));
            ss.setRelatedParty((List<RelatedParty>) Hibernate.unproxy(ss.getRelatedParty()));
            ss.setResourceSpecification((List<ResourceSpecificationRef>)
                    Hibernate.unproxy(ss.getResourceSpecification()));
            ss.setServiceLevelSpecification((List<ServiceLevelSpecificationRef>)
                    Hibernate.unproxy(ss.getServiceLevelSpecification()));

            ss.setServiceSpecCharacteristic((List<ServiceSpecCharacteristic>)
                    Hibernate.unproxy(ss.getServiceSpecCharacteristic()));
            if(ss.getServiceSpecCharacteristic() != null) {
                for (ServiceSpecCharacteristic ssc : ss.getServiceSpecCharacteristic()) {
                    ssc.setServiceSpecCharRelationship((List<ServiceSpecCharRelationship>)
                            Hibernate.unproxy(ssc.getServiceSpecCharRelationship()));
                    ssc.setServiceSpecCharacteristicValue((List<ServiceSpecCharacteristicValue>)
                            Hibernate.unproxy(ssc.getServiceSpecCharacteristicValue()));
                }
            }

            ss.setServiceSpecRelationship((List<ServiceSpecRelationship>)
                    Hibernate.unproxy(ss.getServiceSpecRelationship()));
            ss.setTargetServiceSchema((TargetServiceSchema) Hibernate.unproxy(ss.getTargetServiceSchema()));
        }

        log.info("Service Specifications retrieved.");

        return serviceSpecifications;
    }

    public ServiceSpecification patch(String id, ServiceSpecificationUpdate serviceSpecificationUpdate, String lastUpdate)
        throws NotExistingEntityException {

        log.info("Received request to patch Service Specification with id " + id + ".");

        Optional<ServiceSpecification> toUpdate = serviceSpecificationRepository.findByServiceSpecificationId(id);
        if(!toUpdate.isPresent())
            throw new NotExistingEntityException("Service Specification with id " + id + " not found in DB.");

        ServiceSpecification serviceSpecification = toUpdate.get();

        serviceSpecification.setBaseType(serviceSpecificationUpdate.getBaseType());
        serviceSpecification.setSchemaLocation(serviceSpecificationUpdate.getSchemaLocation());
        serviceSpecification.setType(serviceSpecificationUpdate.getType());

        final List<AttachmentRef> attachment = serviceSpecificationUpdate.getAttachment();
        if(serviceSpecification.getAttachment() == null)
            serviceSpecification.setAttachment(attachment);
        else if(attachment != null) {
            serviceSpecification.getAttachment().clear();
            serviceSpecification.getAttachment().addAll(attachment);
        }
        else
            serviceSpecification.getAttachment().clear();

        serviceSpecification.setDescription(serviceSpecificationUpdate.getDescription());
        serviceSpecification.setIsBundle(serviceSpecificationUpdate.isIsBundle());
        serviceSpecification.setLastUpdate(lastUpdate);
        serviceSpecification.setLifecycleStatus(serviceSpecificationUpdate.getLifecycleStatus());
        serviceSpecification.setName(serviceSpecificationUpdate.getName());

        final List<RelatedParty> relatedParty = serviceSpecificationUpdate.getRelatedParty();
        if(serviceSpecification.getRelatedParty() == null)
            serviceSpecification.setRelatedParty(relatedParty);
        else if(relatedParty != null) {
            serviceSpecification.getRelatedParty().clear();
            serviceSpecification.getRelatedParty().addAll(relatedParty);
        }
        else
            serviceSpecification.getRelatedParty().clear();

        final List<ResourceSpecificationRef> resourceSpecification =
                serviceSpecificationUpdate.getResourceSpecification();
        if(serviceSpecification.getResourceSpecification() == null)
            serviceSpecification.setResourceSpecification(resourceSpecification);
        else if(resourceSpecification != null) {
            serviceSpecification.getResourceSpecification().clear();
            serviceSpecification.getResourceSpecification().addAll(resourceSpecification);
        }
        else
            serviceSpecification.getResourceSpecification().clear();

        final List<ServiceLevelSpecificationRef> serviceLevelSpecification =
                serviceSpecificationUpdate.getServiceLevelSpecification();
        if(serviceSpecification.getServiceLevelSpecification() == null)
            serviceSpecification.setServiceLevelSpecification(serviceLevelSpecification);
        else if(serviceLevelSpecification != null) {
            serviceSpecification.getServiceLevelSpecification().clear();
            serviceSpecification.getServiceLevelSpecification().addAll(serviceLevelSpecification);
        }
        else
            serviceSpecification.getServiceLevelSpecification().clear();

        final List<ServiceSpecCharacteristic> serviceSpecCharacteristic =
                serviceSpecificationUpdate.getServiceSpecCharacteristic();
        if(serviceSpecification.getServiceSpecCharacteristic() == null)
            serviceSpecification.setServiceSpecCharacteristic(serviceSpecCharacteristic);
        else if(serviceSpecCharacteristic != null) {
            serviceSpecification.getServiceSpecCharacteristic().clear();
            serviceSpecification.getServiceSpecCharacteristic().addAll(serviceSpecCharacteristic);
        }
        else
            serviceSpecification.getServiceSpecCharacteristic().clear();

        final List<ServiceSpecRelationship> serviceSpecRelationship =
                serviceSpecificationUpdate.getServiceSpecRelationship();
        if(serviceSpecification.getServiceSpecRelationship() == null)
            serviceSpecification.setServiceSpecRelationship(serviceSpecRelationship);
        else if(serviceSpecRelationship != null) {
            serviceSpecification.getServiceSpecRelationship().clear();
            serviceSpecification.getServiceSpecRelationship().addAll(serviceSpecRelationship);
        }
        else
            serviceSpecification.getServiceSpecRelationship().clear();

        serviceSpecification.setTargetServiceSchema(serviceSpecificationUpdate.getTargetServiceSchema());
        serviceSpecification.setValidFor(serviceSpecificationUpdate.getValidFor());
        serviceSpecification.setVersion(serviceSpecificationUpdate.getVersion());

        serviceSpecificationRepository.save(serviceSpecification);

        log.info("Service Specification " + id + " patched.");

        return serviceSpecification;
    }

    @Transactional
    public ServiceSpecification get(String id) throws NotExistingEntityException {

        log.info("Received request to retrieve Service Specification with id " + id + ".");

        Optional<ServiceSpecification> retrieved = serviceSpecificationRepository.findByServiceSpecificationId(id);
        if(!retrieved.isPresent())
            throw new NotExistingEntityException("Service Specification with id " + id + " not found in DB.");

        ServiceSpecification ss = retrieved.get();

        ss.setAttachment((List<AttachmentRef>) Hibernate.unproxy(ss.getAttachment()));
        ss.setRelatedParty((List<RelatedParty>) Hibernate.unproxy(ss.getRelatedParty()));
        ss.setResourceSpecification((List<ResourceSpecificationRef>)
                Hibernate.unproxy(ss.getResourceSpecification()));
        ss.setServiceLevelSpecification((List<ServiceLevelSpecificationRef>)
                Hibernate.unproxy(ss.getServiceLevelSpecification()));

        ss.setServiceSpecCharacteristic((List<ServiceSpecCharacteristic>)
                Hibernate.unproxy(ss.getServiceSpecCharacteristic()));
        if(ss.getServiceSpecCharacteristic() != null) {
            for (ServiceSpecCharacteristic ssc : ss.getServiceSpecCharacteristic()) {
                ssc.setServiceSpecCharRelationship((List<ServiceSpecCharRelationship>)
                        Hibernate.unproxy(ssc.getServiceSpecCharRelationship()));
                ssc.setServiceSpecCharacteristicValue((List<ServiceSpecCharacteristicValue>)
                        Hibernate.unproxy(ssc.getServiceSpecCharacteristicValue()));
            }
        }

        ss.setServiceSpecRelationship((List<ServiceSpecRelationship>)
                Hibernate.unproxy(ss.getServiceSpecRelationship()));
        ss.setTargetServiceSchema((TargetServiceSchema) Hibernate.unproxy(ss.getTargetServiceSchema()));

        log.info("Service Specification " + id + " retrieved.");

        return ss;
    }
}
