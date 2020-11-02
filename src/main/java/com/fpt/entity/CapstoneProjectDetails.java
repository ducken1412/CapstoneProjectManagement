package com.fpt.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "[CapstoneProjectDetails]")
public class CapstoneProjectDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "capstone_project_id")
    private CapstoneProjects capstoneProject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private Status status;
    @Column(name = "description_action", columnDefinition = "longtext")
    private String desAction;

    public CapstoneProjectDetails() {
        // TODO Auto-generated constructor stub
    }

    public CapstoneProjectDetails(Integer id, CapstoneProjects capstoneProject, Users user, Status status, String desAction) {
        this.id = id;
        this.capstoneProject = capstoneProject;
        this.user = user;
        this.status = status;
        this.desAction = desAction;
    }

    public String getDesAction() {
        return desAction;
    }

    public void setDesAction(String desAction) {
        this.desAction = desAction;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CapstoneProjects getCapstoneProject() {
        return capstoneProject;
    }

    public void setCapstoneProject(CapstoneProjects capstoneProject) {
        this.capstoneProject = capstoneProject;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
