package com.example.invoice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.invoice.entity.InvoiceEntity;

@Repository
public interface InvoiceRepository  extends JpaRepository<InvoiceEntity, Long>{

}
