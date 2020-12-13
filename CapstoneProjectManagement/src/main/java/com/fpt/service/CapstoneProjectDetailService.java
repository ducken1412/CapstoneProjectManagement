package com.fpt.service;

import java.util.List;
import java.util.Objects;

import com.fpt.entity.*;
import org.springframework.stereotype.Service;

@Service
public interface CapstoneProjectDetailService {

	boolean addCapstonprojectDetail(CapstoneProjectDetails capstoneProjectDetails);

	List<CapstoneProjectDetails> getDetailByCapstoneProjectId(Integer id);
	
	List<CapstoneProjectDetails> getUserByCapstioneID(Integer id);

	List<Integer> getProjectIdByUserId(String id);
	
	List<Integer> getIdProjectByUserIDCheckApprove(String id);

	Integer getStatuByCapstoneProjectDetailIdAndUserId(Integer cpId, Integer userId);

	List<Users> getUserByCapstoneProjectDetailId(Integer id);

	Integer countLecturersIdAndCapstoneProjectIdOP1(Integer cid);

	Integer countLecturersIdAndCapstoneProjectIdOP2(Integer cid);

	Integer countLecturersByProjectId(Integer id);

	Integer updateStatusUserProject(String uid,Integer pid);

	Users findUserByStatusRegisted(String id);

	Integer deleteCapstoneProjectDetailsByUserId(String uid, Integer pid);

	Integer deleteRejectCapstoneProjectDetailsByUserId(String uid, Integer pid);

	CapstoneProjects findCapstoneProjectByUserId(String id);

	List<Users> getUserStudentMemberByProjectId(Integer id);

	Integer getOneProjectIdByUserId(String id);

	//kienbt4 add code capstone start
	List<Users> getUserById(Integer id);
	List<Status> getStatusById(Integer id);
	List<Object[]> getByProjectId(Integer id);

	CapstoneProjectDetails findById(Integer id);
	boolean save(CapstoneProjectDetails capstoneProjectDetails);
	//kienbt4 add code capstone end

	Users userLecturersIdAndCapstoneProjectIdOP1(Integer id);

	Users userLecturersIdAndCapstoneProjectIdOP2(Integer id);


	List<Users> getUserWaitingApproveByCapstoneProjectDetailId(Integer id);


	List<Users> getUserByCapstoneProject(Integer id);

	List<UserRoles> listUserRoleByProjectId(Integer id);

	List<Object[]> getUserEditByCapstoneProject(Integer id);

	CapstoneProjectDetails checkUserCapstoneDetail(String username, Integer id);

	List<Users> findUserByCapstoneProjectDetailId(Integer id);
}
