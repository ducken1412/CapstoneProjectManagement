package com.fpt.dto;

import com.fpt.entity.NotificationDetails;
import com.fpt.entity.Notifications;

import javax.management.Notification;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

public class NotificationDTO {

    private Integer id;

    private List<NotificationDetails> notificationDetails;

    @NotBlank
    private String content;

    @NotBlank

    private String title;

    private String type;

    private String user_id;

    private Integer project_id;

    public NotificationDTO() {
    }

    public NotificationDTO(Notifications notifications) {
        this.id = notifications.getId();
        this.title = notifications.getTitle();
    }

    public NotificationDTO(Integer id, List<NotificationDetails> notificationDetails, String content, String title, String type, String user_id, Integer project_id) {
        this.id = id;
        this.notificationDetails = notificationDetails;
        this.content = content;
        this.title = title;
        this.type = type;
        this.user_id = user_id;
        this.project_id = project_id;
    }

    public List<NotificationDetails> getNotificationDetails() {
        return notificationDetails;
    }

    public void setNotificationDetails(List<NotificationDetails> notificationDetails) {
        this.notificationDetails = notificationDetails;
    }

    public Integer getProject_id() {
        return project_id;
    }

    public void setProject_id(Integer project_id) {
        this.project_id = project_id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
