package com.fa.controller.home;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fa.dto.CartItem;
import com.fa.dto.ProductDTO;
import com.fa.service.CartService;
import com.google.gson.Gson;

@Controller
public class CartController {
	@Autowired
	private CartService cartService;

	private static final int MAX_CART_AGE = 7 * 24 * 60 * 60;

	@RequestMapping(value = "/cart", method = RequestMethod.GET)
	public String viewCart(@CookieValue(name = "cart", defaultValue = "null") String cart, Model model,
			HttpServletResponse response) {
		List<CartItem> cartItems = cartService.getCartItems(cart);
		List<ProductDTO> products = cartService.getProductDTOs(cartItems);
		Cookie cookie = null;

		if (cartItems.isEmpty()) {
			cookie = new Cookie("cart", "null");
			cookie.setMaxAge(MAX_CART_AGE);
			response.addCookie(cookie);
		} else {
			String json = new Gson().toJson(cartItems);
			cookie = new Cookie("cart", encodeValue(json));

		}
		cookie.setMaxAge(MAX_CART_AGE);
		response.addCookie(cookie);
		model.addAttribute("products", products);
		return "home/cart";
	}

	private String encodeValue(String value) {
		try {
			return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "null";
	}
}