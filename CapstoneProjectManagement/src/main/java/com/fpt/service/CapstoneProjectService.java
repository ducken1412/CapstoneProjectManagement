package com.fpt.service;

import org.springframework.stereotype.Service;
import java.util.List;
import com.fpt.entity.CapstoneProjects;

@Service
public interface CapstoneProjectService {
  public interface CapstoneProjectService {
	List<String> getCapstoneProjectNameByUserId(String UserId);
  
	//register project to the database
	boolean saveRegisterProject(CapstoneProjects capstoneProjects);
	
	//get project by Id
	CapstoneProjects getCapstonProjectById(Integer id);
}
