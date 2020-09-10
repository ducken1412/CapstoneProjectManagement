package com.fa.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fa.dto.ProductDTO;
import com.fa.dto.SearchProductDTO;
import com.fa.service.ProductServiceImpl;

@Repository
@Transactional(rollbackFor = Exception.class)
public class ShopRepositoryImpl implements ShopRepository {
	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

	/**
	 * search products.
	 * 
	 * @param productDTO
	 * @param total
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ProductDTO> searchProducts(SearchProductDTO productDTO, int total) {
		Integer page = productDTO.getPage();
		if (page == null || page <= 0) {
			page = 1;
		}
		int at = (page - 1) * total;
		String hql = "SELECT new com.fa.dto.ProductDTO(p) FROM Product p";
		StringBuilder conds = new StringBuilder();
		boolean check = false;
		if (productDTO.getCategoryId() != null && productDTO.getCategoryId() > 0) {
			conds.append(" p.category.id = :category");
			check = true;
		}
		if (productDTO.getCondition() != null && productDTO.getCondition() > 0) {
			if (check) {
				conds.append(" AND");
			} else {
				check = true;
			}
			conds.append(" p.condition = :condition");

		}
		if (productDTO.getPriceMin() != null && productDTO.getPriceMin() > 0) {
			if (check) {
				conds.append(" AND");
			} else {
				check = true;
			}
			conds.append(" p.price*(100-p.discount)/100 >= :priceMin");
		}

		if (productDTO.getPriceMax() != null && productDTO.getPriceMax() > 0) {
			if (check) {
				conds.append(" AND");
			} else {
				check = true;
			}
			conds.append(" p.price*(100-p.discount)/100 <= :priceMax");
		}

		if (productDTO.getName() != null && productDTO.getName() != "") {
			if (check) {
				conds.append(" AND");
			} else {
				check = true;
			}
			conds.append(" p.name LIKE :name");
		}

		if (check) {
			conds.append(" AND");
		} else {
			check = true;
		}
		conds.append(" p.status != 0");
		StringBuilder orderBy = new StringBuilder();
		if (productDTO.getSortedBy() == null || productDTO.getSortedBy() == 0) {
			orderBy.append(" ORDER BY p.id DESC");

		} else if (productDTO.getSortedBy() == 1) {
			orderBy.append(" ORDER BY p.price DESC");
		} else {
			orderBy.append(" ORDER BY p.price ASC");
		}
		String condsHql = conds.toString();
		condsHql = " WHERE" + condsHql;
		hql = hql + condsHql + orderBy.toString();
		Query query = entityManager.createQuery(hql);
		if (productDTO.getCategoryId() != null && productDTO.getCategoryId() > 0) {
			query.setParameter("category", productDTO.getCategoryId());
		}
		if (productDTO.getCondition() != null && productDTO.getCondition() > 0) {
			query.setParameter("condition", productDTO.getCondition());
		}

		if (productDTO.getPriceMin() != null && productDTO.getPriceMin() > 0) {
			query.setParameter("priceMin", productDTO.getPriceMin());
		}
		if (productDTO.getPriceMax() != null && productDTO.getPriceMax() > 0) {
			query.setParameter("priceMax", productDTO.getPriceMax());
		}
		if (productDTO.getName() != null && productDTO.getName() != "") {
			query.setParameter("name", '%' + productDTO.getName() + '%');
		}
		query.setFirstResult(at).setMaxResults(total);
		return query.getResultList();
	}

	/**
	 * count number of products found.
	 * 
	 * @param productDTO
	 * @return
	 */
	@Override
	public long countProducts(SearchProductDTO productDTO) {
		String hql = "SELECT count(p) FROM Product p";
		StringBuilder conds = new StringBuilder();
		boolean check = false;
		if (productDTO.getCategoryId() != null && productDTO.getCategoryId() > 0) {
			conds.append(" p.category.id = :category");
			check = true;
		}
		if (productDTO.getCondition() != null && productDTO.getCondition() > 0) {
			if (check) {
				conds.append(" AND");
			} else {
				check = true;
			}
			conds.append(" p.condition = :condition");

		}
		if (productDTO.getPriceMin() != null && productDTO.getPriceMin() > 0) {
			if (check) {
				conds.append(" AND");
			} else {
				check = true;
			}
			conds.append(" p.price*(100-p.discount)/100 >= :priceMin");
		}

		if (productDTO.getPriceMax() != null && productDTO.getPriceMax() > 0) {
			if (check) {
				conds.append(" AND");
			} else {
				check = true;
			}
			conds.append(" p.price*(100-p.discount)/100 <= :priceMax");
		}
		if (productDTO.getName() != null && productDTO.getName() != "") {
			if (check) {
				conds.append(" AND");
			} else {
				check = true;
			}
			conds.append(" p.name LIKE :name");
		}
		if (check) {
			conds.append(" AND");
		} else {
			check = true;
		}
		conds.append(" p.status != 0");
		String condsHql = conds.toString();
		condsHql = " WHERE" + condsHql;
		hql = hql + condsHql;
		System.out.println(hql);
		Query query = entityManager.createQuery(hql);
		if (productDTO.getCategoryId() != null && productDTO.getCategoryId() > 0) {
			query.setParameter("category", productDTO.getCategoryId());
		}
		if (productDTO.getCondition() != null && productDTO.getCondition() > 0) {
			query.setParameter("condition", productDTO.getCondition());
		}

		if (productDTO.getPriceMin() != null && productDTO.getPriceMin() > 0) {
			query.setParameter("priceMin", productDTO.getPriceMin());
		}
		if (productDTO.getPriceMax() != null && productDTO.getPriceMax() > 0) {
			query.setParameter("priceMax", productDTO.getPriceMax());
		}
		if (productDTO.getName() != null && productDTO.getName() != "") {
			query.setParameter("name", '%' + productDTO.getName() + '%');
		}
		try {
			return (long) query.getSingleResult();
		} catch (Exception e) {
			return 0;
		}

	}
}
