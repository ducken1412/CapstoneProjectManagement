package com.fpt.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "[Status]")
public class Status {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT")
	private Integer id;
	@Column(name = "name", columnDefinition = "NVARCHAR(50) NOT NULL")
	private String name;
	@Column(name = "description", columnDefinition = "NVARCHAR(256)")
	private String description;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "status")
	private List<Users> users;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "status")
	private List<CapstoneProjects> capstoneProjects;

	public Status() {
		super();
	}

	public Status(String name, String description, List<Users> users, List<CapstoneProjects> capstoneProjects) {
		super();
		this.name = name;
		this.description = description;
		this.users = users;
		this.capstoneProjects = capstoneProjects;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Users> getUsers() {
		return users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}

	public List<CapstoneProjects> getCapstoneProjects() {
		return capstoneProjects;
	}

	public void setCapstoneProjects(List<CapstoneProjects> capstoneProjects) {
		this.capstoneProjects = capstoneProjects;
	}

	

}
