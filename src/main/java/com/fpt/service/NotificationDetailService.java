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

    //get top 5 title private
    List<NotificationDetails> getIdNotificationByTop5(String id);

    Integer countNotificationDetailByUserId(String id);

    List<NotificationDetails> saveAllNotificationDetails(List<NotificationDetails> notificationDetails);

    void addNotificationDetailNativeQuery(String type, Integer nid, String userId);
}
