package com.fpt.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fpt.dto.UserDTO;
import com.fpt.entity.Users;
import com.fpt.service.UserService;
@Controller
public class ViewUserDetailController {
	
	@GetMapping("/viewdetail")
	public String viewDetail() {
		return "home/view-detail";
	}
	@Autowired
	private UserService userService;
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public String userProfile(@PathVariable("id") String id, Model model, Principal principal) {
		Users user = userService.findById(id);
		model.addAttribute("user", user);
		
		return "home/view-detail";
	}

	
}
