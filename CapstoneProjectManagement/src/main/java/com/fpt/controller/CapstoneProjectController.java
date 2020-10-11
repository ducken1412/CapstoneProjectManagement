package com.fpt.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fpt.dto.CapstoneProjectDTO;
import com.fpt.dto.CapstoneProjectDetailDTO;
import com.fpt.entity.CapstoneProjectDetails;
import com.fpt.entity.CapstoneProjects;
import com.fpt.entity.Users;
import com.fpt.service.CapstoneProjectDetailService;
import com.fpt.service.CapstoneProjectService;
import com.fpt.service.StatusService;
import com.fpt.service.UserService;

@Controller
public class CapstoneProjectController {
	@Autowired
	private CapstoneProjectService projectService;

	@Autowired
	private CapstoneProjectDetailService capstoneProjectDetailService;

	@Autowired
	private CapstoneProjectService capstoneProjectService;

	@Autowired
	private StatusService statusService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/registerproject", method = RequestMethod.GET)
	public String loadDropDown(Model model) {
		// load all user has role = 2 (student_member)
		List<Users> user = userService.getUserByRoleId(2);
		model.addAttribute("user", user);
		model.addAttribute("capstoneProjectDTO", new CapstoneProjectDTO());
		return "home/register-project";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String addRegisterPoject(@Valid CapstoneProjectDTO dto, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			// load all user has role = 2 (student_member)
			List<Users> user = userService.getUserByRoleId(2);
			model.addAttribute("user", user);
			model.addAttribute("capstoneProjectDTO", dto);
			return "home/register-project-form";
		}
		CapstoneProjects projects = new CapstoneProjects();
		int statusId = 1;
		projects.setName(dto.getName());
		projects.setNameOther(dto.getNameOther());
		projects.setNameVi(dto.getNameVi());
		projects.setNameAbbreviation(dto.getNameAbbreviation());
		projects.setDescription(dto.getDescription());
		projects.setDocument(dto.getDocument());
		projects.setSpecialty(dto.getSpecialty());
		projects.setProgram(dto.getProgram());
		projects.setStatus(statusService.getStatusById(statusId));
		projectService.saveRegisterProject(projects);

		/*CapstoneProjectDetails cpd;
		// lop id by Users Role Student and add capstone project detail
		for (String idStudent : dto.getMembers()) {
			cpd = new CapstoneProjectDetails();
			System.out.println(idStudent);
			cpd.setUser(userService.findById(idStudent));
			int capstoneId = projects.getId();
			cpd.setCapstoneProject(capstoneProjectService.getCapstonProjectById(capstoneId));
			//set status id = 4 (registering_capstone)
			cpd.setStatus(statusService.getStatusById(4));
			capstoneProjectDetailService.addCapstonprojectDetail(cpd);
		}*/
		dto.setId(projects.getId());
		model.addAttribute("capstoneProjectDTO", dto);
		return "home/register-project-form";
	}

	@GetMapping("/get-member-form")
	public String getForm() {
		return "home/add-member";
	}
}
