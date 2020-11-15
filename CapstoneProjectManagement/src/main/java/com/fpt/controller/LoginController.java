package com.fpt.controller;

import java.security.Principal;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.fpt.service.UserRoleService;
import com.fpt.service.UserService;
import com.fpt.utils.Constant;
import com.fpt.utils.WebUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fpt.config.GoogleUtils;
import com.fpt.entity.Users;
import com.fpt.config.*;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Transactional
public class LoginController {
	@Autowired
	private UserService userService;

	@Autowired
	private GoogleUtils googleUtils;

	@Autowired
	private UserRoleService userRoleService;

	@RequestMapping(value = {"/", "/login"})
	public String login(HttpServletRequest request) {
		String referer = request.getHeader("Referer"); //Get previous URL before call '/login'

		//save referer URL to session, for later use on CustomAuthenticationSuccesshandler
		request.getSession().setAttribute(CustomAuthenticationSuccessHandler.REDIRECT_URL_SESSION_ATTRIBUTE_NAME, referer);
		return "login/loginPage";
	}

	@RequestMapping("/login-google")
	public String loginGoogle(HttpServletRequest request, HttpServletResponse response) throws ClientProtocolException, IOException {
		String code = request.getParameter("code");

		if (code == null || code.isEmpty()) {
			return "redirect:/login?google=error";
		}

		String accessToken = googleUtils.getToken(code);

		String email = googleUtils.getUserInfo(accessToken);
		UserDetails userDetail;
		Users appUser;
		if (email != null) {
			appUser = this.userService.findByEmail(email);
			userDetail = googleUtils.buildUser(email, appUser);
		} else {
			return "redirect:/login?google=error";
		}
		String userFullName;
		if (appUser.getFirstName() != null && appUser.getLastName() != null) {
			userFullName = appUser.getFirstName() + "_" + appUser.getLastName();
		} else if (appUser.getFirstName() != null && appUser.getLastName() == null) {
			userFullName = appUser.getFirstName();
		} else {
			userFullName = appUser.getFirstName();
		}
		Cookie cookie = new Cookie("userFullName", userFullName);
		cookie.setMaxAge(1 * 24 * 60 * 60);
		response.addCookie(cookie);
		Cookie cookieId = new Cookie("userId", appUser.getId());
		response.addCookie(cookieId);
		if(appUser.getCapstoneProjectDetails().size() != 0){
			Cookie cookieDetail = new Cookie("idDetail",
					appUser.getCapstoneProjectDetails().get(0).getCapstoneProject().getId().toString() );
			response.addCookie(cookieDetail);
		}

		if (appUser.getImage() != null) {
			Cookie cookieImage = new Cookie("userImage", appUser.getImage());
			cookie.setMaxAge(1 * 24 * 60 * 60);
			// add cookie to response
			response.addCookie(cookieImage);
		}

		if (userDetail != null) {
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null,
					userDetail.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			List<String> roles = userRoleService.getRoleNamesByUserId(appUser.getId());
			for (String role : roles) {
				if(role.equals(Constant.ROLE_LECTURERS_DB) || role.equals(Constant.ROLE_HEAD_DB)) {
					return "redirect:/db/dashboard";
				}
			}
			return "redirect:/forum";
		} else {
			return "redirect:/login?google=error";
		}

	}

	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	public String userInfo(Model model, Principal principal) {
		String userName = principal.getName();
		System.out.println("User Name: " + userName);
		UserDetails loginedUser = (UserDetails) ((Authentication) principal).getPrincipal();
		String userInfo = WebUtils.toString(loginedUser);
		model.addAttribute("userInfo", userInfo);
		return "login/userInfoPage";
	}

	@RequestMapping("/admin")
	public String admin() {
		return "login/adminPage";
	}

	@RequestMapping("/403")
	public String accessDenied() {
		return "error/403Page";
	}
}