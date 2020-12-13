package com.fpt.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fpt.common.NotificationCommon;
import com.fpt.common.SendingMail;
import com.fpt.dto.CapstoneProjectDTO;
import com.fpt.dto.MemberDTO;
import com.fpt.dto.MemberEditDTO;
import com.fpt.entity.*;
import com.fpt.service.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.fpt.dto.UserRoleDTO;
import com.fpt.utils.Constant;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

    @Autowired
    private SemestersService semestersService;

    @RequestMapping(value = "/project-detail/{id}", method = RequestMethod.GET)
    public String detailProject(@PathVariable("id") Integer id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Semesters semesters;
        Users appUser = userService.findByEmail(principal.getName());
        semesters = semestersService.getSemesterByUserId(appUser.getId());
        LocalDate d1;
        d1 = LocalDate.parse(LocalDate.now().toString(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate d2;
        d2 = LocalDate.parse(simpleDateFormat.format(semesters.startDate), DateTimeFormatter.ISO_LOCAL_DATE);
        Duration diff;
        diff = Duration.between(d2.atStartOfDay(), d1.atStartOfDay());
        long diffDays = diff.toDays();
        long currentWeek = diffDays / 7;
        if (currentWeek != 0) {
            model.addAttribute("checkWeek", true);
        }
        CapstoneProjects cp = capstoneProjectService.getCapstonProjectById(id);
        if (cp.getStatus().getName().equals(Constant.STATUS_DOING_CAPSTONE_DB)) {
            model.addAttribute("checkBTChangName", true);
        }
        model.addAttribute("detail", cp);
        List<Users> userproject = capstoneProjectDetailService.findUserByCapstoneProjectDetailId(id);
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
                    model.addAttribute("status", Constant.STATUS_APPROVE_CAPSTONE_LECTURER);
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
            if (cp.getStatus().getName().equals("registering_capstone")) {
                checkStatusProject = true;
                model.addAttribute("checkStatusProject", checkStatusProject);
            }
            model.addAttribute("checkUserRegister", checkUserRegister);
        }


        if (cp.getStatus().getName().equals(Constant.STATUS_REGISTED_CAPSTONE_DB)) {
            model.addAttribute("notification", "Your request has been submitted, please wait for the response from the Training Department.");
        }
        if (cp.getStatus().getName().equals(Constant.STATUS_CHANGING_NAME_CAPSTONE_DB)) {
            model.addAttribute("notification", "Changing successfully !!! Please wait  supervisor confirm your action.");
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
        return "redirect:/project-detail/" + id;
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
        } catch (Exception e) {

        }
        return "redirect:/project-detail/" + id;
    }

    @RequestMapping(value = "/edit-project/{id}", method = RequestMethod.GET)
    public String editProject(@PathVariable("id") Integer id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        try {
            Users user = userService.findByEmail(principal.getName());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Semesters semesters;
            semesters = semestersService.getSemesterByUserId(user.getId());
            LocalDate d1;
            d1 = LocalDate.parse(LocalDate.now().toString(), DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate d2;
            d2 = LocalDate.parse(simpleDateFormat.format(semesters.startDate), DateTimeFormatter.ISO_LOCAL_DATE);
            Duration diff;
            diff = Duration.between(d2.atStartOfDay(), d1.atStartOfDay());
            long diffDays = diff.toDays();
            long currentWeek = diffDays / 7;
            if (currentWeek > 8) {
                return "redirect:/403";
            }

            HistoryRecords historyRecords = historyRecordService.findHistoryByUserIdCapstoneId(user.getId(), id);
            if (historyRecords == null) {
                return "redirect:/403";
            }


            CapstoneProjects capstoneProject = capstoneProjectService.getCapstonProjectById(id);
            String nameStatus = capstoneProject.getStatus().getName();
            if (nameStatus.equals(Constant.STATUS_REGISTERING_CAPSTONE_DB)) {
                model.addAttribute("doingStatus", false);
            } else {
                model.addAttribute("doingStatus", true);
            }

            String changName = capstoneProject.getNameChanging();
            String statusCapstone = capstoneProject.getStatus().getName();
            if (statusCapstone.equals("registering_capstone")) {
                model.addAttribute("checkStatusProject", true);
            } else {
                model.addAttribute("checkStatusProject", false);
            }
            if (changName != null) {
                model.addAttribute("changName", true);
            } else {
                model.addAttribute("changName", false);
            }
            model.addAttribute("capstoneProject", capstoneProject);
            List<Profession> professions = professionService.findAll();
            model.addAttribute("professions", professions);
            model.addAttribute("capstoneProjectDTO", new CapstoneProjectDTO());
        } catch (Exception e) {

        }

        return "home/edit-project-detail";
    }

//    @RequestMapping(value = "/update-project", method = RequestMethod.POST)
//    public String updateProject(Model model, Principal principal){
//        if (principal == null) {
//            return "redirect:/login";
//        }
//
//        return "home/edit-project-detail";
//    }

    @ResponseBody
    @RequestMapping(value = "/getMemberEdit", method = RequestMethod.GET)
    public String getMember(Principal principal) {
        Map<String, Object> result = new HashMap<>();
//        List<Users> users = userService.findByUsername(username);
        List<MemberEditDTO> dto = new ArrayList<>();
        Users user = userService.findByEmail(principal.getName());
        CapstoneProjects capstoneProjects = capstoneProjectService.getCapstoneProjectByUserId(user.getId());
        List<Object[]> users = capstoneProjectDetailService.getUserEditByCapstoneProject(capstoneProjects.getId());
        for (Object[] o : users) {
            MemberEditDTO memberEditDTO = new MemberEditDTO();
            memberEditDTO.setUsername((String) o[0]);
            memberEditDTO.setRole((String) o[1]);
            memberEditDTO.setStatus((String) o[2]);
            dto.add(memberEditDTO);
        }
        result.put("user", dto);
        return new Gson().toJson(result);
    }

    @ResponseBody
    @RequestMapping(value = "/getMemberEdit/{username}")
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
            if (status.getName().equals(Constant.STATUS_INACTIVE_USER_DB) || status.getName().equals(Constant.STATUS_NOT_ELIGIBLE_CAPSTONE_DB)) {
                success = false;
                message = "User is inactive or not eligible";
                result.put("success", success);
                result.put("message", message);
                return new Gson().toJson(result);
            }
            dto = new MemberDTO(users.get(0));
            boolean check = false;
            for (UserRoles userRoles : users.get(0).getRoleUser()) {
                if (userRoles.getUserRoleKey().getRole().getName().equals(Constant.ROLE_STUDENT_MEMBER_DB)) {
                    check = true;
                    break;
                }
            }
            if (!check) {
                success = false;
                message = "User is not a student";
            } else {
                tmp = capstoneProjectDetailService.findUserByStatusRegisted(users.get(0).getId());
            }
        }
        if (tmp != null) {
            success = false;
            message = "The user has joined another group";
        }

        result.put("success", success);
        result.put("message", message);
        result.put("user", dto);
        return new Gson().toJson(result);
    }

    @ResponseBody
    @RequestMapping(value = "/updateProject", method = RequestMethod.POST)
    public String registerProject(@Valid @RequestBody CapstoneProjectDTO dataForm, BindingResult result,
                                  Model model, Principal principal, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/login";
        }

        Users user = userService.findByEmail(principal.getName());
        CapstoneProjects capstoneProjects = capstoneProjectService.getCapstoneProjectByUserId(user.getId());

        if (!capstoneProjects.getStatus().getName().equals(Constant.STATUS_REGISTERING_CAPSTONE)) {
            dataForm.setName(capstoneProjects.getName());
            dataForm.setNameVi(capstoneProjects.getNameVi());
        }
        String baseUrl = String.format("%s://%s:%d/", request.getScheme(), request.getServerName(), request.getServerPort());
        Map<String, Object> output = new HashMap<>();
        List<String> errors = new ArrayList<>();
        if (result.hasErrors()) {
            result.getFieldErrors().stream().forEach(f -> errors.add(f.getDefaultMessage()));
            output.put("hasError", true);
            output.put("errors", errors);
            return new Gson().toJson(output);
        }
        return capstoneProjectService.updateProject(dataForm, principal, baseUrl, capstoneProjects.getId());
    }

    @RequestMapping(value = "/changing-name-project/{id}", method = RequestMethod.GET)
    public String chagingNameProject(@PathVariable("id") Integer id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Users user = userService.findByEmail(principal.getName());
        try {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Semesters semesters;
            semesters = semestersService.getSemesterByUserId(user.getId());
            LocalDate d1;
            d1 = LocalDate.parse(LocalDate.now().toString(), DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate d2;
            d2 = LocalDate.parse(simpleDateFormat.format(semesters.startDate), DateTimeFormatter.ISO_LOCAL_DATE);
            Duration diff;
            diff = Duration.between(d2.atStartOfDay(), d1.atStartOfDay());
            long diffDays = diff.toDays();
            long currentWeek = diffDays / 7;
            if (currentWeek > 8) {
                return "redirect:/403";
            }
            HistoryRecords historyRecords = historyRecordService.findHistoryByUserIdCapstoneId(user.getId(), id);
            if (historyRecords == null) {
                return "redirect:/403";
            }

            CapstoneProjects capstoneProject = capstoneProjectService.getCapstonProjectById(id);
            if (capstoneProject.getStatus().getName().equals("changing_name_capstone")) {
                return "redirect:/403";
            }
            model.addAttribute("capstone", capstoneProject);
            model.addAttribute("capstoneProject", new CapstoneProjects());
            //model.addAttribute("capstoneProjectDTO", new CapstoneProjectDTO());
        } catch (Exception e) {

        }
        return "home/changing-name-project";
    }

    @RequestMapping(value = "/save-change-name", method = RequestMethod.POST)
    public String saveChangingNameProject(CapstoneProjects capstoneProjects, RedirectAttributes redirectAttributes, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Users user = userService.findByEmail(principal.getName());
        boolean check = false;
        CapstoneProjects projects = capstoneProjectService.getCapstonProjectById(capstoneProjects.getId());
        String nameChanging = capstoneProjects.getNameChanging();
        String nameViChange = capstoneProjects.getNameViChanging();
        if (nameChanging == null || nameViChange == null || nameChanging.isEmpty() || nameViChange.isEmpty()) {
            redirectAttributes.addFlashAttribute("notification", "Name Changing and name changingVi not blank!");
            return "redirect:/changing-name-project/" + capstoneProjects.getId();
        }
        if (nameChanging.equalsIgnoreCase(projects.getName()) || nameViChange.equalsIgnoreCase(projects.getName())) {
            redirectAttributes.addFlashAttribute("notification", "Name Changing and name changingVi must not equal the old name!");
            return "redirect:/changing-name-project/" + capstoneProjects.getId();
        }
        try {
            capstoneProjectService.capstoneProjectChangingName(capstoneProjects.getNameChanging(), capstoneProjects.getNameViChanging(), capstoneProjects.getId());
            check = true;
        } catch (Exception e) {

        }
        if (check == true) {
            HistoryRecords historyRecords = new HistoryRecords();
            Date date = new Date();
            historyRecords.setContent("Project Name Change");
            historyRecords.setUser(user);
            historyRecords.setCreatedDate(date);
            historyRecords.setCapstoneProject(projects);
            historyRecordService.save(historyRecords);
        }
        return "redirect:/project-detail/" + capstoneProjects.getId();
    }
}
