package com.example.invoice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.invoice.entity.InvoiceEntity;
import com.example.invoice.repository.InvoiceRepository;
import com.example.invoice.utils.InvoiceConstants;
import com.example.invoice.utils.KafkaMasterUtility;
import com.example.invoice.utils.OrderDTO;
import com.fasterxml.jackson.core.type.TypeReference;


@Service
public class InvoiceService {
	
	@Autowired
	private InvoiceRepository invoiceRepository;
	
	@Autowired
    private KafkaTemplate<String,Object> template;


	@KafkaListener(
	topics = InvoiceConstants.INVOICE_TOPIC,
	groupId = "{spring.kafka.consumer.group-id}")
	@Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void consumeAndCreateInvoice(ConsumerRecord consumerRecord) {
		
		 System.out.println(" consumeAndCreateInvoice Listener ");
		 
		 List<OrderDTO> orderList=KafkaMasterUtility.consumerRecordValueParser(consumerRecord,
				 new TypeReference<List<OrderDTO>>() {});
		 
		 orderList.forEach(System.out::println);
		 
		 List<InvoiceEntity> invoiceEntityList=createInvoiceFromOrder(orderList);
		 
		 invoiceEntityList= invoiceRepository.saveAll(invoiceEntityList);
		      
		 template.send(InvoiceConstants.ORDER_TOPIC, orderList);
		 
		 System.out.println("Order update message pushed to Kafka");
		      
	}


	private List<InvoiceEntity> createInvoiceFromOrder(List<OrderDTO> orderList) {
		
		return orderList.stream().map(orderDTO ->
		{
			InvoiceEntity invoiceEntity  = new InvoiceEntity();
			invoiceEntity.setInvoiceValue(orderDTO.getOrderValue());
			invoiceEntity.setOrderName(orderDTO.getOrderName());
			return invoiceEntity;
		}
				).collect(Collectors.toList());
	}

}
