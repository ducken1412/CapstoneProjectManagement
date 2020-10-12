package com.fpt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fpt.entity.Notifications;
import com.fpt.entity.Users;
import com.fpt.service.NotificationsService;
import com.fpt.service.UserService;

@Controller
public class NotificationsController {

	@Autowired
	public NotificationsService notificationsService;

	@Autowired
	public UserService userService;
	
	@RequestMapping(value = "/notifications")
	public String getAllNotications(Model model) {
		List<Notifications> notification = notificationsService.getAllTitle();
		model.addAttribute("notifications", notification);
		Users user= userService.findById("1");
		List<Notifications> notificationByUserId = notificationsService.getTitleByUserId(user.getId().toString());
		model.addAttribute("notificationByUserId", notificationByUserId);
		return "home/notifications";
	}
	
}
