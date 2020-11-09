package com.fpt.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "[Profession]")
public class Profession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Integer id;
    @Column(name = "name", columnDefinition = "NVARCHAR(100) NOT NULL")
    private String name;
    @Column(name = "subject_code", columnDefinition = "NVARCHAR(50) NOT NULL")
    private String subjectCode;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "profession")
    private List<CapstoneProjects> capstoneProjects;
    @Column(name = "max_member", columnDefinition = "INT")
    private int maxMember;
    @Column(name = "min_member", columnDefinition = "INT")
    private int minMember;

    public Profession(Integer id, String name, String subjectCode, List<CapstoneProjects> capstoneProjects, int maxMember, int minMember) {
        this.id = id;
        this.name = name;
        this.subjectCode = subjectCode;
        this.capstoneProjects = capstoneProjects;
        this.maxMember = maxMember;
        this.minMember = minMember;
    }

    public int getMaxMember() {
        return maxMember;
    }

    public void setMaxMember(int maxMember) {
        this.maxMember = maxMember;
    }

    public int getMinMember() {
        return minMember;
    }

    public void setMinMember(int minMember) {
        this.minMember = minMember;
    }

    public Profession() {
        super();
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

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public List<CapstoneProjects> getCapstoneProjects() {
        return capstoneProjects;
    }

    public void setCapstoneProjects(List<CapstoneProjects> capstoneProjects) {
        this.capstoneProjects = capstoneProjects;
    }

}
