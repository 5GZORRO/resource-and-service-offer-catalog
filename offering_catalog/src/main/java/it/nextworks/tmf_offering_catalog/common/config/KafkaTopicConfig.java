package it.nextworks.tmf_offering_catalog.common.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value("${kafka.topic.offerings}")
    private String topicOfferings;

    @Value("${kafka.topic.orders}")
    private String topicOrders;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public String topicOfferings() { return topicOfferings; }

    @Bean
    public String topicOrders() { return topicOrders; }

    @Bean
    public NewTopic topic1() {
        return new NewTopic("operatora-dlt-product-offerings", 1, (short) 1);
    }
}
