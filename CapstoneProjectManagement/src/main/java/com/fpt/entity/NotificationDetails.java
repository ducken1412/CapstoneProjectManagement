package com.fpt.entity;

import javax.management.Notification;
import javax.persistence.*;

@Entity
@Table(name = "[NotificationDetails]")
public class NotificationDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id")
    private Notifications notification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name = "type", columnDefinition = "NVARCHAR(30) NOT NULL")
    private String type;

    public NotificationDetails() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Notifications getNotification() {
        return notification;
    }

    public void setNotification(Notifications notification) {
        this.notification = notification;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public NotificationDetails(Integer id, Notifications notification, Users user, String type) {
        this.id = id;
        this.notification = notification;
        this.user = user;
        this.type = type;
    }
}
