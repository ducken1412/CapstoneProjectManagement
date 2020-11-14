package com.fpt.common;

import com.fpt.dto.NotificationDTO;
import com.fpt.entity.HistoryRecords;
import com.fpt.entity.NotificationDetails;
import com.fpt.entity.Notifications;
import com.fpt.entity.Users;
import com.fpt.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
public class NotificationCommon {

   @Resource
   private  NotificationsService  notificationsService ;

    @Resource
    private  UserService  userService ;

    @Resource
    private  CapstoneProjectService  capstoneProjectService ;

    @Resource
    private  CapstoneProjectDetailService  capstoneProjectDetailService ;

    @Resource
    private  HistoryRecordService  recordService ;

    @Resource
    private  NotificationDetailService  notificationDetailService ;

   private static  NotificationCommon notificationCommon;

   @PostConstruct
   public  void  init(){
       notificationCommon = this;
       notificationCommon.notificationsService =this.notificationsService;
       notificationCommon.userService =this.userService;
       notificationCommon.capstoneProjectService =this.capstoneProjectService;
       notificationCommon.capstoneProjectDetailService =this.capstoneProjectDetailService;
       notificationCommon.recordService =this.recordService;
       notificationCommon.notificationDetailService =this.notificationDetailService;
   }


    public static void sendNotification(Users user,String title,String content, String userSendTo){
        HistoryRecords records = new HistoryRecords();
        Date date = new Date();
        Notifications notifications = new Notifications();
        NotificationDetails notificationDetails = new NotificationDetails();
        notifications.setType("private");
        notifications.setTitle(title);
        notifications.setContent(content);
        notifications.setCreated_date(date);
        String userId = userSendTo;
        List<NotificationDetails> notificationDetail = new ArrayList<>();
        notificationDetails.setUser(notificationCommon.userService.findById(userId));
        String type = "private";
        notificationDetails.setNotification(notifications);
        notificationDetails.setType(type);
        notificationDetail.add(notificationDetails);
        records.setUser(user);
        records.setContent("Create notificaton");
        records.setNotification(notifications);
        records.setCreatedDate(date);
        notifications.setNotificationDetails(notificationDetail);
        notificationCommon.notificationsService.addNotification(notifications);
        notificationCommon.recordService.save(records);
    }

    public static void sendNotificationIgnoreHistory(Users user,String title,String content, String userSendTo){
        Date date = new Date();
        Notifications notifications = new Notifications();
        NotificationDetails notificationDetails = new NotificationDetails();
        notifications.setType("private");
        notifications.setTitle(title);
        notifications.setContent(content);
        notifications.setCreated_date(date);
        String userId = userSendTo;
        List<NotificationDetails> notificationDetail = new ArrayList<>();
        notificationDetails.setUser(notificationCommon.userService.findById(userId));
        String type = "private";
        notificationDetails.setNotification(notifications);
        notificationDetails.setType(type);
        notificationDetail.add(notificationDetails);
        notifications.setNotificationDetails(notificationDetail);
        notificationCommon.notificationsService.addNotification(notifications);
    }

    public static void sendNotificationByUsername(Users sender,String title,String content, String recipient){
        HistoryRecords records = new HistoryRecords();
        Date date = new Date();
        Notifications notifications = new Notifications();
        NotificationDetails notificationDetails = new NotificationDetails();
        notifications.setType("private");
        notifications.setTitle(title);
        notifications.setContent(content);
        notifications.setCreated_date(date);

        //int noti_id = notifications.getId();
        //notificationDetails.setNotification(notificationsService.getOneNoification(noti_id));
        List<NotificationDetails> notificationDetail = new ArrayList<>();
        notificationDetails.setUser(notificationCommon.userService.findByUsername(recipient).get(0));
        String type = "private";
        notificationDetails.setNotification(notifications);
        notificationDetails.setType(type);
        notificationDetail.add(notificationDetails);
        records.setUser(sender);
        records.setContent("Create notificaton");
        records.setNotification(notifications);
        records.setCreatedDate(date);
        notifications.setNotificationDetails(notificationDetail);
        notificationCommon.notificationsService.addNotification(notifications);
        notificationCommon.recordService.save(records);
    }

    public static void  addNotification(String content, String title, Date date, String type){
        Notifications notifications = new Notifications();
        notifications.setContent(content);
        notifications.setTitle(title);
        notifications.setCreated_date(date);
        notifications.setType(type);
        notificationCommon.notificationsService.addNotification(notifications);
    }

    public static void addNotificationDetail(String type, Notifications notification, String userId){
        NotificationDetails notificationDetails = new NotificationDetails();
        notificationDetails.setNotification(notification);
        notificationDetails.setUser(notificationCommon.userService.findById(userId));
        notificationDetails.setType(type);
        notificationCommon.notificationDetailService.addNotificationDetail(notificationDetails);
    }
}
