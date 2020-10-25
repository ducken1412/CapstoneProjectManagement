package com.fpt.service;

import java.util.List;

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

	Integer getStatuByCapstoneProjectDetailIdAndUserId(Integer cpId, Integer userId);

	List<Users> getUserByCapstoneProjectDetailId(Integer id);


	Integer countLecturersByProjectId(Integer id);

	Integer updateStatusUserProject(String uid,Integer pid);

	Users findUserByStatusRegisted(String id);

	//Integer deleteCapstoneProjectDetailsByUserId(String id);

}
