package com.fa.controller.home;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fa.controller.LoginController;
import com.fa.dto.CategoryDTO;
import com.fa.dto.ProductDTO;
import com.fa.entity.Product;
import com.fa.service.CategoryService;
import com.fa.service.ProductServiceImpl;

@Controller
public class HomeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	
	@Autowired
	private ProductServiceImpl productService;
	
	@Autowired
	private CategoryService categoryService;
	
	
	@RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String home(HttpServletRequest request, Model model) {
		LOGGER.info("Runing on home in HomeController");
		
		List<Product> listProductNew = new ArrayList<Product>();
		listProductNew = productService.getNewProduct();
		List<ProductDTO> listProductDeal =	productService.getDealOfTheWeekProduct();
		model.addAttribute("listProductNew", listProductNew);
		if(listProductDeal.isEmpty()) {
			model.addAttribute("listProductDeal", listProductNew);
		}else {
			model.addAttribute("listProductDeal", listProductDeal);
		}
		model.addAttribute("listTopProductOnSale", productService.findProductOnSale(PageRequest.of(0, 20)));
		model.addAttribute("listProductOnSaleWithNew", productService.findProductOnSaleByCondition(1, PageRequest.of(0, 20)));
		model.addAttribute("listProductOnSaleWithOld", productService.findProductOnSaleByCondition(2, PageRequest.of(0, 20)));
		model.addAttribute("listProductOnSaleWithRefurbished", productService.findProductOnSaleByCondition(3, PageRequest.of(0, 20)));
		return "home/index";
	}
	
	@RequestMapping(value = "/innit-category",method = RequestMethod.GET)
	public String getCategory(Model model) {
		List<CategoryDTO> categoryDTOs = categoryService.getAllCategory();
		model.addAttribute("categories", categoryDTOs);
		return "home/_category";
	}	

}
