package com.fa.test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.fa.controller.admin.OrderController;
import com.fa.dto.SearchOrderDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
class OrderControllerTest {
	@Autowired
	private OrderController orderController;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Test
	void testRewriteUrlSuccess1() {
		Date fromDate = null;
		Date toDate = null;
		try {
			fromDate = sdf.parse("2020-06-12");
			toDate = sdf.parse("2020-06-13");
		} catch (ParseException e) {
		}
		SearchOrderDTO searchOrderDTO = new SearchOrderDTO(1, fromDate, toDate, 1, 1);
		String expected = "?orderId=1&fromDate=2020-06-12&toDate=2020-06-13&status=1";
		String result = ReflectionTestUtils.invokeMethod(orderController, "rewriteUrl", searchOrderDTO);
		assertEquals(expected, result);
	}
	
	@Test
	void testRewriteUrlSuccess2() {
		Date fromDate = null;
		Date toDate = null;
		try {
			fromDate = sdf.parse("2020-06-12");
			toDate = sdf.parse("2020-06-13");
		} catch (ParseException e) {
		}
		SearchOrderDTO searchOrderDTO = new SearchOrderDTO(null, fromDate, toDate, 1, 1);
		String expected = "?fromDate=2020-06-12&toDate=2020-06-13&status=1";
		String result = ReflectionTestUtils.invokeMethod(orderController, "rewriteUrl", searchOrderDTO);
		assertEquals(expected, result);
	}
	
	@Test
	void testRewriteUrlSuccess3() {
		Date toDate = null;
		try {
			toDate = sdf.parse("2020-06-13");
		} catch (ParseException e) {
		}
		SearchOrderDTO searchOrderDTO = new SearchOrderDTO(null, null, toDate, 1, 1);
		String expected = "?toDate=2020-06-13&status=1";
		String result = ReflectionTestUtils.invokeMethod(orderController, "rewriteUrl", searchOrderDTO);
		assertEquals(expected, result);
	}
	
	@Test
	void testRewriteUrlSuccess4() {
		SearchOrderDTO searchOrderDTO = new SearchOrderDTO(null, null, null, 1, 1);
		String expected = "?status=1";
		String result = ReflectionTestUtils.invokeMethod(orderController, "rewriteUrl", searchOrderDTO);
		assertEquals(expected, result);
	}
	
	@Test
	void testRewriteUrlSuccess5() {
		SearchOrderDTO searchOrderDTO = new SearchOrderDTO(null, null, null, null, 1);
		String expected = "";
		String result = ReflectionTestUtils.invokeMethod(orderController, "rewriteUrl", searchOrderDTO);
		assertEquals(expected, result);
	}
	
	@Test
	void testRewriteUrlSuccess6() {
		SearchOrderDTO searchOrderDTO = new SearchOrderDTO(null, null, null, null, null);
		String expected = "";
		String result = ReflectionTestUtils.invokeMethod(orderController, "rewriteUrl", searchOrderDTO);
		assertEquals(expected, result);
	}
	
	@Test
	void testRewriteUrlSuccess7() {
		Date fromDate = null;
		Date toDate = null;
		try {
			fromDate = sdf.parse("2020-06-12");
			toDate = sdf.parse("2020-06-13");
		} catch (ParseException e) {
		}
		SearchOrderDTO searchOrderDTO = new SearchOrderDTO(1, fromDate, toDate, 1, null);
		String expected = "?orderId=1&fromDate=2020-06-12&toDate=2020-06-13&status=1";
		String result = ReflectionTestUtils.invokeMethod(orderController, "rewriteUrl", searchOrderDTO);
		assertEquals(expected, result);
	}

}
