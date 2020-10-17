package com.fpt.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "Notifications")
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Integer id;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "notification")
    private List<NotificationDetails> notificationDetails;
    @Column(name = "content", columnDefinition = "longtext NOT NULL")
    private String content;
    @Column(name = "title", columnDefinition = "NVARCHAR(256) NOT NULL")
    private String title;
    @Column(name = "type", columnDefinition = "NVARCHAR(50) NOT NULL")
    private String type;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "notification")
    private List<HistoryRecords> historyRecords;
    @Column(name = "created_date", columnDefinition = "DATETIME")
    private Date created_date;
    public Notifications() {
    }

    public Notifications(Integer id, List<NotificationDetails> notificationDetails, String content, String title, String type, List<HistoryRecords> historyRecords, Date created_date) {
        this.id = id;
        this.notificationDetails = notificationDetails;
        this.content = content;
        this.title = title;
        this.type = type;
        this.historyRecords = historyRecords;
        this.created_date = created_date;
    }

    public List<HistoryRecords> getHistoryRecords() {
        return historyRecords;
    }

    public void setHistoryRecords(List<HistoryRecords> historyRecords) {
        this.historyRecords = historyRecords;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<NotificationDetails> getNotificationDetails() {
        return notificationDetails;
    }

    public void setNotificationDetails(List<NotificationDetails> notificationDetails) {
        this.notificationDetails = notificationDetails;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
