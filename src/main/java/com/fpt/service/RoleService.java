package com.fpt.service;

import com.fpt.entity.Roles;
import com.fpt.repository.RoleRepository;

public interface RoleService {
    Roles findByName(String name);
}
