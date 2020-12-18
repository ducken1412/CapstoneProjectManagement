package com.fpt.dto;

import java.util.Date;
import java.util.List;
import com.fpt.entity.CapstoneProjects;
import com.fpt.entity.Reports;
import com.fpt.entity.Status;


public class UserDTO {

	private String id;
	private String userName;
	private String firstName;
	private String lastName;
	private Date birthDate;
	private Integer gender;
	private String phone;
	private String email;
	private Date createdDate;
	private String address;
	private String description;
	private Status status;
	private CapstoneProjects capstoneProject;
	private Reports reportReceive;
	private List<Reports> reportReceives;

	public UserDTO() {
	}

	public UserDTO(String id, String userName, String firstName, String lastName, Date birthDate, Integer gender, String phone, String email, Date createdDate, String address, String description, Status status, CapstoneProjects capstoneProject, Reports reportReceive, List<Reports> reportReceives) {
		this.id = id;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.gender = gender;
		this.phone = phone;
		this.email = email;
		this.createdDate = createdDate;
		this.address = address;
		this.description = description;
		this.status = status;
		this.capstoneProject = capstoneProject;
		this.reportReceive = reportReceive;
		this.reportReceives = reportReceives;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public CapstoneProjects getCapstoneProject() {
		return capstoneProject;
	}

	public void setCapstoneProject(CapstoneProjects capstoneProject) {
		this.capstoneProject = capstoneProject;
	}

	public Reports getReportReceive() {
		return reportReceive;
	}

	public void setReportReceive(Reports reportReceive) {
		this.reportReceive = reportReceive;
	}

	public List<Reports> getReportReceives() {
		return reportReceives;
	}

	public void setReportReceives(List<Reports> reportReceives) {
		this.reportReceives = reportReceives;
	}
}
