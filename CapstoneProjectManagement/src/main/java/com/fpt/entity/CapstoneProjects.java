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
import javax.persistence.Table;

@Entity
@Table(name = "[CapstoneProjects]")
public class CapstoneProjects {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT")
	private Integer id;
	@Column(name = "name", columnDefinition = "NVARCHAR(256) NOT NULL")
	private String name;
	@Column(name = "name_lang_other", columnDefinition = "NVARCHAR(256)")
	private String nameOther;
	@Column(name = "name_vi", columnDefinition = "NVARCHAR(256)")
	private String nameVi;
	@Column(name = "name_abbreviation", columnDefinition = "NVARCHAR(256)")
	private String nameAbbreviation;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profession_id")
	private Profession profession;
	@Column(name = "specialty", columnDefinition = "NVARCHAR(256) NOT NULL")
	private String specialty;
	@Column(name = "document", columnDefinition = "NVARCHAR(256) NOT NULL")
	private String document;
	@Column(name = "program", columnDefinition = "NVARCHAR(256) NOT NULL")
	private String program;
	@Column(name = "description", columnDefinition = "NVARCHAR(256) NOT NULL")
	private String description;
	@Column(name = "name_changing", columnDefinition = "NVARCHAR(256)")
	private String nameChanging;
	@Column(name = "name_vi_changing", columnDefinition = "NVARCHAR(256)")
	private String nameViChanging;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_id")
	private Status status;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "capstoneProject")
	private List<HistoryRecords> historyRecords;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "capstoneProject")
	private List<Statistics> statistics;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "capstoneProject")
	private List<Files> files;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "capstoneProject")
	private List<CapstoneProjectDetails> capstoneProjectDetails;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "capstoneProject")
	private List<Evaluations> evaluations;
	@Column(name = "description_action", columnDefinition = "longtext")
	private String desAction;
	@ManyToOne
	@JoinColumn(name = "semester_id")
	private Semesters semester;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "site_id")
	private Sites site;

	public CapstoneProjects() {
		super();
	}

	public CapstoneProjects(Integer id, String name, String nameOther, String nameVi, String nameAbbreviation, Profession profession, String specialty, String document, String program, String description, Status status, List<HistoryRecords> historyRecords, List<Statistics> statistics, List<Files> files, List<CapstoneProjectDetails> capstoneProjectDetails, List<Evaluations> evaluations, String desAction) {
		this.id = id;
		this.name = name;
		this.nameOther = nameOther;
		this.nameVi = nameVi;
		this.nameAbbreviation = nameAbbreviation;
		this.profession = profession;
		this.specialty = specialty;
		this.document = document;
		this.program = program;
		this.description = description;
		this.status = status;
		this.historyRecords = historyRecords;
		this.statistics = statistics;
		this.files = files;
		this.capstoneProjectDetails = capstoneProjectDetails;
		this.evaluations = evaluations;
		this.desAction = desAction;
	}

	public CapstoneProjects(Integer id, String name, String nameOther, String nameVi, String nameAbbreviation, Profession profession, String specialty, String document, String program, String description, Status status, List<HistoryRecords> historyRecords, List<Statistics> statistics, List<Files> files, List<CapstoneProjectDetails> capstoneProjectDetails, List<Evaluations> evaluations, String desAction, Semesters semester, Sites site) {
		this.id = id;
		this.name = name;
		this.nameOther = nameOther;
		this.nameVi = nameVi;
		this.nameAbbreviation = nameAbbreviation;
		this.profession = profession;
		this.specialty = specialty;
		this.document = document;
		this.program = program;
		this.description = description;
		this.status = status;
		this.historyRecords = historyRecords;
		this.statistics = statistics;
		this.files = files;
		this.capstoneProjectDetails = capstoneProjectDetails;
		this.evaluations = evaluations;
		this.desAction = desAction;
		this.semester = semester;
		this.site = site;
	}

	public CapstoneProjects(Integer id, String name, String nameOther, String nameVi, String nameAbbreviation, Profession profession, String specialty, String document, String program, String description, String nameChanging, String nameViChanging, Status status, List<HistoryRecords> historyRecords, List<Statistics> statistics, List<Files> files, List<CapstoneProjectDetails> capstoneProjectDetails, List<Evaluations> evaluations, String desAction, Semesters semester, Sites site) {
		this.id = id;
		this.name = name;
		this.nameOther = nameOther;
		this.nameVi = nameVi;
		this.nameAbbreviation = nameAbbreviation;
		this.profession = profession;
		this.specialty = specialty;
		this.document = document;
		this.program = program;
		this.description = description;
		this.nameChanging = nameChanging;
		this.nameViChanging = nameViChanging;
		this.status = status;
		this.historyRecords = historyRecords;
		this.statistics = statistics;
		this.files = files;
		this.capstoneProjectDetails = capstoneProjectDetails;
		this.evaluations = evaluations;
		this.desAction = desAction;
		this.semester = semester;
		this.site = site;
	}



	public List<Statistics> getStatistics() {
		return statistics;
	}

	public void setStatistics(List<Statistics> statistics) {
		this.statistics = statistics;
	}

	public String getDesAction() {
		return desAction;
	}

	public void setDesAction(String desAction) {
		this.desAction = desAction;
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

	public String getNameOther() {
		return nameOther;
	}

	public void setNameOther(String nameOther) {
		this.nameOther = nameOther;
	}

	public String getNameVi() {
		return nameVi;
	}

	public void setNameVi(String nameVi) {
		this.nameVi = nameVi;
	}

	public String getNameAbbreviation() {
		return nameAbbreviation;
	}

	public void setNameAbbreviation(String nameAbbreviation) {
		this.nameAbbreviation = nameAbbreviation;
	}

	public Profession getProfession() {
		return profession;
	}

	public void setProfession(Profession profession) {
		this.profession = profession;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
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

	public List<HistoryRecords> getHistoryRecords() {
		return historyRecords;
	}

	public void setHistoryRecords(List<HistoryRecords> historyRecords) {
		this.historyRecords = historyRecords;
	}

	public List<Files> getFiles() {
		return files;
	}

	public void setFiles(List<Files> files) {
		this.files = files;
	}

	public List<CapstoneProjectDetails> getCapstoneProjectDetails() {
		return capstoneProjectDetails;
	}

	public void setCapstoneProjectDetails(List<CapstoneProjectDetails> capstoneProjectDetails) {
		this.capstoneProjectDetails = capstoneProjectDetails;
	}

	public List<Evaluations> getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(List<Evaluations> evaluations) {
		this.evaluations = evaluations;
	}

	public Semesters getSemester() {
		return semester;
	}

	public void setSemester(Semesters semester) {
		this.semester = semester;
	}

	public Sites getSite() {
		return site;
	}

	public void setSite(Sites site) {
		this.site = site;
	}

	public String getNameChanging() {
		return nameChanging;
	}

	public void setNameChanging(String nameChanging) {
		this.nameChanging = nameChanging;
	}

	public String getNameViChanging() {
		return nameViChanging;
	}

	public void setNameViChanging(String nameViChanging) {
		this.nameViChanging = nameViChanging;
	}
}
