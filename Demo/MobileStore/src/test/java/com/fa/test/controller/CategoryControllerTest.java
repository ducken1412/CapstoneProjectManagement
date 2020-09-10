package com.fa.test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.UncheckedIOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.validation.Valid;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fa.controller.admin.CategoryController;
import com.fa.entity.Category;
import com.fa.entity.Users;
import com.fa.service.CategoryService;
import com.fa.service.ProductServiceImpl;
import com.fa.service.UserServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryControllerTest {

	@Autowired
	CategoryController categoryController;
	@MockBean
	CategoryService categoryService;
	@MockBean
	UserServiceImpl userService;
	@MockBean
	ProductServiceImpl productService;
	@MockBean
	private Model model;
	@MockBean
	BindingResult br;
	@MockBean
	RedirectAttributes redirectAttrs;
	@MockBean
	UsernamePasswordAuthenticationToken principal;

	@Test
	void testGetListCategorySuccess() {
		String categoryMessage = "message";
		assertEquals("admin/listCategory", categoryController.getListCategory(model, categoryMessage));
	}

	@Test
	void testEditCategoryModalSuccess() {
		String id = "1";
		Category category = new Category();
		when(categoryService.getCategoryById(Integer.parseInt(id))).thenReturn(category);
		assertEquals("admin/editCategory", categoryController.editCategoryModal(model, id));
	}

	@Test
	void testEditCategoryModalException() {
		String id = null;
		assertEquals("403Page", categoryController.editCategoryModal(model, id));
	}

	@Test
	void testEditCategorySuccess() {
		Category category = new Category();
		when(br.hasErrors()).thenReturn(false);
		UserDetails userDetails = (UserDetails) new User("admin", "123456", new ArrayList<GrantedAuthority>());
		when(principal.getPrincipal()).thenReturn(userDetails);
		Users user = new Users();
		when(userService.findByUserName("admin")).thenReturn(user);
		user.setUserName("admin");
		when(categoryService.updateCategory(category)).thenReturn(true);
		assertEquals("redirect:/admin/list-category",
				categoryController.editCategory(model, category, br, redirectAttrs, principal));
	}

	@Test
	void testEditCategoryValidateError() {
		Category category = new Category();
		when(br.hasErrors()).thenReturn(true);
		assertEquals("redirect:/admin/list-category",
				categoryController.editCategory(model, category, br, redirectAttrs, principal));
	}

	@Test
	void testEditCategoryFail() {
		Category category = new Category();
		when(br.hasErrors()).thenReturn(false);
		UserDetails userDetails = (UserDetails) new User("admin", "123456", new ArrayList<GrantedAuthority>());
		when(principal.getPrincipal()).thenReturn(userDetails);
		Users user = new Users();
		when(userService.findByUserName("admin")).thenReturn(user);
		user.setUserName("admin");
		when(categoryService.updateCategory(category)).thenReturn(false);
		assertEquals("redirect:/admin/list-category",
				categoryController.editCategory(model, category, br, redirectAttrs, principal));
	}

	@Test
	void testAddCategoryPathSuccess() {
		assertEquals("admin/addCategory", categoryController.addCategory(model));
	}

	@Test
	void testAddCategorySuccess() {
		Category category = new Category();
		when(br.hasErrors()).thenReturn(false);	
		UserDetails userDetails = (UserDetails) new User("admin", "123456", new ArrayList<GrantedAuthority>());
		when(principal.getPrincipal()).thenReturn(userDetails);
		when(categoryService.saveCategory(category)).thenReturn(true);
		assertEquals("redirect:/admin/list-category", categoryController.addCategory(category, br, redirectAttrs, principal));
	}
	
	@Test
	void testAddCategoryValidateError() {
		Category category = new Category();
		when(br.hasErrors()).thenReturn(true);		
		assertEquals("redirect:/admin/list-category", categoryController.addCategory(category, br, redirectAttrs, principal));
	}
	
	@Test
	void testAddCategoryFail() {
		Category category = new Category();
		when(br.hasErrors()).thenReturn(false);	
		UserDetails userDetails = (UserDetails) new User("admin", "123456", new ArrayList<GrantedAuthority>());
		when(principal.getPrincipal()).thenReturn(userDetails);
		when(categoryService.saveCategory(category)).thenReturn(false);
		assertEquals("redirect:/admin/list-category", categoryController.addCategory(category, br, redirectAttrs, principal));
	}
	
	@Test
	void testDeleteCategorySuccess() {
		int id = 1;
		when(productService.deleteByCategoryId(id)).thenReturn(1);
		when(categoryService.deleteCategory(id)).thenReturn(true);
		assertEquals("redirect:/admin/list-category", categoryController.deleteCategory(id));
	}
	
	@Test
	void testDeleteCategoryException() {
		int id = 0;		
		when(productService.deleteByCategoryId(id)).thenThrow(RuntimeException.class);
		assertEquals("redirect:/admin/list-category", categoryController.deleteCategory(id));
	}
}
