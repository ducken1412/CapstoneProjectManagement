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
import com.fpt.entity.Locations;
import com.fpt.entity.Status;
import com.fpt.entity.Users;
import com.fpt.service.CapstoneProjectDetailService;
import com.fpt.service.StatusService;
import com.fpt.service.UserService;
import com.fpt.utils.Constant;

@Controller
public class DetailProjectController {
	@GetMapping("/detailproject")
	public String detailProject() {
		return "home/detail_project";
	}
@Autowired
private CapstoneProjectDetailService capstoneProjectDetailService;
@Autowired
private UserService userService;
@RequestMapping(value = "/projectdetail/{id}", method = RequestMethod.GET);
public String detailProject(@PathVariable("id") Integer id, Model model, Principal principal) {
	
	CapstoneProjectDetails capstoneProjectDetails = capstoneProjectDetailService.getUserByCapstoneProjectDetailId(id);
	
	
}
}
