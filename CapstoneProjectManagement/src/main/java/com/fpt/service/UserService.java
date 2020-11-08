package com.fpt.service;

import java.util.List;
import java.util.Optional;

import com.fpt.entity.Reports;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
	List<Users> getUserByRoleId(Integer id);
	
	List<Users> findByUsername(String username);

	Users findByEmail(String email);

	//pagination User
	Page<Users> findPaginated(Pageable pageable);

}
