package com.fpt.dto;

import com.fpt.entity.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

public class ReportDTO {

    private Integer id;

    private Reports report;

    private List<Files> files;

    private List<Comments> comments;

    private List<Users> members;

    private Date createdDate;

    private Date lastModifiedDate;

    @NotBlank
    private String content;

    @NotBlank
    private String name;

    private String type;

    public ReportDTO() {
    }

    public ReportDTO(Reports reports) {
        this.name = reports.getName();
        this.type = reports.getType();
    }

    public ReportDTO(Integer id, Reports report, List<Files> files, List<Comments> comments, List<Users> members, Date createdDate, Date lastModifiedDate, @NotBlank String content, @NotBlank String name, String type) {
        this.id = id;
        this.report = report;
        this.files = files;
        this.comments = comments;
        this.members = members;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.content = content;
        this.name = name;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Reports getReport() {
        return report;
    }

    public void setReport(Reports report) {
        this.report = report;
    }

    public List<Files> getFiles() {
        return files;
    }

    public void setFiles(List<Files> files) {
        this.files = files;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    public List<Users> getMembers() {
        return members;
    }

    public void setMembers(List<Users> members) {
        this.members = members;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
