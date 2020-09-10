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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "[Users]")
public class Users implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT")
	private Integer id;

	@NotEmpty(message = "Username is required")
	@Size(min = 5, max = 32, message = "Username is 5 - 32 character")
	@Column(name = "user_name", columnDefinition = "NVARCHAR(50) NOT NULL")
	private String userName;

	@NotEmpty(message = "Password is required")
	@Size(min = 8, max = 256, message = "Password is 8 - 256 character")
	@Column(name = "encryted_password", columnDefinition = "NVARCHAR(256) NOT NULL")
	private String encrytedPassword;

	@Column(name = "first_name", columnDefinition = "NVARCHAR(30)")
	private String firstName;

	@Column(name = "last_name", columnDefinition = "NVARCHAR(30)")
	private String lastName;

	@Column(name = "birth_date", columnDefinition = "DATE")
	private Date birthDate;

	@Column(name = "gender", columnDefinition = "INT")
	private Integer gender;

	@Column(name = "phone", columnDefinition = "VARCHAR(15)")
	private String phone;

	@NotEmpty(message = "Email is required")
	@Column(name = "email", columnDefinition = "NVARCHAR(64) NOT NULL")
	private String email;

	@Column(name = "address", columnDefinition = "NVARCHAR(256)")
	private String address;

	@Column(name = "status", columnDefinition = "INT NOT NULL")
	private Integer status;

	@Column(name = "created_date", columnDefinition = "DATETIME NOT NULL")
	private Date createDate;

	@Column(name = "update_time", columnDefinition = "DATETIME")
	private Date updateTime;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "roleUserKey.user")
	private List<RoleUser> roleUser;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "createdBy")
	private List<Category> categoryList;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "updatedBy")
	private List<Category> updateCategoryList;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "createdBy")
	private List<Product> ProductList;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "updatedBy")
	private List<Product> updateProductList;

	public Users() {
		super();
	}

	public Users(String userName, String firstName, String lastName, Date birthDate, Integer gender, String phone,
			String email, String address, Date updateTime) {
		super();
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.gender = gender;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.updateTime = updateTime;
	}

	public Users(Integer id, String userName, String encrytedPassword, String firstName, String lastName,
			Date birthDate, Integer gender, String phone, String email, String address, Integer status, Date createDate,
			Date updateTime, List<RoleUser> roleUser) {
		super();
		this.id = id;
		this.userName = userName;
		this.encrytedPassword = encrytedPassword;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.gender = gender;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.status = status;
		this.createDate = createDate;
		this.updateTime = updateTime;
		this.roleUser = roleUser;
	}

	public Users(Integer id, String userName, String encrytedPassword, String firstName, String lastName,
			Date birthDate, Integer gender, String phone, String email, String address, Integer status, Date createDate,
			Date updateTime) {
		super();
		this.id = id;
		this.userName = userName;
		this.encrytedPassword = encrytedPassword;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.gender = gender;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.status = status;
		this.createDate = createDate;
		this.updateTime = updateTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEncrytedPassword() {
		return encrytedPassword;
	}

	public void setEncrytedPassword(String encrytedPassword) {
		this.encrytedPassword = encrytedPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public List<RoleUser> getRoleUser() {
		return roleUser;
	}

	public void setRoleUser(List<RoleUser> roleUser) {
		this.roleUser = roleUser;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public List<Category> getUpdateCategoryList() {
		return updateCategoryList;
	}

	public void setUpdateCategoryList(List<Category> updateCategoryList) {
		this.updateCategoryList = updateCategoryList;
	}

	@Override
	public String toString() {
		return "Users [id=" + id + ", userName=" + userName + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", birthDate=" + birthDate + ", gender=" + gender + ", phone=" + phone + ", email=" + email
				+ ", address=" + address + ", status=" + status + ", createdDate=" + createDate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Users other = (Users) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

}
