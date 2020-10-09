package com.fpt.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpt.controller.LecturersController;
import com.fpt.dto.CapstoneProjectDetailDTO;
import com.fpt.dto.ListLecturersDTO;
import com.fpt.dto.UserDTO;
import com.fpt.entity.CapstoneProjectDetails;
import com.fpt.entity.Notifications;
import com.fpt.entity.Posts;
import com.fpt.entity.Users;
import com.fpt.service.UserRoleService;
import com.fpt.service.UserService;
import com.fpt.dto.ListLecturersDTO;
import com.fpt.service.CapstoneProjectDetailService;
import com.fpt.service.CapstoneProjectService;
import com.fpt.service.LecturersService;
import com.fpt.service.NotificationsService;
import com.fpt.service.StatusService;
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
	private NotificationsService notificationService;
	
	@Autowired
	private CapstoneProjectService capstoneProjectService;
	
	@Autowired
	private StatusService statusService;
	
	@Autowired
	private CapstoneProjectDetailService capstoneProjectDetailService;
	
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
	public String getListLecturers(Model model, @RequestParam("page") Optional<Integer> page, 
		      @RequestParam("size") Optional<Integer> size) {
		LOGGER.info("Running on getListLecturers method of UserController");
		//Id = 3 (role lecturers)
		List<Users> lecturer = userService.getUserByRoleId(3);
		model.addAttribute("lecturer", lecturer);
		
		UserDTO userDTO = new UserDTO();
		List<Notifications> notifications = notificationService.getAllTitle();
		model.addAttribute("notifications", notifications);
		
		//phan trang
		int currentPage = page.orElse(1);
        int pageSize = size.orElse(6);
		Page<Users> lecturersPage = userService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
		model.addAttribute("lecturersPage", lecturersPage);
		int totalPages = lecturersPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		return "home/listlecturers";
	}
	
	@RequestMapping(value="/listlecturersproject/{id}", method= RequestMethod.POST)
	public String bookLecturers(@PathVariable("id") String id,UserDTO dto,Model model, BindingResult bindingResult) {
		CapstoneProjectDetails cpd = new CapstoneProjectDetails();
		Users user_id = userService.findById(id);
		cpd.setCapstoneProject(capstoneProjectService.getCapstonProjectById(37));
		cpd.setUser(user_id);
		cpd.setStatus(statusService.getStatusById(4));
		capstoneProjectDetailService.addCapstonprojectDetail(cpd);
		return "redirect:";
	}
	


}
