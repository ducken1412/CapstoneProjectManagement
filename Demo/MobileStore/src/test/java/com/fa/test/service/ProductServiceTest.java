package com.fa.test.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import com.fa.dto.ProductDTO;
import com.fa.entity.Category;
import com.fa.entity.Product;
import com.fa.entity.ProductImage;
import com.fa.entity.Users;
import com.fa.repository.ProductRepository;
import com.fa.service.ProductImageService;
import com.fa.service.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductServiceTest {

	@Autowired
	private ProductService productService;

	@MockBean
	private ProductRepository productRepository;

	@MockBean
	private UsernamePasswordAuthenticationToken principal;

	@MockBean
	private ProductImageService productImageService;

	private Product product;
	private ProductDTO productDTO;

	@Test
	void testGetProductAvailable1() {
		Integer id = 2;
		product = new Product(2, "Iphone 11 Pro max", new Category(), "descprition", 1000, 20, 10, "Apple", 1,
				new Date(), null, 1, new Users(), null, "image", null, null);
		assertEquals(1, product.getStatus());
		productDTO = new ProductDTO(product);
		Optional<Product> returnProduct = Optional.of(product);
		when(productRepository.findById(2)).thenReturn(returnProduct);
		ProductDTO actualProduct = productService.getProductAvailable(id);
		assertEquals(productDTO.getId(), actualProduct.getId());
		assertEquals(productDTO.getName(), actualProduct.getName());
		assertEquals(productDTO.getStatus(), actualProduct.getStatus());
	}

	@Test
	void testGetProductAvailable2() {
		Integer id = 2;
		when(productRepository.findById(id)).thenReturn(Optional.empty());
		assertEquals(null, productService.getProductAvailable(id));
	}

	@Test
	void testGetProductAvailable3() {
		Integer id = 2;
		product = new Product(2, "Iphone 11 Pro max", new Category(), "descprition", 1000, 20, 10, "Apple", 1,
				new Date(), null, 0, new Users(), null, "image", null, null);
		assertEquals(0, product.getStatus());
		Optional<Product> returnProduct = Optional.of(product);
		when(productRepository.findById(2)).thenReturn(returnProduct);
		assertEquals(null, productService.getProductAvailable(id));
	}
	
	@Test
	void testGetAllProductSuccess() {		
		List<ProductDTO> products = new ArrayList<ProductDTO>();	
		when(productRepository.findAll().stream().map(p -> new ProductDTO(p)).collect(Collectors.toList())).thenReturn(products);
		assertEquals(products, productService.getAllProduct());
	}
	
	@Test
	void testGetAllProductFail() {		
		List<ProductDTO> products = new ArrayList<ProductDTO>();
		products.isEmpty();
		when(productRepository.findAll().stream().map(p -> new ProductDTO(p)).collect(Collectors.toList())).thenReturn(products);
		assertEquals(0, productService.getAllProduct().size());
	}

	@Test
	void testSaveProductSuccess() {
		Product product = new Product();
		when(productRepository.save(product)).thenReturn(product);
		assertEquals(true, productService.saveProduct(product));
	}

	@Test
	void testSaveProductException() {
		Product product = null;
		when(productRepository.save(product)).thenThrow(NullPointerException.class);
		assertEquals(false, productService.saveProduct(product));
	}

	@Test
	void testDeleteProductSuccess() {
		int id = 1;
		Mockito.doNothing().when(productRepository).deleteById(id);
		assertEquals(true, productService.delProduct(id));
	}

	@Test
	void testDeleteProductException() {
		int id = 0 ;
		Mockito.doThrow(IllegalArgumentException.class).when(productRepository).deleteById(id);
		assertEquals(false, productService.delProduct(id));
	}

	@Test
	void testGetProductSuccess() {
		// Create 1 product with id=1
		int id = 1;
		Product product = new Product();
		product.setId(id);
		// Return optional product with id = 1
		Optional<Product> optionalProduct = Optional.of(product);
		when(productRepository.findById(id)).thenReturn(optionalProduct);
		// Call service method and set return = productDTO
		ProductDTO productDTO = productService.getProduct(id);
		assertEquals(1, productDTO.getId());
		//optionalProduct.map(Product::getId).orElse(null)
	}

	@Test
	void testGetProductNull() {
		int id = 1;
		Optional<Product> optionalProduct = Optional.empty();
		when(productRepository.findById(id)).thenReturn(optionalProduct);
		assertEquals(null, productService.getProduct(id));
	}

	@Test
	void getProductAvailableSuccess() {
		List<ProductDTO> list = new ArrayList<>();
		when(productRepository.findProductAvailable()).thenReturn(list);
		assertEquals(list, productService.getProductAvailable());
	}

	@Test
	void getProductAvailableNull() {
		List<ProductDTO> list = null;
		when(productRepository.findProductAvailable()).thenReturn(list);
		assertEquals(null, productService.getProductAvailable());
	}

	@Test
	void saveProductAndImageSuccess() {
		// Create productDTO example
		ProductDTO dto = new ProductDTO();
		dto.setName("Product 1");
		dto.setPrice(1200F);
		dto.setManufacturer("Manufacturer");
		dto.setDiscount(10);
		dto.setCondition(1);
		Category category = new Category();
		dto.setCategory(category);
		dto.setImageMain("img-path");
		dto.setDescription("description");
		dto.setStatus(1);
		dto.setQuantity(10);
		List<String> productImageList = new ArrayList<String>();
		productImageList.add("sub-img-path");
		UserDetails userDetails = (UserDetails) new User("admin", "123456", new ArrayList<GrantedAuthority>());
		when(principal.getPrincipal()).thenReturn(userDetails);
		List<ProductImage> productImages = new ArrayList<>();
		when(productImageService.saveAll(productImages)).thenReturn(true);
		ProductDTO productDTO = productService.saveProductAndImage(dto, "img-path", productImageList, principal);
		assertEquals("Product 1", productDTO.getName());
	}

	@Test
	void saveProductAndImageImageListNull() {
		ProductDTO dto = new ProductDTO();
		dto.setName("Product 1");
		dto.setPrice(1200F);
		dto.setManufacturer("Manufacturer");
		dto.setDiscount(10);
		dto.setCondition(1);
		Category category = new Category();
		dto.setCategory(category);
		dto.setImageMain("img-path");
		dto.setDescription("description");
		dto.setStatus(1);
		dto.setQuantity(10);
		UserDetails userDetails = (UserDetails) new User("admin", "123456", new ArrayList<GrantedAuthority>());
		when(principal.getPrincipal()).thenReturn(userDetails);
		// when(productService.saveProduct(any(Product.class))).thenReturn(true);
		List<String> productImageList = null;
		ProductDTO productDTO = productService.saveProductAndImage(dto, "img-path", productImageList, principal);
		assertEquals("Product 1", productDTO.getName());
	}

	@Test
	void saveProductAndImageException() {
		// Create productDTO null
		ProductDTO dto = null;
		List<String> productImageList = new ArrayList<String>();
		ProductDTO productDTO = productService.saveProductAndImage(dto, "img-path", productImageList, principal);
		assertEquals(null, productDTO);
	}

	@Test
	void updateProductAndImageSuccess() {
		Product product = new Product();
		// Create productDTO example
		ProductDTO dto = new ProductDTO();
		dto.setId(1);
		// Return optional product with id = 1
		Optional<Product> optionalProduct = Optional.of(product);
		when(productRepository.findById(dto.getId())).thenReturn(optionalProduct);
		dto.setName("Product 1");
		dto.setPrice(1200F);
		dto.setManufacturer("Manufacturer");
		dto.setDiscount(10);
		dto.setCondition(1);
		Category category = new Category();
		dto.setCategory(category);
		dto.setImageMain("img-path");
		dto.setDescription("description");
		dto.setStatus(1);
		dto.setQuantity(10);
		List<String> productImageList = new ArrayList<String>();
		productImageList.add("sub-img-path");
		UserDetails userDetails = (UserDetails) new User("admin", "123456", new ArrayList<GrantedAuthority>());
		when(principal.getPrincipal()).thenReturn(userDetails);
		product.setId(1);
		when(productService.saveProduct(any(Product.class))).thenReturn(true);
		when(productImageService.deleteByProductId(product.getId())).thenReturn(true);

		List<ProductImage> productImages = new ArrayList<>();
		when(productImageService.saveAll(productImages)).thenReturn(true);
		ProductDTO productDTO = productService.updateProductAndImage(dto, "img-path", productImageList, principal);
		assertEquals("Product 1", productDTO.getName());
	}

	@Test
	void updateProductAndImageImageListNull() {
		Product product = new Product();
		// Create productDTO example
		ProductDTO dto = new ProductDTO();
		dto.setId(1);
		// Return optional product with id = 1
		Optional<Product> optionalProduct = Optional.of(product);
		when(productRepository.findById(dto.getId())).thenReturn(optionalProduct);
		dto.setName("Product 1");
		dto.setPrice(1200F);
		dto.setManufacturer("Manufacturer");
		dto.setDiscount(10);
		dto.setCondition(1);
		Category category = new Category();
		dto.setCategory(category);
		dto.setImageMain("img-path");
		dto.setDescription("description");
		dto.setStatus(1);
		dto.setQuantity(10);
		List<String> productImageList = null;
		UserDetails userDetails = (UserDetails) new User("admin", "123456", new ArrayList<GrantedAuthority>());
		when(principal.getPrincipal()).thenReturn(userDetails);
		List<ProductImage> productImages = new ArrayList<>();
		when(productImageService.saveAll(productImages)).thenReturn(true);
		ProductDTO productDTO = productService.updateProductAndImage(dto, "img-path", productImageList, principal);
		assertEquals("Product 1", productDTO.getName());
	}

	@Test
	void updateProductAndImageProductNull() {
		ProductDTO dto = new ProductDTO();
		Optional<Product> optionalProduct = Optional.empty();
		when(productRepository.findById(dto.getId())).thenReturn(optionalProduct);
		List<String> productImageList = new ArrayList<String>();
		ProductDTO productDTO = productService.updateProductAndImage(dto, "img-path", productImageList, principal);
		assertEquals(null, productDTO);
	}

	@Test
	void updateProductAndImageException() {
		Product product = new Product();
		ProductDTO dto = new ProductDTO();
		List<String> productImageList = new ArrayList<String>();
		dto.setId(1);
		Optional<Product> optionalProduct = Optional.of(product);
		when(productRepository.findById(dto.getId())).thenReturn(optionalProduct);
		ProductDTO productDTO = productService.updateProductAndImage(dto, "img-path", productImageList, principal);
		assertEquals(null, productDTO);
	}

	@Test
	void unActiveProductSuccess() {
		Product product = new Product();
		int id = 1;
		Optional<Product> optionalProduct = Optional.of(product);
		when(productRepository.findById(id)).thenReturn(optionalProduct);
		UserDetails userDetails = (UserDetails) new User("admin", "123456", new ArrayList<GrantedAuthority>());
		when(principal.getPrincipal()).thenReturn(userDetails);
		assertEquals(true, productService.unActiveProduct(id, principal));
	}

	@Test
	// Unactive fail, product status still = 1
	void unActiveProductFail() {
		int id = 1;
		Optional<Product> optionalProduct = Optional.empty();
		when(productRepository.findById(id)).thenReturn(optionalProduct);
		UserDetails userDetails = (UserDetails) new User("admin", "123456", new ArrayList<GrantedAuthority>());
		when(principal.getPrincipal()).thenReturn(userDetails);
		assertEquals(true, productService.unActiveProduct(id, principal));
	}

	@Test
	void testDeleteProductByCategoryIdSuccess() {
		int id = 1;
		when(productRepository.deleteByCategoryId(id)).thenReturn(1);
		assertEquals(1, productService.deleteByCategoryId(id));
	}

	@Test
	void testDeleteProductByCategoryIdFail() {
		int id = 1;
		when(productRepository.deleteByCategoryId(id)).thenReturn(0);
		assertEquals(0, productService.deleteByCategoryId(id));
	}

	@Test
	void testGetNewProductSuccess() {
		int id = 1;
		Pageable page = PageRequest.of(0, 5);
		List<Product> products = new ArrayList<>();
		products.add(new Product());
		when(productRepository.findProductByStatusOrderByIdDesc(id, page)).thenReturn(products);
		assertEquals(1, productService.getNewProduct().size());
	}

	@Test
	void testGetNewProductFail() {
		int id = 1;
		Pageable page = PageRequest.of(0, 5);
		List<Product> products = new ArrayList<>();
		when(productRepository.findProductByStatusOrderByIdDesc(id, page)).thenReturn(products);
		assertEquals(0, productService.getNewProduct().size());
	}

	@Test
	void testGetDealProductSuccess() {
		Pageable page = PageRequest.of(0, 5);
		List<ProductDTO> products = new ArrayList<>();
		products.add(new ProductDTO());
		when(productRepository.findDealOfTheWeekProduct(page)).thenReturn(products);
		assertEquals(1, productService.getDealOfTheWeekProduct().size());
	}

	@Test
	void testGetDealProductFail() {
		Pageable page = PageRequest.of(0, 5);
		List<ProductDTO> products = new ArrayList<>();
		when(productRepository.findDealOfTheWeekProduct(page)).thenReturn(products);
		List<ProductDTO> products1 = productService.getDealOfTheWeekProduct();
		assertEquals(true, products1.isEmpty());
	}

	@Test
	void testFindProductOnSaleSuccess() {
		Pageable page = null;
		List<ProductDTO> products = new ArrayList<>();
		products.add(new ProductDTO());
		when(productRepository.findProductOnSale(page)).thenReturn(products);
		assertEquals(1, productService.findProductOnSale(page).size());
	}

	@Test
	void testFindProductOnSaleFail() {
		Pageable page = null;
		List<ProductDTO> products = new ArrayList<>();
		when(productRepository.findProductOnSale(page)).thenReturn(products);
		assertEquals(true, productService.findProductOnSale(page).isEmpty());
	}

	@Test
	void testfindProductOnSaleByConditionSuccess() {
		int condition = 1;
		Pageable page = null;
		List<ProductDTO> products = new ArrayList<>();
		products.add(new ProductDTO());
		when(productRepository.findProductOnSaleByCondition(condition, page)).thenReturn(products);
		assertEquals(1, productService.findProductOnSaleByCondition(condition, page).size());
	}

	@Test
	void testfindProductOnSaleByConditionFail() {
		int condition = 1;
		Pageable page = null;
		List<ProductDTO> products = new ArrayList<>();
		when(productRepository.findProductOnSaleByCondition(condition, page)).thenReturn(products);
		assertEquals(true, productService.findProductOnSaleByCondition(condition, page).isEmpty());
	}

	@Test
	void testupdateQuantitySuccess() {
		int quantity = 3;
		Category category = new Category();
		Date date = new Date();
		Users users = new Users();
		Product product = new Product("AP11", category, "description", 100, 10, 10, "Apple", 1, date, null, 1, users,
				null, "Image");
		Product product2 = new Product("AP11", category, "description", 100, 10, 10, "Apple", 1, date, null, 1, users,
				null, "Image");

		product.setId(10); 
		product2.setId(10); 
		product2.setQuantity(product2.getQuantity() - quantity);

		Optional<Product> returnProduct = Optional.of(product);
		when(productRepository.findById(product.getId())).thenReturn(returnProduct);
		when(productRepository.save(product)).thenReturn(product2);

		assertTrue(productService.updateQuantity(product.getId(), quantity));
	}

	@Test
	void testupdateQuantityFail1() {
		int quantity = 3;
		Category category = new Category();
		Date date = new Date();
		Users users = new Users();
		Product product = new Product("AP11", category, "description", 100, 10, 10, "Apple", 1, date, null, 0, users,
				null, "Image");

		product.setId(10);

		Optional<Product> returnProduct = Optional.of(product);
		try {
			when(productRepository.findById(product.getId())).thenReturn(returnProduct);
			productService.updateQuantity(product.getId(), quantity);
			assertFalse(true);
		} catch (RuntimeException e) {
			assertTrue(true);
		}

	}

	@Test
	void testupdateQuantityFail2() {
		int quantity = 3;
		when(productRepository.findById(10)).thenReturn(Optional.empty());
		try {
			productService.updateQuantity(product.getId(), quantity);
			assertFalse(true);
		} catch (RuntimeException e) {
			assertTrue(true);
		}

	}
}
