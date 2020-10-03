package com.fpt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.entity.Users;
import com.fpt.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public Users findById(String id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public List<Users> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public List<Users> getAllUserStudent() {
		// return userRepository.getAllUserStudent();
		return null;
	}

	public boolean deleteUser(String id) {
		try {
			userRepository.deleteById(id);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean save(Users user) {
		try {
			userRepository.save(user);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	@Override
	public List<Users> findAll() {
		return userRepository.findAll();
	}
}
