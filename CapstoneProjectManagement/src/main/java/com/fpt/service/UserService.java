package com.fpt.service;

import java.util.Date;
import java.util.List;

import com.fpt.dto.UserManagementDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fpt.entity.Users;

public interface UserService {

	Users findById(String id);
	
	//list all user, all role
	List<Users> getAllUser();
	
	//list all user role student
	List<UserManagementDTO> getAllUserStudent(String site, String semester);

	boolean deleteUser(String id);

	boolean save(Users user);

	boolean saveAll(List<Users> user);

	List<Users> findAll();
	List<Users> getUserByRoleId(Integer id);
	
	List<Users> findByUsername(String username);

	Users findByEmail(String email);

	//pagination User
	Page<Users> findPaginated(Pageable pageable);

	Page<Users> findPaginatedNotLecture2Booked(Pageable pageable,String lId1, String lId2);

	Page<Users> findPaginatedNotLectureBooked(Pageable pageable,String lId);

	Users getUserByUserName(String id);

	List<Users> getUserByUserRoleAndProjectId(Integer id, Integer cid);

	//get user by status project
	List<UserManagementDTO> getUserStudentByStatusId(Integer id);

	//count student
	Integer countStudent(Integer id,String site, String semester);

	//get all student has no team project
	List<UserManagementDTO> getAllUserStudentHasNoTeam(String site, String semester);

	//count student has no team
	Integer countStudentHasNoTeam(String site, String semester);

	//count all student by site, semester
	Integer countAllStudent(String site, String semester);

	//count all student Eligible Capstone by site, semester
	Integer countStudentEligibleCapstone(String site, String semester);

	boolean updateProfileByUserId(String des, String phone, String address, String img, Date date,String uid);

	boolean updateStatusByUserId(int status,String uid);

	//count check leader
	Integer checkCountLeader(String uId, Integer cpId);
}
