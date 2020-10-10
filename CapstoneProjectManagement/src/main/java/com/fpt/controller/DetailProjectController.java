package com.fpt.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fpt.entity.CapstoneProjectDetails;
import com.fpt.entity.CapstoneProjects;
import com.fpt.entity.Locations;
import com.fpt.entity.Status;
import com.fpt.entity.Users;
import com.fpt.service.CapstoneProjectDetailService;
import com.fpt.service.CapstoneProjectService;
import com.fpt.service.StatusService;
import com.fpt.service.UserService;
import com.fpt.utils.Constant;

@Controller
public class DetailProjectController {

	@Autowired
	private CapstoneProjectDetailService capstoneProjectDetailService;

	@Autowired
	private CapstoneProjectService capstoneProjectService;

	@Autowired
	private UserService userService;

	@GetMapping("/detailproject")
	public String detailProject() {
		// CapstoneProjectDetails capstoneProjectDetails =
		// capstoneProjectDetailService.getUserByCapstoneProjectDetailId(id);
		// load capstone project detail
//		CapstoneProjects cp = capstoneProjectService.getCapstonProjectById();
		
//		System.out.println(cp.getName());
//		System.out.println(cp.getDescription());
//		System.out.println(cp.getDocument());
//		System.out.println(cp.getNameVi());
//		System.out.println(cp.getSpecialty());

		// load user thuoc project
		
		return "home/detail_project";
		
	}

	@RequestMapping(value = "/projectDetail/{id}", method = RequestMethod.POST)
	public String detailProject(@PathVariable("id") Integer id, Model model, Principal principal) {
		CapstoneProjects cp = capstoneProjectService.getCapstonProjectById(id);
		model.addAttribute("detail", cp);
		
		List<CapstoneProjectDetails> cpd = capstoneProjectDetailService.getUserByCapstioneID(31);
		for (int i = 0; i < cpd.size(); i++) {
			System.out.println(cpd.get(i).getUser());
			
		}
		return "home/detail_project";
	}

}