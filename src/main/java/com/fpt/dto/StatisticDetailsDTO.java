package com.fpt.dto;

import java.util.Date;

public class StatisticDetailsDTO {
    private Integer idProject;
    private Integer totalTrackingDone;
    private Integer totalTrackingProgress;
    private Integer totalTracking;
    private Date  startDate;
    private Date  endDate;
    public StatisticDetailsDTO() {
    }

    public StatisticDetailsDTO(Integer idProject, Integer totalTrackingDone, Integer totalTrackingProgress, Integer totalTracking, Date startDate, Date endDate) {
        this.idProject = idProject;
        this.totalTrackingDone = totalTrackingDone;
        this.totalTrackingProgress = totalTrackingProgress;
        this.totalTracking = totalTracking;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getIdProject() {
        return idProject;
    }

    public void setIdProject(Integer idProject) {
        this.idProject = idProject;
    }

    public Integer getTotalTrackingDone() {
        return totalTrackingDone;
    }

    public void setTotalTrackingDone(Integer totalTrackingDone) {
        this.totalTrackingDone = totalTrackingDone;
    }

    public Integer getTotalTrackingProgress() {
        return totalTrackingProgress;
    }

    public void setTotalTrackingProgress(Integer totalTrackingProgress) {
        this.totalTrackingProgress = totalTrackingProgress;
    }

    public Integer getTotalTracking() {
        return totalTracking;
    }

    public void setTotalTracking(Integer totalTracking) {
        this.totalTracking = totalTracking;
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
}
