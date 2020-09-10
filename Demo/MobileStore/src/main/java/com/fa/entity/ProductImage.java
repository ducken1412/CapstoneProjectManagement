package com.fa.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Product_Image")
public class ProductImage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT")
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(columnDefinition = "NOT NULL")
	private Product product;

	@Column(name = "path", columnDefinition = "NVARCHAR(500) NOT NULL")
	private String path;

	public ProductImage() {
		super();
	}

	public ProductImage(Integer id, Product product, String path) {
		super();
		this.id = id;
		this.product = product;
		this.path = path;
	}
	
	public ProductImage(Product product, String path) {
		super();
		this.product = product;
		this.path = path;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
