package it.nextworks.tmf_offering_catalog.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class LinkedBlockingQueueConfig {
    @Bean
    public LinkedBlockingQueue<String> linkedBlockingQueue() {
        return new LinkedBlockingQueue<>();
    }
}
