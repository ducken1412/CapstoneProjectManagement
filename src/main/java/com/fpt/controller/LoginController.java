package com.fpt.controller;

import java.security.Principal;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.fpt.entity.CapstoneProjects;
import com.fpt.entity.Semesters;
import com.fpt.service.*;
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

	@Autowired
	private SemestersService semestersService;
	@Autowired
	private CapstoneProjectService capstoneProjectService;
	@Autowired
	private StatisticsService statisticsService;

	@RequestMapping(value = {"/", "/login"})
	public String login(HttpServletRequest request,Model model, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
			for (Cookie cookie : cookies) {
				cookie.setValue("");
				cookie.setPath("/");
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		String error = request.getParameter("error");
		if(error != null){
			model.addAttribute("error" ,"User was not found in the system");
		}
		return "login/loginPage";
	}

	@RequestMapping("/login-google")
	public String loginGoogle(HttpServletRequest request, HttpServletResponse response) throws ClientProtocolException, IOException {
		String code = request.getParameter("code");
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
			for (Cookie cookie : cookies) {
				cookie.setValue("");
				cookie.setPath("/");
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		if (code == null || code.isEmpty()) {
			return "redirect:/login?google=error";
		}

		String accessToken = googleUtils.getToken(code);

		String email = googleUtils.getUserInfo(accessToken);
		UserDetails userDetail;
		Users appUser;
		if (email != null) {
			appUser = this.userService.findByEmail(email);
			try {
				userDetail = googleUtils.buildUser(email, appUser);
			} catch (Exception e) {

				return "redirect:/login?error=true";
			}

		} else {
			return "redirect:/login?error=true";
		}
		String userFullName;
		if (appUser.getFirstName() != null && appUser.getLastName() != null) {
			String firstName = appUser.getFirstName().replace(' ','_');

			userFullName = firstName + "_" + appUser.getLastName();
		} else if (appUser.getFirstName() != null && appUser.getLastName() == null) {
			userFullName = appUser.getFirstName();
		} else {
			userFullName = appUser.getFirstName();
		}
		Cookie cookie = new Cookie("userFullName", userFullName);
		cookie.setMaxAge(1 * 24 * 60 * 60);
		response.addCookie(cookie);
		Cookie cookieId = new Cookie("userId", appUser.getId());
		Cookie cookieName = new Cookie("username", appUser.getUsername());
		response.addCookie(cookieName);
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
				try {
					if(role.equals(Constant.ROLE_STUDENT_LEADER_DB)){
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
						CapstoneProjects capstoneProjects;
						capstoneProjects = capstoneProjectService.getCapstoneProjectByUserId(appUser.getId());
						Semesters semesters;
						semesters = semestersService.getSemesterByUserId(appUser.getId());
						LocalDate d1;
						d1 = LocalDate.parse(LocalDate.now().toString(), DateTimeFormatter.ISO_LOCAL_DATE);
						LocalDate d2;
						d2 = LocalDate.parse(simpleDateFormat.format(semesters.startDate), DateTimeFormatter.ISO_LOCAL_DATE);
						Duration diff;
						diff = Duration.between(d2.atStartOfDay(),d1.atStartOfDay());
						long diffDays = diff.toDays();
						long currentWeek = diffDays/7;
						Cookie cookie1 = new Cookie("currentWeek", String.valueOf(currentWeek));
						response.addCookie(cookie1);
						Integer maxWeekStatistic = statisticsService.findMaxWeekByCap(capstoneProjects.getId());
						if(currentWeek >= 2){
							if(maxWeekStatistic == null){
								Cookie cookieCheckReport = new Cookie("checkReported", "false");
								response.addCookie(cookieCheckReport);
								return "redirect:/report";
							}
							if(maxWeekStatistic < currentWeek){
								Cookie cookieCheckReport = new Cookie("checkReported", "false");
								response.addCookie(cookieCheckReport);
								return "redirect:/report";
							}else {
								Cookie cookieCheckReport = new Cookie("checkReported", "true");
								response.addCookie(cookieCheckReport);
							}
						}else {
							Cookie cookieCheckReport = new Cookie("checkReported", "true");
							response.addCookie(cookieCheckReport);
						}
					}
				}catch (Exception e){
					
				}
				if(role.equals(Constant.ROLE_LECTURERS_DB) || role.equals(Constant.ROLE_HEAD_DB)) {
					return "redirect:/db/dashboard";
				}
			}
			return "redirect:/forum";
		} else {
			return "redirect:/login?error=true";
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