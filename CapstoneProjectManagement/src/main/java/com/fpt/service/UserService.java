package com.fpt.service;

import java.util.List;

import com.fpt.entity.Users;

public interface UserService {
	Users findById(String id);
	List<Users> getAllUser();
}
