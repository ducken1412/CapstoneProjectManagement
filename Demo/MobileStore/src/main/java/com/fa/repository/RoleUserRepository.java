package com.fa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.fa.entity.RoleUser;
import com.fa.entity.RoleUserKey;

public interface RoleUserRepository extends JpaRepository<RoleUser, RoleUserKey>{

	@Query("SELECT ru.roleUserKey.role.name FROM RoleUser ru WHERE ru.roleUserKey.user.id = ?1")
	List<String> getRoleNamesByUserId(int userId);
	
	@Modifying
	@Query("DELETE FROM RoleUser ru WHERE ru.roleUserKey.user.id = ?1")
	void removeByUserId(Integer id);
}
