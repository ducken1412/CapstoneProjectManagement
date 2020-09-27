package com.fpt.entity;

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

@Entity
@Table(name = "[HistoryRecords]")
public class HistoryRecords {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT")
	private Integer id;
	@ManyToOne(fetch = FetchType.EAGER)
	private Users user;
	@Column(name = "created_date", columnDefinition = "DATETIME")
	private Date createdDate;
	@Column(name = "last_modified_date", columnDefinition = "DATETIME")
	private Date lastModifiedDate;
	@Column(name = "content", columnDefinition = "NVARCHAR(256) NOT NULL")
	private String content;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "capstone_project_id")
	private CapstoneProjects capstoneProject;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "report_id")
	private Reports report;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "evaluation_id")
	private Evaluations evaluation;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "post_id")
	private Posts post;

	public HistoryRecords() {
		super();
	}

	public HistoryRecords(Integer id, Users user, Date createdDate, Date lastModifiedDate, String content,
			CapstoneProjects capstoneProject, Reports report, Evaluations evaluation, Posts post) {
		super();
		this.id = id;
		this.user = user;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.content = content;
		this.capstoneProject = capstoneProject;
		this.report = report;
		this.evaluation = evaluation;
		this.post = post;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
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

	public CapstoneProjects getCapstoneProject() {
		return capstoneProject;
	}

	public void setCapstoneProject(CapstoneProjects capstoneProject) {
		this.capstoneProject = capstoneProject;
	}

	public Reports getReport() {
		return report;
	}

	public void setReport(Reports report) {
		this.report = report;
	}

	public Evaluations getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Evaluations evaluation) {
		this.evaluation = evaluation;
	}

	public Posts getPost() {
		return post;
	}

	public void setPost(Posts post) {
		this.post = post;
	}

}
