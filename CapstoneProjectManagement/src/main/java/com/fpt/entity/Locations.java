package com.fpt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "[Locations]")
public class Locations {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT")
	private Integer id;
	@Column(name = "street", columnDefinition = "NVARCHAR(256) NOT NULL")
	private String street;
	@Column(name = "city", columnDefinition = "NVARCHAR(50) NOT NULL")
	private String city;
	@OneToOne(mappedBy = "location")
	private Users user;
}
