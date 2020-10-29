package com.fpt.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fpt.common.NotificationCommon;
import com.fpt.entity.*;
import com.fpt.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fpt.dto.UserRoleDTO;
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
	private UserRoleService userRoleService;
	@Autowired
	private StatusService statusService;

	@Autowired
	private HistoryRecordService historyRecordService;

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

	@RequestMapping(value = "/project-detail/{id}", method = RequestMethod.GET)
	public String detailProject(@PathVariable("id") Integer id, Model model, Principal principal) {

		// chưa có userId lấy từ request
//		Integer stauts = capstoneProjectDetailService.getStatuByCapstoneProjectDetailIdAndUserId(id, "SE04936");
		CapstoneProjects cp = capstoneProjectService.getCapstonProjectById(id);
		model.addAttribute("detail", cp);
		List<Users> userproject = capstoneProjectDetailService.getUserByCapstoneProjectDetailId(id);
		model.addAttribute("userproject", userproject);
		String nameStatus = cp.getStatus().getName();
		if (nameStatus == null) {
			model.addAttribute("status", "");
		} else {
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
					model.addAttribute("status", Constant.STATUS_APPROVE_CAPSTONE_LUCTURER);
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
		List<UserRoleDTO> userRolesDTOs = new ArrayList<>();
		for (Users users : userproject) {
			String user_id = users.getId();
			UserRoleDTO userRoleDTO = new UserRoleDTO();
			userRoleDTO.setId(user_id);
			userRoleDTO.setUsername(users.getUsername());
			List<String> role = userRoleService.getRoleNamesByUserId(user_id);
			for (String string : role) {
				List<String> roleView = new ArrayList<>();
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
				userRoleDTO.setRole(roleView);
			}
			userRolesDTOs.add(userRoleDTO);
		}
		
		Users user = userService.findByEmail(principal.getName());
		String user_login = user.getId();
		boolean check_capstone = false;
		//check user login = user project
		List<Integer> capstone_id = capstoneProjectDetailService.getIdProjectByUserIDCheckApprove(user_login);
		for (int i = 0; i < capstone_id.size(); i++){
			if(capstone_id.get(i) == id){
				check_capstone = true;
			}
		}
		model.addAttribute("check_capstone", check_capstone);
		model.addAttribute("userRolesDTOs", userRolesDTOs);
		return "home/detail_project";
	}

	@RequestMapping(value = "/approve", method = RequestMethod.POST, params = "approve")
	public String approve(@RequestParam Integer id, Model model, Principal principal){
		//int id_project = Integer.parseInt(id);
		boolean check = false;
		Users user = userService.findByEmail(principal.getName());
		String user_login = user.getId();
		
		try {
			
			Date date = new Date();
			HistoryRecords historyRecords = historyRecordService.findHistoryByProjectId(id);
			if(historyRecords != null) {
				String user_booking_id = historyRecords.getUser().getId();
				capstoneProjectDetailService.updateStatusUserProject(user_login, id);
				String title = user_login + " has joined your team";
				String content = user_login + " has joined your team " + date;
				NotificationCommon.sendNotification(user, title, content, user_booking_id);
				capstoneProjectDetailService.deleteCapstoneProjectDetailsByUserId(user_login,id);
				check = true;
			}
		}
		catch (Exception e){

		}
		if(check){
			//capstoneProjectDetailService.deleteCapstoneProjectDetailsByUserId(user_login);
		}
		return "redirect:/lecturers";
	}

	@RequestMapping(value = "/approve", method = RequestMethod.POST, params = "reject")
	public String reject(@RequestParam("id") Integer id, Model model, Principal principal){
		Users user = userService.findByEmail(principal.getName());
		String user_login = user.getId();
		capstoneProjectDetailService.deleteRejectCapstoneProjectDetailsByUserId(user_login, id);
		return "redirect:/lecturers";
	}
}
