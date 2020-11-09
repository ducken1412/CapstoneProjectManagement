package com.fpt.dto;

public class StatusDTO {
	private Integer id;
	private String description;
	private String name;
	
	public StatusDTO() {
		// TODO Auto-generated constructor stub
	}

	public StatusDTO(Integer id, String description, String name) {
		super();
		this.id = id;
		this.description = description;
		this.name = name;
	}

	public StatusDTO(String description, String name) {
		super();
		this.description = description;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
