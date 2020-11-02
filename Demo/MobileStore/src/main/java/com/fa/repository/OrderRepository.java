package com.fa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fa.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders, Integer> {

	/**
	 * Get a order by id.
	 * 
	 * @param id
	 * @return null if order not found
	 */
	@Query("SELECT o FROM Orders o join fetch o.orderDetailList WHERE o.id=:id")
	Orders getOrders(int id);
}
