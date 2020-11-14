package com.fpt.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "[TaskDetails]")
public class TaskDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Integer id;
    @Column(name = "summary", columnDefinition = "longtext")
    public String summary;
    @Column(name = "assignee", columnDefinition = "NVARCHAR(256)")
    public String assignee;
    @Column(name = "time_tracking", columnDefinition = "DOUBLE")
    public double timeTracking;
    @Column(name = "status", columnDefinition = "NVARCHAR(100)")
    public String status;
    @Column(name = "time_spent", columnDefinition = "NVARCHAR(100)")
    public String timeSpent;
    @Column(name = "start_date", columnDefinition = "DATE")
    public Date startDate;
    @Column(name = "end_date", columnDefinition = "DATE")
    public Date endDate;
    @Column(name = "week", columnDefinition = "INT")
    public int week;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "capstone_project_id")
    private CapstoneProjects capstoneProject;

    public TaskDetails() {
    }

    public TaskDetails(Integer id, String summary, String assignee, double timeTracking, String status, String timeSpent, Date startDate, Date endDate, int week, CapstoneProjects capstoneProject) {
        this.id = id;
        this.summary = summary;
        this.assignee = assignee;
        this.timeTracking = timeTracking;
        this.status = status;
        this.timeSpent = timeSpent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.week = week;
        this.capstoneProject = capstoneProject;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public double getTimeTracking() {
        return timeTracking;
    }

    public void setTimeTracking(double timeTracking) {
        this.timeTracking = timeTracking;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(String timeSpent) {
        this.timeSpent = timeSpent;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public CapstoneProjects getCapstoneProject() {
        return capstoneProject;
    }

    public void setCapstoneProject(CapstoneProjects capstoneProject) {
        this.capstoneProject = capstoneProject;
    }
}
