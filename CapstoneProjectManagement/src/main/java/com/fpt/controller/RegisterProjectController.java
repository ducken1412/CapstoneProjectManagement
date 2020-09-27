package com.fpt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fpt.dto.RegisterProjectDTO;
import com.fpt.entity.CapstoneProjects;
import com.fpt.entity.Users;
import com.fpt.service.RegisterProjectService;
import com.fpt.service.StatusService;
import com.fpt.service.UserService;

@Controller
public class RegisterProjectController {
//	@GetMapping("/registerproject")
//	public String registerProject() {
//		return "home/register-project";
//	}

	@Autowired
	private RegisterProjectService projectService;

	@Autowired
	private StatusService statusService;

	@Autowired
	private UserService userService;

	@GetMapping("/registerproject")
	public String getRegisterForm(Model model) {
		List<Users> users = userService.findAll();
		System.out.println(users);
		model.addAttribute("users", users);
		return "home/register-project";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String addRegisterPoject(RegisterProjectDTO dto, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "register-project";
		}
		CapstoneProjects projects = new CapstoneProjects();
		// register status = 1 (seding)
		int status_id = 1;
		projects.setName(dto.getName());
		projects.setDescription(dto.getDescription());
		projects.setDocument(dto.getDocument());
		projects.setProfession(dto.getProfession());
		projects.setSpecialty(dto.getSpecialty());
		projects.setProgram(dto.getProgram());
		projects.setStatus(statusService.getStatusById(status_id));
		// System.out.println(capstoneProjects);
		projectService.saveRegisterProject(projects);
		return "redirect:list";
	}
}
