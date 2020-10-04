package com.fpt.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fpt.controller.ListLecturersController;
import com.fpt.dto.ListLecturersDTO;
import com.fpt.entity.Users;
import com.fpt.service.ListLecturersService;
import com.fpt.dto.ListLecturersDTO;
import com.fpt.service.ListLecturersService;
@Controller

public class ListLecturersController {
	/*
	@GetMapping("/listlecturersproject")
	public String registerProject() {
		return "home/listlecturers";
	}
	*/
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ListLecturersController.class);
	
	@Autowired
	private ListLecturersService lecturersServiceImpl;
	
	@RequestMapping(value = "/listlecturersproject", method = RequestMethod.GET)
	public String getListLecturers(Model model) {
		LOGGER.info("Running on getListLecturers method of UserController");
		
		List<Users> lecturer = lecturersServiceImpl.getAllLecturersDTOActive();
		model.addAttribute("lecturer", lecturer);
		return "home/listlecturers";
	}
	
	
	
	
	


}
