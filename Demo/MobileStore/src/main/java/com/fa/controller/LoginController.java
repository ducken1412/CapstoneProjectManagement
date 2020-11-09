package com.fa.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fa.common.RoleConstant;
import com.fa.entity.Users;
import com.fa.service.RoleUserService;
import com.fa.service.UserService;

@Controller
public class LoginController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserService userServiceImpl;

	@Autowired
	private RoleUserService roleUserServiceImpl;

	@RequestMapping(value = "/loginSuccess", method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model model, Principal principal) {
		LOGGER.info("Runing on home in LoginController");
		if (principal != null) {
			UserDetails loginedUser = (UserDetails) ((Authentication) principal).getPrincipal();
			Users user = userServiceImpl.findByUserName(loginedUser.getUsername());
			List<String> roles = roleUserServiceImpl.getRoleNamesByUserId(user.getId());
			if (roles.contains(RoleConstant.ROLE_ADMIN)) {
				return "redirect:/admin";
			} else {
				return "redirect:/";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(Model model) {
		LOGGER.info("Runing on loginPage in LoginController");
		return "login/loginPage";
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDenied(Model model, Principal principal) {
		LOGGER.info("Runing on accessDenied in LoginController");
		return "403Page";
	}
}
