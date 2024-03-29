package it.nextworks.tmf_offering_catalog.common.config;

import it.nextworks.tmf_offering_catalog.information_models.kafka.ExternalProductOffering;
import it.nextworks.tmf_offering_catalog.information_models.kafka.ExternalProductOrder;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Bean
    public ConsumerFactory<String, ExternalProductOffering> consumerFactory() {

        JsonDeserializer<ExternalProductOffering> epoDeserializer =
                new JsonDeserializer<>(ExternalProductOffering.class);
        epoDeserializer.setRemoveTypeHeaders(false);
        epoDeserializer.addTrustedPackages("eu._5gzorro.manager.domain.events.ProductOfferingUpdateEvent");
        epoDeserializer.setUseTypeMapperForKey(true);

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, epoDeserializer);

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                epoDeserializer);
    }

    @Bean
    public ConsumerFactory<String, ExternalProductOrder> consumerOrderFactory() {

        JsonDeserializer<ExternalProductOrder> epoDeserializer =
                new JsonDeserializer<>(ExternalProductOrder.class);
        epoDeserializer.setRemoveTypeHeaders(false);
        epoDeserializer.addTrustedPackages("eu._5gzorro.manager.domain.events.ProductOrderUpdateEvent");
        epoDeserializer.setUseTypeMapperForKey(true);

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, epoDeserializer);

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                epoDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ExternalProductOffering> kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, ExternalProductOffering> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ExternalProductOrder> kafkaOrderListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, ExternalProductOrder> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerOrderFactory());

        return factory;
    }
}
