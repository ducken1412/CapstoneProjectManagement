package com.fpt.dto;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fpt.entity.Status;

public class ListLecturersDTO {
	private String id;
	
	private Date birthDate;
	
	private Date createdDate;
	
	private String description;
	
	private String email;
	private String encrytedPassword;
	private String firstName;
	private Integer gender;
	private String lastName;
	private String phone;
	private String userName;
    private Integer capstone_project_id;
    private String address;
    private Integer report_detail_id;
    private Integer report_receive_id;
    private Integer status_id;
    
    public ListLecturersDTO() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getCapstone_project_id() {
		return capstone_project_id;
	}

	public void setCapstone_project_id(Integer capstone_project_id) {
		this.capstone_project_id = capstone_project_id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getReport_detail_id() {
		return report_detail_id;
	}

	public void setReport_detail_id(Integer report_detail_id) {
		this.report_detail_id = report_detail_id;
	}

	public Integer getReport_receive_id() {
		return report_receive_id;
	}

	public void setReport_receive_id(Integer report_receive_id) {
		this.report_receive_id = report_receive_id;
	}

	public Integer getStatus_id() {
		return status_id;
	}

	public void setStatus_id(Integer status_id) {
		this.status_id = status_id;
	}

	public ListLecturersDTO(String id, Date birthDate, Date createdDate, String description, String email,
			String encrytedPassword, String firstName, Integer gender, String lastName, String phone, String userName,
			Integer capstone_project_id, String address, Integer report_detail_id, Integer report_receive_id,
			Integer status_id) {
		super();
		this.id = id;
		this.birthDate = birthDate;
		this.createdDate = createdDate;
		this.description = description;
		this.email = email;
		this.encrytedPassword = encrytedPassword;
		this.firstName = firstName;
		this.gender = gender;
		this.lastName = lastName;
		this.phone = phone;
		this.userName = userName;
		this.capstone_project_id = capstone_project_id;
		this.address = address;
		this.report_detail_id = report_detail_id;
		this.report_receive_id = report_receive_id;
		this.status_id = status_id;
	}

	public ListLecturersDTO(Date birthDate, Date createdDate, String description, String email, String encrytedPassword,
			String firstName, Integer gender, String lastName, String phone, String userName,
			Integer capstone_project_id, String address, Integer report_detail_id, Integer report_receive_id,
			Integer status_id) {
		super();
		this.birthDate = birthDate;
		this.createdDate = createdDate;
		this.description = description;
		this.email = email;
		this.encrytedPassword = encrytedPassword;
		this.firstName = firstName;
		this.gender = gender;
		this.lastName = lastName;
		this.phone = phone;
		this.userName = userName;
		this.capstone_project_id = capstone_project_id;
		this.address = address;
		this.report_detail_id = report_detail_id;
		this.report_receive_id = report_receive_id;
		this.status_id = status_id;
	}
    
	
    
       
}
