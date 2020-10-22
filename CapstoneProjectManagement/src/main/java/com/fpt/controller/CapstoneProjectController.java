package com.fpt.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.fpt.dto.MemberDTO;
import com.google.gson.Gson;
import com.sun.codemodel.internal.JForEach;
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
	public String getRegisterProject(Model model) {
		List<Users> users = userService.findByUsername("ducddse04936");
		if (!users.isEmpty()) {
			model.addAttribute("loggedUser", users.get(0));
		} else {
			return "error/403Page";
		}
		model.addAttribute("capstoneProjectDTO", new CapstoneProjectDTO());
		return "home/register-project";
	}

	@ResponseBody
	@RequestMapping(value = "/getMember/{username}")
	public String getMember(@PathVariable String username) {
		Map<String, Object> result = new HashMap<>();
		boolean success = true;
		String message = "";
		System.out.println(username);
		List<Users> users = userService.findByUsername(username);
		MemberDTO dto = new MemberDTO();
		if (users.isEmpty()) {
			success = false;
			message = "Username could not be found";
		} else {
			dto = new MemberDTO(users.get(0));
		}
		result.put("success", success);
		result.put("message", message);
		result.put("user", dto);
		return new Gson().toJson(result);
	}


	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerProject(@Valid @RequestBody CapstoneProjectDTO dataForm, BindingResult result,
									Model model) {
		Map<String, Object> output = new HashMap<>();
		List<String> errors = new ArrayList<>();
		if (result.hasErrors()) {
			result.getFieldErrors().stream().forEach(f -> errors.add(f.getField() + " " + f.getDefaultMessage()));
			output.put("hasError", true);
			output.put("errors", errors);
			return new Gson().toJson(output);
		}
		int count = 0;
		for (MemberDTO m : dataForm.getMembers()) {
			if(m.getRole().equalsIgnoreCase("leader")) {
				count++;
			}
			if(count > 1) {
				errors.add("team just has only one leader");
				output.put("hasError", true);
				output.put("errors", errors);
				return new Gson().toJson(output);
			}
		}
		CapstoneProjects projects = new CapstoneProjects();

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
		output.put("hasError", false);
		return new Gson().toJson(output);
	}

	@GetMapping("/get-member-form")
	public String getForm() {
		return "home/add-member";
	}
}
