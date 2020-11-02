package com.fa.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.fa.dto.ProductDTO;
import com.fa.dto.SearchProductDTO;
import com.fa.repository.ShopRepository;
import com.fa.service.ShopService;

@RunWith(SpringRunner.class)
@SpringBootTest
class ShopServiceTest {

	@Autowired
	private ShopService shopService;
	
	@MockBean
	private ShopRepository shopRepository;
	
	
	@Test
	void testSearchProducts() {
		SearchProductDTO searchProductDTO = new SearchProductDTO(10, "samsung", (float) 3000, (float) 1, 1, 1, 1);
		List<ProductDTO> list = new ArrayList<>();
		when(shopRepository.searchProducts(searchProductDTO, 15)).thenReturn(list);
		assertEquals(list, shopService.searchProducts(searchProductDTO));
	}
	
	@Test
	void testCountProducts() {
		SearchProductDTO searchProductDTO = new SearchProductDTO(10, "samsung", (float) 3000, (float) 1, 1, 1, 1);
		List<ProductDTO> list = new ArrayList<>();
		when(shopRepository.countProducts(searchProductDTO)).thenReturn((long)list.size());
		assertEquals(list.size(), shopService.countProducts(searchProductDTO));
	}

}
