package com.fa.repository;

import org.springframework.data.domain.Page;

import com.fa.dto.SearchOrderDTO;
import com.fa.entity.Orders;

public interface OrderRepositoryCustom {
	/**
	 * Multi search orders by
	 * 
	 * @param search
	 * @return
	 */
	Page<Orders> searchOrders(SearchOrderDTO search, int total);

}