package com.fpt.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.fpt.entity.Users;
import com.fpt.service.UserRoleService;
import com.fpt.service.UserService;
import com.fpt.utils.Constant;
@Controller
public class UserController {
	
	@GetMapping("/viewdetail")
	public String viewDetail() {
		return "home/view-detail";
	}
	@Autowired
	private UserService userService;
	@Autowired
	private UserRoleService userRoleService;
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public String userProfile(@PathVariable("id") String id, Model model, Principal principal) {
		Users user = userService.findById(id); 
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		System.out.println(formatter.format(user.getBirthDate()));
		String parsedDate =  formatter.format(user.getBirthDate());
		model.addAttribute("dob", parsedDate);
		String parseDate =  formatter.format(user.getCreatedDate());
		model.addAttribute("do", parseDate);
		model.addAttribute("user", user);
		List<String> role= userRoleService.getRoleNamesByUserId(id);
		List<String> roleView= new ArrayList<>();
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
		model.addAttribute("roleView",roleView);
		return "home/view-detail";
		
		
		
		
	}

	

	
}
