package com.fpt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fpt.entity.CapstoneProjects;
import com.fpt.entity.Status;

@Service
public interface CapstoneProjectService {
	List<String> getCapstoneProjectNameByUserId(String UserId);

	// register project to the database
	boolean saveRegisterProject(CapstoneProjects capstoneProjects);

	// get project by Id
	CapstoneProjects getCapstonProjectById(Integer id);
	
}
