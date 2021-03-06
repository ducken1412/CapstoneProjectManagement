package com.fpt.service;

import java.security.Principal;
import java.util.List;

import com.fpt.dto.CapstoneProjectDTO;
import com.fpt.entity.Users;
import org.springframework.stereotype.Service;

import com.fpt.entity.CapstoneProjects;
import com.fpt.entity.Status;

import javax.servlet.http.HttpServletResponse;

public interface CapstoneProjectService {
	List<String> getCapstoneProjectNameByUserId(String UserId);

	// register project to the database
	boolean saveRegisterProject(CapstoneProjects capstoneProjects);

	// get project by Id
	CapstoneProjects getCapstonProjectById(Integer id);

	//get all project
	List<CapstoneProjects> getAllProject();

	String registerProject(CapstoneProjectDTO dataForm, Principal principal, String baseUrl, HttpServletResponse response);

	// get all
	List<Object[]> getAllByUserId(String UserId, Integer PageIndex, Integer PageSize,Integer status,Integer profession,String nameSearch);

	List<Object[]> getAllCap(Integer status, Integer profession, String nameSearch);

	Integer countCapAll(String UserId,Integer status,Integer profession,String nameSearch);

	CapstoneProjects findById(Integer id);
	Integer getCountStudent (Integer id);
	CapstoneProjects getCapstoneProjectByUserId(String userId);

	boolean updateStatusCapstoneProjectSendTD(Integer id);

	boolean deleteUserNotSubmitCapstone(Integer id);

	String updateProject(CapstoneProjectDTO dataForm,Principal principal, String baseUrl,Integer projectId);

	boolean updateStatusCapstoneProjectChangingName(Integer id);

	boolean capstoneProjectChangingName(String nameC, String nameV, Integer id);

	CapstoneProjects getCapstoneProjecRegistingtByUserId(String userId);

	CapstoneProjects getCapstoneProjectRegistedByUserId(String userId);

	List<Users> findUserByCapstoneProjectId(Integer id);

	List<CapstoneProjects> findCapstoneProjectRegistedBySupervisorId(String userId);

	boolean updateSupervisorsSubmitCapstone(Integer id);
}
