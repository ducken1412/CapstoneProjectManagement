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
	@Column(name = "id", columnDefinition = "INT")
	private Integer id;
	@Column(name = "name", columnDefinition = "NVARCHAR(50) NOT NULL")
	private String name;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "status")
	private List<Users> users;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "status")
	private List<CapstoneProjectDetails> capstoneProjectDetails;

	public Status() {
		super();
	}

	public Status(Integer id, String name, List<Users> users,
			List<CapstoneProjectDetails> capstoneProjectDetails) {
		super();
		this.id = id;
		this.name = name;
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

	public List<Users> getUsers() {
		return users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}

	public List<CapstoneProjectDetails> getCapstoneProjectDetails() {
		return capstoneProjectDetails;
	}

	public void setCapstoneProjectDetails(List<CapstoneProjectDetails> capstoneProjectDetails) {
		this.capstoneProjectDetails = capstoneProjectDetails;
	}

}
