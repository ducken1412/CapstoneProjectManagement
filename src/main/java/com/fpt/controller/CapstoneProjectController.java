package com.fpt.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Constraint;
import javax.validation.Valid;

import com.fpt.dto.*;
import com.fpt.entity.*;
import com.fpt.service.*;
import com.fpt.utils.Constant;
import com.fpt.utils.ExcelHelper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
	@Autowired
	FilesStorageService storageService;

	//KienBT4 add capstone start
	@GetMapping("/ad/capstoneproject")
	public String forum(Model model , Principal principal) {

		if(principal == null) {
			return "redirect:/login";
		}
		Users users = userService.findByEmail(principal.getName());
		int roleid = -1;
		String userid = "-1";
		if (users !=  null) {
			roleid = users.getRoleUser().get(0).getUserRoleKey().getRole().getId();
		} else {
			return "error/403Page";
		}
		List<Profession> profession = professionService.findAll();
		model.addAttribute("profession", profession);

		List<Status> statusList = statusService.getAll();

		List<Status> statusConstant =  new ArrayList<>();
		for(Status status : statusList){
			if(roleid == 3 || roleid == 5) {
				String nameStatus = null;
				switch (status.getName()) {
					case Constant.STATUS_REGISTERING_CAPSTONE_DB:
						nameStatus = Constant.STATUS_REGISTERING_CAPSTONE;
						break;
					case Constant.STATUS_REGISTED_CAPSTONE_DB:
						nameStatus = Constant.STATUS_REGISTED_CAPSTONE;
						break;
					case Constant.STATUS_APPROVE_CAPSTONE_LUCTURER_DB:
						nameStatus = Constant.STATUS_APPROVE_CAPSTONE_LECTURER;
						break;
					case Constant.STATUS_APPROVE_CAPSTONE_TRAINING_DB:
						nameStatus = Constant.STATUS_APPROVE_CAPSTONE_TRAINING;
						break;
					case Constant.STATUS_APPROVE_CAPSTONE_HEAD_DB:
						nameStatus = Constant.STATUS_APPROVE_CAPSTONE_HEAD;
						break;
					case Constant.STATUS_DOING_CAPSTONE_DB:
						nameStatus = Constant.STATUS_DOING_CAPSTONE;
						break;
					case Constant.STATUS_NOT_ELIGIBLE_DEFENCE_CAPSTONE_DB:
						nameStatus = Constant.STATUS_NOT_ELIGIBLE_DEFENCE_CAPSTONE;
						break;
					case Constant.STATUS_ELIGIBLE_DEFENCE_CAPSTONE_DB:
						nameStatus = Constant.STATUS_ELIGIBLE_DEFENCE_CAPSTONE;
						break;
					case Constant.STATUS_REJECT_CAPSTONE_DB:
						nameStatus = Constant.STATUS_REJECT_CAPSTONE;
						break;
					case Constant.STATUS_CHANGING_NAME_CAPSTONE_DB:
						nameStatus = Constant.STATUS_CHANGING_NAME_CAPSTONE;
						break;
					case Constant.STATUS_CHANGING_NAME_BY_LECTURES_CAPSTONE_DB:
						nameStatus = Constant.STATUS_CHANGING_NAME_BY_LECTURER_CAPSTONE;
						break;
					case Constant.STATUS_PENDING_CAPSTONE_DB:
						nameStatus = Constant.STATUS_PENDING_CAPSTONE;
						break;
					case Constant.STATUS_PENDING_CAPSTONE_BY_HEAD_DB:
						nameStatus = Constant.STATUS_PENDING_CAPSTONE_BY_HEAD;
						break;
					case Constant.STATUS_ELIGIBLE_DEFENCE_CAPSTONE_BY_LECTURE_CAPSTONE_DB:
						nameStatus = Constant.STATUS_ELIGIBLE_DEFENCE_CAPSTONE_BY_LECTURE_CAPSTONE;
						break;
					default:
						nameStatus = null;
				}
				if(nameStatus != null){
					Status statusTemp = status;
					statusTemp.setName(nameStatus);
					statusConstant.add(statusTemp);
				}

			}else {
				String nameStatus = null;
				switch (status.getName()) {
					case Constant.STATUS_REGISTED_CAPSTONE_DB:
						nameStatus = Constant.STATUS_REGISTED_CAPSTONE;
						break;
					case Constant.STATUS_APPROVE_CAPSTONE_TRAINING_DB:
						nameStatus = Constant.STATUS_APPROVE_CAPSTONE_TRAINING;
						break;
					case Constant.STATUS_APPROVE_CAPSTONE_HEAD_DB:
						nameStatus = Constant.STATUS_APPROVE_CAPSTONE_HEAD;
						break;
					case Constant.STATUS_DOING_CAPSTONE_DB:
						nameStatus = Constant.STATUS_DOING_CAPSTONE;
						break;
					case Constant.STATUS_NOT_ELIGIBLE_DEFENCE_CAPSTONE_DB:
						nameStatus = Constant.STATUS_NOT_ELIGIBLE_DEFENCE_CAPSTONE;
						break;
					case Constant.STATUS_ELIGIBLE_DEFENCE_CAPSTONE_DB:
						nameStatus = Constant.STATUS_ELIGIBLE_DEFENCE_CAPSTONE;
						break;
					case Constant.STATUS_CHANGING_NAME_CAPSTONE_DB:
						nameStatus = Constant.STATUS_CHANGING_NAME_CAPSTONE;
						break;
					case Constant.STATUS_PENDING_CAPSTONE_DB:
						nameStatus = Constant.STATUS_PENDING_CAPSTONE;
						break;
					case Constant.STATUS_PENDING_CAPSTONE_BY_HEAD_DB:
						nameStatus = Constant.STATUS_PENDING_CAPSTONE_BY_HEAD;
						break;
					case Constant.STATUS_ELIGIBLE_DEFENCE_CAPSTONE_BY_LECTURE_CAPSTONE_DB:
						nameStatus = Constant.STATUS_ELIGIBLE_DEFENCE_CAPSTONE_BY_LECTURE_CAPSTONE;
						break;
					default:
						nameStatus = null;
				}
				if(nameStatus != null){
					Status statusTemp = status;
					statusTemp.setName(nameStatus);
					statusConstant.add(statusTemp);
				}
			}

		}

		model.addAttribute("status", statusConstant);

		return "home/CapstoneProject";
	}

	@ResponseBody
	@GetMapping("/exportExcel")
	public String exportExcel(Model model, Principal principal, @RequestParam("status") Integer status,
							  @RequestParam("profession") Integer profession
			, @RequestParam("nameSearch") String nameSearch) throws IOException {
		if(principal == null) {
			return "redirect:/login";
		}

		List<Object[]> capstoneProjectPage = capstoneProjectService.getAllCap(status,profession,nameSearch);
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
			detail.setNameChanging((String) obj[12]);
			detail.setNameChangingVi((String) obj[13]);
			String nameStatus = (String) obj[14];
			switch (nameStatus) {
				case Constant.STATUS_REGISTERING_CAPSTONE_DB:
					nameStatus = Constant.STATUS_REGISTERING_CAPSTONE;
					break;
				case Constant.STATUS_REGISTED_CAPSTONE_DB:
					nameStatus = Constant.STATUS_REGISTED_CAPSTONE;
					break;
				case Constant.STATUS_APPROVE_CAPSTONE_LUCTURER_DB:
					nameStatus = Constant.STATUS_APPROVE_CAPSTONE_LECTURER;
					break;
				case Constant.STATUS_APPROVE_CAPSTONE_TRAINING_DB:
					nameStatus = Constant.STATUS_APPROVE_CAPSTONE_TRAINING;
					break;
				case Constant.STATUS_APPROVE_CAPSTONE_HEAD_DB:
					nameStatus = Constant.STATUS_APPROVE_CAPSTONE_HEAD;
					break;
				case Constant.STATUS_DOING_CAPSTONE_DB:
					nameStatus = Constant.STATUS_DOING_CAPSTONE;
					break;
				case Constant.STATUS_NOT_ELIGIBLE_DEFENCE_CAPSTONE_DB:
					nameStatus = Constant.STATUS_NOT_ELIGIBLE_DEFENCE_CAPSTONE;
					break;
				case Constant.STATUS_ELIGIBLE_DEFENCE_CAPSTONE_DB:
					nameStatus = Constant.STATUS_ELIGIBLE_DEFENCE_CAPSTONE;
					break;
				case Constant.STATUS_REJECT_CAPSTONE_DB:
					nameStatus = Constant.STATUS_REJECT_CAPSTONE;
					break;
				case Constant.STATUS_CHANGING_NAME_CAPSTONE_DB:
					nameStatus = Constant.STATUS_CHANGING_NAME_CAPSTONE;
					break;
				case Constant.STATUS_CHANGING_NAME_BY_LECTURES_CAPSTONE_DB:
					nameStatus = Constant.STATUS_CHANGING_NAME_BY_LECTURER_CAPSTONE;
					break;
				case Constant.STATUS_PENDING_CAPSTONE_DB:
					nameStatus = Constant.STATUS_PENDING_CAPSTONE;
					break;
				case Constant.STATUS_PENDING_CAPSTONE_BY_HEAD_DB:
					nameStatus = Constant.STATUS_PENDING_CAPSTONE_BY_HEAD;
					break;
				case Constant.STATUS_ELIGIBLE_DEFENCE_CAPSTONE_BY_LECTURE_CAPSTONE_DB:
					nameStatus = Constant.STATUS_ELIGIBLE_DEFENCE_CAPSTONE_BY_LECTURE_CAPSTONE;
					break;
				default:
					nameStatus = null;
			}
			detail.setNameStatus(nameStatus);
			detail.setSubjectCode(String.valueOf(obj[15] + "_" + obj[0]));
			Integer countstudent = capstoneProjectService.getCountStudent((Integer) obj[0]);
			detail.setCountDetail(countstudent);

			//get User in capstone

			List<Object[]> projectdetail = capstoneProjectDetailService.getByProjectId(detail.getId());
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
				String roleDB = (String) objdetail[13];
				switch (roleDB) {
					case Constant.ROLE_HEAD_DB:
						roleDB = Constant.ROLE_HEAD;
						break;
					case Constant.ROLE_LECTURERS_DB:
						roleDB = Constant.ROLE_LECTURERS;
						break;
					case Constant.ROLE_STUDENT_MEMBER_DB:
						roleDB = Constant.ROLE_STUDENT_MEMBER;
						break;
					case Constant.ROLE_STUDENT_LEADER_DB:
						roleDB = Constant.ROLE_STUDENT_LEADER;
						break;
					default:
						roleDB = null;
				}
				projectdetailnew.setRolename(roleDB);

				String nameStatusDeatil = (String) objdetail[14];
				switch (nameStatusDeatil) {
					case Constant.STATUS_REGISTERING_CAPSTONE_DB:
						nameStatusDeatil = Constant.STATUS_REGISTERING_CAPSTONE;
						break;
					case Constant.STATUS_REGISTED_CAPSTONE_DB:
						nameStatusDeatil = Constant.STATUS_REGISTED_CAPSTONE;
						break;
					case Constant.STATUS_APPROVE_CAPSTONE_LUCTURER_DB:
						nameStatusDeatil = Constant.STATUS_APPROVE_CAPSTONE_LECTURER;
						break;
					case Constant.STATUS_APPROVE_CAPSTONE_TRAINING_DB:
						nameStatusDeatil = Constant.STATUS_APPROVE_CAPSTONE_TRAINING;
						break;
					case Constant.STATUS_APPROVE_CAPSTONE_HEAD_DB:
						nameStatusDeatil = Constant.STATUS_APPROVE_CAPSTONE_HEAD;
						break;
					case Constant.STATUS_DOING_CAPSTONE_DB:
						nameStatusDeatil = Constant.STATUS_DOING_CAPSTONE;
						break;
					case Constant.STATUS_NOT_ELIGIBLE_DEFENCE_CAPSTONE_DB:
						nameStatusDeatil = Constant.STATUS_NOT_ELIGIBLE_DEFENCE_CAPSTONE;
						break;
					case Constant.STATUS_ELIGIBLE_DEFENCE_CAPSTONE_DB:
						nameStatusDeatil = Constant.STATUS_ELIGIBLE_DEFENCE_CAPSTONE;
						break;
					case Constant.STATUS_REJECT_CAPSTONE_DB:
						nameStatusDeatil = Constant.STATUS_REJECT_CAPSTONE;
						break;
					case Constant.STATUS_CHANGING_NAME_CAPSTONE_DB:
						nameStatusDeatil = Constant.STATUS_CHANGING_NAME_CAPSTONE;
						break;
					case Constant.STATUS_CHANGING_NAME_BY_LECTURES_CAPSTONE_DB:
						nameStatusDeatil = Constant.STATUS_CHANGING_NAME_BY_LECTURER_CAPSTONE;
						break;
					case Constant.STATUS_PENDING_CAPSTONE_DB:
						nameStatusDeatil = Constant.STATUS_PENDING_CAPSTONE;
						break;
					case Constant.STATUS_PENDING_CAPSTONE_BY_HEAD_DB:
						nameStatusDeatil = Constant.STATUS_PENDING_CAPSTONE_BY_HEAD;
						break;
					case Constant.STATUS_ELIGIBLE_DEFENCE_CAPSTONE_BY_LECTURE_CAPSTONE_DB:
						nameStatusDeatil = Constant.STATUS_ELIGIBLE_DEFENCE_CAPSTONE_BY_LECTURE_CAPSTONE;
						break;
					default:
						nameStatusDeatil = null;
				}
				if(!roleDB.equals(Constant.ROLE_LECTURERS)){
					projectdetailnew.setNameStatus(nameStatusDeatil);
				}
				projectdetailList.add(projectdetailnew);
			}

			detail.setDetail(projectdetailList);

			AuthorList.add(detail);
		}
		Resource file = storageService.load("CapstoneProjectList.xlsx");
		String excelFilePath = file.getURI().getPath();
		ExcelHelper.writeExcel(AuthorList,excelFilePath);

		return "true";
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
		Integer countCapstoneProjectPage = capstoneProjectService.countCapAll(userid,status,profession,nameSearch);
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
			detail.setNameChanging((String) obj[12]);
			detail.setNameChangingVi((String) obj[13]);
			String nameStatus = (String) obj[14];
			switch (nameStatus) {
				case Constant.STATUS_REGISTERING_CAPSTONE_DB:
					nameStatus = Constant.STATUS_REGISTERING_CAPSTONE;
					break;
				case Constant.STATUS_REGISTED_CAPSTONE_DB:
					nameStatus = Constant.STATUS_REGISTED_CAPSTONE;
					break;
				case Constant.STATUS_APPROVE_CAPSTONE_LUCTURER_DB:
					nameStatus = Constant.STATUS_APPROVE_CAPSTONE_LECTURER;
					break;
				case Constant.STATUS_APPROVE_CAPSTONE_TRAINING_DB:
					nameStatus = Constant.STATUS_APPROVE_CAPSTONE_TRAINING;
					break;
				case Constant.STATUS_APPROVE_CAPSTONE_HEAD_DB:
					nameStatus = Constant.STATUS_APPROVE_CAPSTONE_HEAD;
					break;
				case Constant.STATUS_DOING_CAPSTONE_DB:
					nameStatus = Constant.STATUS_DOING_CAPSTONE;
					break;
				case Constant.STATUS_NOT_ELIGIBLE_DEFENCE_CAPSTONE_DB:
					nameStatus = Constant.STATUS_NOT_ELIGIBLE_DEFENCE_CAPSTONE;
					break;
				case Constant.STATUS_ELIGIBLE_DEFENCE_CAPSTONE_DB:
					nameStatus = Constant.STATUS_ELIGIBLE_DEFENCE_CAPSTONE;
					break;
				case Constant.STATUS_REJECT_CAPSTONE_DB:
					nameStatus = Constant.STATUS_REJECT_CAPSTONE;
					break;
				case Constant.STATUS_CHANGING_NAME_CAPSTONE_DB:
					nameStatus = Constant.STATUS_CHANGING_NAME_CAPSTONE;
					break;
				case Constant.STATUS_CHANGING_NAME_BY_LECTURES_CAPSTONE_DB:
					nameStatus = Constant.STATUS_CHANGING_NAME_BY_LECTURER_CAPSTONE;
					break;
				case Constant.STATUS_PENDING_CAPSTONE_DB:
					nameStatus = Constant.STATUS_PENDING_CAPSTONE;
					break;
				case Constant.STATUS_PENDING_CAPSTONE_BY_HEAD_DB:
					nameStatus = Constant.STATUS_PENDING_CAPSTONE_BY_HEAD;
					break;
				case Constant.STATUS_ELIGIBLE_DEFENCE_CAPSTONE_BY_LECTURE_CAPSTONE_DB:
					nameStatus = Constant.STATUS_ELIGIBLE_DEFENCE_CAPSTONE_BY_LECTURE_CAPSTONE;
					break;
				default:
					nameStatus = null;
			}
			detail.setNameStatus(nameStatus);
			detail.setSubjectCode(String.valueOf(obj[15] + "_" + obj[0]));
			Integer countstudent = capstoneProjectService.getCountStudent((Integer) obj[0]);
			detail.setCountDetail(countstudent);
			detail.setDetail(null);
			AuthorList.add(detail);
		}
		Pageable secondPageWithFiveElements = PageRequest.of(currentPage -1, pageSize, Sort.by("id").descending());
		Page<CapstoneProjectPagingBodyDTO> list = new PageImpl<>(AuthorList, secondPageWithFiveElements, countCapstoneProjectPage);
		model.addAttribute("capstoneProjectPage", list);

		int totalPages = list.getTotalPages();
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
		Status status = statusService.findStatusByCapstoneProject(currentProduct.getId());
		int statusIdOld = status.getId();

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
			String roleDB = (String) objdetail[13];
			switch (roleDB) {
				case Constant.ROLE_HEAD_DB:
					roleDB = Constant.ROLE_HEAD;
					break;
				case Constant.ROLE_LECTURERS_DB:
					roleDB = Constant.ROLE_LECTURERS;
					break;
				case Constant.ROLE_STUDENT_MEMBER_DB:
					roleDB = Constant.ROLE_STUDENT_MEMBER;
					break;
				case Constant.ROLE_STUDENT_LEADER_DB:
					roleDB = Constant.ROLE_STUDENT_LEADER;
					break;
				default:
					roleDB = "";
			}
			projectdetailnew.setRolename(roleDB);

			String nameStatus = (String) objdetail[14];
			switch (nameStatus) {
				case Constant.STATUS_REGISTERING_CAPSTONE_DB:
					nameStatus = Constant.STATUS_REGISTERING_CAPSTONE;
					break;
				case Constant.STATUS_REGISTED_CAPSTONE_DB:
					nameStatus = Constant.STATUS_REGISTED_CAPSTONE;
					break;
				case Constant.STATUS_APPROVE_CAPSTONE_LUCTURER_DB:
					nameStatus = Constant.STATUS_APPROVE_CAPSTONE_LECTURER;
					break;
				case Constant.STATUS_APPROVE_CAPSTONE_TRAINING_DB:
					nameStatus = Constant.STATUS_APPROVE_CAPSTONE_TRAINING;
					break;
				case Constant.STATUS_APPROVE_CAPSTONE_HEAD_DB:
					nameStatus = Constant.STATUS_APPROVE_CAPSTONE_HEAD;
					break;
				case Constant.STATUS_DOING_CAPSTONE_DB:
					nameStatus = Constant.STATUS_DOING_CAPSTONE;
					break;
				case Constant.STATUS_NOT_ELIGIBLE_DEFENCE_CAPSTONE_DB:
					nameStatus = Constant.STATUS_NOT_ELIGIBLE_DEFENCE_CAPSTONE;
					break;
				case Constant.STATUS_ELIGIBLE_DEFENCE_CAPSTONE_DB:
					nameStatus = Constant.STATUS_ELIGIBLE_DEFENCE_CAPSTONE;
					break;
				case Constant.STATUS_REJECT_CAPSTONE_DB:
					nameStatus = Constant.STATUS_REJECT_CAPSTONE;
					break;
				case Constant.STATUS_CHANGING_NAME_CAPSTONE_DB:
					nameStatus = Constant.STATUS_CHANGING_NAME_CAPSTONE;
					break;
				case Constant.STATUS_CHANGING_NAME_BY_LECTURES_CAPSTONE_DB:
					nameStatus = Constant.STATUS_CHANGING_NAME_BY_LECTURER_CAPSTONE;
					break;
				case Constant.STATUS_PENDING_CAPSTONE_DB:
					nameStatus = Constant.STATUS_PENDING_CAPSTONE;
					break;
				case Constant.STATUS_PENDING_CAPSTONE_BY_HEAD_DB:
					nameStatus = Constant.STATUS_PENDING_CAPSTONE_BY_HEAD;
					break;
				case Constant.STATUS_ELIGIBLE_DEFENCE_CAPSTONE_BY_LECTURE_CAPSTONE_DB:
					nameStatus = Constant.STATUS_ELIGIBLE_DEFENCE_CAPSTONE_BY_LECTURE_CAPSTONE;
					break;
				default:
					nameStatus = null;
			}
			if(!roleDB.equals(Constant.ROLE_LECTURERS)){
				projectdetailnew.setNameStatus(nameStatus);
			}
			projectdetailList.add(projectdetailnew);
		}
		Pageable secondPageWithFiveElements = PageRequest.of(0, 100, Sort.by("id").descending());
		Page<CapstoneProjectDetailBody> list = new PageImpl<>(projectdetailList, secondPageWithFiveElements, projectdetailList.size());
		model.addAttribute("capstoneProjectDetailPage", list);
		model.addAttribute("addStudent", addStudent);
		model.addAttribute("role", roleid);
		model.addAttribute("id", projectid);

		if((statusIdOld >= 5 && statusIdOld <= 8)
				|| statusIdOld == 14
				|| statusIdOld == 16 ){
			model.addAttribute("checkEditDetail", true);
		}else {
			model.addAttribute("checkEditDetail", false);
		}

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
			if(currentProduct.getStatus().getId() == 5){
				statusId = 6;
			}else {
				statusId = 17;
			}

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
				case 15:
					statusId = 9;
					break;
				case 14:
					statusId = 7;
					break;
				case 16:
					statusId = 7;
					break;
			}
		}
		Status statusNew = statusService.getStatusById(statusId);
		currentProduct.setStatus(statusNew);
		currentProduct.setDesAction(des);
		capstoneProjectDetailService.save(currentProduct);
		Users user = capstoneProjectDetailService.getUserById(Integer.parseInt(id)).get(0);
		if(user.getRoleUser().get(0).getUserRoleKey().getRole().getId().equals(4)){

			List<CapstoneProjectDetails> detail = capstoneProjectService.findById(currentProduct.getCapstoneProject().getId()).getCapstoneProjectDetails();
			for(CapstoneProjectDetails item : detail) {
				Users userdetail = capstoneProjectDetailService.getUserById(item.getId()).get(0);
				if (userdetail.getRoleUser().get(0).getUserRoleKey().getRole().getId().equals(4) && !item.getId().equals(Integer.parseInt(id)))
				{
					capstoneProjectDetailService.deleteRejectCapstoneProjectDetailsByUserId(userdetail.getId(),currentProduct.getId());
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
		return "The Project has been update successfully";
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

		Integer countLecture = 0;
		List<CapstoneProjectDetails> detail = currentProduct.getCapstoneProjectDetails();
		for(CapstoneProjectDetails item : detail) {
			Users userdetail = capstoneProjectDetailService.getUserById(item.getId()).get(0);
			if(userdetail.getRoleUser().get(0).getUserRoleKey().getRole().getId().equals(4)) {
				if(item.getStatus().getId() >= 5 && item.getStatus().getId() <= 8){
					countLecture = countLecture + 1;
				}
			}

		}
		if(countLecture >1) {
			return "Capstone Project: " + currentProduct.getProfession().getSubjectCode() + "_"
					+ currentProduct.getId() + " has 2 supervisors, please reject a supervisors";
		}
		int statusId = -1;
		Status status = statusService.findStatusByCapstoneProject(currentProduct.getId());
		int statusIdOld = status.getId();
		if(roleid == 3){
			statusId = 8;
		}
		if(roleid == 4){
			if(statusIdOld == 13){
				statusId = 15;
			}else if(statusIdOld == 5) {
				statusId = 6;
			} else {
				statusId = 17;
			}
		}
		if(roleid == 5){
			switch(statusIdOld) {
				case 5:
					statusId = 7;
					break;
				case 6:
					statusId = 7;
					break;
				case 14:
					statusId = 7;
					break;
				case 16:
					statusId = 7;
					break;
				case 8:
					statusId = 9;
					break;
				case 15:
					statusId = 9;
					break;
				case 17:
					statusId = 11;
					break;
			}
		}
		if(statusIdOld == 15){
			currentProduct.setName(currentProduct.getNameChanging());
			currentProduct.setNameVi(currentProduct.getNameViChanging());
		}
		Status statusNew = statusService.getStatusById(statusId);
		currentProduct.setStatus(statusNew);
		currentProduct.setDesAction(des);
		capstoneProjectService.saveRegisterProject(currentProduct);

		if(statusIdOld != 15 && statusIdOld != 13){
			for(CapstoneProjectDetails item : detail) {
				Status data = capstoneProjectDetailService.getStatusById(item.getId()).get(0);
				if (data.getId() == statusIdOld)
				{
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
		return "The Project has been update successfully";
	}

	@ResponseBody
	@GetMapping("/reject")
	public String reject( @RequestParam("id") String id, @RequestParam("des") String des,Principal principal) {
		if(principal == null) {
			return "redirect:/login";
		}
		Users users = userService.findByEmail(principal.getName());
		String userid = "-1";
		int roleid = -1;
		if (users !=  null) {
			userid = users.getId();;
			roleid = users.getRoleUser().get(0).getUserRoleKey().getRole().getId();
		} else {
			return "error/403Page";
		}
		CapstoneProjects currentProduct =capstoneProjectService
				.findById(Integer.parseInt(id));

		if (currentProduct == null) {
			return "Not found project.";
		}
		int statusId = -1;
		Status status = statusService.findStatusByCapstoneProject(currentProduct.getId());
		int statusIdOld = status.getId();
		if(roleid == 3){
			statusId = 16;
		}
		if(roleid == 4){
			if(statusIdOld == 13){
				statusId = 9;
			}else if(statusIdOld == 5){
				statusId = 14;
			}else {
				statusId = 10;
			}
		}
		if(roleid == 5){
			switch(statusIdOld) {
				case 15:
					statusId = 9;
					break;
				default:
					statusId = 12;
			}
		}

		if (statusIdOld == 13 || statusIdOld == 15) {
			currentProduct.setName(null);
			currentProduct.setNameVi(null);
			Status statusNew = statusService.getStatusById(statusId);
			currentProduct.setStatus(statusNew);
			currentProduct.setDesAction(des);
			capstoneProjectService.saveRegisterProject(currentProduct);
		} else {
			Status statusNew = statusService.getStatusById(statusId);
			currentProduct.setStatus(statusNew);
			currentProduct.setDesAction(des);
			capstoneProjectService.saveRegisterProject(currentProduct);
			List<CapstoneProjectDetails> detail = currentProduct.getCapstoneProjectDetails();
			for(CapstoneProjectDetails item : detail) {
				item.setStatus(statusService.getStatusById(statusId));
				item.setDesAction(des);
				capstoneProjectDetailService.save(item);
			}
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
		int roleid = -1;
		if (users !=  null) {
			userid = users.getId();;
			roleid = users.getRoleUser().get(0).getUserRoleKey().getRole().getId();
		} else {
			return "error/403Page";
		}
		CapstoneProjectDetails currentProduct =capstoneProjectDetailService
				.findById(Integer.parseInt(id));

		if (currentProduct == null) {
			return "not found student.";
		}

		int statusId = -1;
		switch(roleid) {
			case 3:
				statusId = 16;
				break;
			case 4:
				statusId = 14;
				break;
			case 5:
				statusId = 12;
		}

		Status statusNew = statusService.getStatusById(statusId);
		currentProduct.setStatus(statusNew);
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

		String subjectCodeUnsucess =  null;
		int success =0;
		String[] st = id.split(",");
		for(int i = 0;i < st.length; i++ ) {
			CapstoneProjects currentProduct =capstoneProjectService
					.findById(Integer.parseInt(st[i]));

			Integer countstudent = capstoneProjectService.getCountStudent(currentProduct.getId());

			if(countstudent > currentProduct.getProfession().getMaxMember()){
				return "Project " + currentProduct.getProfession().getSubjectCode() + "_"
						+ currentProduct.getId() + " over student to can approve project.";
			}

			if(countstudent < currentProduct.getProfession().getMinMember()){
				return "Project " + currentProduct.getProfession().getSubjectCode() + "_"
						+ currentProduct.getId() + " deficient student to can approve.";
			}

			if (currentProduct == null) {
				return "Not found project.";
			}
			Integer countLecture = 0;
			List<CapstoneProjectDetails> detail = currentProduct.getCapstoneProjectDetails();
			for(CapstoneProjectDetails item : detail) {
				Users userdetail = capstoneProjectDetailService.getUserById(item.getId()).get(0);
				if(userdetail.getRoleUser().get(0).getUserRoleKey().getRole().getId().equals(4)) {
					if(item.getStatus().getId() >= 5 && item.getStatus().getId() <= 8){
						countLecture = countLecture + 1;
					}
				}

			}
			if(countLecture >1) {
				if(subjectCodeUnsucess != null){
					subjectCodeUnsucess = subjectCodeUnsucess + " ; "+ currentProduct.getProfession().getSubjectCode() + "_"
							+ currentProduct.getId() ;
				}else {
					subjectCodeUnsucess = currentProduct.getProfession().getSubjectCode() + "_"
							+ currentProduct.getId() ;
				}
				break;

			}else {
				Status statusOld = statusService.findStatusByCapstoneProject(currentProduct.getId());
				int statusIdOld = statusOld.getId();
				int statusId = -1;
				if(roleid == 3){
					statusId = 8;
				}
				if(roleid == 4){
					if(statusIdOld == 13){
						statusId = 15;
					}else if(statusIdOld == 5) {
						statusId = 6;
					} else {
						statusId = 17;
					}

				}

				if(roleid == 5){
					switch(statusIdOld) {
						case 5:
							statusId = 7;
							break;
						case 6:
							statusId = 7;
							break;
						case 14:
							statusId = 7;
							break;
						case 16:
							statusId = 7;
							break;
						case 8:
							statusId = 9;
							break;
						case 15:
							statusId = 9;
							break;
						case 17:
							statusId = 11;
							break;
					}
				}
				if(statusIdOld == 15){
					currentProduct.setName(currentProduct.getNameChanging());
					currentProduct.setNameVi(currentProduct.getNameViChanging());
				}
				Status statusNew = statusService.getStatusById(statusId);
				currentProduct.setStatus(statusNew);
				currentProduct.setDesAction(des);
				boolean data = capstoneProjectService.saveRegisterProject(currentProduct);
				if (!data){
					return "error";
				}
				if(statusIdOld != 15 && statusIdOld != 13){
					for(CapstoneProjectDetails item : detail) {
						Status status = capstoneProjectDetailService.getStatusById(item.getId()).get(0);
						if (status.getId() == statusIdOld)
						{
							item.setStatus(statusService.getStatusById(statusId));
							item.setDesAction(des);
							capstoneProjectDetailService.save(item);
						}
					}
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
				}else {
					success = success +1;
				}
			}

		}

		if(subjectCodeUnsucess != null ){
			if (success > 0){
				return String.valueOf(success) + " project has been update successfully . Capstone Projects: " + subjectCodeUnsucess + " has 2 supervisors, please reject a supervisors";
			}else {
				return "Capstone Projects: " + subjectCodeUnsucess + " has 2 supervisors, please reject a supervisors";
			}
		}


		return "The Project has been update successfully";
	}

	@ResponseBody
	@GetMapping("/rejectList")
	public String rejectList( @RequestParam("id") String id, @RequestParam("des") String des,Principal principal) {
		if(principal == null) {
			return "redirect:/login";
		}
		Users users = userService.findByEmail(principal.getName());
		String userid = "-1";
		int roleid = -1;
		if (users !=  null) {
			userid = users.getId();;
			roleid = users.getRoleUser().get(0).getUserRoleKey().getRole().getId();
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
			int statusId = -1;
			Status status = statusService.findStatusByCapstoneProject(currentProduct.getId());
			int statusIdOld = status.getId();
			if(roleid == 3){
				statusId = 16;
			}
			if(roleid == 4){
				if(statusIdOld == 13){
					statusId = 9;
				}else if(statusIdOld == 5){
					statusId = 14;
				}else {
					statusId = 10;
				}
			}
			if(roleid == 5){
				switch(statusIdOld) {
					case 15:
						statusId = 9;
						break;
					default:
						statusId = 12;
				}
			}
			boolean data = false;
			if(statusIdOld == 13 || statusIdOld == 15){
				currentProduct.setName(null);
				currentProduct.setNameVi(null);
				Status statusNew = statusService.getStatusById(statusId);
				currentProduct.setStatus(statusNew);
				currentProduct.setDesAction(des);
				data = capstoneProjectService.saveRegisterProject(currentProduct);
			} else {
				Status statusNew = statusService.getStatusById(statusId);
				currentProduct.setStatus(statusNew);
				currentProduct.setDesAction(des);
				capstoneProjectService.saveRegisterProject(currentProduct);
				List<CapstoneProjectDetails> detail = currentProduct.getCapstoneProjectDetails();
				for(CapstoneProjectDetails item : detail) {
					item.setStatus(statusService.getStatusById(statusId));
					item.setDesAction(des);
					capstoneProjectDetailService.save(item);
				}
			}
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
	@GetMapping("/editSupervisors")
	public String editSupervisors( @RequestParam("id") String id, @RequestParam("capstoneProject") String capstoneProject,Principal principal) {
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
		List<CapstoneProjectDetails> capstoneProjectDetails = capstoneProjectDetailService.getDetailByCapstoneProjectId(Integer.parseInt(capstoneProject));

		for (CapstoneProjectDetails item :capstoneProjectDetails){
			Users userdetail = capstoneProjectDetailService.getUserById(item.getId()).get(0);
			if (userdetail.getRoleUser().get(0).getUserRoleKey().getRole().getId().equals(4))
			{
				capstoneProjectDetailService.deleteRejectCapstoneProjectDetailsByUserId(userdetail.getId(),Integer.parseInt(capstoneProject));
			}
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


	@ResponseBody
	@RequestMapping(value = "/getSupervisorsProject")
	public String getSupervisorsProject(@RequestParam("username") String username, @RequestParam("capstoneProject") String capstoneProject,Principal principal) {
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
		if (users.isEmpty()) {
			success = false;
			message = "Username could not be found";
		} else {
			dto = new MemberDTO(users.get(0));
			boolean check = false;
			for (UserRoles userRoles : users.get(0).getRoleUser()) {
				if(userRoles.getUserRoleKey().getRole().getName().equals(Constant.ROLE_LECTURERS_DB)) {
					check = true;
					break;
				}
			}
			if(!check) {
				success = false;
				message = "User is not a Supervisor";
			}
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
		Date dateCurrent = new Date();
		Date startRegister = user.getSemester().getStartRegisterCapstone();
		Date endRegister = user.getSemester().getEndRegisterCapstone();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			startRegister = formatter.parse(startRegister.toString());
			endRegister = formatter.parse(endRegister.toString());
		} catch (Exception e) {
			System.out.println(e);
		}
		if(!startRegister.before(dateCurrent) || !endRegister.after(dateCurrent)) {
			SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
			model.addAttribute("messageTime", "The start time is " + fmt.format(startRegister) + ", the end time is " + fmt.format(endRegister));
			model.addAttribute("checkTime", false);
		} else {
			model.addAttribute("checkTime", true);
		}
		System.out.println(user.getSemester().getEndRegisterCapstone());
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
				if(userRoles.getUserRoleKey().getRole().getName().equals(Constant.ROLE_STUDENT_MEMBER_DB) || userRoles.getUserRoleKey().getRole().getName().equals(Constant.ROLE_STUDENT_LEADER_DB)) {
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
