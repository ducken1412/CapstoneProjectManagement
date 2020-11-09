package com.fpt.entity;

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
@Table(name = "[Files]")
public class Files {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT")
	private int id;
	@Column(name = "path", columnDefinition = "NVARCHAR(256)")
	private String path;
	@Column(name = "file_name", columnDefinition = "NVARCHAR(256)")
	private String fileName;
	@Column(name = "description", columnDefinition = "NVARCHAR(256)")
	private String description;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "capstone_project_id")
	private CapstoneProjects capstoneProject;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "report_detail_id")
	private ReportDetails reportDetail;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "evaluation_id")
	private Evaluations evaluation;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Posts post;

	public Files() {
		super();
	}

	public Files(String path,String fileName, String description, CapstoneProjects capstoneProject, ReportDetails reportDetail,
			Evaluations evaluation, Posts post) {
		super();
		this.path = path;
		this.description = description;
		this.capstoneProject = capstoneProject;
		this.reportDetail = reportDetail;
		this.evaluation = evaluation;
		this.post = post;
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CapstoneProjects getCapstoneProject() {
		return capstoneProject;
	}

	public void setCapstoneProject(CapstoneProjects capstoneProject) {
		this.capstoneProject = capstoneProject;
	}

	public ReportDetails getReportDetail() {
		return reportDetail;
	}

	public void setReportDetail(ReportDetails reportDetail) {
		this.reportDetail = reportDetail;
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
