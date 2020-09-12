package com.fpt.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "[Users]")
public class Users implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", columnDefinition = "NVARCHAR(50)")
	private String id;
	@Column(name = "user_name", columnDefinition = "NVARCHAR(50) NOT NULL")
	private String userName;
	@Column(name = "encryted_password", columnDefinition = "NVARCHAR(256) NOT NULL")
	private String encrytedPassword;
	@Column(name = "first_name", columnDefinition = "NVARCHAR(30) NOT NULL")
	private String firstName;
	@Column(name = "last_name", columnDefinition = "NVARCHAR(30) NOT NULL")
	private String lastName;
	@Column(name = "birth_date", columnDefinition = "DATE")
	private Date birthDate;
	@Column(name = "gender", columnDefinition = "INT NOT NULL")
	private Integer gender;
	@Column(name = "phone", columnDefinition = "VARCHAR(15) NOT NULL")
	private String phone;
	@Column(name = "email", columnDefinition = "NVARCHAR(64) NOT NULL")
	private String email;
	@Column(name = "created_date", columnDefinition = "DATETIME NOT NULL")
	private Date createdDate;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "locations_id", referencedColumnName = "id")
	private Locations location;
	@Column(name = "description", columnDefinition = "NVARCHAR(256)")
	private String description;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "status_id", referencedColumnName = "id")
	private Status status;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userRoleKey.user")
	private List<UserRoles> roleUser;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
	private List<HistoryRecords> historyRecords;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "capstone_project_id")
	private CapstoneProjects capstoneProject;
	@OneToOne(mappedBy = "sender")
	private Notifications notificationSend;
	@ManyToMany(mappedBy = "receivers")
	private List<Notifications> notificationReceives;
	@OneToOne(mappedBy = "sender")
	private Reports reportSend;
	@ManyToOne(fetch = FetchType.EAGER)
	private Reports reportReceive;
	@ManyToMany(mappedBy = "reportRecipients")
	private List<Reports> reportReceives;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "sender")
	private List<Comments> comments;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "report_detail_id")
	private ReportDetails reportDetail;
	@OneToOne(mappedBy = "assessor")
	private EvaluationDetails evaluationDetail;

}
