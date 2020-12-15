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


import com.fpt.entity.Status;
import com.fpt.entity.Users;
import com.fpt.service.CapstoneProjectService;
import com.fpt.service.StatusService;
import com.fpt.service.UserRoleService;
import com.fpt.service.UserService;
import com.fpt.utils.Constant;
@Controller
public class UserController {
	
	@GetMapping("/viewdetail")
	public String viewDetail() {
		return "home/view-detail";
	}
	@GetMapping("/error")
	public String eror() {
		return "home/error";
	}
	@Autowired
	private StatusService statusService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private CapstoneProjectService capstoneProjectService;
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public String userProfile(@PathVariable("id") String id, Model model, Principal principal) {
		if(principal == null) {
			return "redirect:/login";
		}

		Users user = userService.findById(id);
		if (user == null) {
			return "home/error";
		}
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		System.out.println(formatter.format(user.getBirthDate()));
		String parsedDate = formatter.format(user.getBirthDate());
		model.addAttribute("dob", parsedDate);
		String parseDate = formatter.format(user.getCreatedDate());
		model.addAttribute("do", parseDate);
		model.addAttribute("user", user);
		List<String> role = userRoleService.getRoleNamesByUserId(id);
		List<String> roleView = new ArrayList<>();
		for (String string : role) {
			if (string.equals(Constant.ROLE_HEAD_DB)) {
				roleView.add(Constant.ROLE_HEAD);
			}
			if (string.equals(Constant.ROLE_LECTURERS_DB)) {
				roleView.add(Constant.ROLE_LECTURERS);
			}
			if (string.equals(Constant.ROLE_STUDENT_LEADER_DB)) {
				roleView.add(Constant.ROLE_STUDENT_LEADER);
			}
			if (string.equals(Constant.ROLE_STUDENT_MEMBER_DB)) {
				roleView.add(Constant.ROLE_STUDENT_MEMBER);
			}
			if (string.equals(Constant.ROLE_TRAINING_DEP_DB)) {
				roleView.add(Constant.ROLE_TRAINING_DEP);
			}
		}
		if (user.getGender() == 1) {
			model.addAttribute("gender", "male");
		} else {
			model.addAttribute("gender", "female");

		}
		Status status = user.getStatus();
		if (status == null) {
			model.addAttribute("status", "");
		} else {
			String nameStatus = user.getStatus().getName();
			switch (nameStatus) {
				case Constant.STATUS_NOT_ELIGIBLE_CAPSTONE_DB:
					model.addAttribute("status", Constant.STATUS_NOT_ELIGIBLE_CAPSTONE);
				case Constant.STATUS_ELIGIBLE_CAPSTONE_DB:
					model.addAttribute("status", Constant.STATUS_ELIGIBLE_CAPSTONE);
				case Constant.STATUS_INACTIVE_USER_DB:
					model.addAttribute("status", Constant.STATUS_INACTIVE_USER);
				case Constant.STATUS_REGISTERING_CAPSTONE_DB:
					model.addAttribute("status", Constant.STATUS_REGISTERING_CAPSTONE);
				case Constant.STATUS_REGISTED_CAPSTONE_DB:
					model.addAttribute("status", Constant.STATUS_REGISTED_CAPSTONE);
				case Constant.STATUS_APPROVE_CAPSTONE_LUCTURER_DB:
					model.addAttribute("status", Constant.STATUS_APPROVE_CAPSTONE_LECTURER);
				case Constant.STATUS_APPROVE_CAPSTONE_TRAINING_DB:
					model.addAttribute("status", Constant.STATUS_APPROVE_CAPSTONE_TRAINING);
				case Constant.STATUS_APPROVE_CAPSTONE_HEAD_DB:
					model.addAttribute("status", Constant.STATUS_APPROVE_CAPSTONE_HEAD);
				case Constant.STATUS_DOING_CAPSTONE_DB:
					model.addAttribute("status", Constant.STATUS_DOING_CAPSTONE);
				case Constant.STATUS_NOT_ELIGIBLE_DEFENCE_CAPSTONE_DB:
					model.addAttribute("status", Constant.STATUS_NOT_ELIGIBLE_DEFENCE_CAPSTONE);
				case Constant.STATUS_ELIGIBLE_DEFENCE_CAPSTONE_DB:
					model.addAttribute("status", Constant.STATUS_ELIGIBLE_DEFENCE_CAPSTONE);
				case Constant.STATUS_REJECT_CAPSTONE_DB:
					model.addAttribute("status", Constant.STATUS_REJECT_CAPSTONE);
				case Constant.STATUS_CHANGING_NAME_CAPSTONE_DB:
					model.addAttribute("status", Constant.STATUS_CHANGING_NAME_CAPSTONE);
				case Constant.STATUS_PENDING_CAPSTONE_DB:
					model.addAttribute("status", Constant.STATUS_PENDING_CAPSTONE);

			}
		}

		model.addAttribute("roleView", roleView);
		List<String> capstone = capstoneProjectService.getCapstoneProjectNameByUserId(id);
		model.addAttribute("capstone", capstone);
		return "home/view-detail";
		}
	}
		
		

