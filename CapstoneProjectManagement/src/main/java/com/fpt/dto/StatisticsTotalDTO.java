package com.fpt.dto;

public class StatisticsTotalDTO {
    private String numProject;
    private Double totalOnSchedule;
    private Double totalWarning;
    private Double totalSerious;
    private int countOnSchedule;
    private int countWarning;
    private int countSerious;



    public StatisticsTotalDTO() {
    }

    public StatisticsTotalDTO(String numProject, Double totalOnSchedule, Double totalWarning, Double totalSerious, int countOnSchedule, int countWarning, int countSerious) {
        this.numProject = numProject;
        this.totalOnSchedule = totalOnSchedule;
        this.totalWarning = totalWarning;
        this.totalSerious = totalSerious;
        this.countOnSchedule = countOnSchedule;
        this.countWarning = countWarning;
        this.countSerious = countSerious;
    }

    public String getNumProject() {
        return numProject;
    }

    public void setNumProject(String numProject) {
        this.numProject = numProject;
    }

    public Double getTotalOnSchedule() {
        return totalOnSchedule;
    }

    public void setTotalOnSchedule(Double totalOnSchedule) {
        this.totalOnSchedule = totalOnSchedule;
    }

    public Double getTotalWarning() {
        return totalWarning;
    }

    public void setTotalWarning(Double totalWarning) {
        this.totalWarning = totalWarning;
    }

    public Double getTotalSerious() {
        return totalSerious;
    }

    public void setTotalSerious(Double totalSerious) {
        this.totalSerious = totalSerious;
    }

    public int getCountOnSchedule() {
        return countOnSchedule;
    }

    public void setCountOnSchedule(int countOnSchedule) {
        this.countOnSchedule = countOnSchedule;
    }

    public int getCountWarning() {
        return countWarning;
    }

    public void setCountWarning(int countWarning) {
        this.countWarning = countWarning;
    }

    public int getCountSerious() {
        return countSerious;
    }

    public void setCountSerious(int countSerious) {
        this.countSerious = countSerious;
    }
}
