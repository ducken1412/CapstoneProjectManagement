package com.fa.service;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fa.dto.ProductDTO;
import com.fa.entity.Product;

@Service
public interface ProductService {

	/**
	 * get all product.
	 * 
	 * @return
	 */
	List<ProductDTO> getAllProduct();

	/**
	 * delete product by id.
	 * 
	 * @param id
	 * @return
	 */
	boolean delProduct(int id);

	/**
	 * get product by id.
	 * 
	 * @param id
	 * @return
	 */
	ProductDTO getProduct(int id);

	/**
	 * get products have status = 1.
	 * 
	 * @return
	 */
	List<ProductDTO> getProductAvailable();

	/**
	 * get product by id have status = 1.
	 * 
	 * @param id
	 * @return
	 */
	ProductDTO getProductAvailable(int id);

	/**
	 * save product.
	 * 
	 * @param product
	 * @return
	 */
	boolean saveProduct(Product product);

	/**
	 * save product and images of product.
	 * 
	 * @param dto
	 * @param imageMain
	 * @param productImageList
	 * @param principal
	 * @return
	 */
	ProductDTO saveProductAndImage(ProductDTO dto, String imageMain, List<String> productImageList,
			Principal principal);

	/**
	 * update product and images of product.
	 * 
	 * @param dto
	 * @param imageMain
	 * @param productImageList
	 * @param principal
	 * @return
	 */
	ProductDTO updateProductAndImage(ProductDTO dto, String imageMain, List<String> productImageList,
			Principal principal);

	/**
	 * delete products by category id.
	 * 
	 * @param id
	 * @return
	 */
	int deleteByCategoryId(int id);

	/**
	 * unActive product by id.
	 * 
	 * @param id
	 * @param principal
	 * @return
	 */
	boolean unActiveProduct(int id, Principal principal);

	/**
	 * get new products.
	 * 
	 * @return
	 */
	List<Product> getNewProduct();

	/**
	 * find products on sale.
	 * 
	 * @param pageable
	 * @return
	 */
	List<ProductDTO> findProductOnSale(Pageable pageable);

	/**
	 * find products on sale by condition.
	 * 
	 * @param condition
	 * @param pageable
	 * @return
	 */
	List<ProductDTO> findProductOnSaleByCondition(int condition, Pageable pageable);

	/**
	 * update quantity of product.
	 * 
	 * @param id
	 * @param quantity
	 * @return
	 */
	boolean updateQuantity(int id, int quantity);

	/**
	 * get deal of the week product.
	 * 
	 * @return
	 */
	List<ProductDTO> getDealOfTheWeekProduct();

}