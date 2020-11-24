package com.fpt.service;

import com.fpt.entity.Chat;
import com.fpt.entity.Users;

import java.util.List;

public interface ChatDetailService {
	int findNumberNewMessage(String userId);
}