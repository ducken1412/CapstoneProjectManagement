package com.fa.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fa.dto.ProductDTO;
import com.fa.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	/**
	 * find products have status = 1.
	 * 
	 * @return
	 */
	@Query("select new com.fa.dto.ProductDTO(p) From Product p  where status != 0")
	List<ProductDTO> findProductAvailable();

	/**
	 * delete product by category id.
	 * 
	 * @param id
	 * @return
	 */
	@Modifying
	@Transactional
	@Query("UPDATE Product p SET p.status = 0 WHERE p.category.id =:id")
	int deleteByCategoryId(int id);

	/**
	 * find products by status or order by id desc.
	 * 
	 * @param status
	 * @param pageable
	 * @return
	 */
	List<Product> findProductByStatusOrderByIdDesc(Integer status, Pageable pageable);

	/**
	 * find deal of the week product.
	 * 
	 * @param pageable
	 * @return
	 */
	@Query("select new com.fa.dto.ProductDTO(p) FROM Product p WHERE p.status = 1 and p.quantity>0 ORDER BY p.price*p.discount DESC")
	List<ProductDTO> findDealOfTheWeekProduct(Pageable pageable);

	/**
	 * find products on sale.
	 * 
	 * @param pageable
	 * @return
	 */
	@Query("SELECT new com.fa.dto.ProductDTO(p) FROM Product p  WHERE status != 0 AND discount > 20 ORDER BY p.discount DESC")
	List<ProductDTO> findProductOnSale(Pageable pageable);

	/**
	 * find products on sale by condition.
	 * 
	 * @param condition
	 * @param pageable
	 * @return
	 */
	@Query("SELECT new com.fa.dto.ProductDTO(p) FROM Product p  WHERE status != 0 AND discount > 0 AND condition = ?1 ORDER BY p.discount DESC")
	List<ProductDTO> findProductOnSaleByCondition(int condition, Pageable pageable);
}
