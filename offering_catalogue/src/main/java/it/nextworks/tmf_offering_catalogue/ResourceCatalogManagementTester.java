package it.nextworks.tmf_offering_catalogue;

import it.nextworks.tmf_offering_catalogue.information_models.Any;
import it.nextworks.tmf_offering_catalogue.information_models.ResourceSpecificationRef;
import it.nextworks.tmf_offering_catalogue.information_models.TimePeriod;
import it.nextworks.tmf_offering_catalogue.information_models.resource.*;
import it.nextworks.tmf_offering_catalogue.services.ResourceCandidateService;
import it.nextworks.tmf_offering_catalogue.services.ResourceSpecificationService;
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
public class ResourceCatalogManagementTester {

    private static final Logger log = LoggerFactory.getLogger(ResourceCatalogManagementTester.class);

    @Autowired
    ResourceCandidateService resourceCandidateService;

    @Autowired
    ResourceSpecificationService resourceSpecificationService;

    public static void main(String[] args) {
        SpringApplication.run(ResourceCatalogManagementTester.class);
    }

    @Bean
    public CommandLineRunner resourceCatalogManagementDemo() {
        return(args) -> {

            ResourceSpecCharacteristicValue resourceSpecCharacteristicValue = new ResourceSpecCharacteristicValue()
                    .value(new Any().value("i_am_an_identifier"))
                    .valueType("UUID");
            ResourceSpecCharacteristicValue resourceSpecCharacteristicValue1 = new ResourceSpecCharacteristicValue()
                    .value(new Any().value("i_am_an_identifier1"))
                    .valueType("UUID");
            List<ResourceSpecCharacteristicValue> values = new ArrayList<>();
            values.add(resourceSpecCharacteristicValue);
            values.add(resourceSpecCharacteristicValue1);
            TimePeriod validFor = new TimePeriod().startDateTime("2017-08-12T00:00").endDateTime("2018-03-07T00:00");
            ResourceSpecCharacteristic resourceSpecCharacteristic = new ResourceSpecCharacteristic()
                    .configurable(true)
                    .description("VNFD")
                    .isUnique(true)
                    .name("my_vnf_descriptor")
                    .resourceSpecCharacteristicValue(values)
                    .validFor(validFor);
            ResourceSpecCharacteristicValue resourceSpecCharacteristicValue2 = new ResourceSpecCharacteristicValue()
                    .value(new Any().value("i_am_a_vnf_provider"))
                    .valueType("String");
            List<ResourceSpecCharacteristicValue> values1 = new ArrayList<>();
            values1.add(resourceSpecCharacteristicValue2);
            TimePeriod validFor1 = new TimePeriod().startDateTime("2014-01-23T00:00").endDateTime("2022-05-07T00:00");
            ResourceSpecCharacteristic resourceSpecCharacteristic1 = new ResourceSpecCharacteristic()
                    .configurable(false)
                    .description("my_vnf_provider")
                    .isUnique(true)
                    .name("my_vnf_provider")
                    .resourceSpecCharacteristicValue(values1)
                    .validFor(validFor1);
            List<ResourceSpecCharacteristic> resourceSpecCharacteristics = new ArrayList<>();
            resourceSpecCharacteristics.add(resourceSpecCharacteristic);
            resourceSpecCharacteristics.add(resourceSpecCharacteristic1);
            ResourceSpecificationCreate resourceSpecification = new ResourceSpecificationCreate()
                    .name("my_resource_specification")
                    .resourceSpecCharacteristic(resourceSpecCharacteristics);
            ResourceSpecification rsSaved = resourceSpecificationService.create(resourceSpecification);

            log.info("ResourceSpecification found with findByResourceSpecificationId:");
            log.info("-----------------------------------------------");
            ResourceSpecification rsTmp = resourceSpecificationService.get(rsSaved.getId());
            log.info(rsTmp.toString());
            log.info("-----------------------------------------------");

            resourceSpecCharacteristics.get(0).setName("my_modified_vnf_descriptor");
            resourceSpecCharacteristics.get(0).getResourceSpecCharacteristicValue()
                    .get(0).getValue().setValue("i_am_a_modified_identifier");
            resourceSpecCharacteristics.get(1).setName("my_modified_vnf_provider");
            resourceSpecCharacteristics.get(1).getResourceSpecCharacteristicValue()
                    .get(0).getValue().setValue("i_am_a_modified_vnf_provider");
            ResourceSpecificationUpdate resourceSpecificationUpdate = new ResourceSpecificationUpdate()
                    .name("my_modified_resource_specification")
                    .resourceSpecCharacteristic(resourceSpecCharacteristics);
            ResourceSpecification rsSaved1 =
                    resourceSpecificationService.patch(rsTmp.getId(), resourceSpecificationUpdate);

            log.info("ResourceSpecification found with findByResourceSpecificationId:");
            log.info("-----------------------------------------------");
            ResourceSpecification rsTmp1 = resourceSpecificationService.get(rsSaved1.getId());
            log.info(rsTmp1.toString());
            log.info("-----------------------------------------------");

            ResourceSpecificationRef resourceSpecificationRef = new ResourceSpecificationRef()
                    .href(rsTmp1.getHref())
                    .id(rsTmp1.getId())
                    .name("my_resource_specification_ref");
            ResourceCandidateCreate resourceCandidateCreate = new ResourceCandidateCreate()
                    .name("my_resource_candidate")
                    .resourceSpecification(resourceSpecificationRef);
            ResourceCandidate rcSaved = resourceCandidateService.create(resourceCandidateCreate);

            log.info("ResourceCandidate found with findByResourceCandidateId:");
            log.info("-----------------------------------------------");
            ResourceCandidate rcTmp = resourceCandidateService.get(rcSaved.getId());
            log.info(rcTmp.toString());
            log.info("-----------------------------------------------");

            resourceSpecificationRef.setName("my_modified_resource_specification_ref");
            ResourceCandidateUpdate resourceCandidateUpdate = new ResourceCandidateUpdate()
                    .name("my_modified_resource_candidate")
                    .resourceSpecification(resourceSpecificationRef);
            ResourceCandidate rcSaved1 = resourceCandidateService.patch(rcTmp.getId(), resourceCandidateUpdate);

            log.info("ResourceCandidate found with findByResourceCandidateId:");
            log.info("-----------------------------------------------");
            ResourceCandidate rcTmp1 = resourceCandidateService.get(rcSaved1.getId());
            log.info(rcTmp1.toString());
            log.info("-----------------------------------------------");


            resourceSpecificationService.delete(rsTmp1.getId());
            resourceCandidateService.delete(rcTmp1.getId());
        };
    }
}
