package com.fpt.dto;

import java.util.Date;
import java.util.List;
import com.fpt.entity.CapstoneProjects;
import com.fpt.entity.Locations;
import com.fpt.entity.ReportDetails;
import com.fpt.entity.Reports;
import com.fpt.entity.Status;
import com.fpt.entity.Users;


public class UserDTO {

	private String id;

	private String userName;

	private String encrytedPassword;

	private String firstName;

	private String lastName;

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
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Locations getLocation() {
		return location;
	}
	public void setLocation(Locations location) {
		this.location = location;
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
	public ReportDetails getReportDetail() {
		return reportDetail;
	}
	public void setReportDetail(ReportDetails reportDetail) {
		this.reportDetail = reportDetail;
	}
	public UserDTO(String id, String userName, String encrytedPassword, String firstName, String lastName,
			Date birthDate, Integer gender, String phone, String email, Date createdDate, Locations location,
			String description, Status status, CapstoneProjects capstoneProject, Reports reportReceive,
			List<Reports> reportReceives, ReportDetails reportDetail) {
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
		this.createdDate = createdDate;
		this.location = location;
		this.description = description;
		this.status = status;
		this.capstoneProject = capstoneProject;
		this.reportReceive = reportReceive;
		this.reportReceives = reportReceives;
		this.reportDetail = reportDetail;
	}
	public UserDTO(String userName, String encrytedPassword, String firstName, String lastName,
			Date birthDate, Integer gender, String phone, String email, Date createdDate, Locations location,
			String description, Status status, CapstoneProjects capstoneProject, Reports reportReceive,
			List<Reports> reportReceives, ReportDetails reportDetail) {
		super();
		this.userName = userName;
		this.encrytedPassword = encrytedPassword;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.gender = gender;
		this.phone = phone;
		this.email = email;
		this.createdDate = createdDate;
		this.location = location;
		this.description = description;
		this.status = status;
		this.capstoneProject = capstoneProject;
		this.reportReceive = reportReceive;
		this.reportReceives = reportReceives;
		this.reportDetail = reportDetail;
	}
	
	private Date birthDate;

	private Integer gender;

	public UserDTO() {
		super();
	}
	
	private String phone;

	private String email;

	private Date createdDate;

	private Locations location;
	private String description;
	private Status status;
	private CapstoneProjects capstoneProject;
	private Reports reportReceive;
	private List<Reports> reportReceives;
	private ReportDetails reportDetail;

}
