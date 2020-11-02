package com.fa.test.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.fa.dto.ProductDTO;
import com.fa.dto.SearchProductDTO;
import com.fa.repository.ShopRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class ShopRepositoryTest {

	@Mock
	private EntityManager entityManager;

	@Autowired
	private ShopRepository shopRepository;

	@Mock
	Query query;

	@Test
	void testSearchProductsSuccess() {
		ReflectionTestUtils.setField(shopRepository, "entityManager", entityManager);
		SearchProductDTO searchProductDTO = new SearchProductDTO(10, "samsung", (float) 3000, (float) 1, 1, 1, 1);
		when(entityManager.createQuery(
				"SELECT new com.fa.dto.ProductDTO(p) FROM Product p WHERE p.category.id = :category AND p.condition = :condition AND p.price*(100-p.discount)/100 >= :priceMin AND p.price*(100-p.discount)/100 <= :priceMax AND p.name LIKE :name AND p.status != 0 ORDER BY p.price DESC"))
						.thenReturn(query);
		when(query.setFirstResult(0)).thenReturn(query);
		when(query.setMaxResults(15)).thenReturn(query);
		List<ProductDTO> expected = new ArrayList<>();
		when(query.getResultList()).thenReturn(expected);
		assertEquals(expected, shopRepository.searchProducts(searchProductDTO, 15));
	}

	@Test
	void testSearchProductsSuccess2() {
		ReflectionTestUtils.setField(shopRepository, "entityManager", entityManager);
		SearchProductDTO searchProductDTO = new SearchProductDTO(0, "", (float) 0, (float) 0, 0, 0, 0);
		when(entityManager.createQuery("SELECT new com.fa.dto.ProductDTO(p) FROM Product p WHERE p.status != 0 ORDER BY p.id DESC"))
				.thenReturn(query);
		when(query.setFirstResult(0)).thenReturn(query);
		when(query.setMaxResults(15)).thenReturn(query);
		List<ProductDTO> expected = new ArrayList<>();
		when(query.getResultList()).thenReturn(expected);
		assertEquals(expected, shopRepository.searchProducts(searchProductDTO, 15));
	}

	@Test
	void testSearchProductsSuccessWithCategoryIdNull() {
		ReflectionTestUtils.setField(shopRepository, "entityManager", entityManager);
		SearchProductDTO searchProductDTO = new SearchProductDTO(null, "samsung", (float) 3000, null, 1, null, 1);
		when(entityManager.createQuery(
				"SELECT new com.fa.dto.ProductDTO(p) FROM Product p WHERE p.price*(100-p.discount)/100 <= :priceMax AND p.name LIKE :name AND p.status != 0 ORDER BY p.price DESC"))
						.thenReturn(query);
		when(query.setFirstResult(0)).thenReturn(query);
		when(query.setMaxResults(15)).thenReturn(query);
		List<ProductDTO> expected = new ArrayList<>();
		when(query.getResultList()).thenReturn(expected);
		assertEquals(expected, shopRepository.searchProducts(searchProductDTO, 15));
	}

	@Test
	void testSearchProductsSuccessWithCategoryId0() {
		ReflectionTestUtils.setField(shopRepository, "entityManager", entityManager);
		SearchProductDTO searchProductDTO = new SearchProductDTO(0, "samsung", (float) 3000, null, 2, 1, 1);
		when(entityManager.createQuery(
				"SELECT new com.fa.dto.ProductDTO(p) FROM Product p WHERE p.condition = :condition AND p.price*(100-p.discount)/100 <= :priceMax AND p.name LIKE :name AND p.status != 0 ORDER BY p.price ASC"))
						.thenReturn(query);
		when(query.setFirstResult(0)).thenReturn(query);
		when(query.setMaxResults(15)).thenReturn(query);
		List<ProductDTO> expected = new ArrayList<>();
		when(query.getResultList()).thenReturn(expected);
		assertEquals(expected, shopRepository.searchProducts(searchProductDTO, 15));
	}

	@Test
	void testSearchProductsSuccessWithNameEmpty() {
		ReflectionTestUtils.setField(shopRepository, "entityManager", entityManager);
		SearchProductDTO searchProductDTO = new SearchProductDTO(null, "", (float) 3000, (float) 1, 1, null, 1);
		when(entityManager.createQuery(
				"SELECT new com.fa.dto.ProductDTO(p) FROM Product p WHERE p.price*(100-p.discount)/100 >= :priceMin AND p.price*(100-p.discount)/100 <= :priceMax AND p.status != 0 ORDER BY p.price DESC"))
						.thenReturn(query);
		when(query.setFirstResult(0)).thenReturn(query);
		when(query.setMaxResults(15)).thenReturn(query);
		List<ProductDTO> expected = new ArrayList<>();
		when(query.getResultList()).thenReturn(expected);
		assertEquals(expected, shopRepository.searchProducts(searchProductDTO, 15));
	}

	@Test
	void testSearchProductsSuccessWithNameNull() {
		ReflectionTestUtils.setField(shopRepository, "entityManager", entityManager);
		SearchProductDTO searchProductDTO = new SearchProductDTO(10, null, (float) 3000, (float) 1, 2, 1, 1);
		when(entityManager.createQuery(
				"SELECT new com.fa.dto.ProductDTO(p) FROM Product p WHERE p.category.id = :category AND p.condition = :condition AND p.price*(100-p.discount)/100 >= :priceMin AND p.price*(100-p.discount)/100 <= :priceMax AND p.status != 0 ORDER BY p.price ASC"))
						.thenReturn(query);
		when(query.setFirstResult(0)).thenReturn(query);
		when(query.setMaxResults(15)).thenReturn(query);
		List<ProductDTO> expected = new ArrayList<>();
		when(query.getResultList()).thenReturn(expected);
		assertEquals(expected, shopRepository.searchProducts(searchProductDTO, 15));
	}

	@Test
	void testSearchProductsSuccessWithPriceMin0() {
		ReflectionTestUtils.setField(shopRepository, "entityManager", entityManager);
		SearchProductDTO searchProductDTO = new SearchProductDTO(null, "samsung", (float) 0, (float) 0, 0, 0, 0);
		when(entityManager.createQuery(
				"SELECT new com.fa.dto.ProductDTO(p) FROM Product p WHERE p.name LIKE :name AND p.status != 0 ORDER BY p.id DESC"))
						.thenReturn(query);
		when(query.setFirstResult(0)).thenReturn(query);
		when(query.setMaxResults(15)).thenReturn(query);
		List<ProductDTO> expected = new ArrayList<>();
		when(query.getResultList()).thenReturn(expected);
		assertEquals(expected, shopRepository.searchProducts(searchProductDTO, 15));
	}

	@Test
	void testSearchProductsSuccessWithPriceMinNull() {
		ReflectionTestUtils.setField(shopRepository, "entityManager", entityManager);
		SearchProductDTO searchProductDTO = new SearchProductDTO(10, "samsung", null, (float) 1, 1, 1, 1);
		when(entityManager.createQuery(
				"SELECT new com.fa.dto.ProductDTO(p) FROM Product p WHERE p.category.id = :category AND p.condition = :condition AND p.price*(100-p.discount)/100 >= :priceMin AND p.name LIKE :name AND p.status != 0 ORDER BY p.price DESC"))
						.thenReturn(query);
		when(query.setFirstResult(0)).thenReturn(query);
		when(query.setMaxResults(15)).thenReturn(query);
		List<ProductDTO> expected = new ArrayList<>();
		when(query.getResultList()).thenReturn(expected);
		assertEquals(expected, shopRepository.searchProducts(searchProductDTO, 15));
	}

	@Test
	void testSearchProductsSuccessWithPriceMax0() {
		ReflectionTestUtils.setField(shopRepository, "entityManager", entityManager);
		SearchProductDTO searchProductDTO = new SearchProductDTO(10, "samsung", (float) 1, (float) 0, 2, 1, 1);
		when(entityManager.createQuery(
				"SELECT new com.fa.dto.ProductDTO(p) FROM Product p WHERE p.category.id = :category AND p.condition = :condition AND p.price*(100-p.discount)/100 <= :priceMax AND p.name LIKE :name AND p.status != 0 ORDER BY p.price ASC"))
						.thenReturn(query);
		when(query.setFirstResult(0)).thenReturn(query);
		when(query.setMaxResults(15)).thenReturn(query);
		List<ProductDTO> expected = new ArrayList<>();
		when(query.getResultList()).thenReturn(expected);
		assertEquals(expected, shopRepository.searchProducts(searchProductDTO, 15));
	}

	@Test
	void testSearchProductsSuccessWithSortedBy0() {
		ReflectionTestUtils.setField(shopRepository, "entityManager", entityManager);
		SearchProductDTO searchProductDTO = new SearchProductDTO(10, "samsung", (float) 1, (float) 3000, 0, null, null);
		when(entityManager.createQuery(
				"SELECT new com.fa.dto.ProductDTO(p) FROM Product p WHERE p.category.id = :category AND p.price*(100-p.discount)/100 >= :priceMin AND p.price*(100-p.discount)/100 <= :priceMax AND p.name LIKE :name AND p.status != 0 ORDER BY p.id DESC"))
						.thenReturn(query);
		when(query.setFirstResult(0)).thenReturn(query);
		when(query.setMaxResults(15)).thenReturn(query);
		List<ProductDTO> expected = new ArrayList<>();
		when(query.getResultList()).thenReturn(expected);
		assertEquals(expected, shopRepository.searchProducts(searchProductDTO, 15));
	}

	@Test
	void testSearchProductsSuccessWithSortedByNull() {
		ReflectionTestUtils.setField(shopRepository, "entityManager", entityManager);
		SearchProductDTO searchProductDTO = new SearchProductDTO(10, "samsung", (float) 1, (float) 3000, null, 0, 0);
		when(entityManager.createQuery(
				"SELECT new com.fa.dto.ProductDTO(p) FROM Product p WHERE p.category.id = :category AND p.price*(100-p.discount)/100 >= :priceMin AND p.price*(100-p.discount)/100 <= :priceMax AND p.name LIKE :name AND p.status != 0 ORDER BY p.id DESC"))
						.thenReturn(query);
		when(query.setFirstResult(0)).thenReturn(query);
		when(query.setMaxResults(15)).thenReturn(query);
		List<ProductDTO> expected = new ArrayList<>();
		when(query.getResultList()).thenReturn(expected);
		assertEquals(expected, shopRepository.searchProducts(searchProductDTO, 15));
	}
	
	@Test
	void testCountSearchProductsSuccess() {
		ReflectionTestUtils.setField(shopRepository, "entityManager", entityManager);
		SearchProductDTO searchProductDTO = new SearchProductDTO(10, "samsung", (float) 3000, (float) 1, 1, 1, 1);
		when(entityManager.createQuery(
				"SELECT count(p) FROM Product p WHERE p.category.id = :category AND p.condition = :condition AND p.price*(100-p.discount)/100 >= :priceMin AND p.price*(100-p.discount)/100 <= :priceMax AND p.name LIKE :name AND p.status != 0"))
						.thenReturn(query);
		List<ProductDTO> expected = new ArrayList<>();
		when(query.getSingleResult()).thenReturn((long)expected.size());
		assertEquals(expected.size(), shopRepository.countProducts(searchProductDTO));
	}
	
	@Test
	void testCountSearchProductsException() {
		ReflectionTestUtils.setField(shopRepository, "entityManager", entityManager);
		SearchProductDTO searchProductDTO = new SearchProductDTO(10, "samsung", (float) 3000, (float) 1, 1, 1, 1);
		when(entityManager.createQuery(
				"SELECT count(p) FROM Product p WHERE p.category.id = :category AND p.condition = :condition AND p.price*(100-p.discount)/100 >= :priceMin AND p.price*(100-p.discount)/100 <= :priceMax AND p.name LIKE :name AND p.status != 0"))
						.thenReturn(query);
		List<ProductDTO> expected = new ArrayList<>();
		when(query.getSingleResult()).thenReturn((long)expected.size());
		Mockito.doThrow(NoResultException.class).when(query).getSingleResult();
		assertEquals(0, shopRepository.countProducts(searchProductDTO));
	}
	
	@Test
	void testCountSearchProductsSuccess2() {
		ReflectionTestUtils.setField(shopRepository, "entityManager", entityManager);
		SearchProductDTO searchProductDTO = new SearchProductDTO(0, "", (float) 0, (float) 0, 0, 0, 0);
		when(entityManager.createQuery("SELECT count(p) FROM Product p WHERE p.status != 0"))
				.thenReturn(query);
		List<ProductDTO> expected = new ArrayList<>();
		when(query.getSingleResult()).thenReturn((long)expected.size());
		assertEquals(expected.size(), shopRepository.countProducts(searchProductDTO));
	}

	@Test
	void testCountSearchProductsSuccessWithCategoryIdNull() {
		ReflectionTestUtils.setField(shopRepository, "entityManager", entityManager);
		SearchProductDTO searchProductDTO = new SearchProductDTO(null, "samsung", (float) 3000, null, 1, null, 1);
		when(entityManager.createQuery(
				"SELECT count(p) FROM Product p WHERE p.price*(100-p.discount)/100 <= :priceMax AND p.name LIKE :name AND p.status != 0"))
						.thenReturn(query);
		List<ProductDTO> expected = new ArrayList<>();
		when(query.getSingleResult()).thenReturn((long)expected.size());
		assertEquals(expected.size(), shopRepository.countProducts(searchProductDTO));
	}

	@Test
	void testCountSearchProductsSuccessWithCategoryId0() {
		ReflectionTestUtils.setField(shopRepository, "entityManager", entityManager);
		SearchProductDTO searchProductDTO = new SearchProductDTO(0, "samsung", (float) 3000, null, 2, 1, 1);
		when(entityManager.createQuery(
				"SELECT count(p) FROM Product p WHERE p.condition = :condition AND p.price*(100-p.discount)/100 <= :priceMax AND p.name LIKE :name AND p.status != 0"))
						.thenReturn(query);
		List<ProductDTO> expected = new ArrayList<>();
		when(query.getSingleResult()).thenReturn((long)expected.size());
		assertEquals(expected.size(), shopRepository.countProducts(searchProductDTO));
	}

	@Test
	void testCountSearchProductsSuccessWithNameEmpty() {
		ReflectionTestUtils.setField(shopRepository, "entityManager", entityManager);
		SearchProductDTO searchProductDTO = new SearchProductDTO(null, "", (float) 3000, (float) 1, 1, null, 1);
		when(entityManager.createQuery(
				"SELECT count(p) FROM Product p WHERE p.price*(100-p.discount)/100 >= :priceMin AND p.price*(100-p.discount)/100 <= :priceMax AND p.status != 0"))
						.thenReturn(query);
		List<ProductDTO> expected = new ArrayList<>();
		when(query.getSingleResult()).thenReturn((long)expected.size());
		assertEquals(expected.size(), shopRepository.countProducts(searchProductDTO));
	}

	@Test
	void testCountSearchProductsSuccessWithNameNull() {
		ReflectionTestUtils.setField(shopRepository, "entityManager", entityManager);
		SearchProductDTO searchProductDTO = new SearchProductDTO(10, null, (float) 3000, (float) 1, 2, 1, 1);
		when(entityManager.createQuery(
				"SELECT count(p) FROM Product p WHERE p.category.id = :category AND p.condition = :condition AND p.price*(100-p.discount)/100 >= :priceMin AND p.price*(100-p.discount)/100 <= :priceMax AND p.status != 0"))
						.thenReturn(query);
		List<ProductDTO> expected = new ArrayList<>();
		when(query.getSingleResult()).thenReturn((long)expected.size());
		assertEquals(expected.size(), shopRepository.countProducts(searchProductDTO));
	}

	@Test
	void testCountSearchProductsSuccessWithPriceMin0() {
		ReflectionTestUtils.setField(shopRepository, "entityManager", entityManager);
		SearchProductDTO searchProductDTO = new SearchProductDTO(null, "samsung", (float) 0, (float) 0, 0, 0, 0);
		when(entityManager.createQuery(
				"SELECT count(p) FROM Product p WHERE p.name LIKE :name AND p.status != 0"))
						.thenReturn(query);
		List<ProductDTO> expected = new ArrayList<>();
		when(query.getSingleResult()).thenReturn((long)expected.size());
		assertEquals(expected.size(), shopRepository.countProducts(searchProductDTO));
	}

	@Test
	void testCountSearchProductsSuccessPriceMinNull() {
		ReflectionTestUtils.setField(shopRepository, "entityManager", entityManager);
		SearchProductDTO searchProductDTO = new SearchProductDTO(10, "samsung", null, (float) 1, 1, 1, 1);
		when(entityManager.createQuery(
				"SELECT count(p) FROM Product p WHERE p.category.id = :category AND p.condition = :condition AND p.price*(100-p.discount)/100 >= :priceMin AND p.name LIKE :name AND p.status != 0"))
						.thenReturn(query);
		List<ProductDTO> expected = new ArrayList<>();
		when(query.getSingleResult()).thenReturn((long)expected.size());
		assertEquals(expected.size(), shopRepository.countProducts(searchProductDTO));
	}

	@Test
	void testCountSearchProductsSuccessWithPriceMax0() {
		ReflectionTestUtils.setField(shopRepository, "entityManager", entityManager);
		SearchProductDTO searchProductDTO = new SearchProductDTO(10, "samsung", (float) 1, (float) 0, 2, 1, 1);
		when(entityManager.createQuery(
				"SELECT count(p) FROM Product p WHERE p.category.id = :category AND p.condition = :condition AND p.price*(100-p.discount)/100 <= :priceMax AND p.name LIKE :name AND p.status != 0"))
						.thenReturn(query);
		List<ProductDTO> expected = new ArrayList<>();
		when(query.getSingleResult()).thenReturn((long)expected.size());
		assertEquals(expected.size(), shopRepository.countProducts(searchProductDTO));
	}

}
