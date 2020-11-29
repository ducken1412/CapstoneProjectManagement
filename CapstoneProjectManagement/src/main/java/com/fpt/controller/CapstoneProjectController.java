package com.fpt.controller;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Constraint;
import javax.validation.Valid;

import com.fpt.dto.CapstoneProjectDetailBody;
import com.fpt.dto.CapstoneProjectPagingBodyDTO;
import com.fpt.dto.MemberDTO;
import com.fpt.entity.*;
import com.fpt.service.*;
import com.fpt.utils.Constant;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.fpt.dto.CapstoneProjectDTO;

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

	@Autowired
	private HistoryRecordService historyRecordService;

	//KienBT4 add capstone start
	@GetMapping("/ad/capstoneproject")
	public String forum(Model model) {
		List<Users> user = userService.getUserByRoleId(2);
		model.addAttribute("user", user);

		List<Profession> profession = professionService.findAll();
		model.addAttribute("profession", profession);

		List<Status> status = statusService.getAll();
		model.addAttribute("status", status);
		return "home/CapstoneProject";
	}

	@GetMapping("/list-project")
	public String getProjects(Model model, Principal principal, @RequestParam("page") String page, @RequestParam("size") String size, @RequestParam("status") Integer status, @RequestParam("profession") Integer profession, @RequestParam("nameSearch") String nameSearch) {
		//get username
		//UserDetails loginedUser = (UserDetails) ((Authentication) principal).getPrincipal();
		//return loginedUser.getUsername();
		// get user logged
		if(principal == null) {
			return "redirect:/login";
		}
		Users users = userService.findByEmail(principal.getName());
		int roleid = -1;
		String userid = "-1";
		if (users !=  null) {
			roleid = users.getRoleUser().get(0).getUserRoleKey().getRole().getId();
			userid = users.getId();
			model.addAttribute("loggedUser", userid);
			model.addAttribute("role", roleid);
		} else {
			return "error/403Page";
		}
		if(roleid == 3){

		}
		switch(roleid) {
			case 3:
				userid = "-1";
				break;
			case 5:
				userid = "-1";
				break;
		}
		int currentPage = 1;
		int pageSize = 10;
		try {
			currentPage = Integer.parseInt(page);
			pageSize = Integer.parseInt(size);
		} catch (Exception ex) {

		}
		List<Object[]> capstoneProjectPage = capstoneProjectService.getAllByUserId(userid,currentPage- 1,pageSize,status,profession,nameSearch);
		List<CapstoneProjectPagingBodyDTO> AuthorList = new ArrayList<CapstoneProjectPagingBodyDTO>();
		for(Object[] obj : capstoneProjectPage){
			CapstoneProjectPagingBodyDTO detail = new CapstoneProjectPagingBodyDTO();
			detail.setId((Integer) obj[0]);
			detail.setDescription_action((String) obj[1]);
			detail.setDescription((String) obj[2]);
			detail.setDocument((String) obj[3]);
			detail.setName((String) obj[4]);
			detail.setName_abbreviation((String) obj[5]);
			detail.setName_lang_other((String) obj[6]);
			detail.setName_vi((String) obj[7]);
			detail.setProgram((String) obj[8]);
			detail.setSpecialty((String) obj[9]);
			detail.setProfession_id((Integer) obj[10]);
			detail.setStatus_id((Integer) obj[11]);
			detail.setNameStatus((String) obj[14]);
			Integer countstudent = capstoneProjectService.getCountStudent((Integer) obj[0]);
			detail.setCountDetail(countstudent);
			detail.setDetail(null);
			AuthorList.add(detail);
		}
		Pageable secondPageWithFiveElements = PageRequest.of(currentPage -1, pageSize, Sort.by("id").descending());
		Page<CapstoneProjectPagingBodyDTO> list = new PageImpl<>(AuthorList, secondPageWithFiveElements, AuthorList.size());
		model.addAttribute("capstoneProjectPage", list);
		int totalPages = capstoneProjectPage.size();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		return "home/list-capstoneProject";
	}

	@GetMapping("/list-StudentProject")
	public String listStudentProject(Model model, @RequestParam("projectid") String projectid,Principal principal) {
		if(principal == null) {
			return "redirect:/login";
		}
		Users users = userService.findByEmail(principal.getName());
		int roleid = -1;
		if (users != null) {
			roleid = users.getRoleUser().get(0).getUserRoleKey().getRole().getId();
		} else {
			return "error/403Page";
		}
		Integer addStudent = 0;
		CapstoneProjects currentProduct =capstoneProjectService
				.findById(Integer.parseInt(projectid));
		Integer countstudent = capstoneProjectService.getCountStudent(currentProduct.getId());

		if(countstudent == currentProduct.getProfession().getMaxMember()){
			addStudent = 1;
		}
		List<Object[]> projectdetail = capstoneProjectDetailService.getByProjectId(Integer.parseInt(projectid));
		List<CapstoneProjectDetailBody> projectdetailList = new ArrayList<CapstoneProjectDetailBody>();
		for(Object[] objdetail : projectdetail){
			CapstoneProjectDetailBody projectdetailnew = new CapstoneProjectDetailBody();
			projectdetailnew.setId((Integer) objdetail[0]);
			projectdetailnew.setDescription_action((String) objdetail[1]);
			projectdetailnew.setCapstone_project_id((Integer) objdetail[2]);
			projectdetailnew.setStatus_id((Integer) objdetail[3]);
			projectdetailnew.setUser_id((String) objdetail[4]);
			projectdetailnew.setEmail((String) objdetail[5]);
			projectdetailnew.setFirst_name((String) objdetail[6]);
			projectdetailnew.setGender((Integer) objdetail[7]);
			projectdetailnew.setImage((String) objdetail[8]);
			projectdetailnew.setLast_name((String) objdetail[9]);
			projectdetailnew.setPhone((String) objdetail[10]);
			projectdetailnew.setUser_name((String) objdetail[11]);
			projectdetailnew.setRoleid((Integer) objdetail[12]);
			projectdetailnew.setRolename((String) objdetail[13]);
			projectdetailnew.setNameStatus((String) objdetail[14]);
			projectdetailList.add(projectdetailnew);
		}
		Pageable secondPageWithFiveElements = PageRequest.of(0, 100, Sort.by("id").descending());
		Page<CapstoneProjectDetailBody> list = new PageImpl<>(projectdetailList, secondPageWithFiveElements, projectdetailList.size());
		model.addAttribute("capstoneProjectDetailPage", list);
		model.addAttribute("addStudent", addStudent);
		model.addAttribute("role", roleid);
		model.addAttribute("id", projectid);
		return "home/list-capstoneProjectStudent";
	}


	@ResponseBody
	@GetMapping("/update-Status-Detail")
	public String updateStatusDetail( @RequestParam("id") String id, @RequestParam("des") String des,Principal principal) {
		if(principal == null) {
			return "redirect:/login";
		}
		Users users = userService.findByEmail(principal.getName());
		int roleid = -1;
		String userid = "-1";
		if (users !=  null) {
			roleid = users.getRoleUser().get(0).getUserRoleKey().getRole().getId();
			userid = users.getId();;
		} else {
			return "error/403Page";
		}
		Date date = new Date();
		CapstoneProjectDetails currentProduct =capstoneProjectDetailService
				.findById(Integer.parseInt(id));

		if (currentProduct == null) {
			return "error/403Page";
		}
		int statusId = -1;
		if(roleid == 3){
			statusId = 8;
		}
		if(roleid == 4){
			statusId = 6;
		}
		if(roleid == 5){
			switch(currentProduct.getStatus().getId()) {
				case 5:
					statusId = 7;
					break;
				case 6:
					statusId = 7;
					break;
				case 8:
					statusId = 9;
					break;
				case 13:
					statusId = 9;
					break;
			}
		}
//		if(roleid == 5){
//			statusId = 7;
//		}
		currentProduct.setStatus(statusService.getStatusById(statusId));
		currentProduct.setDesAction(des);
		capstoneProjectDetailService.save(currentProduct);
		Users user = capstoneProjectDetailService.getUserById(Integer.parseInt(id)).get(0);
		if(user.getRoleUser().get(0).getUserRoleKey().getRole().getId().equals(4)){

			List<CapstoneProjectDetails> detail = capstoneProjectService.findById(currentProduct.getCapstoneProject().getId()).getCapstoneProjectDetails();
			for(CapstoneProjectDetails item : detail) {
				Users userdetail = capstoneProjectDetailService.getUserById(item.getId()).get(0);
				if (userdetail.getRoleUser().get(0).getUserRoleKey().getRole().getId().equals(4) && !item.getId().equals(Integer.parseInt(id)))
				{
					item.setStatus(statusService.getStatusById(12));
					item.setDesAction(des);
					capstoneProjectDetailService.save(item);
				}
			}
		}

		//insert history
		HistoryRecords history = new HistoryRecords();
		history.setContent("Update Status Capstone Project Detail");
		history.setCapstoneProject(capstoneProjectService.findById(Integer.parseInt(id)));
		history.setCreatedDate(date);
		history.setUser(userService.findById(userid));
		historyRecordService.save(history);
		return "The Project Detail has been update successfully";
	}

	@ResponseBody
	@GetMapping("/update-Status")
	public String updateStatus( @RequestParam("id") String id, @RequestParam("des") String des,Principal principal) {

		if(principal == null) {
			return "redirect:/login";
		}
		Users users = userService.findByEmail(principal.getName());
		int roleid = -1;
		String userid = "-1";
		if (users != null) {
			roleid = users.getRoleUser().get(0).getUserRoleKey().getRole().getId();
			userid = users.getId();;
		} else {
			return "error/403Page";
		}

		CapstoneProjects currentProduct =capstoneProjectService
				.findById(Integer.parseInt(id));

		Integer countstudent = capstoneProjectService.getCountStudent(currentProduct.getId());

		if(countstudent > currentProduct.getProfession().getMaxMember()){
			return "Over student to can approve.";
		}

		if(countstudent < currentProduct.getProfession().getMinMember()){
			return "Deficient student to can approve.";
		}

		if (currentProduct == null) {
			return "Not found project.";
		}
		int statusId = -1;
		int statusIdOld = currentProduct.getStatus().getId();
		if(roleid == 3){
			statusId = 8;
		}
		if(roleid == 4){
			statusId = 6;
		}
		if(roleid == 5){
			switch(statusIdOld) {
				case 6:
					statusId = 7;
					break;
				case 8:
					statusId = 9;
					break;
				case 13:
					statusId = 9;
					break;
			}
		}
		currentProduct.setStatus(statusService.getStatusById(statusId));
		currentProduct.setDesAction(des);
		capstoneProjectService.saveRegisterProject(currentProduct);

		List<CapstoneProjectDetails> detail = currentProduct.getCapstoneProjectDetails();
		Integer count = 0;
		for(CapstoneProjectDetails item : detail) {
			Status data = capstoneProjectDetailService.getStatusById(item.getId()).get(0);
			if (data.getId() == statusIdOld)
			{
				Users userdetail = capstoneProjectDetailService.getUserById(item.getId()).get(0);
				if(userdetail.getRoleUser().get(0).getUserRoleKey().getRole().getId().equals(4) ){
					count = count + 1;
					if(count.equals(1)){
						item.setStatus(statusService.getStatusById(statusId));
						item.setDesAction(des);
						capstoneProjectDetailService.save(item);
					}
					else {
						item.setStatus(statusService.getStatusById(12));
						item.setDesAction(des);
						capstoneProjectDetailService.save(item);
					}
				}
				else {
					item.setStatus(statusService.getStatusById(statusId));
					item.setDesAction(des);
					capstoneProjectDetailService.save(item);
				}
			}
		}
		Date date = new Date();
		HistoryRecords history = new HistoryRecords();
		history.setContent("Update Status Capstone Project");
		history.setCapstoneProject(capstoneProjectService.findById(Integer.parseInt(id)));
		history.setCreatedDate(date);
		history.setUser(userService.findById(userid));
		historyRecordService.save(history);
		return "The Project Detail has been update successfully";
	}

	@ResponseBody
	@GetMapping("/reject")
	public String reject( @RequestParam("id") String id, @RequestParam("des") String des,Principal principal) {
		if(principal == null) {
			return "redirect:/login";
		}
		Users users = userService.findByEmail(principal.getName());
		String userid = "-1";
		if (users !=  null) {
			userid = users.getId();;
		} else {
			return "error/403Page";
		}
		CapstoneProjects currentProduct =capstoneProjectService
				.findById(Integer.parseInt(id));

		if (currentProduct == null) {
			return "Not found project.";
		}
		currentProduct.setStatus(statusService.getStatusById(12));
		currentProduct.setDesAction(des);
		capstoneProjectService.saveRegisterProject(currentProduct);

		List<CapstoneProjectDetails> detail = currentProduct.getCapstoneProjectDetails();
		for(CapstoneProjectDetails item : detail) {
			item.setStatus(statusService.getStatusById(12));
			item.setDesAction(des);
			capstoneProjectDetailService.save(item);
		}

		Date date = new Date();
		HistoryRecords history = new HistoryRecords();
		history.setContent("Reject Capstone Project");
		history.setCapstoneProject(capstoneProjectService.findById(Integer.parseInt(id)));
		history.setCreatedDate(date);
		history.setUser(userService.findById(userid));
		historyRecordService.save(history);
		return "The Project has been update successfully";
	}

	@ResponseBody
	@GetMapping("/rejectDetail")
	public String rejectDetail( @RequestParam("id") String id, @RequestParam("des") String des,Principal principal) {
		if(principal == null) {
			return "redirect:/login";
		}
		Users users = userService.findByEmail(principal.getName());
		String userid = "-1";
		if (users !=  null) {
			userid = users.getId();;
		} else {
			return "error/403Page";
		}
		CapstoneProjectDetails currentProduct =capstoneProjectDetailService
				.findById(Integer.parseInt(id));

		if (currentProduct == null) {
			return "Không tìm thấy student.";
		}
		currentProduct.setStatus(statusService.getStatusById(12));
		currentProduct.setDesAction(des);
		capstoneProjectDetailService.save(currentProduct);

		Date date = new Date();
		HistoryRecords history = new HistoryRecords();
		history.setContent("Reject Capstone Project Detail");
		history.setCapstoneProject(capstoneProjectService.findById(Integer.parseInt(id)));
		history.setCreatedDate(date);
		history.setUser(userService.findById(userid));
		historyRecordService.save(history);
		return "The Project has been update successfully";
	}


	@ResponseBody
	@GetMapping("/update-StatusList")
	public String updateStatusList( @RequestParam("id") String id, @RequestParam("des") String des,Principal principal) {
		if(principal == null) {
			return "redirect:/login";
		}
		Users users = userService.findByEmail(principal.getName());
		int roleid = -1;
		String userid = "-1";
		if (users != null) {
			roleid = users.getRoleUser().get(0).getUserRoleKey().getRole().getId();
			userid = users.getId();;
		} else {
			return "error/403Page";
		}
		String[] st = id.split(",");
		for(int i = 0;i < st.length; i++ ) {
			CapstoneProjects currentProduct =capstoneProjectService
					.findById(Integer.parseInt(st[i]));

			Integer countstudent = capstoneProjectService.getCountStudent(currentProduct.getId());

			if(countstudent > currentProduct.getProfession().getMaxMember()){
				return "Đã quá số học sinh.";
			}

			if(countstudent < currentProduct.getProfession().getMinMember()){
				return "Chưa đủ số học sinh.";
			}

			if (currentProduct == null) {
				return "Không tìm thấy project.";
			}
			int statusId = -1;
			if(roleid == 3){
				statusId = 8;
			}
			if(roleid == 4){
				statusId = 6;
			}
			if(roleid == 5){
				switch(currentProduct.getStatus().getId()) {
					case 6:
						statusId = 7;
						break;
					case 8:
						statusId = 9;
						break;
					case 13:
						statusId = 9;
						break;
				}
			}
			currentProduct.setStatus(statusService.getStatusById(statusId));
			currentProduct.setDesAction(des);
			boolean data = capstoneProjectService.saveRegisterProject(currentProduct);
			if (!data){
				return "error";
			}
			List<CapstoneProjectDetails> detail = currentProduct.getCapstoneProjectDetails();
			for(CapstoneProjectDetails item : detail) {
				item.setStatus(statusService.getStatusById(statusId));
				item.setDesAction(des);
				capstoneProjectDetailService.save(item);
			}
			Date date = new Date();
			HistoryRecords history = new HistoryRecords();
			history.setContent("Update Status Capstone Project");
			history.setCapstoneProject(capstoneProjectService.findById(Integer.parseInt(st[i])));
			history.setCreatedDate(date);
			history.setUser(userService.findById(userid));
			boolean hist = historyRecordService.save(history);
			if (!hist){
				return "error";
			}
		}

		return "The Project Detail has been update successfully";
	}

	@ResponseBody
	@GetMapping("/rejectList")
	public String rejectList( @RequestParam("id") String id, @RequestParam("des") String des,Principal principal) {
		if(principal == null) {
			return "redirect:/login";
		}
		Users users = userService.findByEmail(principal.getName());
		String userid = "-1";
		if (users != null) {
			userid = users.getId();;
		} else {
			return "error/403Page";
		}
		String[] st = id.split(",");
		for(int i = 0;i < st.length; i++ ) {
			CapstoneProjects currentProduct =capstoneProjectService
					.findById(Integer.parseInt(st[i]));

			if (currentProduct == null) {
				return "Not found project.";
			}
			currentProduct.setStatus(statusService.getStatusById(12));
			currentProduct.setDesAction(des);
			boolean data = capstoneProjectService.saveRegisterProject(currentProduct);
			if (!data){
				return "error";
			}

			Date date = new Date();
			HistoryRecords history = new HistoryRecords();
			history.setContent("Reject Capstone Project");
			history.setCapstoneProject(capstoneProjectService.findById(Integer.parseInt(st[i])));
			history.setCreatedDate(date);
			history.setUser(userService.findById(userid));
			boolean hist = historyRecordService.save(history);
			if (!hist){
				return "error";
			}
		}

		return "The Project has been update successfully";
	}
	@ResponseBody
	@GetMapping("/insertDetail")
	public String insertDetail( @RequestParam("id") String id, @RequestParam("capstoneProject") String capstoneProject,Principal principal) {
		if(principal == null) {
			return "redirect:/login";
		}
		Users users = userService.findByEmail(principal.getName());
		String userid = "-1";
		if (users !=  null) {
			userid = users.getId();;
		} else {
			return "error/403Page";
		}
		CapstoneProjects mas = capstoneProjectService.findById(Integer.parseInt(capstoneProject));
		Integer countstudent = capstoneProjectService.getCountStudent(mas.getId());
		if(countstudent > mas.getProfession().getMaxMember()){
			return "Over student to can approve project.";
		}
		String[] st = id.split(",");
		for(int i = 0;i < st.length; i++ ) {
			CapstoneProjectDetails projects = new CapstoneProjectDetails();
			int statusId = 9;
			projects.setDesAction("");
			projects.setUser(userService.findByUsername(st[i]).get(0));
			projects.setCapstoneProject(capstoneProjectService.findById(Integer.parseInt(capstoneProject)));
			projects.setStatus(statusService.getStatusById(statusId));
			capstoneProjectDetailService.addCapstonprojectDetail(projects);
		}


		Date date = new Date();
		HistoryRecords history = new HistoryRecords();
		history.setContent("Add Capstone Project Detail ");
		history.setCapstoneProject(capstoneProjectService.findById(Integer.parseInt(capstoneProject)));
		history.setCreatedDate(date);
		history.setUser(userService.findById(userid));
		historyRecordService.save(history);
		return "Create Project Detail successfully";
	}
	@ResponseBody
	@RequestMapping(value = "/getMemberProject")
	public String getMemberProject(@RequestParam("username") String username, @RequestParam("capstoneProject") String capstoneProject,Principal principal) {
		if(principal == null) {
			return "redirect:/login";
		}
		Map<String, Object> result = new HashMap<>();
		boolean success = true;
		String message = "";
		List<Users> mas = capstoneProjectDetailService.getUserByCapstoneProjectDetailId(Integer.parseInt(capstoneProject));
		for(Users item : mas) {
			if(item.getUsername().equals(username)){
				success = false;
				message = "Username already exists in Project";
				result.put("success", success);
				result.put("message", message);
				result.put("user", null);
				return new Gson().toJson(result);
			}
		}
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
	//KienBT4 add capstone end

	@RequestMapping(value = "/st/registerproject", method = RequestMethod.GET)
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
			//check status user
			Status status = users.get(0).getStatus();
			if(status.getName().equals(Constant.STATUS_INACTIVE_USER_DB) || status.getName().equals(Constant.STATUS_NOT_ELIGIBLE_CAPSTONE_DB)){
				success = false;
				message = "User is inactive or not eligible";
				result.put("success", success);
				result.put("message", message);
				return new Gson().toJson(result);
			}
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
			result.getFieldErrors().stream().forEach(f -> errors.add(f.getDefaultMessage()));
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
