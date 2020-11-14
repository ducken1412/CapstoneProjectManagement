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
    @Column(name = "time_tracking_current", columnDefinition = "DOUBLE")
    public double timeTrackingCurrent;
    @Column(name = "time_tracking_progress", columnDefinition = "DOUBLE")
    public double timeTrackingProgress;
    @Column(name = "time_tracking_todo", columnDefinition = "DOUBLE")
    public double timeTrackingTodo;
    @Column(name = "time_tracking_done", columnDefinition = "DOUBLE")
    public double timeTrackingDone;
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

    public Statistics(Integer id, double timeTrackingCurrent, double timeTrackingProgress, double timeTrackingTodo, double timeTrackingDone,Date startDate, Date endDate, int week, CapstoneProjects capstoneProject) {
        this.id = id;
        this.timeTrackingCurrent = timeTrackingCurrent;
        this.timeTrackingProgress = timeTrackingProgress;
        this.timeTrackingTodo = timeTrackingTodo;
        this.timeTrackingDone = timeTrackingDone;
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

    public double getTimeTrackingCurrent() {
        return timeTrackingCurrent;
    }

    public void setTimeTrackingCurrent(double timeTrackingCurrent) {
        this.timeTrackingCurrent = timeTrackingCurrent;
    }

    public double getTimeTrackingProgress() {
        return timeTrackingProgress;
    }

    public void setTimeTrackingProgress(double timeTrackingProgress) {
        this.timeTrackingProgress = timeTrackingProgress;
    }

    public double getTimeTrackingTodo() {
        return timeTrackingTodo;
    }

    public void setTimeTrackingTodo(double timeTrackingTodo) {
        this.timeTrackingTodo = timeTrackingTodo;
    }

    public double getTimeTrackingDone() {
        return timeTrackingDone;
    }

    public void setTimeTrackingDone(double timeTrackingDone) {
        this.timeTrackingDone = timeTrackingDone;
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
