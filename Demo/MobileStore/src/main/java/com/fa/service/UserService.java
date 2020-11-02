package com.fa.service;

import java.util.List;

import com.fa.dto.UserDTO;
import com.fa.entity.Users;

public interface UserService {

	Users findByEmail(String email);

	Users findByUserName(String username);

	Users findByUserNameAndStatus(String username);

	List<Users> findByStatus();

	List<UserDTO> getAllUserDTOActive();

//	Page<Users> findAll(Integer page, Integer size);

	Users findByEmailAndStatus(String email, int status);

	Users findByUserNameAndStatus(String userName, int status);

	boolean saveUser(Users user);

	Users findById(int id);

	boolean unActiveUser(Users user);

	boolean isAdmin(Users user);

	boolean editProfile(UserDTO user, Users userActual);

	/* @Transactional(rollbackFor = Exception.class) */
	boolean changePassword(String oldPassword, String newPassword, String confirmNewPassword, Users user) throws Exception;
	
	UserDTO getUser(int id);
	
	UserDTO getUser(String userName);
	
	boolean editUser(UserDTO userDto, List<String> listRole);
}