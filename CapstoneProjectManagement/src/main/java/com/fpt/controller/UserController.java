package com.fpt.controller;


import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.fpt.dto.UserManagementDTO;
import com.fpt.entity.*;
import com.fpt.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

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
	@Autowired
	private SemestersService semestersService;
	@Autowired
	private SitesService sitesService;
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
		if(user.getBirthDate() != null) {
			String parsedDate = formatter.format(user.getBirthDate());
			model.addAttribute("dob", parsedDate);
		}else {
			model.addAttribute("dob", null);
		}

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

    @RequestMapping(value = "/student-managements", method = RequestMethod.GET)
    public String studentManagment(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        List<Sites> sites = sitesService.findAll();
        model.addAttribute("sites", sites);

        List<Semesters> semesters = semestersService.findAll();
        model.addAttribute("semesters", semesters);
        return "home/student-management";
    }

    @RequestMapping(value = "/student-management", method = RequestMethod.GET)
    public String loadDataTableToTheDropdown(Model model, @RequestParam(required=false,name = "type") String typeParam, @RequestParam(required=false,name ="site") String site,
                                             @RequestParam(required=false,name ="semester") String semester, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        //count student doing capstone
        Integer countStudentDoingCp = userService.countStudent(9,site,semester);

        //count student has no team
        Integer countStudentHasNoTeam = userService.countStudentHasNoTeam(site, semester);

        //count student has no team
        Integer countStudent = userService.countAllStudent(site, semester);

        //count student has no team
        Integer countStudentEligibleCapstone = userService.countStudentEligibleCapstone(site, semester);

        model.addAttribute("countStudentEligibleCapstone",countStudentEligibleCapstone);
        model.addAttribute("countStudent", countStudent);
        model.addAttribute("countStudentHasNoTeam", countStudentHasNoTeam);
        model.addAttribute("countStudentDoingCP", countStudentDoingCp);

        List<UserManagementDTO> users;
        Integer type = Integer.parseInt(typeParam);
        switch (type) {
            //get all student
            case 1:
                users = userService.getAllUserStudent(site,semester);
                model.addAttribute("users", users);
                break;

            //get student doing capstone project
            case 2:
                users = userService.getUserStudentByStatusId(9);
                model.addAttribute("users", users);
                break;

            //get student registering project
            case 3:
                users = userService.getUserStudentByStatusId(4);
                model.addAttribute("users", users);
                break;

            //Registered Capstone Project by status project
            case 4:
                users = userService.getUserStudentByStatusId(5);
                model.addAttribute("users", users);
                break;

            //Approve Capstone Supervisor by status project
            case 5:
                users = userService.getUserStudentByStatusId(6);
                model.addAttribute("users", users);
                break;

            //Approve Capstone Training Department by status project
            case 6:
                users = userService.getUserStudentByStatusId(7);
                model.addAttribute("users", users);
                break;

            //Approve Capstone Head by status project
            case 7:
                users = userService.getUserStudentByStatusId(8);
                model.addAttribute("users", users);
                break;

            //Not Eligible Defence Capstone by status project
            case 8:
                users = userService.getUserStudentByStatusId(10);
                model.addAttribute("users", users);
                break;

            //Eligible Defence Capstone by status project
            case 9:
                users = userService.getUserStudentByStatusId(11);
                model.addAttribute("users", users);
                break;

            //Reject Capstone by status project
            case 10:
                users = userService.getUserStudentByStatusId(12);
                model.addAttribute("users", users);
                break;

            //Changing Name Capstone by status project
            case 11:
                users = userService.getUserStudentByStatusId(13);
                model.addAttribute("users", users);
                break;

            //Pending Capstone by status project
            case 12:
                users = userService.getUserStudentByStatusId(14);
                model.addAttribute("users", users);
                break;

            //Change Name Capstone Supervisor by status project
            case 13:
                users = userService.getUserStudentByStatusId(15);
                model.addAttribute("users", users);
                break;

            //List student has no team
            case 14:
                users = userService.getAllUserStudentHasNoTeam(site,semester);
                model.addAttribute("users", users);
                break;
        }


        return "home/student-management-component";
    }

}

		
		

