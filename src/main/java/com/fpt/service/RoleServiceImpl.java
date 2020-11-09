package com.fpt.service;

import com.fpt.entity.Roles;
import com.fpt.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Roles findByName(String name) {
        return roleRepository.findRolesByName(name);
    }
}
