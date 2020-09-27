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
import javax.persistence.Table;

@Entity
@Table(name = "[CapstoneProjectDetails]")
public class CapstoneProjectDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT")
	private Integer id;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "capstoneProjectDetails")
	private List<Users> users;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "capstoneProjectDetails")
	private List<CapstoneProjects> capstoneProjects;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "capstoneProjectDetails")
	private List<Status> status;

	public CapstoneProjectDetails(Integer id, List<Users> users, List<CapstoneProjects> capstoneProjects,
			List<Status> status) {
		super();
		this.id = id;
		this.users = users;
		this.capstoneProjects = capstoneProjects;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public List<Status> getStatus() {
		return status;
	}

	public void setStatus(List<Status> status) {
		this.status = status;
	}

}
