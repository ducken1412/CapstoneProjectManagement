package com.fpt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	@OneToOne(mappedBy = "status")
	private Users user;
	@OneToOne(mappedBy = "status")
	private CapstoneProjects capstoneProject;

	public Status() {
		super();
	}

	public Status(String name, String description, Users user, CapstoneProjects capstoneProject) {
		super();
		this.name = name;
		this.description = description;
		this.user = user;
		this.capstoneProject = capstoneProject;
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

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public CapstoneProjects getCapstoneProject() {
		return capstoneProject;
	}

	public void setCapstoneProject(CapstoneProjects capstoneProject) {
		this.capstoneProject = capstoneProject;
	}

}
