package com.fpt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fpt.entity.Notifications;

@Service
public interface NotificationsService {
	List<Notifications> getAllTitle();
}
