package com.fpt.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fpt.dto.CapstoneProjectDTO;
import com.fpt.entity.CapstoneProjects;
import com.fpt.entity.Users;
import com.fpt.service.CapstoneProjectService;
import com.fpt.service.StatusService;
import com.fpt.service.UserService;


@Controller
public class CapstoneProjectController {
	@Autowired
	private CapstoneProjectService projectService;
	@Autowired
	private StatusService statusService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/registerproject", method = RequestMethod.GET)
	public String loadDropDown(Model model) {
		List<Users> user = userService.getAllUser();
		model.addAttribute("user", user);
		return "home/register-project";
	}
	

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String addRegisterPoject(CapstoneProjectDTO dto, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "register-project";
		}
		CapstoneProjects projects = new CapstoneProjects();
		int status_id = 1;
		projects.setName(dto.getName());
		projects.setDescription(dto.getDescription());
		projects.setDocument(dto.getDocument());
		projects.setProfession(dto.getProfession());
		projects.setSpecialty(dto.getSpecialty());
		projects.setProgram(dto.getProgram());
		projects.setStatus(statusService.getStatusById(status_id));
		projectService.saveRegisterProject(projects);
		return "redirect:";
	}
}
