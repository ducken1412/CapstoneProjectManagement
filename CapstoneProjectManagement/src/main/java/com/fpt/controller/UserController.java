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

import com.fpt.entity.Locations;
import com.fpt.entity.Status;
import com.fpt.entity.Users;
import com.fpt.service.CapstoneProjectService;
import com.fpt.service.LocationService;
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
	private LocationService locationService;
	@Autowired
	private CapstoneProjectService capstoneProjectService;
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public String userProfile(@PathVariable("id") String id, Model model, Principal principal) {
		
		
		Users user = userService.findById(id); 
		if(user == null){
			return "home/error";
		}
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
		if(user.getGender()==1) {
			model.addAttribute("gender","male");
		}
		else {
			model.addAttribute("gender","female");
			
		}
		Status status= user.getStatus();
		if(status==null) {
			model.addAttribute("status","");
		}
		else {
			model.addAttribute("status",user.getStatus().getName());
		}
		
		
		model.addAttribute("roleView",roleView);
		List<String> capstone= capstoneProjectService.getCapstoneProjectNameByUserId(id);
		model.addAttribute("capstone",capstone);
	List<Locations> location= locationService.getLocationByUserId(id);
	if(location.size()>0) {
	model.addAttribute("location",location.get(0).getCity()+ "," + location.get(0).getStreet());
	}else {
		model.addAttribute("location","");
	}
		return "home/view-detail";
	}
		
		

	

	
}
