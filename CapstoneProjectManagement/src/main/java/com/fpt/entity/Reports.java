package com.fpt.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "Reports")
public class Reports {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT")
	private Integer id;
	@Column(name = "title", columnDefinition = "NVARCHAR(256) NOT NULL")
	private String title;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "report")
	private List<HistoryRecords> historyRecords;
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "report_details", joinColumns = { @JoinColumn(name = "report_id") }, inverseJoinColumns = {
			@JoinColumn(name = "user_id") })
	private List<Users> reportRecipients;
	@Column(name = "type", columnDefinition = "NVARCHAR(50) NOT NULL")
	private String type;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "report")
	private List<Files> files;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "report")
	private List<Comments> comments;
	@Column(name = "created_date", columnDefinition = "DATETIME NOT NULL")
	private Date createdDate;
	@Column(name = "content", columnDefinition = "longtext NOT NULL")
	private String content;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by")
	private Users user;

	public Reports() {
	}

	public Reports(Integer id, String title, List<HistoryRecords> historyRecords, List<Users> reportRecipients, String type, List<Files> files, List<Comments> comments, Date createdDate, String content, Users user) {
		this.id = id;
		this.title = title;
		this.historyRecords = historyRecords;
		this.reportRecipients = reportRecipients;
		this.type = type;
		this.files = files;
		this.comments = comments;
		this.createdDate = createdDate;
		this.content = content;
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<HistoryRecords> getHistoryRecords() {
		return historyRecords;
	}

	public void setHistoryRecords(List<HistoryRecords> historyRecords) {
		this.historyRecords = historyRecords;
	}

	public List<Users> getReportRecipients() {
		return reportRecipients;
	}

	public void setReportRecipients(List<Users> reportRecipients) {
		this.reportRecipients = reportRecipients;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
}
