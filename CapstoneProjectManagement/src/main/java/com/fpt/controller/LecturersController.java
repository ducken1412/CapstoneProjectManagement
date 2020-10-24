package com.fpt.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fpt.entity.*;
import com.fpt.service.*;
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
import com.fpt.dto.ListLecturersDTO;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LecturersController {

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

	@Autowired
	private HistoryRecordService historyRecordService;
	
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
	
	@RequestMapping(value = "/lecturers", method = RequestMethod.GET)
	public String getListLecturers(Model model, @RequestParam("page") Optional<Integer> page, 
		      @RequestParam("size") Optional<Integer> size) {
		LOGGER.info("Running on getListLecturers method of UserController");
		String user_id_login = "SE05045";
		List<HistoryRecords> historyRecords = historyRecordService.getHistoryRecordsByUserId(user_id_login);
		if(historyRecords != null){
			for (int i = 0; i < historyRecords.size(); i++){
				if(historyRecords.get(i).getContent().equals("Register Capstone")){
					//check booking
					boolean check_user_register = true;
					model.addAttribute("check_user_register", check_user_register);
					int project_id = capstoneProjectDetailService.getProjectIdByUserId(user_id_login);
					//check total lecture bookded
					int count = capstoneProjectDetailService.countLecturersByProjectId(project_id);
					if(count >= 2){
						model.addAttribute("disable","booking lectuers enough!!!");
					}
				}
			}
		}else {
			boolean check_user_register = false;
			model.addAttribute("check_user_register", check_user_register);
		}


		//Id = 4 (role lecturers)
		List<Users> lecturer = userService.getUserByRoleId(4);
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

	@RequestMapping(value="/listlecturersproject/{id}", method= RequestMethod.GET)
	public String bookLecturers(@PathVariable("id") String id, UserDTO dto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		String user_id_login = "SE05045";
		int project_id = capstoneProjectDetailService.getProjectIdByUserId(user_id_login);
		int count = capstoneProjectDetailService.countLecturersByProjectId(project_id);
		List<CapstoneProjectDetails> listUser = capstoneProjectDetailService.getUserByCapstioneID(project_id);
		for (int i = 0; i < listUser.size(); i++){
			if(listUser.get(i).getUser().getId().equals(id)){
				redirectAttributes.addFlashAttribute("disable","lectuers booked!!!");
				return "redirect:/lecturers";
			}
		}
		if(count >= 2){
			redirectAttributes.addFlashAttribute("disable","booking lectuers enough!!!");
			return "redirect:/lecturers";
		}else {
			//booking lecture
			CapstoneProjectDetails cpd = new CapstoneProjectDetails();
			Users user_id = userService.findById(id);
			cpd.setCapstoneProject(capstoneProjectService.getCapstonProjectById(project_id));
			cpd.setUser(user_id);
			cpd.setDesAction("booking lecturers");
			cpd.setStatus(statusService.getStatusById(4));
			capstoneProjectDetailService.addCapstonprojectDetail(cpd);
			//save history records
			HistoryRecords records = new HistoryRecords();
			Date date = new Date();
			records.setContent("Booking Lecture");
			records.setCreatedDate(date);
			records.setUser(userService.findById(user_id_login));
			records.setCapstoneProject(capstoneProjectService.getCapstonProjectById(project_id));
			historyRecordService.save(records);
		}
		return "redirect:/lecturers";
	}
}
