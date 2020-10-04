package com.fpt.service;

import org.springframework.stereotype.Service;

import com.fpt.entity.CapstoneProjects;

@Service
public interface CapstoneProjectService {
	//register project to the database
	boolean saveRegisterProject(CapstoneProjects capstoneProjects);
	
	//get project by Id
	CapstoneProjects getCapstonProjectById(Integer id);
}
