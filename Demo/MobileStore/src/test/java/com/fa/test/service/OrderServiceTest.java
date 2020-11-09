package com.fa.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import com.fa.common.OrderContant;
import com.fa.dto.OrdersDTO;
import com.fa.dto.ProductDTO;
import com.fa.dto.SearchOrderDTO;
import com.fa.entity.Category;
import com.fa.entity.OrderDetail;
import com.fa.entity.OrderDetailKey;
import com.fa.entity.Orders;
import com.fa.entity.Product;
import com.fa.entity.Users;
import com.fa.repository.OrderRepository;
import com.fa.repository.OrderRepositoryCustom;
import com.fa.service.OrdersService;
import com.fa.service.ProductServiceImpl;
import com.fa.service.UserServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
class OrderServiceTest {
	@MockBean
	private UsernamePasswordAuthenticationToken principal;

	@MockBean
	ProductServiceImpl productService;

	@MockBean
	private UserServiceImpl userService;

	@MockBean
	private OrderRepository orderRepository;

	@MockBean
	private OrderRepositoryCustom orderRepositoryCustom;

	@Autowired
	private OrdersService ordersService;

	@Test
	void saveOrder1() {
		UserDetails userDetails = (UserDetails) new User("customer", "123456", new ArrayList<GrantedAuthority>());
		Users user = new Users(1, "customer", "123456", "First", "Last", new Date(), 1, "123456789", "email", "address",
				1, new Date(), null);
		Date now = new Date();
		OrdersDTO ordersDTO = new OrdersDTO(null, null, "address", "0123456789", "email@gmail.com", "", now, null, 0);
		Orders orders = new Orders(user, "address", "0123456789", "email@gmail.com", "", now, null, 0);
		orders.setId(1);
		List<ProductDTO> productDTOs = new ArrayList<>();
		productDTOs.add(new ProductDTO(1, "p1", new Category(), "", 100f, 10, 15, "Apple", 1, 1, "", null));
		productDTOs.add(new ProductDTO(2, "p2", new Category(), "", 100f, 10, 15, "Samsung", 1, 1, "", null));
		List<OrderDetail> orderDetails = new ArrayList<>();
		OrderDetailKey orderDetailKey = null;
		Product product = null;

		for (ProductDTO productDTO : productDTOs) {
			product = new Product();
			product.setId(productDTO.getId());
			orderDetailKey = new OrderDetailKey(orders, product);
			orderDetails.add(new OrderDetail(orderDetailKey, productDTO.getQuantity(), productDTO.getPrice(),
					productDTO.getDiscount()));
		}
		orders.setOrderDetailList(orderDetails);
		when(principal.getPrincipal()).thenReturn(userDetails);
		when(userService.findByUserNameAndStatus(userDetails.getUsername())).thenReturn(user);
		when(productService.updateQuantity(anyInt(), anyInt())).thenReturn(true);
		when(orderRepository.save(any(Orders.class))).thenReturn(orders);

		assertEquals(orders, ordersService.saveOrder(ordersDTO, productDTOs, principal));
	}

	@Test
	void saveOrder2() {
		Users user = null;
		Date now = new Date();
		OrdersDTO ordersDTO = new OrdersDTO(null, null, "address", "0123456789", "email@gmail.com", "", now, null, 0);
		Orders orders = new Orders(user, "address", "0123456789", "email@gmail.com", "", now, null, 0);
		orders.setId(1);
		List<ProductDTO> productDTOs = new ArrayList<>();
		productDTOs.add(new ProductDTO(1, "p1", new Category(), "", 100f, 10, 15, "Apple", 1, 1, "", null));
		productDTOs.add(new ProductDTO(2, "p2", new Category(), "", 100f, 10, 15, "Samsung", 1, 1, "", null));
		List<OrderDetail> orderDetails = new ArrayList<>();
		OrderDetailKey orderDetailKey = null;
		Product product = null;

		for (ProductDTO productDTO : productDTOs) {
			product = new Product();
			product.setId(productDTO.getId());
			orderDetailKey = new OrderDetailKey(orders, product);
			orderDetails.add(new OrderDetail(orderDetailKey, productDTO.getQuantity(), productDTO.getPrice(),
					productDTO.getDiscount()));
		}
		orders.setOrderDetailList(orderDetails);
		when(productService.updateQuantity(anyInt(), anyInt())).thenReturn(true);
		when(orderRepository.save(any(Orders.class))).thenReturn(orders);
		ordersService.saveOrder(ordersDTO, productDTOs, null);
		assertEquals(orders, ordersService.saveOrder(ordersDTO, productDTOs, null));
	}

	@Test
	void searchOrders1() {
		Orders orders1 = new Orders(null, "address", "phone", "email", "name", new Date(), null, 0);
		Orders orders2 = new Orders(null, "address", "phone", "email", "name", new Date(), null, 0);
		orders1.setId(1);
		orders2.setId(2);
		List<Orders> listOrders = new ArrayList<>();
		listOrders.add(orders1);
		listOrders.add(orders2);
		SearchOrderDTO search = new SearchOrderDTO(null, null, null, null, null);
		Pageable pageable = PageRequest.of(1, 1);
		Page<Orders> page = new PageImpl<>(listOrders, pageable, 2);
		when(orderRepositoryCustom.searchOrders(search, OrderContant.TOTAL_PER_PAGE)).thenReturn(page);
		assertEquals(page, ordersService.searchOrders(search));
		assertEquals(1, search.getPage());
		assertEquals(null, search.getToDate());
	}

	@Test
	void searchOrders2() {
		Orders orders1 = new Orders(null, "address", "phone", "email", "name", new Date(), null, 0);
		Orders orders2 = new Orders(null, "address", "phone", "email", "name", new Date(), null, 0);
		orders1.setId(1);
		orders2.setId(2);
		List<Orders> listOrders = new ArrayList<>();
		listOrders.add(orders1);
		listOrders.add(orders2);
		SearchOrderDTO search = new SearchOrderDTO(null, null, null, null, 0);
		Pageable pageable = PageRequest.of(1, 1);
		Page<Orders> page = new PageImpl<>(listOrders, pageable, 2);
		when(orderRepositoryCustom.searchOrders(search, OrderContant.TOTAL_PER_PAGE)).thenReturn(page);
		assertEquals(page, ordersService.searchOrders(search));
		assertEquals(1, search.getPage());
		assertEquals(null, search.getToDate());
	}

	@Test
	void searchOrders3() {
		Orders orders1 = new Orders(null, "address", "phone", "email", "name", new Date(), null, 0);
		Orders orders2 = new Orders(null, "address", "phone", "email", "name", new Date(), null, 0);
		orders1.setId(1);
		orders2.setId(2);
		List<Orders> listOrders = new ArrayList<>();
		listOrders.add(orders1);
		listOrders.add(orders2);
		Date toDate = new Date();
		SearchOrderDTO search = new SearchOrderDTO(null, null, toDate, null, -1);
		Pageable pageable = PageRequest.of(1, 1);
		Page<Orders> page = new PageImpl<>(listOrders, pageable, 2);
		Date expectedDate = new Date(search.getToDate().getTime() + (1000 * 60 * 60 * 24));
		when(orderRepositoryCustom.searchOrders(search, OrderContant.TOTAL_PER_PAGE)).thenReturn(page);
		assertEquals(page, ordersService.searchOrders(search));
		assertEquals(1, search.getPage());
		assertEquals(expectedDate, search.getToDate());
	}

	@Test
	void searchOrders4() {
		Orders orders1 = new Orders(null, "address", "phone", "email", "name", new Date(), null, 0);
		Orders orders2 = new Orders(null, "address", "phone", "email", "name", new Date(), null, 0);
		orders1.setId(1);
		orders2.setId(2);
		List<Orders> listOrders = new ArrayList<>();
		listOrders.add(orders1);
		listOrders.add(orders2);
		Date toDate = new Date();
		SearchOrderDTO search = new SearchOrderDTO(null, null, toDate, null, 2);
		Pageable pageable = PageRequest.of(1, 1);
		Page<Orders> page = new PageImpl<>(listOrders, pageable, 2);
		Date expectedDate = new Date(search.getToDate().getTime() + (1000 * 60 * 60 * 24));
		when(orderRepositoryCustom.searchOrders(search, OrderContant.TOTAL_PER_PAGE)).thenReturn(page);
		assertEquals(page, ordersService.searchOrders(search));
		assertEquals(2, search.getPage());
		assertEquals(expectedDate, search.getToDate());
	}

	@Test
	void searchOrders5() {
		Orders orders1 = new Orders(null, "address", "phone", "email", "name", new Date(), null, 0);
		Orders orders2 = new Orders(null, "address", "phone", "email", "name", new Date(), null, 0);
		orders1.setId(1);
		orders2.setId(2);
		List<Orders> listOrders = new ArrayList<>();
		listOrders.add(orders1);
		listOrders.add(orders2);
		SearchOrderDTO search = new SearchOrderDTO(null, null, null, null, 2);
		Pageable pageable = PageRequest.of(1, 1);
		Page<Orders> page = new PageImpl<>(listOrders, pageable, 2);
		when(orderRepositoryCustom.searchOrders(search, OrderContant.TOTAL_PER_PAGE)).thenReturn(page);
		assertEquals(page, ordersService.searchOrders(search));
		assertEquals(2, search.getPage());
		assertEquals(null, search.getToDate());
	}

	@Test
	void getOrders1() {
		Orders orders1 = new Orders(null, "address", "phone", "email", "name", new Date(), null, 0);
		orders1.setId(1);
		when(orderRepository.getOrders(1)).thenReturn(orders1);
		assertEquals(orders1, ordersService.getOrders(1));
	}

	@Test
	void getOrders2() {
		when(orderRepository.getOrders(1)).thenReturn(null);
		assertEquals(null, ordersService.getOrders(1));
	}
}
