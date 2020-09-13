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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "[CapstoneProjects]")
public class CapstoneProjects {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT")
	private Integer id;
	@Column(name = "name", columnDefinition = "NVARCHAR(100) NOT NULL")
	private String name;
	@Column(name = "profession", columnDefinition = "NVARCHAR(10) NOT NULL")
	private String profession;
	@Column(name = "specialty", columnDefinition = "NVARCHAR(10) NOT NULL")
	private String specialty;
	@Column(name = "document", columnDefinition = "NVARCHAR(256) NOT NULL")
	private String document;
	@Column(name = "program", columnDefinition = "NVARCHAR(256) NOT NULL")
	private String program;
	@Column(name = "description", columnDefinition = "NVARCHAR(256) NOT NULL")
	private String description;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "status_id", referencedColumnName = "id")
	private Status status;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "capstoneProject")
	private List<HistoryRecords> historyRecords;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "capstoneProject")
	private List<Files> files;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "capstoneProject")
	private List<Users> users;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "capstoneProject")
	private List<Evaluations> evaluations;

	public CapstoneProjects() {
		super();
	}

	public CapstoneProjects(String name, String profession, String specialty, String document, String program,
			String description, Status status, List<HistoryRecords> historyRecords, List<Files> files,
			List<Users> users, List<Evaluations> evaluations) {
		super();
		this.name = name;
		this.profession = profession;
		this.specialty = specialty;
		this.document = document;
		this.program = program;
		this.description = description;
		this.status = status;
		this.historyRecords = historyRecords;
		this.files = files;
		this.users = users;
		this.evaluations = evaluations;
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

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
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

	public List<Users> getUsers() {
		return users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}

	public List<Evaluations> getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(List<Evaluations> evaluations) {
		this.evaluations = evaluations;
	}

}
