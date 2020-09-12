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
	@Column(name = "created_date", columnDefinition = "DATE")
	private Date createdDate;
	@Column(name = "last_modified_date", columnDefinition = "DATE")
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
}
