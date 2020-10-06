package com.fpt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.entity.Users;
@Repository
public interface UserRepository  extends JpaRepository<Users, String>{	
	
//	@Query("select ur.user_id, u.user_name\r\n" + 
//			"from user_roles as ur, roles as r, users as u\r\n" + 
//			"where ur.role_id = r.id and ur.user_id = u.id and r.id = 2")
	//List<UserDTO> getAllUserStudent;
	List<Users> findByUsername(String username);
	
}
