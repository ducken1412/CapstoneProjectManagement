package com.fpt.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fpt.dto.CapstoneProjectDTO;
import com.fpt.entity.CapstoneProjectDetails;
import com.fpt.entity.CapstoneProjects;
import com.fpt.entity.Users;
import com.fpt.service.CapstoneProjectDetailService;
import com.fpt.service.CapstoneProjectService;
import com.fpt.service.StatusService;
import com.fpt.service.UserService;


@Controller
public class CapstoneProjectController {
	@Autowired
	private CapstoneProjectService projectService;
	
	@Autowired
	private CapstoneProjectDetailService capstoneProjectDetailService;
	
	@Autowired
	private CapstoneProjectService casptoneProjectService;
	
	@Autowired
	private StatusService statusService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/registerproject", method = RequestMethod.GET)
	public String loadDropDown(Model model) {
		List<Users> user = userService.getAllUser();
		model.addAttribute("user", user);
		model.addAttribute("CapstoneProjectDTO", new CapstoneProjectDTO());
		return "home/register-project";
	}
	

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String addRegisterPoject(CapstoneProjectDTO dto, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "register-project";
		}
		CapstoneProjects projects = new CapstoneProjects();
		int statusId = 1;
		projects.setName(dto.getName());
		projects.setNameOther(dto.getNameOther());
		projects.setNameVi(dto.getNameVi());
		projects.setNameAbbreviation(dto.getNameAbbreviation());
		projects.setDescription(dto.getDescription());
		projects.setDocument(dto.getDocument());
//		projects.setProfession(dto.getProfession());
		projects.setSpecialty(dto.getSpecialty());
		projects.setProgram(dto.getProgram());
		projects.setStatus(statusService.getStatusById(statusId));
		projectService.saveRegisterProject(projects);
		
		CapstoneProjectDetails cpd = new CapstoneProjectDetails();
		//cpd.setCapstoneProject(projects.setId(dto.getId()));
		//cpd.setStatus(statusService.getStatusById(statusId));
		
		//lop id by Users Role Student
		for (String str : dto.getMembers()) {
			System.out.println(str);
		}
		return "redirect:";
	}
}
