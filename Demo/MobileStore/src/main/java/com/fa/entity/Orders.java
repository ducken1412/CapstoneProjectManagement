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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "[Orders]")
public class Orders implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT")
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER)
	private Users customer;

	@Column(name = "address", columnDefinition = "NVARCHAR(256) NOT NULL")
	private String address;

	@Column(name = "phone", columnDefinition = "VARCHAR(15) NOT NULL")
	private String phone;

	@Column(name = "email", columnDefinition = "NVARCHAR(50)")
	private String email;

	@Column(name = "customer_name", columnDefinition = "NVARCHAR(256) NOT NULL")
	private String customerName;

	@Column(name = "created_date", columnDefinition = "DATETIME NOT NULL")
	private Date createdDate;

	@Column(name = "updated_time", columnDefinition = "DATETIME")
	private Date updatedTime;

	@Column(name = "status", columnDefinition = "INT DEFAULT 0 NOT NULL")
	private Integer status;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "orderDetailKey.orders")
	private List<OrderDetail> orderDetailList;

	public Orders() {
		super();
	}

	public Orders(Users customer, String address, String phone, String email, String customerName, Date createdDate,
			Date updatedTime, Integer status) {
		super();
		this.customer = customer;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.customerName = customerName;
		this.createdDate = createdDate;
		this.updatedTime = updatedTime;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Users getCustomer() {
		return customer;
	}

	public void setCustomer(Users customer) {
		this.customer = customer;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

}
