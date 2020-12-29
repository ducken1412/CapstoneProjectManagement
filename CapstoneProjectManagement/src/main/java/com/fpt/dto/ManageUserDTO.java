package com.fpt.dto;

import java.util.Date;

public class ManageUserDTO {
    private String nameSemester;
    private String startDate;
    private String endDate;
    private String startDateRegister;
    private String endDateRegister;
    private int site;
    private int status;
    private int semester;
    private int isUpdateSemester;

    private int role;

    public ManageUserDTO() {
    }

    public ManageUserDTO(String nameSemester, String startDate, String endDate, String startDateRegister, String endDateRegister, int site, int status, int semester, int isUpdateSemester, int role) {
        this.nameSemester = nameSemester;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startDateRegister = startDateRegister;
        this.endDateRegister = endDateRegister;
        this.site = site;
        this.status = status;
        this.semester = semester;
        this.isUpdateSemester = isUpdateSemester;
        this.role = role;
    }

    public String getNameSemester() {
        return nameSemester;
    }

    public void setNameSemester(String nameSemester) {
        this.nameSemester = nameSemester;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDateRegister() {
        return startDateRegister;
    }

    public void setStartDateRegister(String startDateRegister) {
        this.startDateRegister = startDateRegister;
    }

    public String getEndDateRegister() {
        return endDateRegister;
    }

    public void setEndDateRegister(String endDateRegister) {
        this.endDateRegister = endDateRegister;
    }

    public int getSite() {
        return site;
    }

    public void setSite(int site) {
        this.site = site;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getIsUpdateSemester() {
        return isUpdateSemester;
    }

    public void setIsUpdateSemester(int isUpdateSemester) {
        this.isUpdateSemester = isUpdateSemester;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
