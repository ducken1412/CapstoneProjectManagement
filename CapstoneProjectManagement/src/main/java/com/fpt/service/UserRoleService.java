package com.fpt.service;

import java.util.List;

import com.fpt.entity.UserRoleKey;
import com.fpt.entity.UserRoles;
import com.fpt.entity.Users;


public interface UserRoleService {

	List<String> getRoleNamesByUserId(String userId);

	boolean saveRoleUser(UserRoles roleUser);

	boolean removeAllRoleOfUserByUserId(String userId);

	UserRoles isExists(UserRoleKey roleUserKey);
	List<String> getRoleNamesByEmail(String email);

	void updateRoleStudentReject(String id);

	void updateRoleLeader(String id);

}