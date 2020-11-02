package com.fa.service;

import java.util.List;

import com.fa.dto.ProductDTO;
import com.fa.dto.SearchProductDTO;

public interface ShopService {
	/**
	 * search products.
	 * 
	 * @param productDTO
	 * @return
	 */
	List<ProductDTO> searchProducts(SearchProductDTO productDTO);

	/**
	 * count number of product found.
	 * 
	 * @param productDTO
	 * @return
	 */
	long countProducts(SearchProductDTO productDTO);

}