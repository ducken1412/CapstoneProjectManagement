package com.fpt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.entity.CapstoneProjectDetails;
import com.fpt.entity.Status;
import com.fpt.entity.Users;
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

	@Override
	public List<CapstoneProjectDetails> getUserByCapstoneProjectDetailId(Integer id) {
		return capstoneProjectDetailRepository.getUserByCapstoneProjectDetailId(id);
	}

	@Override
	public List<CapstoneProjectDetails> getUserByCapstioneID(Integer id) {
		//return capstoneProjectDetailRepository.getUserByCapstoneProjectDetailId(id);
		return new ArrayList<>();
	}

	@Override

	public Integer getProjectIdByUserId(String id) {
		return capstoneProjectDetailRepository.getIdProjectByUserID(id);

	public List<Users> getUserByCapstoneProjectDetailId(Integer id) {
		return capstoneProjectDetailRepository.getUserByCapstoneProjectDetailId(id);

	}

//	@Override
//	public List<Status> getStatusByCapStoneProjrectDeatailId(Integer id) {
//		
//		return capstoneProjectDetailRepository.getStatusByCapStoneProjrectDeatailId(id);
//	}

	

	

	
	
}
