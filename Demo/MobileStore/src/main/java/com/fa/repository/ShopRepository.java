package com.fa.repository;

import java.util.List;

import com.fa.dto.ProductDTO;
import com.fa.dto.SearchProductDTO;

public interface ShopRepository {

	/**
	 * count number of products found.
	 * 
	 * @param productDTO
	 * @return
	 */
	long countProducts(SearchProductDTO productDTO);

	/**
	 * search products.
	 * 
	 * @param productDTO
	 * @param total
	 * @return
	 */
	List<ProductDTO> searchProducts(SearchProductDTO productDTO, int total);

}