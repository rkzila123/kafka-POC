package com.example.invoice.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class KafkaConsumerConfig {
	
		@Value("${spring.kafka.bootstrap-servers}")
		private String bootstrapServer;
	
	    @Bean
	    public Map<String, Object> consumerConfig() {
	        Map<String, Object> props = new HashMap<>();
	        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServer);
	        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
	        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,JsonDeserializer.class);
	        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS,ErrorHandlingDeserializer.class);
	        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS,ErrorHandlingDeserializer.class);
	        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
	        props.put(JsonDeserializer.KEY_DEFAULT_TYPE, String.class);
	        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, Object.class);
	        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
	        return props;
	    }
	    
	    
	    @Bean
	    public ConsumerFactory<String,Object> consumerFactory(){
	        return new DefaultKafkaConsumerFactory<>(consumerConfig());
	    }

	    @Bean
	    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> kafkaListenerContainerFactory() {
	        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
	                new ConcurrentKafkaListenerContainerFactory<>();
	        factory.setConsumerFactory(consumerFactory());
	        return factory;
	    }
	    
	  

}
