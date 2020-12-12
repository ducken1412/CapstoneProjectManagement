package com.fpt.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fpt.common.NotificationCommon;
import com.fpt.common.SendingMail;
import com.fpt.dto.CapstoneProjectDTO;
import com.fpt.entity.*;
import com.fpt.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fpt.dto.UserRoleDTO;
import com.fpt.utils.Constant;

@Controller
public class DetailProjectController {

    @Autowired
    private CapstoneProjectDetailService capstoneProjectDetailService;

    @Autowired
    private CapstoneProjectService capstoneProjectService;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private StatusService statusService;

    @Autowired
    private HistoryRecordService historyRecordService;

    @Autowired
    private ProfessionService professionService;

    @RequestMapping(value = "/project-detail/{id}", method = RequestMethod.GET)
    public String detailProject(@PathVariable("id") Integer id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        CapstoneProjects cp = capstoneProjectService.getCapstonProjectById(id);
        model.addAttribute("detail", cp);
        List<Users> userproject = capstoneProjectDetailService.getUserByCapstoneProjectDetailId(id);
        model.addAttribute("userproject", userproject);
        List<Users> userprojectWaitingApprove = capstoneProjectDetailService.getUserWaitingApproveByCapstoneProjectDetailId(id);

        String nameStatus = cp.getStatus().getName();
        if (nameStatus == null) {
            model.addAttribute("status", "");
        } else {
            switch (nameStatus) {
                case Constant.STATUS_NOT_ELIGIBLE_CAPSTONE_DB:
                    model.addAttribute("status", Constant.STATUS_NOT_ELIGIBLE_CAPSTONE);
                case Constant.STATUS_ELIGIBLE_CAPSTONE_DB:
                    model.addAttribute("status", Constant.STATUS_ELIGIBLE_CAPSTONE);
                case Constant.STATUS_INACTIVE_USER_DB:
                    model.addAttribute("status", Constant.STATUS_INACTIVE_USER);
                case Constant.STATUS_REGISTERING_CAPSTONE_DB:
                    model.addAttribute("status", Constant.STATUS_REGISTERING_CAPSTONE);
                case Constant.STATUS_REGISTED_CAPSTONE_DB:
                    model.addAttribute("status", Constant.STATUS_REGISTED_CAPSTONE);
                case Constant.STATUS_APPROVE_CAPSTONE_LUCTURER_DB:
                    model.addAttribute("status", Constant.STATUS_APPROVE_CAPSTONE_LUCTURER);
                case Constant.STATUS_APPROVE_CAPSTONE_TRAINING_DB:
                    model.addAttribute("status", Constant.STATUS_APPROVE_CAPSTONE_TRAINING);
                case Constant.STATUS_APPROVE_CAPSTONE_HEAD_DB:
                    model.addAttribute("status", Constant.STATUS_APPROVE_CAPSTONE_HEAD);
                case Constant.STATUS_DOING_CAPSTONE_DB:
                    model.addAttribute("status", Constant.STATUS_DOING_CAPSTONE);
                case Constant.STATUS_NOT_ELIGIBLE_DEFENCE_CAPSTONE_DB:
                    model.addAttribute("status", Constant.STATUS_NOT_ELIGIBLE_DEFENCE_CAPSTONE);
                case Constant.STATUS_ELIGIBLE_DEFENCE_CAPSTONE_DB:
                    model.addAttribute("status", Constant.STATUS_ELIGIBLE_DEFENCE_CAPSTONE);
                case Constant.STATUS_REJECT_CAPSTONE_DB:
                    model.addAttribute("status", Constant.STATUS_REJECT_CAPSTONE);
                case Constant.STATUS_CHANGING_NAME_CAPSTONE_DB:
                    model.addAttribute("status", Constant.STATUS_CHANGING_NAME_CAPSTONE);
                case Constant.STATUS_PENDING_CAPSTONE_DB:
                    model.addAttribute("status", Constant.STATUS_PENDING_CAPSTONE);

            }
        }
        List<UserRoleDTO> userRolesDTOs = new ArrayList<>();
        for (Users users : userproject) {
            String user_id = users.getId();
            UserRoleDTO userRoleDTO = new UserRoleDTO();
            userRoleDTO.setId(user_id);
            userRoleDTO.setUsername(users.getUsername());
            List<String> role = userRoleService.getRoleNamesByUserId(user_id);
            for (String string : role) {
                List<String> roleView = new ArrayList<>();
                if (string.equals(Constant.ROLE_HEAD_DB)) {
                    roleView.add(Constant.ROLE_HEAD);
                }
                if (string.equals(Constant.ROLE_LECTURERS_DB)) {
                    roleView.add(Constant.ROLE_LECTURERS);
                }
                if (string.equals(Constant.ROLE_STUDENT_LEADER_DB)) {
                    roleView.add(Constant.ROLE_STUDENT_LEADER);
                }
                if (string.equals(Constant.ROLE_STUDENT_MEMBER_DB)) {
                    roleView.add(Constant.ROLE_STUDENT_MEMBER);
                }
                if (string.equals(Constant.ROLE_TRAINING_DEP_DB)) {
                    roleView.add(Constant.ROLE_TRAINING_DEP);
                }
                userRoleDTO.setRole(roleView);
            }
            userRolesDTOs.add(userRoleDTO);
        }

        //
        List<UserRoleDTO> userRolesDTOsWaitingApprove = new ArrayList<>();
        for (Users users : userprojectWaitingApprove) {
            String user_id = users.getId();
            UserRoleDTO userRoleDTO = new UserRoleDTO();
            userRoleDTO.setId(user_id);
            userRoleDTO.setUsername(users.getUsername());
            List<String> role = userRoleService.getRoleNamesByUserId(user_id);
            for (String string : role) {
                List<String> roleView = new ArrayList<>();
                if (string.equals(Constant.ROLE_HEAD_DB)) {
                    roleView.add(Constant.ROLE_HEAD);
                }
                if (string.equals(Constant.ROLE_LECTURERS_DB)) {
                    roleView.add(Constant.ROLE_LECTURERS);
                }
                if (string.equals(Constant.ROLE_STUDENT_LEADER_DB)) {
                    roleView.add(Constant.ROLE_STUDENT_LEADER);
                }
                if (string.equals(Constant.ROLE_STUDENT_MEMBER_DB)) {
                    roleView.add(Constant.ROLE_STUDENT_MEMBER);
                }
                if (string.equals(Constant.ROLE_TRAINING_DEP_DB)) {
                    roleView.add(Constant.ROLE_TRAINING_DEP);
                }
                userRoleDTO.setRole(roleView);
            }
            userRolesDTOsWaitingApprove.add(userRoleDTO);
        }

        Users user = userService.findByEmail(principal.getName());
        String userId = user.getId();
        boolean check_capstone = false;
        //check user login = user project
        List<Integer> capstone_id = capstoneProjectDetailService.getIdProjectByUserIDCheckApprove(userId);
        for (int i = 0; i < capstone_id.size(); i++) {
            if (capstone_id.get(i) == id) {
                check_capstone = true;
            }
        }
        model.addAttribute("check_capstone", check_capstone);
        model.addAttribute("userRolesDTOs", userRolesDTOs);
        model.addAttribute("userprojectWaitingApprove", userRolesDTOsWaitingApprove);

        //check history show submit send training department
        HistoryRecords historyRecords = historyRecordService.findHistoryByUserIdCapstoneId(userId, id);
        if (historyRecords != null) {
            boolean checkUserRegister = true;
            boolean checkStatusProject = false;
            if(cp.getStatus().getName().equals("registering_capstone")){
                checkStatusProject = true;
                model.addAttribute("checkStatusProject", checkStatusProject);
            }
            model.addAttribute("checkUserRegister", checkUserRegister);
        }


        if (cp.getStatus().getName().equals(Constant.STATUS_REGISTED_CAPSTONE_DB)) {
            model.addAttribute("notification", "Your request has been submitted, please wait for the response from the Training Department.");
        }
        return "home/detail_project";
    }

    @RequestMapping(value = "/approve", method = RequestMethod.POST, params = "approve")
    public String approve(@RequestParam Integer id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        //int id_project = Integer.parseInt(id);
        boolean check = false;
        Users user = userService.findByEmail(principal.getName());
        String user_login = user.getId();
        CapstoneProjects capstoneProjects = capstoneProjectService.findById(id);
        try {

            Date date = new Date();
            HistoryRecords historyRecords = historyRecordService.findHistoryByProjectId(id);
            if (historyRecords != null) {
                HistoryRecords records = new HistoryRecords();
                String user_booking_id = historyRecords.getUser().getId();
                Users u = userService.findById(user_booking_id);
                capstoneProjectDetailService.updateStatusUserProject(user_login, id);
                String title = user_login + " has joined your team";
                String content = user_login + " has joined your team " + date;
                NotificationCommon.sendNotification(user, title, content, user_booking_id);
                capstoneProjectDetailService.deleteCapstoneProjectDetailsByUserId(user_login, id);
                records.setContent("Approve Project");
                records.setCreatedDate(date);
                records.setCapstoneProject(capstoneProjects);
                records.setUser(user);
                historyRecordService.save(records);
                SendingMail.sendEmail(u.getEmail(), "[FPTU Capstone Project] " + title, content);
                check = true;
            }
        } catch (Exception e) {

        }
        if (check) {
            //capstoneProjectDetailService.deleteCapstoneProjectDetailsByUserId(user_login);
        }
        return "redirect:/project-detail/" +id;
    }

    @RequestMapping(value = "/approve", method = RequestMethod.POST, params = "reject")
    public String reject(@RequestParam("id") Integer id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        CapstoneProjects capstoneProjects = capstoneProjectService.findById(id);
        try {
            Date date = new Date();
            HistoryRecords historyRecords = historyRecordService.findHistoryByProjectId(id);
            if (historyRecords != null) {
                HistoryRecords records = new HistoryRecords();
                Users user = userService.findByEmail(principal.getName());
                String user_booking_id = historyRecords.getUser().getId();
                Users u = userService.findById(user_booking_id);
                String userLogin = user.getId();
                String title = userLogin + " has rejected your team";
                String content = userLogin + " has rejected your team " + date;
                NotificationCommon.sendNotification(user, title, content, user_booking_id);
                capstoneProjectDetailService.deleteRejectCapstoneProjectDetailsByUserId(userLogin, id);
                records.setContent("Reject Project");
                records.setCreatedDate(date);
                records.setCapstoneProject(capstoneProjects);
                records.setUser(user);
                historyRecordService.save(records);
                SendingMail.sendEmail(u.getEmail(), "[FPTU Capstone Project] " + title, content);
                userRoleService.updateRoleStudentReject(userLogin);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:/forum";
    }

    @RequestMapping(value = "/sendtd", method = RequestMethod.POST)
    public String editReport(@RequestParam("projectId") Integer id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        try {
            capstoneProjectService.updateStatusCapstoneProjectSendTD(id);
            capstoneProjectService.deleteUserNotSubmitCapstone(id);
        }catch (Exception e){

        }

        return "redirect:/project-detail/" + id;
    }

    @RequestMapping(value = "/edit-project/{id}", method = RequestMethod.GET)
    public String editProject(@PathVariable("id") Integer id, Model model, Principal principal){
        if (principal == null) {
            return "redirect:/login";
        }
        System.out.println(id);
        Users user = userService.findByEmail(principal.getName());
        CapstoneProjects capstoneProject = capstoneProjectService.getCapstonProjectById(id);
        //List<UserRoles> userRoles = capstoneProjectDetailService.listUserRoleByProjectId(id);
//        model.addAttribute("userRoles", userRoles);

        model.addAttribute("capstoneProject",capstoneProject);
        model.addAttribute("loggedUser", user);
        List<Profession> professions = professionService.findAll();
        model.addAttribute("professions", professions);
        model.addAttribute("capstoneProjectDTO", new CapstoneProjectDTO());
        return "home/edit-project-detail";
    }

    @RequestMapping(value = "/update-project", method = RequestMethod.POST)
    public String updateProject(Model model, Principal principal){
        if (principal == null) {
            return "redirect:/login";
        }

        return "home/edit-project-detail";
    }
}
