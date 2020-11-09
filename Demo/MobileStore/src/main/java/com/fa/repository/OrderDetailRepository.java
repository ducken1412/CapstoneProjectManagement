package com.fa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fa.entity.OrderDetail;
import com.fa.entity.OrderDetailKey;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailKey> {

}
