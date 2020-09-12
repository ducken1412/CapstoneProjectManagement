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
}
