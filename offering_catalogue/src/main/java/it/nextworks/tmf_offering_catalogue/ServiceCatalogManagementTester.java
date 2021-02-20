package it.nextworks.tmf_offering_catalogue;

import it.nextworks.tmf_offering_catalogue.information_models.Any;
import it.nextworks.tmf_offering_catalogue.information_models.ServiceSpecificationRef;
import it.nextworks.tmf_offering_catalogue.information_models.TimePeriod;
import it.nextworks.tmf_offering_catalogue.information_models.service.*;
import it.nextworks.tmf_offering_catalogue.services.ServiceCandidateService;
import it.nextworks.tmf_offering_catalogue.services.ServiceSpecificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ServiceCatalogManagementTester {

    private static final Logger log = LoggerFactory.getLogger(ServiceCatalogManagementTester.class);

    @Autowired
    ServiceCandidateService serviceCandidateService;

    @Autowired
    ServiceSpecificationService serviceSpecificationService;

    public static void main(String[] args) { SpringApplication.run(ServiceCatalogManagementTester.class); }

    @Bean
    public CommandLineRunner serviceCatalogManagementDemo() {
        return(args) -> {

            ServiceSpecCharacteristicValue serviceSpecCharacteristicValue = new ServiceSpecCharacteristicValue()
                    .value(new Any().value("i_am_a_service_specification_value"))
                    .valueType("String");
            ServiceSpecCharacteristicValue serviceSpecCharacteristicValue1 = new ServiceSpecCharacteristicValue()
                    .value(new Any().value("i_am_a_service_specification_value1"))
                    .valueType("String");
            List<ServiceSpecCharacteristicValue> values = new ArrayList<>();
            values.add(serviceSpecCharacteristicValue);
            values.add(serviceSpecCharacteristicValue1);
            TimePeriod validFor = new TimePeriod().startDateTime("2017-08-12T00:00").endDateTime("2018-03-07T00:00");
            ServiceSpecCharacteristic serviceSpecCharacteristic = new ServiceSpecCharacteristic()
                    .configurable(true)
                    .description("i_am_a_service_specification")
                    .isUnique(true)
                    .name("my_service_specification")
                    .serviceSpecCharacteristicValue(values)
                    .validFor(validFor);
            ServiceSpecCharacteristicValue serviceSpecCharacteristicValue2 = new ServiceSpecCharacteristicValue()
                    .value(new Any().value("i_am_a_service_specification_value2"))
                    .valueType("String");
            List<ServiceSpecCharacteristicValue>values1 = new ArrayList<>();
            values1.add(serviceSpecCharacteristicValue2);
            TimePeriod validFor1 = new TimePeriod().startDateTime("2014-01-23T00:00").endDateTime("2022-05-07T00:00");
            ServiceSpecCharacteristic serviceSpecCharacteristic1 = new ServiceSpecCharacteristic()
                    .configurable(false)
                    .description("i_am_a_service_specification1")
                    .isUnique(true)
                    .name("my_service_specification1")
                    .serviceSpecCharacteristicValue(values1)
                    .validFor(validFor1);
            List<ServiceSpecCharacteristic> serviceSpecCharacteristics = new ArrayList<>();
            serviceSpecCharacteristics.add(serviceSpecCharacteristic);
            serviceSpecCharacteristics.add(serviceSpecCharacteristic1);
            ServiceSpecificationCreate serviceSpecificationCreate = new ServiceSpecificationCreate()
                    .name("my_service_specification")
                    .serviceSpecCharacteristic(serviceSpecCharacteristics);
            ServiceSpecification ssSaved = serviceSpecificationService.create(serviceSpecificationCreate);

            log.info("ServiceSpecification found with findByServiceSpecificationId:");
            log.info("-----------------------------------------------");
            ServiceSpecification ssTmp = serviceSpecificationService.get(ssSaved.getId());
            log.info(ssTmp.toString());
            log.info("-----------------------------------------------");

            serviceSpecCharacteristics.get(0).setName("my_modified_service_specification");
            serviceSpecCharacteristics.get(0).getServiceSpecCharacteristicValue()
                    .get(0).getValue().setValue("i_am_a_modified_service_specification_value");
            serviceSpecCharacteristics.get(1).setName("my_modified_service_specification1");
            serviceSpecCharacteristics.get(1).getServiceSpecCharacteristicValue()
                    .get(0).getValue().setValue("i_am_a_modified_service_specification_value2");
            ServiceSpecificationUpdate serviceSpecificationUpdate = new ServiceSpecificationUpdate()
                    .name("my_modified_service_specification")
                    .serviceSpecCharacteristic(serviceSpecCharacteristics);
            ServiceSpecification ssSaved1 = serviceSpecificationService.patch(ssTmp.getId(), serviceSpecificationUpdate);

            log.info("ServiceSpecification found with findByServiceSpecificationId:");
            log.info("-----------------------------------------------");
            ServiceSpecification ssTmp1 = serviceSpecificationService.get(ssSaved1.getId());
            log.info(ssTmp1.toString());
            log.info("-----------------------------------------------");

            ServiceSpecificationRef serviceSpecificationRef = new ServiceSpecificationRef()
                    .href(ssTmp1.getHref())
                    .id(ssTmp1.getId())
                    .name("my_service_specification_ref");
            ServiceCandidateCreate serviceCandidateCreate = new ServiceCandidateCreate()
                    .name("my_service_candidate")
                    .serviceSpecification(serviceSpecificationRef);
            ServiceCandidate scSaved = serviceCandidateService.create(serviceCandidateCreate);

            log.info("ServiceCandidate found with findByServiceCandidateId:");
            log.info("-----------------------------------------------");
            ServiceCandidate scTmp = serviceCandidateService.get(scSaved.getId());
            log.info(scTmp.toString());
            log.info("-----------------------------------------------");

            serviceSpecificationRef.setName("my_modified_service_specification_ref");
            ServiceCandidateUpdate serviceCandidateUpdate  = new ServiceCandidateUpdate()
                    .name("my_modified_service_candidate")
                    .serviceSpecification(serviceSpecificationRef);
            ServiceCandidate scSaved1 = serviceCandidateService.patch(scTmp.getId(), serviceCandidateUpdate);

            log.info("ServiceCandidate found with findByServiceCandidateId:");
            log.info("-----------------------------------------------");
            ServiceCandidate scTmp1 = serviceCandidateService.get(scSaved1.getId());
            log.info(scTmp1.toString());
            log.info("-----------------------------------------------");

            serviceSpecificationService.delete(ssTmp1.getId());
            serviceCandidateService.delete(scTmp1.getId());
        };
    }
}
