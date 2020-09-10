package com.fa.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fa.dto.ProductDTO;
import com.fa.entity.Product;
import com.fa.entity.ProductImage;
import com.fa.entity.Users;
import com.fa.repository.ProductRepository;
import com.fa.utils.FormatMoney;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private ProductImageService productImageService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

	/**
	 * get all product.
	 * 
	 * @return
	 */
	@Override
	public List<ProductDTO> getAllProduct() {
		return productRepository.findAll().stream().map(p -> new ProductDTO(p)).collect(Collectors.toList());
	}

	/**
	 * save product.
	 * 
	 * @param product
	 * @return
	 */
	public boolean saveProduct(Product product) {
		try {
			productRepository.save(product);
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return false;
	}

	/**
	 * delete product by id.
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public boolean delProduct(int id) {
		try {
			productRepository.deleteById(id);
		} catch (IllegalArgumentException e) {
			LOGGER.error(e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * get product by id.
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public ProductDTO getProduct(int id) {
		Product product = productRepository.findById(id).orElse(null);
		if (product != null) {
			return new ProductDTO(product);
		}
		return null;
	}

	/**
	 * get product by id have status = 1.
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public ProductDTO getProductAvailable(int id) {
		Product product = productRepository.findById(id).orElse(null);
		if (product != null && product.getStatus() == 1) {
			return new ProductDTO(product);
		}
		return null;
	}

	/**
	 * get products have status = 1.
	 * 
	 * @return
	 */
	@Override
	public List<ProductDTO> getProductAvailable() {
		return productRepository.findProductAvailable();
	}

	/**
	 * save product and images of product.
	 * 
	 * @param dto
	 * @param imageMain
	 * @param productImageList
	 * @param principal
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ProductDTO saveProductAndImage(ProductDTO dto, String imageMain, List<String> productImageList,
			Principal principal) {
		Product product = new Product();
		try {
			product.setName(dto.getName().trim());
			product.setPrice(FormatMoney.round(dto.getPrice()));
			product.setManufacturer(dto.getManufacturer().trim());
			product.setDiscount(dto.getDiscount());
			product.setCondition(dto.getCondition());
			product.setCategory(dto.getCategory());
			product.setImageMain(imageMain);
			product.setDescription(dto.getDescription());
			product.setStatus(1);
			product.setQuantity(dto.getQuantity());
			Date date = new Date();
			product.setCreatedDate(date);
			Users user = null;

			UserDetails loginedUser = (UserDetails) ((Authentication) principal).getPrincipal();
			user = userServiceImpl.findByUserName(loginedUser.getUsername());

			saveProduct(product);
			if (productImageList != null) {
				List<ProductImage> productImages = new ArrayList<>();
				for (String path : productImageList) {
					productImages.add(new ProductImage(product, path));
				}
				productImageService.saveAll(productImages);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return null;
		}
		return new ProductDTO(product);
	}

	/**
	 * update product and images of product.
	 * 
	 * @param dto
	 * @param imageMain
	 * @param productImageList
	 * @param principal
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ProductDTO updateProductAndImage(ProductDTO dto, String imageMain, List<String> productImageList,
			Principal principal) {
		Product product = productRepository.findById(dto.getId()).orElse(null);
		if (product != null) {
			try {
				product.setName(dto.getName().trim());
				product.setPrice(FormatMoney.round(dto.getPrice()));
				product.setManufacturer(dto.getManufacturer().trim());
				product.setDiscount(dto.getDiscount());
				product.setCondition(dto.getCondition());
				product.setCategory(dto.getCategory());
				product.setImageMain(imageMain);
				product.setDescription(dto.getDescription());
				product.setStatus(1);
				product.setQuantity(dto.getQuantity());
				Date date = new Date();
				product.setUpdateTime(date);
				Users user = null;
				UserDetails loginedUser = (UserDetails) ((Authentication) principal).getPrincipal();
				user = userServiceImpl.findByUserName(loginedUser.getUsername());
				product.setUpdatedBy(user);
				saveProduct(product);
				if (productImageList != null) {
					if (productImageService.deleteByProductId(product.getId())) {
						List<ProductImage> productImages = new ArrayList<>();
						for (String path : productImageList) {
							productImages.add(new ProductImage(product, path));
						}
						productImageService.saveAll(productImages);
					}
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				return null;
			}
		} else {
			return null;
		}
		return new ProductDTO(product);
	}

	/**
	 * delete products by category id.
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public int deleteByCategoryId(int id) {
		return productRepository.deleteByCategoryId(id);
	}

	/**
	 * unActive product by id.
	 * 
	 * @param id
	 * @param principal
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean unActiveProduct(int id, Principal principal) {
		Product product = productRepository.findById(id).orElse(null);
		if (product != null) {
			product.setStatus(0);
			Users user = null;
			UserDetails loginedUser = (UserDetails) ((Authentication) principal).getPrincipal();
			user = userServiceImpl.findByUserName(loginedUser.getUsername());
			product.setUpdatedBy(user);
			Date date = new Date();
			product.setUpdateTime(date);
		}
		return saveProduct(product);
	}

	/**
	 * get new products.
	 * 
	 * @return
	 */
	@Override
	public List<Product> getNewProduct() {
		Pageable page = PageRequest.of(0, 5);
		return productRepository.findProductByStatusOrderByIdDesc(1, page);
	}

	/**
	 * get deal of the week product.
	 * 
	 * @return
	 */
	@Override
	public List<ProductDTO> getDealOfTheWeekProduct() {
		Pageable page = PageRequest.of(0, 5);
		return productRepository.findDealOfTheWeekProduct(page);
	}

	/**
	 * find products on sale.
	 * 
	 * @param pageable
	 * @return
	 */
	@Override
	public List<ProductDTO> findProductOnSale(Pageable pageable) {
		return productRepository.findProductOnSale(pageable);
	}

	/**
	 * find products on sale by condition.
	 * 
	 * @param condition
	 * @param pageable
	 * @return
	 */
	@Override
	public List<ProductDTO> findProductOnSaleByCondition(int condition, Pageable pageable) {
		return productRepository.findProductOnSaleByCondition(condition, pageable);
	}

	/**
	 * update quantity of product.
	 * 
	 * @param id
	 * @param quantity
	 * @return
	 */
	@Override
	public boolean updateQuantity(int id, int quantity) {
		Product product = productRepository.findById(id).orElseThrow(RuntimeException::new);
		if (product.getStatus() != 1) {
			throw new RuntimeException();
		}
		product.setQuantity(product.getQuantity() - quantity);
		productRepository.save(product);
		return true;
	}
}
