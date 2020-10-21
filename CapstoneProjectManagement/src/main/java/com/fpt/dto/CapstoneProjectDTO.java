package com.fpt.dto;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fpt.entity.Profession;

public class CapstoneProjectDTO {
    private Integer id;
    @NotBlank
    @Size(max = 50)
    private String name;
    @NotBlank
    @Size(max = 50)
    private String nameOther;
    private String nameVi;
    private String nameAbbreviation;
    private Profession profession;
    private String specialty;
    private String document;
    private String program;
    private String description;
    private Integer stautus;
    private List<MemberDTO> members;

    public CapstoneProjectDTO() {
        // TODO Auto-generated constructor stub
    }

    public CapstoneProjectDTO(Integer id, String name, String nameOther, String nameVi, String nameAbbreviation,
                              Profession profession, String specialty, String document, String program, String description,
                              Integer stautus, List<MemberDTO> members) {
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
        this.stautus = stautus;
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

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
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

    public Integer getStautus() {
        return stautus;
    }

    public void setStautus_id(Integer stautus) {
        this.stautus = stautus;
    }

    public List<MemberDTO> getMembers() {
        return members;
    }

    public void setMembers(List<MemberDTO> members) {
        this.members = members;
    }

}
