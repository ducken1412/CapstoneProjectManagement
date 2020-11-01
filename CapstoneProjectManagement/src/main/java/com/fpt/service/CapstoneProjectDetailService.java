package com.fpt.service;

import java.util.List;

import com.fpt.entity.CapstoneProjects;
import org.springframework.stereotype.Service;

import com.fpt.entity.CapstoneProjectDetails;
import com.fpt.entity.Status;
import com.fpt.entity.Users;

@Service
public interface CapstoneProjectDetailService {

	boolean addCapstonprojectDetail(CapstoneProjectDetails capstoneProjectDetails);

	List<CapstoneProjectDetails> getDetailByCapstoneProjectId(Integer id);
	
	List<CapstoneProjectDetails> getUserByCapstioneID(Integer id);

	List<Integer> getProjectIdByUserId(String id);
	
	List<Integer> getIdProjectByUserIDCheckApprove(String id);

	Integer getStatuByCapstoneProjectDetailIdAndUserId(Integer cpId, Integer userId);

	List<Users> getUserByCapstoneProjectDetailId(Integer id);


	Integer countLecturersByProjectId(Integer id);

	Integer updateStatusUserProject(String uid,Integer pid);

	Users findUserByStatusRegisted(String id);

	Integer deleteCapstoneProjectDetailsByUserId(String uid, Integer pid);

	Integer deleteRejectCapstoneProjectDetailsByUserId(String uid, Integer pid);

	CapstoneProjects findCapstoneProjectByUserId(String id);

	List<Users> getUserStudentMemberByProjectId(Integer id);

	Integer getOneProjectIdByUserId(String id);
}
