package it.nextworks.tmf_offering_catalog.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class RestApiAutoConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        // To avoid instantiating and configuring the mapper everywhere
        ObjectMapper mapper = new ObjectMapper();
        return mapper;
    }

}
