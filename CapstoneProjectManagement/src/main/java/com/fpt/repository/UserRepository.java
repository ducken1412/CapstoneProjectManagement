package com.fpt.repository;

import java.util.List;
import java.util.Optional;

import com.fpt.entity.Reports;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.entity.Users;
@Repository
public interface UserRepository  extends JpaRepository<Users, String>{	
	
//	@Query("select ur.user_id, u.user_name\r\n" + 
//			"from user_roles as ur, roles as r, users as u\r\n" + 
//			"where ur.role_id = r.id and ur.user_id = u.id and r.id = 2")
	//List<UserDTO> getAllUserStudent;
	List<Users> findByUsername(String username);
	Optional<Users> findByEmail(String email);
	
	//List<UserDTO> getAllUserStudent;
		@Query("SELECT ru.userRoleKey.user FROM UserRoles ru WHERE ru.userRoleKey.role.id = ?1")
		Page<Users> getUserByRoleId(Pageable pageable,Integer id);

	@Query("select r from Users r where r.id = ?1")
	Page<Reports> findReportByUserId(Pageable pageable,String id);

	@Query("SELECT ru.userRoleKey.user FROM UserRoles ru WHERE ru.userRoleKey.role.id = ?1 and ru.userRoleKey.user.id <> ?2 and ru.userRoleKey.user.id <> ?3")
	Page<Users> getLectureNoteBooked2ByRoleId(Pageable pageable,Integer id, String lId1, String lId2);

	@Query("SELECT ru.userRoleKey.user FROM UserRoles ru WHERE ru.userRoleKey.role.id = ?1 and ru.userRoleKey.user.id <> ?2")
	Page<Users> getLectureNoteBookedByRoleId(Pageable pageable,Integer id, String lId);

	@Query("select u from Users u where u.username =?1")
	Users getUserByUserName(String id);

	@Query(value = "select u.* \n" +
			"from users as u \n" +
			"join user_roles as ur on u.id = ur.user_id\n" +
			"join roles as r on r.id = ur.role_id\n" +
			"join capstone_project_details as cp on u.id = cp.user_id\n" +
			"join capstone_projects as c on c.id = cp.capstone_project_id\n" +
			"where r.id = ?1 and c.id =?2",nativeQuery = true)
	List<Users> getUserByUserRoleAndProjectId(Integer id, Integer cid);

}
