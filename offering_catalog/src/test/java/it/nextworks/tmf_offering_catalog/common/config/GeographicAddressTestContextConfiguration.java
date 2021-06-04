package it.nextworks.tmf_offering_catalog.common.config;

import it.nextworks.tmf_offering_catalog.services.GeographicAddressService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class GeographicAddressTestContextConfiguration {
    @Bean
    public GeographicAddressService geographicAddressTestService() {
        return new GeographicAddressService();
    }
}
