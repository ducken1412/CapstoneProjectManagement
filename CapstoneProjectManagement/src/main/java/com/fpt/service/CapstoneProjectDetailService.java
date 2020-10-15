package com.fpt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fpt.entity.CapstoneProjectDetails;
import com.fpt.entity.Status;
import com.fpt.entity.Users;

@Service
public interface CapstoneProjectDetailService {
	//add the capstone project detail
	boolean addCapstonprojectDetail(CapstoneProjectDetails capstoneProjectDetails);
	List<Users> getUserByCapstoneProjectDetailId(Integer id);
	
	List<CapstoneProjectDetails> getUserByCapstioneID(Integer id);
//	List<Status> getStatusByCapStoneProjrectDeatailId(Integer id);
}
