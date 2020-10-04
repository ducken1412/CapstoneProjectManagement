package com.fpt.dto;

import java.util.List;

public class CapstoneProjectDTO {
	private Integer id;
	private String name;
	private String nameOther;
	private String nameVi;
	private String nameAbbreviation;
	private String profession;
	private String specialty;
	private String document;
	private String program;
	private String description;
	private Integer stautus_id;
	private List<String> members;

	public CapstoneProjectDTO() {
		// TODO Auto-generated constructor stub
	}

	public CapstoneProjectDTO(Integer id, String name, String nameOther, String nameVi, String nameAbbreviation,
			String profession, String specialty, String document, String program, String description,
			Integer stautus_id, List<String> members) {
		super();
		this.id = id;
		this.name = name;
		this.nameOther = nameOther;
		this.nameVi = nameVi;
		this.nameAbbreviation = nameAbbreviation;
		this.profession = profession;
		this.specialty = specialty;
		this.document = document;
		this.program = program;
		this.description = description;
		this.stautus_id = stautus_id;
		this.members = members;
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

	public String getNameOther() {
		return nameOther;
	}

	public void setNameOther(String nameOther) {
		this.nameOther = nameOther;
	}

	public String getNameVi() {
		return nameVi;
	}

	public void setNameVi(String nameVi) {
		this.nameVi = nameVi;
	}

	public String getNameAbbreviation() {
		return nameAbbreviation;
	}

	public void setNameAbbreviation(String nameAbbreviation) {
		this.nameAbbreviation = nameAbbreviation;
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

	public List<String> getMembers() {
		return members;
	}

	public void setMembers(List<String> members) {
		this.members = members;
	}

}
