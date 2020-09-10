package com.fa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fa.entity.ProductImage;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
	/**
	 * delete ProductImage by product id.
	 * 
	 * @param id
	 * @return
	 */
	@Modifying
	@Query("DELETE FROM ProductImage WHERE product.id = :id")
	int deleteByProductId(Integer id);
}
