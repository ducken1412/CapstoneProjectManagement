package com.fa.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fa.entity.Users;
import com.fa.validator.NotSepecialCharConstraint;

public class OrdersDTO {

	private Integer id;
	
	private Users customer;
	@NotBlank
	@Length(min = 3,max = 256)
	private String address;
	@NotBlank
	private String phone;
	
	@Email
	private String email;
	@NotSepecialCharConstraint
	@NotBlank
	private String customerName;

	private Date createdDate;

	private Date updatedTime;

	private Integer status;

	public OrdersDTO() {

	}

	public OrdersDTO(Integer id, Users customer, String address, String phone, String email, String customerName,
			Date createdDate, Date updatedTime, Integer status) {
		super();
		this.id = id;
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

	@Override
	public String toString() {
		return "OrdersDTO [address=" + address + ", phone=" + phone + ", email=" + email + ", customerName="
				+ customerName + "]";
	}

}
