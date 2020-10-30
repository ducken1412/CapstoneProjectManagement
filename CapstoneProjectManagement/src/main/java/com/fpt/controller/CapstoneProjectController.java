package com.fpt.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Constraint;
import javax.validation.Valid;

import com.fpt.dto.MemberDTO;
import com.fpt.entity.*;
import com.fpt.utils.Constant;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.fpt.dto.CapstoneProjectDTO;
import com.fpt.service.CapstoneProjectDetailService;
import com.fpt.service.CapstoneProjectService;
import com.fpt.service.ProfessionService;
import com.fpt.service.StatusService;
import com.fpt.service.UserService;

@Controller
public class CapstoneProjectController {
	@Autowired
	private CapstoneProjectDetailService capstoneProjectDetailService;

	@Autowired
	private CapstoneProjectService capstoneProjectService;

	@Autowired
	private StatusService statusService;

	@Autowired
	private UserService userService;

	@Autowired
	private ProfessionService professionService;

	@RequestMapping(value = "/registerproject", method = RequestMethod.GET)
	public String getRegisterProject(Model model, Principal principal) {
		if(principal == null) {
			return "redirect:/login";
		}
		Users user = userService.findByEmail(principal.getName());
		model.addAttribute("loggedUser", user);
		List<Profession> professions = professionService.findAll();
		model.addAttribute("professions", professions);
		model.addAttribute("capstoneProjectDTO", new CapstoneProjectDTO());
		return "home/register-project";
	}

	@ResponseBody
	@RequestMapping(value = "/getMember/{username}")
	public String getMember(@PathVariable String username) {
		Map<String, Object> result = new HashMap<>();
		boolean success = true;
		String message = "";
		List<Users> users = userService.findByUsername(username);
		MemberDTO dto = new MemberDTO();
		Users tmp = null;
		if (users.isEmpty()) {
			success = false;
			message = "Username could not be found";
		} else {
			dto = new MemberDTO(users.get(0));
			boolean check = false;
			for (UserRoles userRoles : users.get(0).getRoleUser()) {
				if(userRoles.getUserRoleKey().getRole().getName().equals(Constant.ROLE_STUDENT_MEMBER_DB)) {
					check = true;
					break;
				}
			}
			if(!check) {
				success = false;
				message = "User is not a student";
			} else {
				tmp = capstoneProjectDetailService.findUserByStatusRegisted(users.get(0).getId());
			}
		}
		if(tmp != null) {
			success = false;
			message = "The user has joined another group";
		}

		result.put("success", success);
		result.put("message", message);
		result.put("user", dto);
		return new Gson().toJson(result);
	}


	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerProject(@Valid @RequestBody CapstoneProjectDTO dataForm, BindingResult result,
								  Model model, Principal principal, HttpServletRequest request){
		if(principal == null) {
			return "redirect:/login";
		}
		String baseUrl = String.format("%s://%s:%d/",request.getScheme(),  request.getServerName(), request.getServerPort());
		Map<String, Object> output = new HashMap<>();
		List<String> errors = new ArrayList<>();
		if (result.hasErrors()) {
			result.getFieldErrors().stream().forEach(f -> errors.add(f.getField() + " " + f.getDefaultMessage()));
			output.put("hasError", true);
			output.put("errors", errors);
			return new Gson().toJson(output);
		}
		return capstoneProjectService.registerProject(dataForm,principal,baseUrl);
	}

	@GetMapping("/get-member-form")
	public String getForm(Principal principal) {
		if(principal == null) {
			return "redirect:/login";
		}
		return "home/add-member";
	}
}
