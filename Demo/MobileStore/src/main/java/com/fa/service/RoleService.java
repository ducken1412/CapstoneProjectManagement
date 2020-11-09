package com.fa.service;

import java.util.List;

import com.fa.entity.Role;

public interface RoleService {

	boolean saveRole(Role role);

	List<Role> getAll();

	Role getRoleByName(String name);

}