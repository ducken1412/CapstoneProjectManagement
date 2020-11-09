package com.fa.controller.admin;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fa.common.CategoryConstant;
import com.fa.dto.CategoryDTO;
import com.fa.entity.Category;
import com.fa.entity.Users;
import com.fa.service.CategoryService;
import com.fa.service.ProductServiceImpl;
import com.fa.service.UserService;

@Controller
@RequestMapping("/admin")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductServiceImpl productService;

	@Autowired
	private UserService userService;

	private Date currentDate = new Date();

	/**
	 * Redirect to list category page
	 * @param model
	 * @param categoryMessage
	 * @return
	 */
	@RequestMapping(value = "/list-category", method = RequestMethod.GET)
	public String getListCategory(Model model, @ModelAttribute("categoryMessage") String categoryMessage) {
		List<CategoryDTO> listCategory = categoryService.getAllCategory();
		Category category = new Category();
		model.addAttribute("category", category);
		model.addAttribute("listCategory", listCategory);
		model.addAttribute("categoryMessage", categoryMessage);
		return "admin/listCategory";
	}

	/**
	 * Return editCategory modal to ajax request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/editCategory", method = RequestMethod.GET)
	public String editCategoryModal(Model model, String id) {
		try {
			Category category = categoryService.getCategoryById(Integer.parseInt(id));
			model.addAttribute("category", category);
			return "admin/editCategory";
		} catch (Exception e) {
			model.addAttribute("message", "Category not exist");
			return "403Page";
		}
	}

	/**
	 *  Edit category action
	 * @param model
	 * @param category
	 * @param br
	 * @param redirectAttrs
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/editCategory", method = RequestMethod.POST)
	public String editCategory(Model model, @Valid Category category, BindingResult br,
			RedirectAttributes redirectAttrs, Principal principal) {
		if (br.hasErrors()) {
			redirectAttrs.addFlashAttribute("categoryMessage", CategoryConstant.INVALID_CATEGORY);
			System.out.println("Error message: " + br);
			return "redirect:/admin/list-category";
		} else {
			UserDetails userInfo = (UserDetails) ((Authentication) principal).getPrincipal();
			Users u = userService.findByUserName(userInfo.getUsername());
			category.setUpdatedBy(u);
			category.setUpdateTime(currentDate);
			// Update method , update only edited field
			if (categoryService.updateCategory(category)) {				
				redirectAttrs.addFlashAttribute("updateBy", u.getUserName());
				redirectAttrs.addFlashAttribute("categoryMessage", CategoryConstant.EDIT_SUCCESS);
				return "redirect:/admin/list-category";
			} else {
				redirectAttrs.addFlashAttribute("categoryMessage", CategoryConstant.EDIT_FAIL);
				return "redirect:/admin/list-category";
			}
		}
	}

	/**
	 * Return addCategory modal to ajax request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addCategory", method = RequestMethod.GET)
	public String addCategory(Model model) {
		Category category = new Category();
		model.addAttribute("category", category);
		return "admin/addCategory";
	}

	/**
	 * Add category action
	 * @param category
	 * @param br
	 * @param redirectAttrs
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/add-category", method = RequestMethod.POST)
	public String addCategory(@Valid @ModelAttribute(value = "category") Category category, BindingResult br,
			RedirectAttributes redirectAttrs, Principal principal) {
		if (br.hasErrors()) {
			redirectAttrs.addFlashAttribute("categoryMessage", CategoryConstant.INVALID_CATEGORY);
			System.out.println("Error message: " + br);
			return "redirect:/admin/list-category";
		} else {
			Users u = null;
			UserDetails userInfo = (UserDetails) ((Authentication) principal).getPrincipal();
			u = userService.findByUserName(userInfo.getUsername());
			category.setCreatedBy(u);
			category.setCreateDate(currentDate);
			category.setStatus(1);
			if (categoryService.saveCategory(category)) {
				redirectAttrs.addFlashAttribute("categoryMessage", CategoryConstant.ADD_SUCCESS);
				return "redirect:/admin/list-category";
			} else {
				redirectAttrs.addFlashAttribute("categoryMessage", CategoryConstant.ADD_FAIL);
				return "redirect:/admin/list-category";
			}
		}
	}

	/**
	 * Return show list category action to ajax request
	 * @param id
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "deleteCategory")
	public String deleteCategory(int id) {
		try {
			productService.deleteByCategoryId(id);
			categoryService.deleteCategory(id);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return "redirect:/admin/list-category";
	}

}
