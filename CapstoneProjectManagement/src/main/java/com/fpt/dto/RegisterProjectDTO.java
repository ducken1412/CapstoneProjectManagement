package com.fpt.dto;

public class RegisterProjectDTO {
	private Integer id;
	private String name;
	private String profession;
	private String specialty;
	private String document;
	private String program;
	private String description;
	private Integer stautus_id;
	
	public RegisterProjectDTO() {
		// TODO Auto-generated constructor stub
	}

	public RegisterProjectDTO(String name, String profession, String specialty, String document,
			String program, String description, Integer stautus_id) {
		super();
		this.name = name;
		this.profession = profession;
		this.specialty = specialty;
		this.document = document;
		this.program = program;
		this.description = description;
		this.stautus_id = stautus_id;
	}

	public RegisterProjectDTO(Integer id, String name, String profession, String specialty, String document,
			String program, String description, Integer stautus_id) {
		super();
		this.id = id;
		this.name = name;
		this.profession = profession;
		this.specialty = specialty;
		this.document = document;
		this.program = program;
		this.description = description;
		this.stautus_id = stautus_id;
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

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStautus_id() {
		return stautus_id;
	}

	public void setStautus_id(Integer stautus_id) {
		this.stautus_id = stautus_id;
	}
	
	
}
