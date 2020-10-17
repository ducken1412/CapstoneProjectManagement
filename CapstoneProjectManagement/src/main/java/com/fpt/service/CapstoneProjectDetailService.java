package com.fpt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fpt.entity.CapstoneProjectDetails;
import com.fpt.entity.Users;

@Service
public interface CapstoneProjectDetailService {
	//add the capstone project detail
	boolean addCapstonprojectDetail(CapstoneProjectDetails capstoneProjectDetails);
	List<CapstoneProjectDetails> getUserByCapstoneProjectDetailId(Integer id);
	
	List<CapstoneProjectDetails> getUserByCapstioneID(Integer id);

	Integer getProjectIdByUserId(String id);
}
