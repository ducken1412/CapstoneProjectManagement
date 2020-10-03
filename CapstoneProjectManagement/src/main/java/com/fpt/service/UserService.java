package com.fpt.service;

import java.util.List;

import com.fpt.entity.Users;

public interface UserService {

	Users findById(String id);
	
	//list all user, all role
	List<Users> getAllUser();
	
	//list all user role student
	List<Users> getAllUserStudent();

	boolean deleteUser(String id);

	boolean save(Users user);

	List<Users> findAll();

}
