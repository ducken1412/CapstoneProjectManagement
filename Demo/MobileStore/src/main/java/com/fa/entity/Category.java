package com.fa.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Category")
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT")
	private Integer id;

	@NotEmpty(message = "Name is required")
	@Size(min = 5, max = 32, message = "Username is 5 - 32 character")
	@Column(name = "name", columnDefinition = "NVARCHAR(50) NOT NULL")
	private String name;

	@NotEmpty(message = "Name is required")
	@Size(min = 10, max = 120, message = "Password is 10 - 120 character")
	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@Column(name = "created_date", columnDefinition = "DATETIME NOT NULL")
	private Date createDate;

	@Column(name = "update_time", columnDefinition = "DATETIME")
	private Date updateTime;

	@Column(name = "status", columnDefinition = "INT NOT NULL")
	private Integer status;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(columnDefinition = "NOT NULL")
	private Users createdBy;

	@ManyToOne(fetch = FetchType.EAGER)
	private Users updatedBy;

	public Category() {
		super();
	}

	public Category(Integer id, String name, String description, Integer status) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.status = status;
	}

	public Category(Integer id, String name, String description, Date createDate, Date updateTime, Integer status,
			Users createdBy, Users updatedBy) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.createDate = createDate;
		this.updateTime = updateTime;
		this.status = status;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
