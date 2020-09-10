package com.fa.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.fa.common.OrderContant;
import com.fa.dto.OrdersDTO;
import com.fa.dto.ProductDTO;
import com.fa.dto.SearchOrderDTO;
import com.fa.entity.OrderDetail;
import com.fa.entity.OrderDetailKey;
import com.fa.entity.Orders;
import com.fa.entity.Product;
import com.fa.entity.Users;
import com.fa.repository.OrderDetailRepository;
import com.fa.repository.OrderRepository;
import com.fa.repository.OrderRepositoryCustom;
import com.fa.repository.ProductRepository;

@Service
public class OrderServiceImpl implements OrdersService {

	@Autowired
	OrderRepository orderRepository;
	@Autowired
	OrderRepositoryCustom orderRepositoryCustom;
	@Autowired
	OrderDetailRepository orderDetailRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	ProductService productService;
	@Autowired
	private UserService userService;

	/**
	 * Save order to database.
	 * 
	 * @param ordersDTO
	 * @param productDTOs
	 * @param principal
	 * @return
	 */
	@Override
	@Transactional(rollbackOn = Exception.class)
	public Orders saveOrder(OrdersDTO ordersDTO, List<ProductDTO> productDTOs, Principal principal) {
		Users user = null;
		if (principal != null) {
			UserDetails loginedUser = (UserDetails) ((Authentication) principal).getPrincipal();
			user = userService.findByUserNameAndStatus(loginedUser.getUsername());
		}

		Orders orders = new Orders(user, ordersDTO.getAddress(), ordersDTO.getPhone(), ordersDTO.getEmail(),
				ordersDTO.getCustomerName(), new Date(), null, 0);
		List<OrderDetail> orderDetails = new ArrayList<>();
		OrderDetailKey orderDetailKey = null;
		Product product = null;

		for (ProductDTO productDTO : productDTOs) {
			productService.updateQuantity(productDTO.getId(), productDTO.getQuantity());
			product = new Product();
			product.setId(productDTO.getId());
			orderDetailKey = new OrderDetailKey(orders, product);
			orderDetails.add(new OrderDetail(orderDetailKey, productDTO.getQuantity(), productDTO.getPrice(),
					productDTO.getDiscount()));
		}
		orders.setOrderDetailList(orderDetails);
		return orderRepository.save(orders);
	}

	/**
	 * Get a order by id.
	 * 
	 * @param id
	 * @return null if order not found
	 */
	@Override
	public Orders getOrders(int id) {
		return orderRepository.getOrders(id);
	}

	/**
	 * Multi search orders by
	 * 
	 * @param search
	 * @return
	 */
	@Override
	public Page<Orders> searchOrders(SearchOrderDTO search) {
		if (search.getPage() == null || search.getPage() < 1) {
			search.setPage(1);
		}
		if (search.getToDate() != null) {
			search.setToDate(new Date(search.getToDate().getTime() + (1000 * 60 * 60 * 24)));
		}
		return orderRepositoryCustom.searchOrders(search, OrderContant.TOTAL_PER_PAGE);
	}
}
