package com.example.kafkaexample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaMessageService {
	
	
	@Autowired
    private KafkaTemplate<String,Object> template;

    public void sendMessageToTopic(String message){
    	
        ListenableFuture<SendResult<String, Object>> future = template.send("kafka-rohit-demo", message);
                
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
        	
        	@Override
        	public void onSuccess(SendResult<String, Object> result) {
        		System.out.println("Sent message =[ " + message +" ] with offset=[" + result.getRecordMetadata().offset()+" ]");
        	}
        	
        	@Override
        	public void onFailure(Throwable ex) {
        		System.out.println("Unable to send message =[" + message +" ] due to : " + ex.getMessage());
        	}
        	
		});
        
        

    }
    
    @KafkaListener(
    topics = "kafka-rohit-demo",
    groupId = "rohit-group-1")
    public void consume(String message) {
      System.out.println("consumer consume the message {} "+ message);
    }

}
