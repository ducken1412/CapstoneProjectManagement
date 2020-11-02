package com.fa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fa.dto.ProductDTO;
import com.fa.dto.SearchProductDTO;
import com.fa.repository.ShopRepository;

@Service
public class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopRepository shopRepository;

	/**
	 * search products.
	 * 
	 * @param productDTO
	 * @return
	 */
	@Override
	public List<ProductDTO> searchProducts(SearchProductDTO productDTO) {
		//xu ly logic
		return shopRepository.searchProducts(productDTO, 15);
	}

	/**
	 * count number of product found.
	 * 
	 * @param productDTO
	 * @return
	 */
	@Override
	public long countProducts(SearchProductDTO productDTO) {
		return shopRepository.countProducts(productDTO);
	}
}
