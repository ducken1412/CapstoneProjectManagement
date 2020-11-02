package com.fpt.repository;

import com.fpt.entity.Roles;
import com.fpt.entity.UserRoleKey;
import com.fpt.entity.UserRoles;
import com.fpt.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Integer>{
	Roles findRolesByName(String name);
}
