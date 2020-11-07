package com.fpt.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "[Statistics]")
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Integer id;
    @Column(name = "time_tracking_current", columnDefinition = "INT")
    public int timeTrackingCurrent;
    @Column(name = "time_tracking_progress", columnDefinition = "INT")
    public int timeTrackingProgress;
    @Column(name = "time_tracking_todo", columnDefinition = "INT")
    public int timeTrackingTodo;
    @Column(name = "start_date", columnDefinition = "DATE")
    public Date startDate;
    @Column(name = "end_date", columnDefinition = "DATE")
    public Date endDate;
    @Column(name = "week", columnDefinition = "INT")
    public int week;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "capstone_project_id")
    private CapstoneProjects capstoneProject;

    public Statistics() {
    }

    public Statistics(Integer id, int timeTrackingCurrent, int timeTrackingProgress, int timeTrackingTodo, Date startDate, Date endDate, int week, CapstoneProjects capstoneProject) {
        this.id = id;
        this.timeTrackingCurrent = timeTrackingCurrent;
        this.timeTrackingProgress = timeTrackingProgress;
        this.timeTrackingTodo = timeTrackingTodo;
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

    public int getTimeTrackingCurrent() {
        return timeTrackingCurrent;
    }

    public void setTimeTrackingCurrent(int timeTrackingCurrent) {
        this.timeTrackingCurrent = timeTrackingCurrent;
    }

    public int getTimeTrackingProgress() {
        return timeTrackingProgress;
    }

    public void setTimeTrackingProgress(int timeTrackingProgress) {
        this.timeTrackingProgress = timeTrackingProgress;
    }

    public int getTimeTrackingTodo() {
        return timeTrackingTodo;
    }

    public void setTimeTrackingTodo(int timeTrackingTodo) {
        this.timeTrackingTodo = timeTrackingTodo;
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
