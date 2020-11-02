package com.fa.service;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Page;

import com.fa.dto.OrdersDTO;
import com.fa.dto.ProductDTO;
import com.fa.dto.SearchOrderDTO;
import com.fa.entity.Orders;

public interface OrdersService {
	/**
	 * Save order to database.
	 * 
	 * @param ordersDTO
	 * @param productDTOs
	 * @param principal
	 * @return
	 */
	Orders saveOrder(OrdersDTO ordersDTO, List<ProductDTO> productDTOs, Principal principal);

	/**
	 * Get a order by id.
	 * 
	 * @param id
	 * @return null if order not found
	 */
	Orders getOrders(int id);

	/**
	 * Multi search orders by
	 * 
	 * @param search
	 * @return
	 */
	Page<Orders> searchOrders(SearchOrderDTO search);

}