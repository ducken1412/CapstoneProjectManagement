package com.fpt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fpt.entity.Users;
import com.fpt.repository.UserRepository;
import com.fpt.repository.UserRolesRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRolesRepository userRoleRepository;

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

	@Override
	public List<Users> getUserByRoleId(Integer id) {
		// TODO Auto-generated method stub
		return userRoleRepository.getUserByRoleId(id);
	}

	//load user role = 3 (lecturers)
	@Override
	public Page<Users> findPaginated(Pageable pageable) {
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		Pageable secondPageWithFiveElements = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());
		return userRepository.getUserByRoleId(secondPageWithFiveElements, 3);
	}
}
