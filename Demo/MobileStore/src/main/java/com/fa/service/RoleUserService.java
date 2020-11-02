package com.fa.service;

import java.util.List;

import com.fa.entity.RoleUser;
import com.fa.entity.RoleUserKey;

public interface RoleUserService {

	List<String> getRoleNamesByUserId(int userId);

	boolean saveRoleUser(RoleUser roleUser);

	boolean removeAllRoleOfUserByUserId(Integer userId);

	RoleUser isExists(RoleUserKey roleUserKey);

}