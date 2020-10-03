package com.fpt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.entity.UserRoleKey;
import com.fpt.entity.UserRoles;
@Repository
public interface UserRolesRepository  extends JpaRepository<UserRoles, UserRoleKey>{
	@Query("SELECT ru.userRoleKey.role.name FROM UserRoles ru WHERE ru.userRoleKey.user.id = ?1")
	List<String> getRoleNamesByUserId(String userId);
	@Modifying
	@Query("DELETE FROM UserRoles ru WHERE ru.userRoleKey.user.id = ?1")
	void removeByUserId(Integer id);
	

}
