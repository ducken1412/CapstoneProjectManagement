package com.fa.controller.admin;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fa.common.ResponseDTOStatusCode;
import com.fa.common.RoleConstant;
import com.fa.dto.ResponseDTO;
import com.fa.dto.UserDTO;
import com.fa.entity.Users;
import com.fa.service.RoleService;
import com.fa.service.RoleUserService;
import com.fa.service.UserService;
import com.google.gson.Gson;

@Controller
public class UserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userServiceImpl;
	
	@Autowired
	private RoleService roleServiceImpl;
	
	@Autowired
	private RoleUserService roleUserServiceImpl;
	
	@RequestMapping(value = "/admin/user/list-user", method = RequestMethod.GET)
	public String getListUser(Model model) {
		LOGGER.info("Running on getListUser method of UserController");
		
		List<UserDTO> users = userServiceImpl.getAllUserDTOActive();
		model.addAttribute("users", users);
		return "admin/listUser";
	}
	
	@RequestMapping(value = "/user/profile", method = RequestMethod.GET)
	public String userProfile(Model model, Principal principal) {
		LOGGER.info("Running on userProfile method of UserController");
		try {
			if (principal != null) {
				UserDetails userDetails = (UserDetails) ((Authentication) principal).getPrincipal();
				UserDTO user = userServiceImpl.getUser(userDetails.getUsername());
				model.addAttribute("user", user);
			}
		}catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("User id not found", e);
		}
		
		return "user/userInfo";
	}
	
	@RequestMapping(value = "/user/edit-user", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO editProfile(Model model, Principal principal, @Validated @ModelAttribute UserDTO user, 
			BindingResult bindingResult) {
		LOGGER.info("Running on editProfile method of UserController");
		if (bindingResult.hasErrors()) {
			return new ResponseDTO(ResponseDTOStatusCode.ERROR, bindingResult.getFieldError().getDefaultMessage());
		}
		
		System.out.println(user);
		try {
			Users userActual = null;
			if (principal != null) {
				UserDetails userDetails = (UserDetails) ((Authentication) principal).getPrincipal();
				userActual = userServiceImpl.findByUserName(userDetails.getUsername());
			}
			userServiceImpl.editProfile(user, userActual);
			return new ResponseDTO(ResponseDTOStatusCode.SUCCESS, "Edit profile have been success.");
		}catch (Exception e) {
			LOGGER.error("Edit user fail", e.getMessage());
			return new ResponseDTO(ResponseDTOStatusCode.ERROR, e.getMessage());
		}
	}
	
	
	
	
	@RequestMapping(value = "/admin/user/edit-user", method = RequestMethod.GET)
	public String editUser(Model model, @RequestParam(value = "id", required = false) int id ) {
		LOGGER.info("Running on editUser method of UserController");
		try {
			UserDTO user = userServiceImpl.getUser(id);
			model.addAttribute("user", user);
			List<String> roleNames = this.roleUserServiceImpl.getRoleNamesByUserId(id);
			model.addAttribute("roleNames", roleNames);
		}catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("User id not found", e);
		}
		
		model.addAttribute("roles", roleServiceImpl.getAll());
		return "admin/addUser";
	}
	
	@RequestMapping(value = "/admin/user/edit-user", method = RequestMethod.POST)
	@ResponseBody
	public String adminEditUser( Model model, Principal principal, @Validated @ModelAttribute UserDTO userDto, 
			BindingResult bindingResult, 
			@RequestParam(value = "roleName", required = false) List<String> roleList,
			HttpServletRequest request, HttpServletResponse response) {
		
		boolean result = false;
		Map<String, Object> output = new HashMap<>();
		if (bindingResult.hasErrors()) { 
			output.put("result", result);
			output.put("message", "Data is not valid");
			return new Gson().toJson(output);
		}
		
		boolean check = false;
		if (principal != null) {
			UserDetails userDetails = (UserDetails) ((Authentication) principal).getPrincipal();
			Users user = userServiceImpl.findByUserName(userDetails.getUsername());
			
			if(userServiceImpl.isAdmin(user) && user.getId().intValue() == userDto.getId().intValue()) {
				if(!roleList.contains(RoleConstant.ROLE_ADMIN)) {
					check = true;
				}
			}
		}
		
		if(userServiceImpl.editUser(userDto, roleList)) {
			result = true;
			output.put("result", result);
			output.put("message", "edit user " + userDto.getUserName() + " success");
			if(check) {
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				if (auth != null) {
				    new SecurityContextLogoutHandler().logout(request, response, auth);
				    output.put("logout", true);
				}
			}
			return new Gson().toJson(output);
		}else {
			output.put("result", result);
			output.put("message", "edit user " + userDto.getUserName() + " fail");
			return new Gson().toJson(output);
		}
	}
	
	@RequestMapping(value = "/admin/user/add-user", method = RequestMethod.GET)
	public String addUser(Model model) {
		LOGGER.info("Running on addUser method of UserController");
		return "admin/addUser";
	}
	
	@RequestMapping(value = "/admin/user/delete-user", method = RequestMethod.GET)
	@ResponseBody
	public ResponseDTO deleteUser(Model model, @RequestParam(value = "id", required = false) int id, Principal principal, HttpServletRequest request, HttpServletResponse response) {
		
		LOGGER.info("Running on deleteUser method of UserController");
		boolean check = false;
		try {
			Users user = userServiceImpl.findById(id);
			
			if (principal != null) {
				UserDetails userDetails = (UserDetails) ((Authentication) principal).getPrincipal();
				if(userDetails.getUsername().equals(user.getUserName())) {
					check = true;
				}
			}
			userServiceImpl.unActiveUser(user);
			
			if(check) {
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				if (auth != null) {
				    new SecurityContextLogoutHandler().logout(request, response, auth);
				}
			}
		}catch (Exception e) {
			LOGGER.error("User id not found", e);
			return new ResponseDTO(ResponseDTOStatusCode.ERROR, "User haven't been delete");
		}
		return new ResponseDTO(ResponseDTOStatusCode.SUCCESS, "User have been deleted");
	}
	
	@RequestMapping(value = "/user/change-password", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO changePassword(@RequestBody Map<String, Object> data, Model model, Principal principal) {
		LOGGER.info("Running on changePassword method of UserController");
		try {
			String oldPassword = data.get("oldPassword").toString();
			String newPassword = data.get("newPassword").toString();
			String confirmNewPassword = data.get("confirmNewPassword").toString();
			Users user = null;
			if (principal != null) {
				UserDetails userDetails = (UserDetails) ((Authentication) principal).getPrincipal();
				user = userServiceImpl.findByUserName(userDetails.getUsername());
			}
			
			userServiceImpl.changePassword(oldPassword, newPassword, confirmNewPassword, user);
			return new ResponseDTO(ResponseDTOStatusCode.SUCCESS, "Change password successfully.");
		}catch (Exception e) {
			return new ResponseDTO(ResponseDTOStatusCode.ERROR, e.getMessage());
		}
	}
	
	@RequestMapping(value = "/user/change-password", method = RequestMethod.GET)
	public String goToChangePassword(Model model) {
		LOGGER.info("Running on goToChangePassword method of UserController");
		return "user/changpassword";
	}
}
