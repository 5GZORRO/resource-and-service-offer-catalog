package it.nextworks.tmf_offering_catalogue.services;

import it.nextworks.tmf_offering_catalogue.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalogue.information_models.*;
import it.nextworks.tmf_offering_catalogue.information_models.service.*;
import it.nextworks.tmf_offering_catalogue.repo.ServiceSpecificationRepository;
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
public class ServiceSpecificationService {

    private static final Logger log = LoggerFactory.getLogger(ServiceSpecificationService.class);

    private static final String protocol = "http://";
    @Value("${server.hostname}")
    private String hostname;
    @Value("${server.port}")
    private String port;
    private static final String path = "/serviceCatalogManagement/v4/serviceSpecification/";

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
                .href(protocol + hostname + port + path + id)
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

        log.info("Service Specification created with id " + serviceSpecification.getId() + ".");

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

    public List<ServiceSpecification> list() {

        List<ServiceSpecification> serviceSpecifications = serviceSpecificationRepository.findAll();
        for(ServiceSpecification ss : serviceSpecifications) {
            Hibernate.initialize(ss.getAttachment());
            Hibernate.initialize(ss.getRelatedParty());
            Hibernate.initialize(ss.getResourceSpecification());
            Hibernate.initialize(ss.getServiceLevelSpecification());

            for(ServiceSpecCharacteristic ssc : ss.getServiceSpecCharacteristic()){
                Hibernate.initialize(ssc.getServiceSpecCharRelationship());
                Hibernate.initialize(ssc.getServiceSpecCharacteristicValue());
            }

            Hibernate.initialize(ss.getServiceSpecRelationship());
            Hibernate.initialize(ss.getTargetServiceSchema());
        }

        return serviceSpecifications;
    }

    public ServiceSpecification patch(String id, ServiceSpecificationUpdate serviceSpecificationUpdate)
        throws NotExistingEntityException {

        log.info("Received request to patch Service Specification with id " + id + ".");

        Optional<ServiceSpecification> toUpdate = serviceSpecificationRepository.findByServiceSpecificationId(id);
        if(!toUpdate.isPresent())
            throw new NotExistingEntityException("Service Specification with id " + id + " not found in DB.");

        ServiceSpecification serviceSpecification = toUpdate.get();

        final String baseType = serviceSpecificationUpdate.getBaseType();
        if(baseType != null)
            serviceSpecification.setBaseType(baseType);

        final String schemaLocation = serviceSpecificationUpdate.getSchemaLocation();
        if(schemaLocation != null)
            serviceSpecification.setSchemaLocation(schemaLocation);

        final String type = serviceSpecificationUpdate.getType();
        if(type != null)
            serviceSpecification.setType(type);

        final List<AttachmentRef> attachment = serviceSpecificationUpdate.getAttachment();
        if(attachment != null)
            serviceSpecification.setAttachment(attachment);

        final String description = serviceSpecificationUpdate.getDescription();
        if(description != null)
            serviceSpecification.setDescription(description);

        final Boolean isBundle = serviceSpecificationUpdate.isIsBundle();
        if(isBundle != null)
            serviceSpecification.setIsBundle(isBundle);

        final String lifecycleStatus = serviceSpecificationUpdate.getLifecycleStatus();
        if(lifecycleStatus != null)
            serviceSpecification.setLifecycleStatus(lifecycleStatus);

        final String name = serviceSpecificationUpdate.getName();
        if(name != null)
            serviceSpecification.setName(name);

        final List<RelatedParty> relatedParty = serviceSpecificationUpdate.getRelatedParty();
        if(relatedParty != null)
            serviceSpecification.setRelatedParty(relatedParty);

        final List<ResourceSpecificationRef> resourceSpecification =
                serviceSpecificationUpdate.getResourceSpecification();
        if(resourceSpecification != null)
            serviceSpecification.setResourceSpecification(resourceSpecification);

        final List<ServiceLevelSpecificationRef> serviceLevelSpecification =
                serviceSpecificationUpdate.getServiceLevelSpecification();
        if(serviceLevelSpecification != null)
            serviceSpecification.setServiceLevelSpecification(serviceLevelSpecification);

        final List<ServiceSpecCharacteristic> serviceSpecCharacteristic =
                serviceSpecificationUpdate.getServiceSpecCharacteristic();
        if(serviceSpecCharacteristic != null)
            serviceSpecification.setServiceSpecCharacteristic(serviceSpecCharacteristic);

        final List<ServiceSpecRelationship> serviceSpecRelationship =
                serviceSpecificationUpdate.getServiceSpecRelationship();
        if(serviceSpecRelationship != null)
            serviceSpecification.setServiceSpecRelationship(serviceSpecRelationship);

        final TargetServiceSchema targetServiceSchema = serviceSpecificationUpdate.getTargetServiceSchema();
        if(targetServiceSchema != null)
            serviceSpecification.setTargetServiceSchema(targetServiceSchema);

        final TimePeriod validFor = serviceSpecificationUpdate.getValidFor();
        if(validFor != null)
            serviceSpecification.setValidFor(validFor);

        final String version = serviceSpecificationUpdate.getVersion();
        if(version != null)
            serviceSpecification.setVersion(version);

        serviceSpecificationRepository.save(serviceSpecification);

        log.info("Service Specification " + id + " patched.");

        return serviceSpecification;
    }

    public ServiceSpecification get(String id) throws NotExistingEntityException {

        Optional<ServiceSpecification> retrieved = serviceSpecificationRepository.findByServiceSpecificationId(id);
        if(!retrieved.isPresent())
            throw new NotExistingEntityException("Service Specification with id " + id + " not found in DB.");

        ServiceSpecification serviceSpecification = retrieved.get();

        Hibernate.initialize(serviceSpecification.getAttachment());
        Hibernate.initialize(serviceSpecification.getRelatedParty());
        Hibernate.initialize(serviceSpecification.getResourceSpecification());
        Hibernate.initialize(serviceSpecification.getServiceLevelSpecification());

        for(ServiceSpecCharacteristic ssc : serviceSpecification.getServiceSpecCharacteristic()){
            Hibernate.initialize(ssc.getServiceSpecCharRelationship());
            Hibernate.initialize(ssc.getServiceSpecCharacteristicValue());
        }

        Hibernate.initialize(serviceSpecification.getServiceSpecRelationship());
        Hibernate.initialize(serviceSpecification.getTargetServiceSchema());

        return serviceSpecification;
    }
}
