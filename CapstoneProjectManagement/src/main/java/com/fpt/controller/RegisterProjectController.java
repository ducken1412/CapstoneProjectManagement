package com.fpt.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fpt.dto.RegisterProjectDTO;
import com.fpt.entity.CapstoneProjects;
import com.fpt.repository.RegisterProjectRepository;
import com.fpt.service.RegisterProjectService;
import com.fpt.service.StatusService;

@Controller

public class RegisterProjectController {
	@GetMapping("/registerproject")
	public String registerProject() {
		return "home/register-project";
	}
	
	@Autowired
	private RegisterProjectService projectService;
	
	@Autowired
	private StatusService statusService;
	
	
	//add a project in the database
	@ResponseBody
	@RequestMapping(value= "/register", method= RequestMethod.POST)
	public String addRegisterPoject(RegisterProjectDTO dto ,BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "register-project";
		}
		CapstoneProjects projects = new CapstoneProjects();
		projects.setName(dto.getName());
		projects.setDescription(dto.getDescription());
		projects.setDocument(dto.getDocument());
		projects.setProfession(dto.getProfession());
		projects.setSpecialty(dto.getSpecialty());
		projects.setProgram(dto.getProgram());
		projects.setStatus(statusService.getStatusById(dto.getStautus_id()));
		//System.out.println(capstoneProjects);
		projectService.saveRegisterProject(projects);
		return "redirect:list";
	}
}
