package com.fpt.entity;

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

    public Notifications() {
    }

    public Notifications(Integer id, List<NotificationDetails> notificationDetails, String content, String title, String type) {
        this.id = id;
        this.notificationDetails = notificationDetails;
        this.content = content;
        this.title = title;
        this.type = type;
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
