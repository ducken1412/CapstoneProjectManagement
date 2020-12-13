package com.fpt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.entity.UserRoleKey;
import com.fpt.entity.UserRoles;
import com.fpt.entity.Users;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRolesRepository  extends JpaRepository<UserRoles, UserRoleKey>{
	@Query("SELECT ru.userRoleKey.role.name FROM UserRoles ru WHERE ru.userRoleKey.user.id = ?1")
	List<String> getRoleNamesByUserId(String userId);
	@Modifying
	@Query("DELETE FROM UserRoles ru WHERE ru.userRoleKey.user.id = ?1")
	void removeByUserId(String id);
	
	@Query("SELECT ru.userRoleKey.user FROM UserRoles ru WHERE ru.userRoleKey.role.id = ?1")
	List<Users> getUserByRoleId(Integer id);

	@Query("SELECT ru.userRoleKey.role.name FROM UserRoles ru WHERE ru.userRoleKey.user.email = ?1")
	List<String> getRoleNamesByEmail(String email);

	@Transactional
	@Modifying
	@Query(value = "UPDATE UserRoles u SET u.userRoleKey.role.id = 2 WHERE u.userRoleKey.user.id = ?1")
	void updateRoleStudentReject(String id);

	@Transactional
	@Modifying
	@Query(value = "UPDATE UserRoles u SET u.userRoleKey.role.id = 1 WHERE u.userRoleKey.user.id = ?1")
	void updateRoleLeader(String id);

}
