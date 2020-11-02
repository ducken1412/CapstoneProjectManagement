package com.fa.test.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.fa.dto.CategoryDTO;
import com.fa.entity.Category;
import com.fa.repository.CategoryRepository;
import com.fa.service.CategoryService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest {

	@Autowired
	private CategoryService categoryService;

	@MockBean
	private CategoryRepository categoryRepository;

	@Test
	void testGetAllCategorySuccess() {
		List<CategoryDTO> list = new ArrayList<>();
		CategoryDTO categoryDTO = new CategoryDTO();
		list.add(categoryDTO);
		when(categoryRepository.findCategoryAvailable()).thenReturn(list);
		List<CategoryDTO> listResult = categoryService.getAllCategory();
		assertEquals(list, listResult);
	}

	@Test
	void testGetAllCategoryFail() {
		List<CategoryDTO> list = null;
		when(categoryRepository.findCategoryAvailable()).thenReturn(list);
		assertEquals(null, categoryService.getAllCategory());
	}

	@Test
	void testGetCategoryByIdSuccess() {
		Category category = new Category();
		Optional<Category> optionalCategory = Optional.of(category);
		when(categoryRepository.findById(1)).thenReturn(optionalCategory);
		Optional<Category> actualCategory = Optional.ofNullable(categoryService.getCategoryById(1));
		assertEquals(optionalCategory, actualCategory);
	}

	@Test
	void testGetCategoryByIdFail() {
		Optional<Category> optionalCategory = Optional.empty();
		when(categoryRepository.findById(1)).thenReturn(optionalCategory);
		Optional<Category> actualCategory = Optional.ofNullable(categoryService.getCategoryById(1));
		assertEquals(optionalCategory, actualCategory);
	}

	@Test
	void testSaveCategorySuccess() {
		Category category = new Category();
		when(categoryRepository.save(category)).thenReturn(category);
		assertTrue(categoryService.saveCategory(category));
	}

	@Test
	void testSaveCategoryFail() {
		Category category = null;
		when(categoryRepository.save(category)).thenReturn(category);
		assertFalse(categoryService.saveCategory(category));
	}

	@Test
	void testDeleteCategorySuccess() {
		Category category = new Category();
		category.setId(1);
		Optional<Category> optionalCategory = Optional.of(category);
		when(categoryRepository.findById(1)).thenReturn(optionalCategory);
		when(categoryRepository.save(category)).thenReturn(category);
		assertEquals(true, categoryService.deleteCategory(1));
	}

	@Test
	void testDeleteCategoryFail() {
		Optional<Category> optionalCategory = Optional.empty();
		when(categoryRepository.findById(1)).thenReturn(optionalCategory);
		categoryService.deleteCategory(1);
		assertEquals(false, categoryService.deleteCategory(1));
	}

	@Test
	void testUpdateCategorySuccess() {
		Category category = new Category();
		category.setId(1);
		Optional<Category> optionalCategory = Optional.of(category);
		when(categoryRepository.findById(1)).thenReturn(optionalCategory);
		when(categoryRepository.save(category)).thenReturn(category);
		boolean check = categoryService.updateCategory(category);
		assertTrue(check);
		System.out.println(check);
	}

	@Test
	void testUpdateCategoryFail1() {
		Category category = null;
		boolean check = categoryService.updateCategory(category);
		assertFalse(check);
		System.out.println(check);
	}

	@Test
	void testUpdateCategoryFail2() {
		Category category = new Category();
		boolean check = categoryService.updateCategory(category);
		assertFalse(check);
		System.out.println(check);
	}

}
