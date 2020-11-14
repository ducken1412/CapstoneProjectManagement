package com.fpt.dto;

public class StatisticsTotalDTO {
    private String numProject;
    private String totalOnSchedule;
    private String totalWarning;
    private String totalSerious;


    public StatisticsTotalDTO() {
    }

    public StatisticsTotalDTO(String numProject, String totalOnSchedule, String totalWarning, String totalSerious) {
        this.numProject = numProject;
        this.totalOnSchedule = totalOnSchedule;
        this.totalWarning = totalWarning;
        this.totalSerious = totalSerious;
    }

    public String getNumProject() {
        return numProject;
    }

    public void setNumProject(String numProject) {
        this.numProject = numProject;
    }

    public String getTotalOnSchedule() {
        return totalOnSchedule;
    }

    public void setTotalOnSchedule(String totalOnSchedule) {
        this.totalOnSchedule = totalOnSchedule;
    }

    public String getTotalWarning() {
        return totalWarning;
    }

    public void setTotalWarning(String totalWarning) {
        this.totalWarning = totalWarning;
    }

    public String getTotalSerious() {
        return totalSerious;
    }

    public void setTotalSerious(String totalSerious) {
        this.totalSerious = totalSerious;
    }
}
