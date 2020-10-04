package com.fpt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.entity.CapstoneProjectDetails;
import com.fpt.repository.CapstoneProjectDetailRepository;

@Service
public class CapstoneProjectDetailServiceImpl implements CapstoneProjectDetailService{

	@Autowired
	private CapstoneProjectDetailRepository capstoneProjectDetailRepository;
	
	@Override
	public boolean addCapstonprojectDetail(CapstoneProjectDetails capstoneProjectDetails) {
		try {
			capstoneProjectDetailRepository.save(capstoneProjectDetails);
			return true;
		}
		catch (Exception e) {
			System.out.println("eror add capstone project detail");
		}
		return false;
	}
	
}
