package com.fpt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fpt.dto.CapstoneProjectDTO;
import com.fpt.dto.MemberDTO;
import com.fpt.entity.CapstoneProjectDetails;
import com.fpt.utils.Constant;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.entity.CapstoneProjects;
import com.fpt.entity.Status;
import com.fpt.repository.CapstoneProjectRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Service
public class CapstoneProjectServiceImpl implements CapstoneProjectService {
	@Autowired
	private CapstoneProjectRepository capstoneProjectRepository;

	@Autowired
	private StatusService statusService;

	@Autowired
	private ProfessionService professionService;

	@Autowired
	private UserService userService;

	@Autowired
	private CapstoneProjectService capstoneProjectService;

	@Override
	public List<String> getCapstoneProjectNameByUserId(String userId) {
		return capstoneProjectRepository.getCapstoneProjectNameByUserId(userId);
	}

	@Override
	public boolean saveRegisterProject(CapstoneProjects capstoneProjects) {
		try {
			capstoneProjectRepository.save(capstoneProjects);
			return true;
		} catch (Exception e) {
			System.out.println("error add capstone project");
		}
		return false;
	}

	@Override
	public CapstoneProjects getCapstonProjectById(Integer id) {
		CapstoneProjects cp = capstoneProjectRepository.findById(id).orElse(null);
		return cp;
	}


	@Override
	public List<CapstoneProjects> getAllProject() {
		return capstoneProjectRepository.findAll();
	}

	@Transactional(rollbackFor=Exception.class)
	public String registerProject(CapstoneProjectDTO dataForm){
		Map<String, Object> output = new HashMap<>();
		List<String> errors = new ArrayList<>();
		int count = 0;
		for (MemberDTO m : dataForm.getMembers()) {
			if(m.getRole().equals("Leader")) {
				count++;
			}
			if(count > 1) {
				errors.add("Team just has only one leader");
				output.put("hasError", true);
				output.put("errors", errors);
				return new Gson().toJson(output);
			}
		}
		if(count == 0) {
			errors.add("Team must has a leader");
			output.put("hasError", true);
			output.put("errors", errors);
			return new Gson().toJson(output);
		}
		CapstoneProjects projects = new CapstoneProjects();
		try {
			Status status = statusService.findByName(Constant.STATUS_REGISTERING_CAPSTONE_DB);
			projects.setName(dataForm.getName());
			projects.setNameOther(dataForm.getNameOther());
			projects.setNameVi(dataForm.getNameVi());
			projects.setNameAbbreviation(dataForm.getNameAbbreviation());
			projects.setDescription(dataForm.getDescription());
			projects.setDocument(dataForm.getDocument());
			projects.setSpecialty(dataForm.getSpecialty());
			projects.setProgram(dataForm.getProgram());
			projects.setStatus(status);
			projects.setProfession(professionService.findById(Integer.parseInt(dataForm.getProfession())));

			List<CapstoneProjectDetails> cpds = new ArrayList<>();
			CapstoneProjectDetails cpd = null;
			for (MemberDTO member : dataForm.getMembers()) {
				cpd = new CapstoneProjectDetails();
				cpd.setUser(userService.findByUsername(member.getUsername()).get(0));
				cpd.setCapstoneProject(projects);
				cpd.setStatus(status);
				cpds.add(cpd);
			}
			projects.setCapstoneProjectDetails(cpds);
			capstoneProjectService.saveRegisterProject(projects);
		}catch (Exception ex) {
			errors.add("Registration failed");
			output.put("hasError", true);
			output.put("errors", errors);
			return new Gson().toJson(output);
		}
		output.put("hasError", false);
		output.put("message", "Project registration is successful. The screen will switch after 3 seconds");
		return new Gson().toJson(output);
	}

}
