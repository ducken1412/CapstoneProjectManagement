package com.fa.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fa.entity.Role;
import com.fa.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public boolean saveRole(Role role) {
		try {
			roleRepository.save(role);
			return true;
		}catch (Exception e) {
			LOGGER.error("Error while save role", e);
			return false;
		}
	}

	@Override
	public List<Role> getAll() {
		return roleRepository.findAll();
	}

	@Override
	public Role getRoleByName(String name) {
		return roleRepository.findByName(name);
	}
}
