package com.fa.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fa.entity.RoleUser;
import com.fa.entity.RoleUserKey;
import com.fa.repository.RoleUserRepository;

@Service
public class RoleUserServiceImpl implements RoleUserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleUserServiceImpl.class);
	
	@Autowired
	private RoleUserRepository roleUserRepository;

	@Override
	public List<String> getRoleNamesByUserId(int userId) {
		return roleUserRepository.getRoleNamesByUserId(userId);
	}

	@Override
	public boolean saveRoleUser(RoleUser roleUser) {
		try {
			roleUserRepository.save(roleUser);
			return true;
		}catch (Exception e) {
			LOGGER.error("Save RoleUser fail :", e);
			return false;
		}
	}
	
	@Override
	public boolean removeAllRoleOfUserByUserId(Integer userId) {
		try {
			roleUserRepository.removeByUserId(userId);
			return true;
		}catch (Exception e) {
			LOGGER.error("Remove all role of user " + userId.intValue() + " fail : ", e);
			return false;
		}
	}
	
	@Override
	public RoleUser isExists(RoleUserKey roleUserKey) {
		return roleUserRepository.findById(roleUserKey).orElse(null);
	}
}
