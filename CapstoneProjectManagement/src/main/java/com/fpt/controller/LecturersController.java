package com.fpt.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpt.controller.LecturersController;
import com.fpt.dto.ListLecturersDTO;
import com.fpt.entity.Posts;
import com.fpt.entity.Users;
import com.fpt.service.UserRoleService;
import com.fpt.service.UserService;
import com.fpt.dto.ListLecturersDTO;
import com.fpt.service.LecturersService;
@Controller

public class LecturersController {
	/*
	@GetMapping("/listlecturersproject")
	public String registerProject() {
		return "home/listlecturers";
	}
	*/
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LecturersController.class);
	
	@Autowired
	private UserService  userService;
	
	@Autowired
	private LecturersService lecturersService;
	
//	@RequestMapping(value = "/listlecturersproject", method = RequestMethod.GET)
//	public String getListLecturers(Model model) {
//		LOGGER.info("Running on getListLecturers method of UserController");
//		//Id = 3 (role lecturers)
//		List<Users> lecturer = userService.getUserByRoleId(3);
//		model.addAttribute("lecturer", lecturer);
//		
//		
//		return "home/listlecturers";
//	}
	
	@RequestMapping(value = "/listlecturersproject", method = RequestMethod.GET)
	public String getPosts(Model model, @RequestParam("page") Optional<Integer> page, 
		      @RequestParam("size") Optional<Integer> size) {
		LOGGER.info("Running on getListLecturers method of UserController");
		List<Users> lecturer = userService.getUserByRoleId(3);
		model.addAttribute("lecturer", lecturer);

		int currentPage = page.orElse(1);
        int pageSize = size.orElse(6);

		Page<Users> postPage = lecturersService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
		model.addAttribute("postPage", postPage);
		int totalPages = postPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		return "home/listlecturers";
	}
	
	
	


}
