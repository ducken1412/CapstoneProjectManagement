package com.fpt.dto;

import javax.persistence.Column;
import javax.persistence.OneToOne;

import com.fpt.entity.Users;

public class LocationDTO {
	private Integer id;
	
	private String street;

	private String city;

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

	public LocationDTO() {
		super();
	}

	public LocationDTO(Integer id, String street, String city) {
		super();
		this.id = id;
		this.street = street;
		this.city = city;
	}

	public LocationDTO(String street, String city) {
		super();
		this.street = street;
		this.city = city;
	}
	
	

}
