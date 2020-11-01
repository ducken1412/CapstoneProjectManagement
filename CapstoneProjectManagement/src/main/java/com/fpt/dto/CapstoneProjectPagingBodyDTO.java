package com.fpt.dto;

import java.util.List;

public class CapstoneProjectPagingBodyDTO {
    private Integer id;
    private String description_action;
    private String description;
    private String document;
    private String name;
    private String name_abbreviation;
    private String name_lang_other;
    private String name_vi;
    private String program;
    private String specialty;
    private Integer profession_id;
    private Integer status_id;
    private String nameStatus;
    private Integer countDetail;

    private List<CapstoneProjectDetailBody> detail;

    public CapstoneProjectPagingBodyDTO(Integer id, String description_action, String description, String document, String name, String name_abbreviation, String name_lang_other, String name_vi, String program, String specialty, Integer profession_id, Integer status_id, String nameStatus, Integer countDetail, List<CapstoneProjectDetailBody> detail) {
        super();
        this.id = id;
        this.description_action = description_action;
        this.description = description;
        this.document = document;
        this.name = name;
        this.name_abbreviation = name_abbreviation;
        this.name_lang_other = name_lang_other;
        this.name_vi = name_vi;
        this.program = program;
        this.specialty = specialty;
        this.profession_id = profession_id;
        this.status_id = status_id;
        this.nameStatus = nameStatus;
        this.countDetail = countDetail;
        this.detail = detail;
    }

    public CapstoneProjectPagingBodyDTO() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription_action() {
        return description_action;
    }

    public void setDescription_action(String description_action) {
        this.description_action = description_action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_abbreviation() {
        return name_abbreviation;
    }

    public void setName_abbreviation(String name_abbreviation) {
        this.name_abbreviation = name_abbreviation;
    }

    public String getName_lang_other() {
        return name_lang_other;
    }

    public void setName_lang_other(String name_lang_other) {
        this.name_lang_other = name_lang_other;
    }

    public String getName_vi() {
        return name_vi;
    }

    public void setName_vi(String name_vi) {
        this.name_vi = name_vi;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Integer getProfession_id() {
        return profession_id;
    }

    public void setProfession_id(Integer profession_id) {
        this.profession_id = profession_id;
    }

    public Integer getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Integer status_id) {
        this.status_id = status_id;
    }

    public List<CapstoneProjectDetailBody> getDetail() {
        return detail;
    }

    public void setDetail(List<CapstoneProjectDetailBody> detail) {
        this.detail = detail;
    }

    public String getNameStatus() {
        return nameStatus;
    }

    public void setNameStatus(String nameStatus) {
        this.nameStatus = nameStatus;
    }

    public Integer getCountDetail() {
        return countDetail;
    }

    public void setCountDetail(Integer countDetail) {
        this.countDetail = countDetail;
    }
}

