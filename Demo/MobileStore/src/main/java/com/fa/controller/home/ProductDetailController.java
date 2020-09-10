package com.fa.controller.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fa.dto.ProductDTO;
import com.fa.service.ProductService;

@Controller
public class ProductDetailController {
	@Autowired
	private ProductService productService;
	@GetMapping("/detail/{id}")
	public String viewDetailProduct(@PathVariable(name="id") Integer id, Model model) {
		ProductDTO productDTO = productService.getProductAvailable(id);
		model.addAttribute("product", productDTO);
		return "home/product";
	}
}
