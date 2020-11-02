package com.fpt.service;

import java.security.Principal;
import java.util.List;

import com.fpt.dto.CapstoneProjectDTO;
import org.springframework.stereotype.Service;

import com.fpt.entity.CapstoneProjects;
import com.fpt.entity.Status;

public interface CapstoneProjectService {
	List<String> getCapstoneProjectNameByUserId(String UserId);

	// register project to the database
	boolean saveRegisterProject(CapstoneProjects capstoneProjects);

	// get project by Id
	CapstoneProjects getCapstonProjectById(Integer id);

	//get all project
	List<CapstoneProjects> getAllProject();

	String registerProject(CapstoneProjectDTO dataForm, Principal principal, String baseUrl);

	// get all
	List<Object[]> getAllByUserId(String UserId, Integer PageIndex, Integer PageSize,Integer status,Integer profession,String nameSearch);
	CapstoneProjects findById(Integer id);
	Integer getCountStudent (Integer id);

}
