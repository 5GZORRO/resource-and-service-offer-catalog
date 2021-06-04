package it.nextworks.tmf_offering_catalog.common.config;

import it.nextworks.tmf_offering_catalog.services.GeographicAddressValidationService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class GeographicAddressValidationTestContextConfiguration {
    @Bean
    public GeographicAddressValidationService geographicAddressValidationTestService() {
        return new GeographicAddressValidationService();
    }
}
