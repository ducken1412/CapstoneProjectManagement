package com.fpt.service;

import org.springframework.stereotype.Service;

import com.fpt.entity.CapstoneProjectDetails;

@Service
public interface CapstoneProjectDetailService {
	//add the capstone project detail
	boolean addCapstonprojectDetail(CapstoneProjectDetails capstoneProjectDetails);
}
