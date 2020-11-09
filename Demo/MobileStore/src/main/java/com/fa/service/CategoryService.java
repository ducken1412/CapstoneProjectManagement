package com.fa.service;

import java.util.List;

import com.fa.dto.CategoryDTO;
import com.fa.entity.Category;

public interface CategoryService {

	List<CategoryDTO> getAllCategory();

	Category getCategoryById(int id);

	boolean saveCategory(Category category);

	boolean deleteCategory(int id);

	boolean updateCategory(Category category);

}