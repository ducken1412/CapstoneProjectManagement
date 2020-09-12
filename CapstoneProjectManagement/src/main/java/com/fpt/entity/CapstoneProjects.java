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
}
