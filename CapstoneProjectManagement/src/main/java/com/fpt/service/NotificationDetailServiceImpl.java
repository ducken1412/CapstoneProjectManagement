package com.fpt.service;

import com.fpt.entity.NotificationDetails;
import com.fpt.repository.NotificationDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationDetailServiceImpl implements NotificationDetailService{
    @Autowired
    public NotificationDetailRepository notificationDetailRepository;

    @Override
    public List<NotificationDetails> getIdNotification(String id) {
        return notificationDetailRepository.getIdNotification(id);
    }

    //add notification detail
    @Override
    public boolean addNotificationDetail(NotificationDetails notificationDetails) {
        try{
            notificationDetailRepository.save(notificationDetails);
            return true;
        }catch (Exception e){
            System.out.println("error add nottification detail table");
        }
        return false;
    }

    @Override
    public List<NotificationDetails> getIdNotificationByTop5(String id) {
        return notificationDetailRepository.getIdNotificationByTop5(id);
    }

    @Override
    public Integer countNotificationDetailByUserId(String id) {
        return notificationDetailRepository.countNotificationDetailByUserId(id);
    }

    @Override
    public List<NotificationDetails> saveAllNotificationDetails(List<NotificationDetails> notificationDetails) {
        return notificationDetailRepository.saveAll(notificationDetails);
    }

    @Override
    public void addNotificationDetailNativeQuery(String type, Integer nid, String userId) {
        notificationDetailRepository.addNotificationDetailNativeQuery(type,nid,userId);
    }


}
