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
@Table(name = "[Comments]")
public class Comments {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT")
	private Integer id;
	@Column(name = "content", columnDefinition = "NVARCHAR(256) NOT NULL")
	private String content;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sender_id")
	private Users sender;
	@Column(name = "created_date", columnDefinition = "DATETIME NOT NULL")
	private Date createdDate;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "report_detail_id")
	private ReportDetails reportDetail;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "post_id")
	private Posts post ;
	
	public Comments() {
		super();
	}

	public Comments(String content, Users sender, Date createdDate, ReportDetails reportDetail, Posts post) {
		super();
		this.content = content;
		this.sender = sender;
		this.createdDate = createdDate;
		this.reportDetail = reportDetail;
		this.post = post;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Users getSender() {
		return sender;
	}

	public void setSender(Users sender) {
		this.sender = sender;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public ReportDetails getReportDetail() {
		return reportDetail;
	}

	public void setReportDetail(ReportDetails reportDetail) {
		this.reportDetail = reportDetail;
	}

	public Posts getPost() {
		return post;
	}

	public void setPost(Posts post) {
		this.post = post;
	}

	
}
