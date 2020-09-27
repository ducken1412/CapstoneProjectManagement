package com.fpt.service;

import java.util.List;

import com.fpt.entity.Users;

public interface UserService {

	Users findById(String id);

	boolean deleteUser(String id);

	boolean save(Users user);

	List<Users> findAll();

}