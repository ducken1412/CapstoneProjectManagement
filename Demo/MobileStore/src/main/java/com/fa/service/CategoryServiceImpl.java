package com.fa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fa.dto.CategoryDTO;
import com.fa.entity.Category;
import com.fa.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<CategoryDTO> getAllCategory() {
		return categoryRepository.findCategoryAvailable();
	}

	@Override
	public Category getCategoryById(int id) {
		return categoryRepository.findById(id).orElse(null);
	}

	@Override	 
	public boolean saveCategory(Category category) {
		if (category != null) {
			categoryRepository.save(category);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean deleteCategory(int id) {
		Category category = categoryRepository.findById(id).orElse(null);
		if (category != null) {
			category.setStatus(0);
			categoryRepository.save(category);
			return true;
		}
		return false;
	}

	@Override
	public boolean updateCategory(Category category) {
		if (category != null) {
// Create new Category avoid override field of update category
			Category categoryOld = categoryRepository.findById(category.getId()).orElse(null);
			if (categoryOld != null) {
				categoryOld.setName(category.getName());
				categoryOld.setDescription(category.getDescription());
				categoryOld.setUpdateTime(category.getUpdateTime());
				categoryOld.setUpdatedBy(category.getUpdatedBy());
				categoryRepository.save(categoryOld);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}