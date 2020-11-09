package com.fpt.dto;

import java.util.Date;

public class TaskDetailsDTO {


    private String id ;
    private String summary ;
    private String assignee ;
    private int timeTracking;
    private String status;
    private String timeSpent;
    private Date startDate;
    private Date endDate;
    private int week;

    public TaskDetailsDTO() {
        // TODO Auto-generated constructor stub
    }
    public TaskDetailsDTO(String id, String summary, String assignee, int timeTracking, String status, String timeSpent, Date startDate, Date endDate, int week) {
        super();
        this.id = id;
        this.summary = summary;
        this.assignee = assignee;
        this.timeTracking = timeTracking;
        this.status = status;
        this.timeSpent = timeSpent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.week = week;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public int getTimeTracking() {
        return timeTracking;
    }

    public void setTimeTracking(int timeTracking) {
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
}
