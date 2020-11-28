package com.fpt.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "[Semesters]")
public class Semesters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Integer id;
    @Column(name = "name", columnDefinition = "NVARCHAR(50) NOT NULL UNIQUE")
    public String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "semester")
    private List<CapstoneProjects> capstoneProject;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "semester")
    private List<Users> users;

    public Semesters() {
    }

    public Semesters(Integer id, String name, List<CapstoneProjects> capstoneProject, List<Users> users) {
        this.id = id;
        this.name = name;
        this.capstoneProject = capstoneProject;
        this.users = users;
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

    public List<CapstoneProjects> getCapstoneProject() {
        return capstoneProject;
    }

    public void setCapstoneProject(List<CapstoneProjects> capstoneProject) {
        this.capstoneProject = capstoneProject;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }
}
