package com.fa.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.fa.dto.ProductDTO;
import com.fa.entity.Product;
import com.fa.entity.ProductImage;
import com.fa.repository.ProductImageRepository;
import com.fa.service.ProductImageService;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductImageTest {

	@Autowired
	private ProductImageService imageService;

	@MockBean
	private ProductImageRepository imageRepository;

	@Test
	void testGetAllProductImageSuccess() {
		List<ProductImage> list = new ArrayList<>();
		when(imageRepository.findAll()).thenReturn(list);
		assertEquals(list, imageService.getAllProductImage());
	}

	@Test
	void testSaveProductImageSuccess() {
		ProductImage productImage = new ProductImage();
		when(imageRepository.save(productImage)).thenReturn(productImage);
		assertEquals(true, imageService.saveProductImage(productImage));
	}

	@Test
	void testSaveProductImageException() {
		ProductImage productImage = null;
		when(imageRepository.save(productImage)).thenThrow(NullPointerException.class);
		assertEquals(false, imageService.saveProductImage(productImage));
	}

	@Test
	void testDelProductImageSuccess() {
		int id = 1;
		Mockito.doNothing().when(imageRepository).deleteById(id);
		assertEquals(true, imageService.delProductImage(id));
	}

	@Test
	void testdDelProductImageException() {
		int id = 1;
		Mockito.doThrow(IllegalArgumentException.class).when(imageRepository).deleteById(id);
		assertEquals(false, imageService.delProductImage(id));
	}

	@Test
	void testGetProductImageSuccess() {
		int id = 1;
		ProductImage productImage = new ProductImage();
		productImage.setId(id);
		Optional<ProductImage> optionalProductImage = Optional.of(productImage);
		when(imageRepository.findById(id)).thenReturn(optionalProductImage);
		ProductImage image = imageService.getProductImage(id);
		assertEquals(optionalProductImage.map(ProductImage::getId).orElse(null), image.getId());
	}

	@Test
	void testSaveAllSuccess() {
		List<ProductImage> list = new ArrayList<>();
		list.add(new ProductImage());
		when(imageRepository.saveAll(list)).thenReturn(list);
		assertEquals(true, imageService.saveAll(list));
	}

	@Test
	void testSaveAllFail() {
		List<ProductImage> list = new ArrayList<>();
		when(imageRepository.saveAll(list)).thenReturn(list);
		assertEquals(false, imageService.saveAll(list));
	}

	@Test
	void testDeleteByProductIdSuccess() {
		int id = 1;
		when(imageRepository.deleteByProductId(id)).thenReturn(1);
		assertEquals(true, imageService.deleteByProductId(id));
	}

	@Test
	void testDeleteByProductIdException() {
		int id = 1;
		when(imageRepository.deleteByProductId(id)).thenReturn(0);
		assertEquals(false, imageService.deleteByProductId(id));
	}
}
