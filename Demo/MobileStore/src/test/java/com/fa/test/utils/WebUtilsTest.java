package com.fa.test.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fa.utils.WebUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
class WebUtilsTest {
	@Test
	void testgetPaging1() {
		String expected = "<ul class=\"pagination \"><li class=\"page-item active\"><a class=\"page-link\" href=\"#\" >1</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?page=2\" >2</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?page=3\" >3</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?page=4\" >4</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url??page=1\">...</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?&page=50\" >50</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?page=50\" >Last</a></li></ul><!--/.pagination-->";
		String actual = WebUtils.getPaging(500, "url", 1, 10);
		assertEquals(expected, actual);
	}
	
	@Test
	void testgetPaging2() {
		String expected = "<ul class=\"pagination \"><li class=\"page-item active\"><a class=\"page-link\" href=\"#\" >1</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?page=2\" >2</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?page=3\" >3</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?page=4\" >4</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url??page=1\">...</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?&page=51\" >51</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?page=51\" >Last</a></li></ul><!--/.pagination-->";
		String actual = WebUtils.getPaging(505, "url", 1, 10);
		assertEquals(expected, actual);
	}
	
	@Test
	void testgetPaging3() {
		String expected = "<ul class=\"pagination \"><li class=\"page-item active\"><a class=\"page-link\" href=\"#\" >1</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&page=2\" >2</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&page=3\" >3</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&page=4\" >4</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&?page=1\">...</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&&page=51\" >51</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&page=51\" >Last</a></li></ul><!--/.pagination-->";
		String actual = WebUtils.getPaging(505, "url?id=1", 1, 10);
		assertEquals(expected, actual);
	}
	
	@Test
	void testgetPaging4() {
		String expected = "<ul class=\"pagination \"><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&page=1\" >First</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&page=1\" >1</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"#\">...</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&page=2\" >2</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&page=3\" >3</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&page=4\" >4</a></li><li class=\"page-item active\"><a class=\"page-link\" href=\"#\" >5</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&page=6\" >6</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&page=7\" >7</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&page=8\" >8</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&?page=5\">...</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&&page=51\" >51</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&page=51\" >Last</a></li></ul><!--/.pagination-->";
		String actual = WebUtils.getPaging(505, "url?id=1", 5, 10);
		assertEquals(expected, actual);
	}
	
	@Test
	void testgetPaging5() {
		String expected = "<ul class=\"pagination \"><li class=\"page-item active\"><a class=\"page-link\" href=\"#\" >1</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&page=2\" >2</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&page=3\" >3</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&page=4\" >4</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&?page=1\">...</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&&page=51\" >51</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&page=51\" >Last</a></li></ul><!--/.pagination-->";
		String actual = WebUtils.getPaging(505, "url?id=1", -1, 10);
		assertEquals(expected, actual);
	}
	
	@Test
	void testgetPaging6() {
		String expected = "<ul class=\"pagination \"><li class=\"page-item active\"><a class=\"page-link\" href=\"#\" >1</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&page=2\" >2</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&page=3\" >3</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&page=4\" >4</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&?page=1\">...</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&&page=51\" >51</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&page=51\" >Last</a></li></ul><!--/.pagination-->";
		String actual = WebUtils.getPaging(505, "url?id=1", 9999, 10);
		assertEquals(expected, actual);
	}
	
	@Test
	void testgetPaging7() {
		String expected = "<ul class=\"pagination \"><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&page=1\" >First</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&page=1\" >1</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"#\">...</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&page=48\" >48</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&page=49\" >49</a></li><li class=\"page-item\"><a class=\"page-link\" href=\"url?id=1&page=50\" >50</a></li><li class=\"page-item active\"><a class=\"page-link\" href=\"#\" >51</a></li></ul><!--/.pagination-->";
		String actual = WebUtils.getPaging(505, "url?id=1", 51, 10);
		assertEquals(expected, actual);
	}

}
