package com.fpt.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "[ReportDetails]")
public class ReportDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT")
	private Integer id;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "report_id", referencedColumnName = "id")
	private Reports report;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "reportDetail")
	private List<Files> files;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "reportDetail")
	private List<Comments> comments;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "reportDetail")
	private List<Users> members;
	@Column(name = "created_date", columnDefinition = "DATETIME NOT NULL")
	private Date createdDate;
	@Column(name = "last_modified_date", columnDefinition = "DATETIME NOT NULL")
	private Date lastModifiedDate;
	@Column(name = "content", columnDefinition = "longtext NOT NULL")
	private String content;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Created_by")
	private Users user;

	public ReportDetails() {
		super();
	}

	public ReportDetails(Integer id, Reports report, List<Files> files, List<Comments> comments, List<Users> members, Date createdDate, Date lastModifiedDate, String content, Users user) {
		this.id = id;
		this.report = report;
		this.files = files;
		this.comments = comments;
		this.members = members;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.content = content;
		this.user = user;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Reports getReport() {
		return report;
	}

	public void setReport(Reports report) {
		this.report = report;
	}

	public List<Files> getFiles() {
		return files;
	}

	public void setFiles(List<Files> files) {
		this.files = files;
	}

	public List<Comments> getComments() {
		return comments;
	}

	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}

	public List<Users> getMembers() {
		return members;
	}

	public void setMembers(List<Users> members) {
		this.members = members;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
