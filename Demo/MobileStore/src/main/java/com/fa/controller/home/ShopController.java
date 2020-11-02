package com.fa.controller.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fa.dto.CategoryDTO;
import com.fa.dto.ProductDTO;
import com.fa.dto.SearchProductDTO;
import com.fa.service.CategoryService;
import com.fa.service.ShopService;
import com.fa.utils.TimeUtils;
import com.fa.utils.WebUtils;

@Controller
public class ShopController {
	

	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ShopService shopService;
	
	@RequestMapping(value = "/shop", method = RequestMethod.GET)
	public String getShop( Model model) {
		List<CategoryDTO> categoryDTOs = categoryService.getAllCategory();
		model.addAttribute("categories", categoryDTOs);
		return "home/shop";
	}
	
	
	@RequestMapping(value = "/list-product", method = RequestMethod.GET)
	public String getListProduct(@ModelAttribute SearchProductDTO search,Model model) {
		List<ProductDTO> products = shopService.searchProducts(search);
		int total = (int)shopService.countProducts(search);
		model.addAttribute("products", products);
		String message = null;
		if(products.isEmpty()) {
			message = "No matching records found";
			
		}
		model.addAttribute("message",message);
		Integer page = search.getPage();
		if(page==null||page<0) {
			page = 1;
		}
		if(total>15) {
			model.addAttribute("pagination", WebUtils.getPaging(total,rewriteUrl(search),page,15));
		}
		model.addAttribute("checkDate", TimeUtils.getPreTime());
		model.addAttribute("search", search);
		model.addAttribute("total", total);
		return "home/listProduct";
	}
	
	private String rewriteUrl(SearchProductDTO search) {
		String url = "";
	
		
		if (search.getCategoryId() != null && search.getCategoryId() > 0) {
			url+="?";
			url += "category=" + search.getCategoryId();
		}
		if (search.getCondition() != null && search.getCondition() > 0) {
			if("".equals(url)) {
				url+="?";
			}else {
				url+="&";
			}
			url += "condition=" + search.getCondition();
		}

		if (search.getPriceMin() != null && search.getPriceMin() > 0) {
			if("".equals(url)) {
				url+="?";
			}else {
				url+="&";
			}
			url += "priceMin=" + search.getPriceMin();
		}
		if (search.getPriceMax() != null && search.getPriceMax() > 0) {
			if("".equals(url)) {
				url+="?";
			}else {
				url+="&";
			}
			url += "priceMax=" + search.getPriceMax();
		}
		if (search.getName() != null && search.getName() != "") {
			if("".equals(url)) {
				url+="?";
			}else {
				url+="&";
			}
			url += "keyword=" + search.getName();
		}
		return url;
	}
}
