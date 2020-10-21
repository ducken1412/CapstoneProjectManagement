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

	Integer getProjectIdByUserId(String id);

	Integer getStatuByCapstoneProjectDetailIdAndUserId(Integer cpId, Integer userId);

	List<Users> getUserByCapstoneProjectDetailId(Integer id);

	Integer countLecturersByProjectId(Integer id);
}
