package com.example.invoice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.util.StringUtils;


@Entity
@Table(name = "INVOICE_TABLE")
public class InvoiceEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "invoice_id")
	private Long id;

	@Column(name = "invoice_value")
	private Double invoiceValue;

	@Column(name = "invoice_status")
	private String invoiceStatus;
	
	@Column(name = "order_name")
	private String orderName;
	
	
	
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Double getInvoiceValue() {
		return invoiceValue;
	}



	public void setInvoiceValue(Double invoiceValue) {
		this.invoiceValue = invoiceValue;
	}



	public String getInvoiceStatus() {
		return invoiceStatus;
	}



	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}



	public String getOrderName() {
		return orderName;
	}



	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}



	@SuppressWarnings("deprecation")
	@PrePersist
	void statusDefaultSetter(){
		if(StringUtils.isEmpty(this.invoiceStatus)) {
			this.invoiceStatus="DISPATCHED";
		}
	}

}
