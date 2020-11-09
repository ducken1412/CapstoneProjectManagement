package com.fpt.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Evaluations")
public class Evaluations {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT")
	private Integer id;
	@Column(name = "name", columnDefinition = "NVARCHAR(50) NOT NULL")
	private String name;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "evaluation")
	private List<Files> files;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "evaluation")
	private List<HistoryRecords> historyRecords;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "capstone_project_id")
	private CapstoneProjects capstoneProject;
	@Column(name = "type", columnDefinition = "NVARCHAR(50) NOT NULL")
	private String type;
	@OneToOne(mappedBy = "evaluation")
	private EvaluationDetails evaluationDetail;

	public Evaluations() {
		super();
	}

	public Evaluations(String name, List<Files> files, List<HistoryRecords> historyRecords,
			CapstoneProjects capstoneProject, String type, EvaluationDetails evaluationDetail) {
		super();
		this.name = name;
		this.files = files;
		this.historyRecords = historyRecords;
		this.capstoneProject = capstoneProject;
		this.type = type;
		this.evaluationDetail = evaluationDetail;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Files> getFiles() {
		return files;
	}

	public void setFiles(List<Files> files) {
		this.files = files;
	}

	public List<HistoryRecords> getHistoryRecords() {
		return historyRecords;
	}

	public void setHistoryRecords(List<HistoryRecords> historyRecords) {
		this.historyRecords = historyRecords;
	}

	public CapstoneProjects getCapstoneProject() {
		return capstoneProject;
	}

	public void setCapstoneProject(CapstoneProjects capstoneProject) {
		this.capstoneProject = capstoneProject;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public EvaluationDetails getEvaluationDetail() {
		return evaluationDetail;
	}

	public void setEvaluationDetail(EvaluationDetails evaluationDetail) {
		this.evaluationDetail = evaluationDetail;
	}
}
