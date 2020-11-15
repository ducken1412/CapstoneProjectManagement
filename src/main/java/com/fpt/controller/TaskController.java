package com.fpt.controller;

import com.fpt.dto.ContactDTO;
import com.fpt.entity.CapstoneProjects;
import com.fpt.entity.TaskDetails;
import com.fpt.entity.Users;
import com.fpt.service.CapstoneProjectDetailService;
import com.fpt.service.CapstoneProjectService;
import com.fpt.service.TaskDetailsService;
import com.fpt.service.UserRoleService;
import com.fpt.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class TaskController {

    @Autowired
    private TaskDetailsService taskDetailsService;

    @Autowired
    private CapstoneProjectService capstoneProjectService;

    @Autowired
    private CapstoneProjectDetailService capstoneProjectDetailService;

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("/db/task-detail/{id}")
    public String getDetail(@PathVariable Integer id,Integer week, Model model, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        List<TaskDetails> taskDetails;
        List<Integer> weeks;
        String nameProject = "";
        if(week == null) {
            week = taskDetailsService.findMaxWeek();
        }
        try{
            taskDetails = taskDetailsService.findTaskDetailsByCapstoneProjectId(id, week);
            weeks = taskDetailsService.findDistinctByWeek();
            CapstoneProjects capstoneProject = capstoneProjectService.findById(id);
            if(capstoneProject != null) {
                nameProject = capstoneProject.getName();
            }
            List<Users> userproject = capstoneProjectDetailService.getUserByCapstoneProjectDetailId(id);
            List<ContactDTO> contacts = new ArrayList<>();
            List<String> roles;
            ContactDTO contactDTO;
            for (Users user : userproject) {
                contactDTO = new ContactDTO();
                contactDTO.setUsername(user.getUsername());
                contactDTO.setEmail(user.getEmail());
                contactDTO.setPhone(user.getPhone());
                roles = userRoleService.getRoleNamesByUserId(user.getId());
                for (String role : roles) {
                    if(role.equals(Constant.ROLE_LECTURERS_DB)) {
                        contactDTO.setRole(Constant.ROLE_LECTURERS);
                        break;
                    }
                    if(role.equals(Constant.ROLE_STUDENT_LEADER_DB)) {
                        contactDTO.setRole(Constant.ROLE_STUDENT_LEADER);
                        break;
                    }
                    if(role.equals(Constant.ROLE_STUDENT_MEMBER_DB)) {
                        contactDTO.setRole(Constant.ROLE_STUDENT_MEMBER);
                        break;
                    }
                }
                contacts.add(contactDTO);

            }
            Collections.sort(contacts, (a, b) -> b.getRole().compareToIgnoreCase(a.getRole()));
            model.addAttribute("weeks", weeks);
            model.addAttribute("weekSelect", week);
            model.addAttribute("tasks", taskDetails);
            model.addAttribute("projectId", id);
            model.addAttribute("nameProject", nameProject);
            model.addAttribute("contacts", contacts);
        }catch (Exception ex) {

        }
        return "admin/task-detail";
    }

    @PostMapping("/db/task-detail/{id}")
    public String getTaskDetail(@PathVariable Integer id,Integer week, Model model, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        List<TaskDetails> taskDetails;
        List<Integer> weeks;
        if(week == null) {
            week = taskDetailsService.findMaxWeek();
        }
        try{
            taskDetails = taskDetailsService.findTaskDetailsByCapstoneProjectId(id, week);
            weeks = taskDetailsService.findDistinctByWeek();
            model.addAttribute("weeks", weeks);
            model.addAttribute("weekSelect", week);
            model.addAttribute("tasks", taskDetails);
            model.addAttribute("projectId", id);
        }catch (Exception ex) {

        }
        return "admin/task-component";
    }
}
