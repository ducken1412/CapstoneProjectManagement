package com.fa.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fa.entity.ProductImage;
import com.fa.repository.ProductImageRepository;

@Service
public class ProductImageServiceImpl implements ProductImageService {
	@Autowired
	private ProductImageRepository imageRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductImageServiceImpl.class);

	/**
	 * get all ProductImage.
	 * 
	 * @return
	 */
	@Override
	public List<ProductImage> getAllProductImage() {
		return imageRepository.findAll();
	}

	/**
	 * save ProductImage.
	 * 
	 * @param productImage
	 * @return
	 */
	@Override
	public boolean saveProductImage(ProductImage productImage) {
		try {
			imageRepository.save(productImage);
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return false;
	}

	/**
	 * delete ProductImage.
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public boolean delProductImage(int id) {
		try {
			imageRepository.deleteById(id);
		} catch (IllegalArgumentException e) {
			LOGGER.error(e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * get ProductImage.
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public ProductImage getProductImage(int id) {
		return imageRepository.findById(id).orElse(null);
	}

	/**
	 * save list ProductImages.
	 * 
	 * @param productImages
	 * @return
	 */
	@Override
	public boolean saveAll(List<ProductImage> productImages) {
		List<ProductImage> list = imageRepository.saveAll(productImages);
		if (list.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * delete ProductImage by product id.
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public boolean deleteByProductId(Integer id) {
		int num = imageRepository.deleteByProductId(id);
		if (num > 0) {
			return true;
		}
		return false;
	}
}
