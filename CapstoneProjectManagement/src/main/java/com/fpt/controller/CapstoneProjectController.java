package com.fpt.controller;

import java.util.List;

import javax.validation.Valid;

import com.fpt.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.fpt.dto.CapstoneProjectDTO;
import com.fpt.entity.CapstoneProjects;
import com.fpt.entity.Profession;
import com.fpt.entity.Users;
import com.fpt.service.CapstoneProjectDetailService;
import com.fpt.service.CapstoneProjectService;
import com.fpt.service.ProfessionService;
import com.fpt.service.StatusService;
import com.fpt.service.UserService;

@Controller
public class CapstoneProjectController {
	@Autowired
	private CapstoneProjectService projectService;

	@Autowired
	private StatusService statusService;

	@Autowired
	private UserService userService;

	@Autowired
	private ProfessionService professionService;

	@RequestMapping(value = "/registerproject", method = RequestMethod.GET)
	public String loadDropDown(Model model) {
		// load all user has role = 2 (student_member)
		List<Users> user = userService.getUserByRoleId(2);
		List<Profession> professions = professionService.findAll();
		model.addAttribute("user", user);
		model.addAttribute("professions", professions);
		model.addAttribute("capstoneProjectDTO", new CapstoneProjectDTO());
		return "home/register-project";
	}

	@ResponseBody
	@RequestMapping(value = "/getMember")
	public String getMember() {

		return "home/register-project-form";
	}

	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String addRegisterPoject(@Valid @RequestBody CapstoneProjectDTO dataForm, BindingResult result,
									Model model) {
		if (result.hasErrors()) {
			// load all user has role = 2 (student_member)
			List<Users> user = userService.getUserByRoleId(2);
			model.addAttribute("user", user);
			model.addAttribute("capstoneProjectDTO", dataForm);
			return "home/register-project-form";
		}
		CapstoneProjects projects = new CapstoneProjects();
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		System.out.println(dataForm);
//		int statusId = 4;
//		projects.setName(dto.getName());
//		projects.setNameOther(dto.getNameOther());
//		projects.setNameVi(dto.getNameVi());
//		projects.setNameAbbreviation(dto.getNameAbbreviation());
//		projects.setDescription(dto.getDescription());
//		projects.setDocument(dto.getDocument());
//		projects.setSpecialty(dto.getSpecialty());
//		projects.setProgram(dto.getProgram());
//		projects.setStatus(statusService.getStatusById(statusId));
//		projects.setProfession(dto.getProfession());
//		projectService.saveRegisterProject(projects);

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
//		dto.setId(projects.getId());
		model.addAttribute("capstoneProjectDTO", dataForm);
		return "home/register-project-form";
	}

	@GetMapping("/get-member-form")
	public String getForm() {
		return "home/add-member";
	}
}
