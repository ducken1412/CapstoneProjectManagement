package com.fa.test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.fa.controller.home.ShopController;
import com.fa.dto.SearchProductDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
class ShopControllerTest {
	@Autowired
	private ShopController shopController;
	
	@Test
	void testRewriteUrlSuccess() {
		SearchProductDTO searchProductDTO = new SearchProductDTO(10, "samsung", (float) 3000, (float) 1, 1, 1, 1);
		String expected = "?category=10&condition=1&priceMin=1.0&priceMax=3000.0&keyword=samsung";
		String result = ReflectionTestUtils.invokeMethod(shopController, "rewriteUrl", searchProductDTO);
		assertEquals(expected, result);
	}
	
	@Test
	void testRewriteUrlSuccess2() {
		SearchProductDTO searchProductDTO = new SearchProductDTO(10, null, (float) 3000, (float) 1, 1, 1, 1);
		String expected = "?category=10&condition=1&priceMin=1.0&priceMax=3000.0";
		String result = ReflectionTestUtils.invokeMethod(shopController, "rewriteUrl", searchProductDTO);
		assertEquals(expected, result);
	}
	
	@Test
	void testRewriteUrlSuccess3() {
		SearchProductDTO searchProductDTO = new SearchProductDTO(0, "", (float) 0, (float) 0, 0, 0, 0);
		String expected = "";
		String result = ReflectionTestUtils.invokeMethod(shopController, "rewriteUrl", searchProductDTO);
		assertEquals(expected, result);
	}
	
	@Test
	void testRewriteUrlSuccess4() {
		SearchProductDTO searchProductDTO = new SearchProductDTO(null, "samsung", (float) 3000, null, 1, null, 1);
		String expected = "?priceMax=3000.0&keyword=samsung";
		String result = ReflectionTestUtils.invokeMethod(shopController, "rewriteUrl", searchProductDTO);
		assertEquals(expected, result);
	}
	@Test
	void testRewriteUrlSuccess5() {
		SearchProductDTO searchProductDTO = new SearchProductDTO(0, "samsung", (float) 3000, null, 2, 1, 1);
		String expected = "?condition=1&priceMax=3000.0&keyword=samsung";
		String result = ReflectionTestUtils.invokeMethod(shopController, "rewriteUrl", searchProductDTO);
		assertEquals(expected, result);
	}
	
	@Test
	void testRewriteUrlSuccess6() {
		SearchProductDTO searchProductDTO = new SearchProductDTO(null, "", (float) 3000, (float) 1, 1, null, 1);
		String expected = "?priceMin=1.0&priceMax=3000.0";
		String result = ReflectionTestUtils.invokeMethod(shopController, "rewriteUrl", searchProductDTO);
		assertEquals(expected, result);
	}
	
	@Test
	void testRewriteUrlSuccess7() {
		SearchProductDTO searchProductDTO = new SearchProductDTO(null, "samsung", (float) 0, (float) 0, 0, 0, 0);
		String expected = "?keyword=samsung";
		String result = ReflectionTestUtils.invokeMethod(shopController, "rewriteUrl", searchProductDTO);
		assertEquals(expected, result);
	}
	
	@Test
	void testRewriteUrlSucces8() {
		SearchProductDTO searchProductDTO = new SearchProductDTO(10, "samsung", null, (float) 1, 1, 1, 1);
		String expected = "?category=10&condition=1&priceMin=1.0&keyword=samsung";
		String result = ReflectionTestUtils.invokeMethod(shopController, "rewriteUrl", searchProductDTO);
		assertEquals(expected, result);
	}
	
	@Test
	void testRewriteUrlSuccess9() {
		SearchProductDTO searchProductDTO = new SearchProductDTO(10, "samsung", (float) 1, (float) 0, 2, 1, 1);
		String expected = "?category=10&condition=1&priceMax=1.0&keyword=samsung";
		String result = ReflectionTestUtils.invokeMethod(shopController, "rewriteUrl", searchProductDTO);
		assertEquals(expected, result);
	}
}
