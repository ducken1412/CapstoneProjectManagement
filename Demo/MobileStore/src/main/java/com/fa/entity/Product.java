package com.fa.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Product")
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT")
	private Integer id;

	@Column(name = "name", columnDefinition = "NVARCHAR(100) NOT NULL")
	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(columnDefinition = "NOT NULL")
	private Category category;

	@Column(name = "description", columnDefinition = "NVARCHAR(MAX)")
	private String description;

	@Column(name = "price", columnDefinition = "FLOAT NOT NULL")
	private float price;

	@Column(name = "discount", columnDefinition = "INT DEFAULT 0 NOT NULL")
	private Integer discount;

	@Column(name = "quantity", columnDefinition = "INT NOT NULL")
	private Integer quantity;

	@Column(name = "manufacturer", columnDefinition = "NVARCHAR(100) NOT NULL")
	private String manufacturer;

	@Column(name = "condition", columnDefinition = "INT NOT NULL")
	private Integer condition;

	@Column(name = "created_date", columnDefinition = "DATETIME NOT NULL")
	private Date createdDate;

	@Column(name = "update_time", columnDefinition = "DATETIME")
	private Date updateTime;

	@Column(name = "status", columnDefinition = "INT NOT NULL")
	private Integer status;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(columnDefinition = "NOT NULL")
	private Users createdBy;

	@ManyToOne(fetch = FetchType.EAGER)
	private Users updatedBy;

	@Column(name = "image", columnDefinition = "NVARCHAR(500)")
	private String imageMain;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "product")
	private List<ProductImage> productImageList;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "orderDetailKey.product")
	private List<OrderDetail> orderDetailList;

	public Product() {
		super();
	}

	

	public Product(Integer id, String name, Category category, String description, float price, Integer discount,
			Integer quantity, String manufacturer, Integer condition, Date createdDate, Date updateTime, Integer status,
			Users createdBy, Users updatedBy, String imageMain, List<ProductImage> productImageList,
			List<OrderDetail> orderDetailList) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.description = description;
		this.price = price;
		this.discount = discount;
		this.quantity = quantity;
		this.manufacturer = manufacturer;
		this.condition = condition;
		this.createdDate = createdDate;
		this.updateTime = updateTime;
		this.status = status;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.imageMain = imageMain;
		this.productImageList = productImageList;
		this.orderDetailList = orderDetailList;
	}



	public Product(String name, Category category, String description, float price, Integer discount, Integer quantity,
			String manufacturer, Integer condition, Date createdDate, Date updateTime,Integer status, Users createdBy, Users updatedBy,
			String imageMain) {
		super();
		this.name = name;
		this.category = category;
		this.description = description;
		this.price = price;
		this.discount = discount;
		this.quantity = quantity;
		this.manufacturer = manufacturer;
		this.condition = condition;
		this.createdDate = createdDate;
		this.updateTime = updateTime;
		this.status = status;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.imageMain = imageMain;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Integer getCondition() {
		return condition;
	}

	public void setCondition(Integer condition) {
		this.condition = condition;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Users getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Users createdBy) {
		this.createdBy = createdBy;
	}

	public Users getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Users updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getImageMain() {
		return imageMain;
	}

	public void setImageMain(String imageMain) {
		this.imageMain = imageMain;
	}

	public List<ProductImage> getProductImageList() {
		return productImageList;
	}

	public void setProductImageList(List<ProductImage> productImageList) {
		this.productImageList = productImageList;
	}

	public List<OrderDetail> getOrderDetailList() {
		return orderDetailList;
	}

	public void setOrderDetailList(List<OrderDetail> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
