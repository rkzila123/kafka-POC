package com.example.order.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.order.entity.OrderEntity;

@Repository
public interface OrderRepository  extends JpaRepository<OrderEntity, Long>{

	
	@Modifying
	@Query("Update OrderEntity oe SET oe.status=:status where oe.id in :orderId")
	void updateOrderSatus(@Param ("orderId") Set<Long> orderId,@Param ("status") String status);

}
