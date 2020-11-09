package com.fa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fa.dto.CategoryDTO;
import com.fa.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	/**
	 * Find all category available
	 * 
	 * @return
	 */
	@Query("select new com.fa.dto.CategoryDTO(c) From Category c  where status != 0")
	public List<CategoryDTO> findCategoryAvailable();

}
