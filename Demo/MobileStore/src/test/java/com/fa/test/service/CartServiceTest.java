package com.fa.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import com.fa.dto.CartItem;
import com.fa.dto.ProductDTO;
import com.fa.entity.Category;
import com.fa.entity.Product;
import com.fa.entity.Users;
import com.fa.repository.ProductRepository;
import com.fa.service.CartService;
import com.fa.service.ProductService;
import com.fa.service.ProductServiceImpl;
import com.fa.service.UserServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
class CartServiceTest {
	@MockBean
	private ProductServiceImpl productService;

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	private CartService cartService;

	@Test
	void testGetCartItems1() {
		List<CartItem> cartItems = new ArrayList<>();
		cartItems.add(new CartItem(362, 1));
		cartItems.add(new CartItem(360, 4));
		String cart = "[{\"id\":362,\"quantity\":1},{\"id\":360,\"quantity\":4}]";
		assertEquals(cartItems, cartService.getCartItems(cart));
	}

	@Test
	void testGetCartItems2() {
		String cart = "null";
		assertEquals(new ArrayList<CartItem>(), cartService.getCartItems(cart));
	}

	@Test
	void testGetCartItems3() {
		String cart = "[]";
		assertEquals(new ArrayList<CartItem>(), cartService.getCartItems(cart));
	}

	@Test
	void testGetCartItems4() {
		String cart = "[asdsa]asasdasd";
		assertEquals(new ArrayList<CartItem>(), cartService.getCartItems(cart));
	}

	@Test
	void testGetProductDTOs1() {
		List<CartItem> cartItems = new ArrayList<>();
		cartItems.add(new CartItem(362, 1));
		cartItems.add(new CartItem(360, 4));
		Category category = new Category(1, "Apple", "", 1);

		ProductDTO productDTO1 = new ProductDTO(362, "ap1", category, "", 1000f, 20, 10, "Apple", 1, 1, "", null);
		ProductDTO productDTO2 = new ProductDTO(360, "ap2", category, "", 1000f, 20, 15, "Apple", 1, 1, "", null);
		when(productService.getProductAvailable(productDTO1.getId())).thenReturn(productDTO1);
		when(productService.getProductAvailable(productDTO2.getId())).thenReturn(productDTO2);

		ProductDTO productDTO3 = new ProductDTO(362, "ap1",category, "10", 1000f, 20, 1, "Apple", 1, 1, "",
				null);
		ProductDTO productDTO4 = new ProductDTO(360, "ap2",category, "15", 1000f, 20, 4, "Apple", 1, 1, "",
				null);

		List<ProductDTO> productDTOs = new ArrayList<>();
		productDTOs.add(productDTO3);
		productDTOs.add(productDTO4);
		
		List<ProductDTO> actuaDtos= cartService.getProductDTOs(cartItems);
		assertEquals(productDTOs,actuaDtos);
	}
	
	@Test
	void testGetProductDTOs2() {
		List<CartItem> cartItems = new ArrayList<>();
		cartItems.add(new CartItem(362, 1));
		cartItems.add(new CartItem(360, 4));
		Category category = new Category(1, "Apple", "", 1);

		ProductDTO productDTO1 = new ProductDTO(362, "ap1", category, "", 1000f, 20, 10, "Apple", 1, 1, "", null);
		ProductDTO productDTO2 = new ProductDTO(360, "ap2", category, "", 1000f, 20, 15, "Apple", 1, 0, "", null);
		when(productService.getProductAvailable(productDTO1.getId())).thenReturn(productDTO1);
		when(productService.getProductAvailable(productDTO2.getId())).thenReturn(null);

		ProductDTO productDTO3 = new ProductDTO(362, "ap1",category, "10", 1000f, 20, 1, "Apple", 1, 1, "",
				null);


		List<ProductDTO> productDTOs = new ArrayList<>();
		productDTOs.add(productDTO3);
	
		
		List<ProductDTO> actuaDtos= cartService.getProductDTOs(cartItems);
		assertEquals(productDTOs,actuaDtos);
	}
	
	@Test
	void testGetProductDTOs3() {
		List<CartItem> cartItems = new ArrayList<>();
		cartItems.add(new CartItem(362, 1));
		cartItems.add(new CartItem(360, 0));
		Category category = new Category(1, "Apple", "", 1);

		ProductDTO productDTO1 = new ProductDTO(362, "ap1", category, "", 1000f, 20, 10, "Apple", 1, 1, "", null);
		ProductDTO productDTO2 = new ProductDTO(360, "ap2", category, "", 1000f, 20, 15, "Apple", 1, 1, "", null);
		when(productService.getProductAvailable(productDTO1.getId())).thenReturn(productDTO1);
		when(productService.getProductAvailable(productDTO2.getId())).thenReturn(productDTO2);

		ProductDTO productDTO3 = new ProductDTO(362, "ap1",category, "10", 1000f, 20, 1, "Apple", 1, 1, "",
				null);


		List<ProductDTO> productDTOs = new ArrayList<>();
		productDTOs.add(productDTO3);
	
		
		List<ProductDTO> actuaDtos= cartService.getProductDTOs(cartItems);
		assertEquals(productDTOs,actuaDtos);
	}
	
	@Test
	void testGetProductDTOs4() {
		List<CartItem> cartItems = new ArrayList<>();
		cartItems.add(new CartItem(362, 1));
		cartItems.add(new CartItem(360, 3));
		Category category = new Category(1, "Apple", "", 1);

		ProductDTO productDTO1 = new ProductDTO(362, "ap1", category, "", 1000f, 20, 10, "Apple", 1, 1, "", null);
		ProductDTO productDTO2 = new ProductDTO(360, "ap2", category, "", 1000f, 20, 15, "Apple", 1, 0, "", null);
		when(productService.getProductAvailable(productDTO1.getId())).thenReturn(productDTO1);
		when(productService.getProductAvailable(productDTO2.getId())).thenReturn(null);

		ProductDTO productDTO3 = new ProductDTO(362, "ap1",category, "10", 1000f, 20, 1, "Apple", 1, 1, "",
				null);


		List<ProductDTO> productDTOs = new ArrayList<>();
		productDTOs.add(productDTO3);
	
		
		List<ProductDTO> actuaDtos= cartService.getProductDTOs(cartItems);
		assertEquals(productDTOs,actuaDtos);
	}
	
	@Test
	void testcheckCart1() {
		List<CartItem> cartItems = new ArrayList<>();
		cartItems.add(new CartItem(362, 1));
		cartItems.add(new CartItem(360, 4));
		Category category = new Category(1, "Apple", "", 1);

		ProductDTO productDTO1 = new ProductDTO(362, "ap1", category, "", 1000f, 20, 10, "Apple", 1, 1, "", null);
		ProductDTO productDTO2 = new ProductDTO(360, "ap2", category, "", 1000f, 20, 15, "Apple", 1, 1, "", null);
		when(productService.getProductAvailable(productDTO1.getId())).thenReturn(productDTO1);
		when(productService.getProductAvailable(productDTO2.getId())).thenReturn(productDTO2);

		ProductDTO productDTO3 = new ProductDTO(362, "ap1",category, "10", 1000f, 20, 1, "Apple", 1, 1, "",
				null);
		ProductDTO productDTO4 = new ProductDTO(360, "ap2",category, "15", 1000f, 20, 4, "Apple", 1, 1, "",
				null);

		List<ProductDTO> productDTOs = new ArrayList<>();
		productDTOs.add(productDTO3);
		productDTOs.add(productDTO4);
		
		List<ProductDTO> actuaDtos = new ArrayList<>();
		assertEquals(true,cartService.checkCart(cartItems, actuaDtos));
	}
	
	@Test
	void testcheckCart2() {
		List<CartItem> cartItems = new ArrayList<>();
		cartItems.add(new CartItem(362, 1));
		cartItems.add(new CartItem(360, 0));
		Category category = new Category(1, "Apple", "", 1);

		ProductDTO productDTO1 = new ProductDTO(362, "ap1", category, "", 1000f, 20, 10, "Apple", 1, 1, "", null);
		ProductDTO productDTO2 = new ProductDTO(360, "ap2", category, "", 1000f, 20, 15, "Apple", 1, 1, "", null);
		when(productService.getProductAvailable(productDTO1.getId())).thenReturn(productDTO1);
		when(productService.getProductAvailable(productDTO2.getId())).thenReturn(productDTO2);

		ProductDTO productDTO3 = new ProductDTO(362, "ap1",category, "10", 1000f, 20, 1, "Apple", 1, 1, "",
				null);


		List<ProductDTO> productDTOs = new ArrayList<>();
		productDTOs.add(productDTO3);
		
		List<ProductDTO> actuaDtos = new ArrayList<>();
		assertEquals(false,cartService.checkCart(cartItems, actuaDtos));
	}
	
	@Test
	void testcheckCart3() {
		List<CartItem> cartItems = new ArrayList<>();
		cartItems.add(new CartItem(362, 1));
		cartItems.add(new CartItem(360, 4));
		Category category = new Category(1, "Apple", "", 1);

		ProductDTO productDTO1 = new ProductDTO(362, "ap1", category, "", 1000f, 20, 10, "Apple", 1, 1, "", null);
		ProductDTO productDTO2 = new ProductDTO(360, "ap2", category, "", 1000f, 20, 15, "Apple", 1, 0, "", null);
		when(productService.getProductAvailable(productDTO1.getId())).thenReturn(productDTO1);
		when(productService.getProductAvailable(productDTO2.getId())).thenReturn(null);

		ProductDTO productDTO3 = new ProductDTO(362, "ap1",category, "10", 1000f, 20, 1, "Apple", 1, 1, "",
				null);


		List<ProductDTO> productDTOs = new ArrayList<>();
		productDTOs.add(productDTO3);
		
		List<ProductDTO> actuaDtos = new ArrayList<>();
		assertEquals(false,cartService.checkCart(cartItems, actuaDtos));
	}
	
	@Test
	void testcheckCart4() {
		List<CartItem> cartItems = new ArrayList<>();
		cartItems.add(new CartItem(362, 1));
		cartItems.add(new CartItem(360, 4));
		Category category = new Category(1, "Apple", "", 1);

		ProductDTO productDTO1 = new ProductDTO(362, "ap1", category, "", 1000f, 20, 10, "Apple", 1, 1, "", null);
		ProductDTO productDTO2 = new ProductDTO(360, "ap2", category, "", 1000f, 20, 2, "Apple", 1, 1, "", null);
		when(productService.getProductAvailable(productDTO1.getId())).thenReturn(productDTO1);
		when(productService.getProductAvailable(productDTO2.getId())).thenReturn(productDTO2);

		ProductDTO productDTO3 = new ProductDTO(362, "ap1",category, "10", 1000f, 20, 1, "Apple", 1, 1, "",
				null);


		List<ProductDTO> productDTOs = new ArrayList<>();
		productDTOs.add(productDTO3);
		
		List<ProductDTO> actuaDtos = new ArrayList<>();
		assertEquals(false,cartService.checkCart(cartItems, actuaDtos));
	}
	
	@Test
	void testcheckCart5() {
		List<CartItem> cartItems = new ArrayList<>();
		cartItems.add(new CartItem(362, 1));
		cartItems.add(new CartItem(360, 3));
		Category category = new Category(1, "Apple", "", 1);

		ProductDTO productDTO1 = new ProductDTO(362, "ap1", category, "", 1000f, 20, 10, "Apple", 1, 1, "", null);
		ProductDTO productDTO2 = new ProductDTO(360, "ap2", category, "", 1000f, 20, 15, "Apple", 1, 0, "", null);
		when(productService.getProductAvailable(productDTO1.getId())).thenReturn(productDTO1);
		when(productService.getProductAvailable(productDTO2.getId())).thenReturn(null);

		ProductDTO productDTO3 = new ProductDTO(362, "ap1",category, "10", 1000f, 20, 1, "Apple", 1, 1, "",
				null);


		List<ProductDTO> productDTOs = new ArrayList<>();
		productDTOs.add(productDTO3);
		
		List<ProductDTO> actuaDtos = new ArrayList<>();
		assertEquals(false,cartService.checkCart(cartItems, actuaDtos));
	}
	
	@Test
	void testcheckCart6() {
		List<CartItem> cartItems = new ArrayList<>();
		List<ProductDTO> actuaDtos = new ArrayList<>();
		assertEquals(false,cartService.checkCart(cartItems, actuaDtos));
	}

}
