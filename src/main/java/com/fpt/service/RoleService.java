package com.fpt.service;

import com.fpt.entity.Roles;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Roles findByName(String name);

    List<Roles> findAll();

    Roles findById(Integer id);
}
