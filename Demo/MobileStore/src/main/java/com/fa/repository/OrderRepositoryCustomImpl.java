package com.fa.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.fa.dto.SearchOrderDTO;
import com.fa.entity.Orders;

@Repository
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Multi search orders by
	 * 
	 * @param search
	 * @return
	 */
	@Override
	public Page<Orders> searchOrders(SearchOrderDTO search, int total) {
		int at = (search.getPage() - 1) * total;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Orders> query = builder.createQuery(Orders.class);
		Root<Orders> root = query.from(Orders.class);
		query.select(root);

		List<Predicate> predicates = new ArrayList<Predicate>();
		if (search.getOrderId() != null) {
			predicates.add(builder.equal(root.get("id"), search.getOrderId()));
		}

		if (search.getFromDate() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("createdDate"), search.getFromDate()));
		}

		if (search.getToDate() != null) {
			predicates.add(builder.lessThan(root.get("createdDate"), search.getToDate()));
		}

		if (search.getStatus() != null) {
			predicates.add(builder.equal(root.get("status"), search.getStatus()));
		}

		Predicate conditon = builder.and(predicates.toArray(new Predicate[predicates.size()]));
		query.where(conditon);
		query.orderBy(builder.desc(root.get("id")));
		TypedQuery<Orders> typedQuery = entityManager.createQuery(query);
		typedQuery.setFirstResult(at);
		typedQuery.setMaxResults(total);
		List<Orders> listOrders = typedQuery.getResultList();

		CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
		countQuery.select(builder.count(countQuery.from(Orders.class)));
		countQuery.where(conditon);

		Long count = entityManager.createQuery(countQuery).getSingleResult();
		Pageable pageable = PageRequest.of(1, 1);
		Page<Orders> page = new PageImpl<>(listOrders, pageable, count);
		return page;
	}
}
