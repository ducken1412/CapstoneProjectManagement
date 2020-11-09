package com.fa.service;

import java.util.List;

import com.fa.entity.ProductImage;

public interface ProductImageService {

	/**
	 * get all ProductImage.
	 * 
	 * @return
	 */
	List<ProductImage> getAllProductImage();

	/**
	 * save ProductImage.
	 * 
	 * @param productImage
	 * @return
	 */
	boolean saveProductImage(ProductImage productImage);

	/**
	 * delete ProductImage.
	 * 
	 * @param id
	 * @return
	 */
	boolean delProductImage(int id);

	/**
	 * get ProductImage.
	 * 
	 * @param id
	 * @return
	 */
	ProductImage getProductImage(int id);

	/**
	 * save list ProductImages.
	 * 
	 * @param productImages
	 * @return
	 */
	boolean saveAll(List<ProductImage> productImages);

	/**
	 * delete ProductImage by product id.
	 * 
	 * @param id
	 * @return
	 */
	boolean deleteByProductId(Integer id);

}