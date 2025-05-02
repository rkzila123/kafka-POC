package com.example.order.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.order.entity.OrderEntity;
import com.example.order.repository.OrderRepository;
import com.example.order.utils.GenericEventDTO;
import com.example.order.utils.KafkaMasterUtility;
import com.example.order.utils.OrderConstants;
import com.example.order.utils.OrderDTO;
import com.fasterxml.jackson.core.type.TypeReference;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
    private KafkaTemplate<String,Object> template;

	@Transactional(readOnly = false, rollbackFor = Throwable.class)
	public GenericEventDTO<OrderDTO> createOrderAndInvokeInventory(List<OrderDTO> orderDTOs) {
		
		List<OrderEntity> orderEntityList= orderDTOs.stream().map(orderDto ->
											{OrderEntity orderEntity = new OrderEntity();
												BeanUtils.copyProperties(orderDto, orderEntity);
												return orderEntity;
											}).collect(Collectors.toList());
		
		System.out.println("Printing order list before saving");
		
		orderEntityList.forEach(System.out::println);
		orderEntityList=orderRepository.saveAll(orderEntityList);
		
		List<OrderDTO> updatedDTOList= orderEntityList.stream().map(orderEntity ->
											{OrderDTO orderDTO = new OrderDTO();
												BeanUtils.copyProperties(orderEntity, orderDTO);
												return orderDTO;
											}).collect(Collectors.toList());
		
		template.send(OrderConstants.INVOICE_TOPIC, updatedDTOList);
		
		GenericEventDTO<OrderDTO> response= new GenericEventDTO<>();
		
		response.setStatus("SUCCESS");
		response.setMessage("Order has been created successfully and event published to Inventory");
		
		return response;
	}
	
	
	@KafkaListener(
	topics = OrderConstants.ORDER_TOPIC,
	groupId = "{spring.kafka.consumer.group-id}")
	@Transactional(readOnly = false, rollbackFor = Throwable.class)
	public void consumeAndUpdateOrder(ConsumerRecord consumerRecord) {
				
		System.out.println(" consumeAndUpdateOrder Listener ");
		 
		 List<OrderDTO> orderList=KafkaMasterUtility.consumerRecordValueParser(consumerRecord,
				 new TypeReference<List<OrderDTO>>() {});
		 
		 orderList.forEach(System.out::println);
		 
		 try {
			 System.out.println("order status update after 15 sec");
			 Thread.sleep(15000);
		 }catch(Exception ex) {
			 ex.printStackTrace();
		 }
		 
		 Set<Long> orderId=orderList.stream().map(OrderDTO::getId).collect(Collectors.toSet());
		 
		 orderRepository.updateOrderSatus(orderId,"CLOSED");
		      
		 		 
				      
	}

}
