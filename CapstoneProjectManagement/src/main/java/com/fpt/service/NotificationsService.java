package com.fpt.service;

import java.util.List;

import com.fpt.dto.NotificationDTO;

import com.fpt.entity.Notifications;


public interface NotificationsService {
	List<Notifications> getAllTitle();
	
	//get notification by user id
//	List<Notifications> getTitleByUserId(String id);
	List<NotificationDTO> getTitle();

	//add notification
	boolean addNotification(Notifications notifications);

	//get one notification
	Notifications getOneNoification(Integer id);

	//get notification by id
	Notifications getNotificationById(Integer id);

	List<Notifications> getTop5NotificationsByCreatedDate();
}
