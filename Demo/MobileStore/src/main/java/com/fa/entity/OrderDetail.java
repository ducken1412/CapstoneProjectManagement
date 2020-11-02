package com.fa.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "OrderDetail")
public class OrderDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OrderDetailKey orderDetailKey;

	@Column(name = "quantity", columnDefinition = "INT NOT NULL")
	private Integer quantity;

	@Column(name = "price", columnDefinition = "FLOAT NOT NULL")
	private float price;

	@Column(name = "discount", columnDefinition = "INT NOT NULL")
	private Integer discount;

	public OrderDetail() {
		super();
	}

	public OrderDetail(OrderDetailKey orderDetailKey, Integer quantity, float price, Integer discount) {
		super();
		this.orderDetailKey = orderDetailKey;
		this.quantity = quantity;
		this.price = price;
		this.discount = discount;
	}

	public OrderDetailKey getOrderDetailKey() {
		return orderDetailKey;
	}

	public void setOrderDetailKey(OrderDetailKey orderDetailKey) {
		this.orderDetailKey = orderDetailKey;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
