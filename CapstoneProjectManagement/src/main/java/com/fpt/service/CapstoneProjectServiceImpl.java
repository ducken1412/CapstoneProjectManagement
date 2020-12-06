package com.fpt.service;

import java.util.*;
import java.security.Principal;

import com.fpt.common.NotificationCommon;
import com.fpt.common.SendingMail;
import com.fpt.dto.CapstoneProjectDTO;
import com.fpt.dto.MemberDTO;
import com.fpt.entity.*;
import com.fpt.utils.Constant;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
	private UserRoleService userRoleService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private HistoryRecordService recordService;

	@Autowired
	private CapstoneProjectDetailService capstoneProjectDetailService;

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
	public String registerProject(CapstoneProjectDTO dataForm,Principal principal, String baseUrl){

		Map<String, Object> output = new HashMap<>();
		List<String> errors = new ArrayList<>();
		if(principal == null) {
			errors.add("You must login to register Project");
			output.put("hasError", true);
			output.put("errors", errors);
			return new Gson().toJson(output);
		}
		Users user = userService.findByEmail(principal.getName());
		CapstoneProjects capstoneProject = capstoneProjectDetailService.findCapstoneProjectByUserId(user.getId());
		if(capstoneProject != null) {
			errors.add("You already have a project");
			output.put("hasError", true);
			output.put("errors", errors);
			return new Gson().toJson(output);
		}
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
		List<MemberDTO> members = new ArrayList<>();
		try {
			Status status = statusService.findByName(Constant.STATUS_REGISTERING_CAPSTONE_DB);
			Status registedStatus = statusService.findByName(Constant.STATUS_REGISTED_CAPSTONE_DB);
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
			members = dataForm.getMembers();
			Users tmp = null;
			UserRoles userRoles= null;
			UserRoleKey roleKey = null;
			Roles role = null;
			for (MemberDTO member : members) {
				userRoles = new UserRoles();
				roleKey = new UserRoleKey();
				cpd = new CapstoneProjectDetails();
				cpd.setUser(userService.findByUsername(member.getUsername()).get(0));
				cpd.setCapstoneProject(projects);
				if(member.getUsername().equalsIgnoreCase(user.getUsername())) {
					cpd.setStatus(registedStatus);
				} else  {
					cpd.setStatus(status);
				}
				cpds.add(cpd);
				tmp = userService.findByUsername(member.getUsername()).get(0);
				if(member.getRole().equals("Leader")) {
					userRoleService.removeAllRoleOfUserByUserId(tmp.getId());
					role = roleService.findByName(Constant.ROLE_STUDENT_LEADER_DB);
					roleKey.setRole(role);
					roleKey.setUser(tmp);
					userRoles.setUserRoleKey(roleKey);
					userRoleService.saveRoleUser(userRoles);
				}
			}
			projects.setCapstoneProjectDetails(cpds);
			HistoryRecords records = new HistoryRecords();
			Date date = new Date();
			records.setCreatedDate(date);
			records.setContent("Register Capstone");
			records.setCapstoneProject(projects);
			records.setUser(user);
			if (saveRegisterProject(projects)) {
				recordService.save(records);
			}
		}catch (Exception ex) {
			errors.add("Registration failed");
			output.put("hasError", true);
			output.put("errors", errors);
			return new Gson().toJson(output);
		}
		String title = user.getUsername() + " invites you to participate in a capstone project";
		String content =  user.getUsername() + " invites you to participate in a capstone project. Click " + "<a href=\"" + baseUrl + "project-detail/" + projects.getId() + "\">view</a>";
		Users userTmp = null;
		for (MemberDTO member : members){
			if(!member.getUsername().equalsIgnoreCase(user.getUsername())) {
				NotificationCommon.sendNotificationByUsername(user, title, content, member.getUsername());
				userTmp = userService.findByUsername(member.getUsername()).get(0);
				try{
					SendingMail.sendEmail(userTmp.getEmail(),"[FPTU Capstone Project] Invite to participate capstone project", content);
				}catch (Exception ex) {
					System.out.println(ex);
				}
			}
		}
		output.put("hasError", false);
		output.put("message", "Project registration is successful.");
		return new Gson().toJson(output);
	}
	@Override
	public List<Object[]> getAllByUserId(String UserId, Integer PageIndex, Integer PageSize,Integer status,Integer profession,String nameSearch) {
		PageIndex = PageIndex * PageSize;
		nameSearch = '%' + nameSearch + '%';
		return capstoneProjectRepository.getAll(UserId,PageIndex,PageSize,status,profession,nameSearch);
	}
	@Override
	public CapstoneProjects findById(Integer id) {
		return capstoneProjectRepository.findById(id).orElse(null);
	}

	@Override
	public Integer getCountStudent(Integer id) {
		return capstoneProjectRepository.getCountStudent(id);
	}

	@Override
	public CapstoneProjects getCapstoneProjectByUserId(String userId) {
		return capstoneProjectRepository.getCapstoneProjectByUserId(userId);
	}

	@Override
	public boolean updateStatusCapstoneProjectSendTD(Integer id) {
		try {
			capstoneProjectRepository.updateStatusCapstoneProjectSendTD(id);
		}catch (Exception e){

		}
		return false;
	}

	@Override
	public boolean deleteUserNotSubmitCapstone(Integer id) {
		try {
			capstoneProjectRepository.deleteUserNotSubmitCapstone(id);
		}catch (Exception e){

		}
		return false;
	}

}
