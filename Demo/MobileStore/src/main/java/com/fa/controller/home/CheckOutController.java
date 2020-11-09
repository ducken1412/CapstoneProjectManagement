package com.fa.controller.home;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fa.common.OrderContant;
import com.fa.dto.CartItem;
import com.fa.dto.OrdersDTO;
import com.fa.dto.ProductDTO;
import com.fa.entity.Orders;
import com.fa.entity.Users;
import com.fa.service.CartService;
import com.fa.service.OrdersService;
import com.fa.service.UserService;
import com.fa.utils.SendEmailUtils;

@Controller
public class CheckOutController {
	@Autowired
	private CartService cartService;

	@Autowired
	private OrdersService ordersService;
	@Autowired
	private SendEmailUtils sendEmailUtils;

	@Autowired
	private UserService userServiceImpl;

	@RequestMapping(value = "/checkout", method = RequestMethod.GET)
	public String getCheckOut(@CookieValue(name = "cart", defaultValue = "null") String cart, Principal principal,
			Model model, RedirectAttributes redirectAttributes) {
		List<CartItem> cartItems = cartService.getCartItems(cart);
		List<ProductDTO> products = new ArrayList<ProductDTO>();
		if (principal != null) {
			UserDetails loginedUser = (UserDetails) ((Authentication) principal).getPrincipal();
			Users user = userServiceImpl.findByUserNameAndStatus(loginedUser.getUsername());
			if (user != null) {
				model.addAttribute("user", user);
				model.addAttribute("loginned", true);
			} else {
				model.addAttribute("user", new Users());
			}
		}
		boolean check = cartService.checkCart(cartItems, products);
		model.addAttribute("products", products);
		if (check) {
			return "home/checkout";
		} else {
			redirectAttributes.addFlashAttribute("message", OrderContant.PRODUCT_CHANGED);
			return "redirect:/cart";
		}
	}

	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	public String checkout(@CookieValue(name = "cart", defaultValue = "null") String cart,
			@Valid @ModelAttribute OrdersDTO order, BindingResult bindingResult, Principal principal, Model model,
			RedirectAttributes redirectAttributes, HttpServletResponse response) {

		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("message", OrderContant.INVALID_INFOR);
			return "redirect:/checkout";
		}
		List<CartItem> cartItems = cartService.getCartItems(cart);
		List<ProductDTO> products = new ArrayList<ProductDTO>();
		boolean check = cartService.checkCart(cartItems, products);
		model.addAttribute("products", products);
		if (check && !products.isEmpty()) {
			try {
				Orders orders = ordersService.saveOrder(order, products, principal);
				if (sendEmailUtils.sendMail(orders, products)) {
					redirectAttributes.addFlashAttribute("sentMail", true);
				}
				Cookie cookie = new Cookie("cart", "null");
				response.addCookie(cookie);
				redirectAttributes.addFlashAttribute("orderId", orders.getId());
				return "redirect:/success";
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("message", OrderContant.PROCESS_WRONG);
				return "redirect:/checkout";
			}

		} else {
			redirectAttributes.addFlashAttribute("message", OrderContant.PRODUCT_CHANGED);
			return "redirect:/cart";
		}
	}

	@RequestMapping(value = "/success", method = RequestMethod.GET)
	public String getCheckOut(Model model) {
		Integer orderId = (Integer) model.asMap().get("orderId");
		if (orderId != null) {
			return "home/success";
		} else {
			return "redirect:/";
		}

	}

}
