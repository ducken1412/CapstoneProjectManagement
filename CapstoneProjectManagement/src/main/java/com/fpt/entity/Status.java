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
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "capstone_project_detail_id")
	private CapstoneProjectDetails capstoneProjectDetails;

	public Status() {
		super();
	}

	public Status(Integer id, String name, String description, List<Users> users,
			CapstoneProjectDetails capstoneProjectDetails) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.users = users;
		this.capstoneProjectDetails = capstoneProjectDetails;
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

	public CapstoneProjectDetails getCapstoneProjectDetails() {
		return capstoneProjectDetails;
	}

	public void setCapstoneProjectDetails(CapstoneProjectDetails capstoneProjectDetails) {
		this.capstoneProjectDetails = capstoneProjectDetails;
	}

}
