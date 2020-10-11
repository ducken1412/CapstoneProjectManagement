package com.fpt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fpt.entity.Notifications;
import com.fpt.service.NotificationsService;

@Controller
public class NotificationsController {

	@Autowired
	public NotificationsService notificationsService;

	@RequestMapping(value = "/notifications")
	public String getAllNotications(Model model) {
		List<Notifications> notification = notificationsService.getAllTitle();
		model.addAttribute("notifications", notification);
		return "home/notifications";
	}

}
