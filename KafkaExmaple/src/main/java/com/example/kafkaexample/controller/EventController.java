package com.example.kafkaexample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kafkaexample.service.KafkaMessageService;

@RestController
@RequestMapping("/kafka-demo")
public class EventController {
	
	    @Autowired
	    private KafkaMessageService publisher;

	    @GetMapping("/publish/{message}")
	    public ResponseEntity<?> publishMessage(@PathVariable String message) {
	        try {
	            for (int i = 0; i <= 100000; i++) {
	                publisher.sendMessageToTopic(message + " : " + i);
	            }
	            return ResponseEntity.ok("message published successfully ..");
	        } catch (Exception ex) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .build();
	        }
	    }

}
