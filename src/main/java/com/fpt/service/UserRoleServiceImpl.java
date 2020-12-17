package com.fpt.service;

import java.util.List;

import com.fpt.entity.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.entity.UserRoleKey;
import com.fpt.entity.UserRoles;
import com.fpt.repository.UserRolesRepository;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserRoleServiceImpl implements UserRoleService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleServiceImpl.class);
	
	@Autowired
	private UserRolesRepository userRolesRepository;

	

	@Override
	public boolean saveRoleUser(UserRoles userRole) {
		try {
			userRolesRepository.save(userRole);
			return true;
		}catch (Exception e) {
			LOGGER.error("Save RoleUser fail :", e);
			return false;
		}
	}
	
	@Override
	public boolean removeAllRoleOfUserByUserId(String userId) {
		try {
			userRolesRepository.removeByUserId(userId);
			return true;
		}catch (Exception e) {
			LOGGER.error("Remove all role of user " + userId + " fail : ", e);
			return false;
		}
	}
	
	@Override
	public UserRoles isExists(UserRoleKey roleUserKey) {
		return userRolesRepository.findById(roleUserKey).orElse(null);
	}

	@Override
	public List<String> getRoleNamesByEmail(String email) {
		return userRolesRepository.getRoleNamesByEmail(email);
	}

	@Transactional
	@Override
	public void updateRoleStudentReject(String id) {
		userRolesRepository.updateRoleStudentReject(id);
	}

	@Override
	public void updateRoleLeader(String id) {
		userRolesRepository.updateRoleLeader(id);
	}

	@Override
	public List<String> getRoleNamesByUserId(String userId) {
		return userRolesRepository.getRoleNamesByUserId(userId);
	}
}
