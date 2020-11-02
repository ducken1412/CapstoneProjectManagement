package com.fa.controller.admin;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fa.dto.CategoryDTO;
import com.fa.dto.ProductDTO;
import com.fa.service.CategoryService;
import com.fa.service.ProductService;
import com.google.gson.Gson;

@Controller
@RequestMapping("/admin")
public class ProductController {
	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/list-product", method = RequestMethod.GET)
	public String getListProduct(Model model) {
		List<ProductDTO> products = productService.getProductAvailable();
		model.addAttribute("products", products);
		return "admin/listProduct";
	}

	@RequestMapping(value = "/add-product", method = RequestMethod.GET)
	public String saveProductForm(Model model) {
		model.addAttribute("product", new ProductDTO());
		List<CategoryDTO> categoryDTOs = categoryService.getAllCategory();
		model.addAttribute("categories", categoryDTOs);
		return "admin/addProduct";
	}

	@ResponseBody
	@RequestMapping(value = "/add-product", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute @Valid ProductDTO dto, BindingResult bindingResult,
			@RequestParam(value = "image", required = false) String imageMain,
			@RequestParam(value = "slide", required = false) List<String> productImageList, Principal principal, 
			Model model) {
		if (bindingResult.hasErrors()) { 
			Map<String, Object> output = new HashMap<>();
			output.put("result", false);
			output.put("message", "Data is not valid");
			output.put("product", new ProductDTO());
			return new Gson().toJson(output);
		}
		Map<String, Object> output = new HashMap<>();
		if(dto.getDiscount() == null) {
			dto.setDiscount(0);
		}
		ProductDTO productDTO = productService.saveProductAndImage(dto, imageMain, productImageList, principal);
		boolean result = false;
		if(productDTO != null) {
			result = true;
			output.put("message", "Add " + productDTO.getName() + " with id = " + productDTO.getId() + " success");
		} else {
			output.put("message", "Add fail");
		}
		productDTO.setImageMain(null);
		productDTO.setProductImageList(null);
		productDTO.getCategory().setCreatedBy(null);
		productDTO.getCategory().setUpdatedBy(null);
		output.put("result", result);
		output.put("product", productDTO);
		return new Gson().toJson(output);
	}
	
	@ResponseBody
	@RequestMapping(value = "/edit-product", method = RequestMethod.POST)
	public String editProduct(@ModelAttribute @Valid ProductDTO dto,
			@RequestParam(value = "image", required = false) String imageMain,BindingResult bindingResult,
			@RequestParam(value = "slide", required = false) List<String> productImageList, Principal principal,
			Model model) {
		if (bindingResult.hasErrors()) { 
			Map<String, Object> output = new HashMap<>();
			output.put("result", false);
			output.put("message", "Data is not valid");
			output.put("product", new ProductDTO());
			bindingResult.getAllErrors().forEach(x -> System.out.println(x));
			return new Gson().toJson(output);
		}
		Map<String, Object> output = new HashMap<>();
		if(dto.getDiscount() == null) {
			dto.setDiscount(0);
		}
		ProductDTO productDTO = productService.updateProductAndImage(dto, imageMain, productImageList, principal);
		boolean result = false;
		if(productDTO != null) {
			result = true;
			output.put("message", "Update " + productDTO.getName() + " with id = " + productDTO.getId() + " success");
		} else {
			output.put("message", "Update fail");
		}
		productDTO.setImageMain(null);
		productDTO.setProductImageList(null);
		productDTO.getCategory().setCreatedBy(null);
		productDTO.getCategory().setUpdatedBy(null);
		output.put("result", result);
		output.put("product", productDTO);
		return new Gson().toJson(output);
	}

	@RequestMapping(value = "/edit-product/{productId}", method = RequestMethod.GET)
	public String editProduct(@PathVariable Integer productId, Model model) {
		ProductDTO productDTO = productService.getProduct(productId);
		List<CategoryDTO> categoryDTOs = categoryService.getAllCategory();
		model.addAttribute("categories", categoryDTOs);
		model.addAttribute("product", productDTO);
		return "admin/addProduct";
	}
	
	@ResponseBody
	@RequestMapping(value = "/delete-product/{productId}", method = RequestMethod.GET)
	public String deleteProduct(@PathVariable Integer productId, Principal principal,
			Model model) {
		Map<String, Object> output = new HashMap<>();
		boolean result = productService.unActiveProduct(productId, principal);
		if(result) {
			output.put("message", "Delete product with id = " + productId + " success");
		} else {
			output.put("message", "Delete fail");
		}
		output.put("result", result);
		return new Gson().toJson(output);
	}
}
