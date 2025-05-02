package com.example.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.order.service.OrderService;
import com.example.order.utils.GenericEventDTO;
import com.example.order.utils.OrderDTO;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	
	@Autowired
	private OrderService orderService;
	
	    @PostMapping(value="/createOrder" , consumes="application/json" , produces="application/json")
	    public GenericEventDTO<OrderDTO> sendEvents(@RequestBody List<OrderDTO> orderDTOs) {
	    	
	    	GenericEventDTO<OrderDTO> response= new GenericEventDTO<>();
	    	
	    	try {
				response=orderService.createOrderAndInvokeInventory(orderDTOs);
			} catch (Exception e) {
				response= new GenericEventDTO<>();
				response.setStatus("FAILURE");
				response.setMessage("Order Creation failed due to : "+ e.getMessage());
				e.printStackTrace();
			}
	    	
	       return response;
	    }

}
