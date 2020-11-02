package com.fa.controller.admin;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fa.common.OrderContant;
import com.fa.dto.SearchOrderDTO;
import com.fa.entity.Orders;
import com.fa.service.OrdersService;
import com.fa.utils.WebUtils;

@Controller
@RequestMapping("/admin")
public class OrderController {

	@Autowired
	private OrdersService ordersService;

	@RequestMapping(value = "/order", method = RequestMethod.GET)
	public String getOrders(@ModelAttribute SearchOrderDTO search, Model model) {
		String url = rewriteUrl(search);
		Page<Orders> page = ordersService.searchOrders(search);
		List<Orders> ordersList = page.getContent();
		model.addAttribute("ordersList", ordersList);
		model.addAttribute("status", OrderContant.STATUS);
		if (page.getTotalPages() > 0) {
			model.addAttribute("pagination", WebUtils.getPaging((int) page.getTotalElements(), url, search.getPage(),
					OrderContant.TOTAL_PER_PAGE));
		}
		return "admin/listOrder";
	}

	@RequestMapping(value = { "/order/{id}" }, method = RequestMethod.GET)
	public String getInvoice(@PathVariable(name = "id") Integer id, Model model) {
		Orders orders = ordersService.getOrders(id);
		model.addAttribute("orders", orders);
		return "admin/invoice";
	}

	private String rewriteUrl(SearchOrderDTO search) {
		String url = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		if (search.getOrderId() != null) {
			url += "?";
			url += "orderId=" + search.getOrderId();
		}
		if (search.getFromDate() != null) {
			if ("".equals(url)) {
				url += "?";
			} else {
				url += "&";
			}
			url += "fromDate=" + sdf.format(search.getFromDate());
		}
		if (search.getToDate() != null) {
			if ("".equals(url)) {
				url += "?";
			} else {
				url += "&";
			}
			url += "toDate=" + sdf.format(search.getToDate());
		}

		if (search.getStatus() != null) {
			if ("".equals(url)) {
				url += "?";
			} else {
				url += "&";
			}
			url += "status=" + search.getStatus();
		}

		return url;
	}

}
