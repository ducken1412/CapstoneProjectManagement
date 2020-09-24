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

import com.fpt.entity.CapstoneProjects;
import com.fpt.repository.RegisterProjectRepository;

@Controller

public class RegisterProjectController {
	@GetMapping("/registerproject")
	public String registerProject() {
		return "home/register-project";
	}
	
	@Autowired
	private RegisterProjectRepository registerProjectRepository;
	
	
	
	
	//add a project in the database
	@ResponseBody
	@RequestMapping(value= "/register", method= RequestMethod.POST)
	public String addRegisterPoject(CapstoneProjects capstoneProjects ,BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "register-project";
		}

		this.registerProjectRepository.save(capstoneProjects);
		return "redirect:list";
	}
}
