package com.fpt.service;

import com.fpt.entity.NotificationDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationDetailService {
    //get list by id
    List<NotificationDetails> getIdNotification(String id);

    //add notificationdetail table
    boolean addNotificationDetail(NotificationDetails notificationDetails);
}
