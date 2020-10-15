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
import com.fpt.service.UserRoleService;
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
	@Autowired
	private UserRoleService userRoleService ; 
	@Autowired
	private StatusService statusService;

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

	@RequestMapping(value = "/projectDetail/{id}", method = RequestMethod.GET)
	public String detailProject(@PathVariable("id") Integer id, Model model, Principal principal) {
		CapstoneProjects cp = capstoneProjectService.getCapstonProjectById(id);
		model.addAttribute("detail", cp);
		List<Users> userproject= capstoneProjectDetailService.getUserByCapstoneProjectDetailId(id);
		model.addAttribute("userproject", userproject);
		List<String> statusProject= new ArrayList<>();
		
		List<String> roleView= new ArrayList<>();
		for (Users users : userproject) {
			String user_id = users.getId();
			
			List<String> role= userRoleService.getRoleNamesByUserId(user_id);
			for (String string : role) {
				if(string.equals(Constant.ROLE_HEAD_DB)) {
					roleView.add(Constant.ROLE_HEAD);
				}
				if(string.equals(Constant.ROLE_LECTURERS_DB)) {
					roleView.add(Constant.ROLE_LECTURERS);
				}
				if(string.equals(Constant.ROLE_STUDENT_LEADER_DB)) {
					roleView.add(Constant.ROLE_STUDENT_LEADER);
				}
				if(string.equals(Constant.ROLE_STUDENT_MEMBER_DB)) {
					roleView.add(Constant.ROLE_STUDENT_MEMBER);
				}
				if(string.equals(Constant.ROLE_TRAINING_DEP_DB)) {
					roleView.add(Constant.ROLE_TRAINING_DEP);
				}
				
		}
		}
		
//		for (Users users : userproject) {
//			int adsa = users.getStatus();
//			
//			List<String> status= userService.findById(user_id);
//			for (String string : role) {
//				if(string.equals(Constant.ROLE_HEAD_DB)) {
//					roleView.add(Constant.ROLE_HEAD);
//				}
//				if(string.equals(Constant.ROLE_LECTURERS_DB)) {
//					roleView.add(Constant.ROLE_LECTURERS);
//				}
//				if(string.equals(Constant.ROLE_STUDENT_LEADER_DB)) {
//					roleView.add(Constant.ROLE_STUDENT_LEADER);
//				}
//				if(string.equals(Constant.ROLE_STUDENT_MEMBER_DB)) {
//					roleView.add(Constant.ROLE_STUDENT_MEMBER);
//				}
//				if(string.equals(Constant.ROLE_TRAINING_DEP_DB)) {
//					roleView.add(Constant.ROLE_TRAINING_DEP);
//				}
//				
//		}
//		}
		
//		model.addAttribute("statusProject", statusProject);
		
		model.addAttribute("roleView",roleView);
		
		return "home/detail_project";
	}
}


