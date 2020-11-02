package com.fa.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fa.entity.RoleUser;
import com.fa.entity.Users;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDTO {
	@JsonProperty("id")
	@NotNull
	private Integer id;

	@NotBlank(message = "Username must be not contained blank")
	@Size(min = 5, max = 32, message = "Username is 5 - 32 character")
	private String userName;

	private String encrytedPassword;

	@NotBlank(message = "First name must be not contained blank")
	@Size(min = 1, max = 30, message = "First name maximum is 30 character")
	private String firstName;

	@NotBlank(message = "Last name must be not contained blank")
	@Size(min = 1, max = 30, message = "Last name maximum is 30 character")
	private String lastName;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthDate;

	private Integer gender;

	@NotBlank(message = "Phone must be not contained blank")
	@Size(min = 8, max = 15, message = "Phone is 8 - 15 character")
	private String phone;

	@Email(message = "Email must be not contained blank")
	@NotBlank(message = "Email must be not contained blank")
	@Size(min = 8, max = 64, message = "Email is 5 - 32 character")
	private String email;

	@NotBlank(message = "Address must be not contained blank")
	@Size(min = 1, max = 256, message = "Address maximum is 32 character")
	private String address;

	private Integer status;
	private Date createDate;
	private Date updateTime;
	private List<RoleUser> roleUser;

	public UserDTO(Integer id, String userName, String encrytedPassword, String firstName, String lastName,
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

	public UserDTO(Users u) {
		super();
		this.id = u.getId();
		this.userName = u.getUserName();
		this.encrytedPassword = u.getEncrytedPassword();
		this.firstName = u.getFirstName();
		this.lastName = u.getLastName();
		this.birthDate = u.getBirthDate();
		this.gender = u.getGender();
		this.phone = u.getPhone();
		this.email = u.getEmail();
		this.address = u.getAddress();
		this.status = u.getStatus();
		this.createDate = u.getCreateDate();
		this.updateTime = u.getUpdateTime();
		this.roleUser = u.getRoleUser();
	}

	public UserDTO() {
		super();
		// TODO Auto-generated constructor stub
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((encrytedPassword == null) ? 0 : encrytedPassword.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((roleUser == null) ? 0 : roleUser.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
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
		UserDTO other = (UserDTO) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
			return false;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (encrytedPassword == null) {
			if (other.encrytedPassword != null)
				return false;
		} else if (!encrytedPassword.equals(other.encrytedPassword))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (roleUser == null) {
			if (other.roleUser != null)
				return false;
		} else if (!roleUser.equals(other.roleUser))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", userName=" + userName + ", encrytedPassword=" + encrytedPassword
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", birthDate=" + birthDate + ", gender="
				+ gender + ", phone=" + phone + ", email=" + email + ", address=" + address + ", status=" + status
				+ ", createDate=" + createDate + ", updateTime=" + updateTime + "]";
	}

	

}
