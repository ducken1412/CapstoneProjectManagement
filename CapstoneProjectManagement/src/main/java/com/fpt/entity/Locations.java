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

	public Locations() {
		super();
	}

	public Locations(String street, String city, Users user) {
		super();
		this.street = street;
		this.city = city;
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

}
