package com.example.order.utils;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class KafkaMasterUtility {
	
	public static <T> T consumerRecordValueParser(ConsumerRecord consumerRecord,
			 TypeReference<T> typeRef) {
		T serializedObject=null;
		
		try {
			if(consumerRecord!=null && consumerRecord.value()!=null) {
				
				ObjectMapper objectMapper = new ObjectMapper();
				serializedObject=objectMapper.convertValue(consumerRecord.value(), typeRef);
				
			}
		} catch (Exception e) {
			System.out.println("consumerRecordValueParser error");
			e.printStackTrace();
		}
		
		return serializedObject;
	}

}
