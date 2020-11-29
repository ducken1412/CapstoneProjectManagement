package com.fpt.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fpt.common.SendingMail;
import com.fpt.entity.*;
import com.fpt.service.*;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpt.controller.LecturersController;
import com.fpt.dto.CapstoneProjectDetailDTO;
import com.fpt.dto.ListLecturersDTO;
import com.fpt.dto.UserDTO;
import com.fpt.dto.ListLecturersDTO;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LecturersController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LecturersController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationsService notificationService;

    @Autowired
    private CapstoneProjectService capstoneProjectService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private CapstoneProjectDetailService capstoneProjectDetailService;

    @Autowired
    private HistoryRecordService historyRecordService;

    @RequestMapping(value = "/lecturers", method = RequestMethod.GET)
    public String getListLecturers(Model model, @RequestParam("page") Optional<Integer> page,
                                   @RequestParam("size") Optional<Integer> size, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        LOGGER.info("Running on getListLecturers method of UserController");
        Users user = userService.findByEmail(principal.getName());
        String userId = user.getId();
        HistoryRecords historyRecords = historyRecordService.findHistoryByUserId(userId);
        if (historyRecords != null) {
            try {
                //check booking
                boolean check_user_register = true;
                model.addAttribute("check_user_register", check_user_register);
                int projectId = historyRecords.getCapstoneProject().getId();
                boolean check_lecture_op1 = true;
                boolean check_lecture_op2 = true;
                Users lecture1 = capstoneProjectDetailService.userLecturersIdAndCapstoneProjectIdOP1(projectId);
                Users lecture2 = capstoneProjectDetailService.userLecturersIdAndCapstoneProjectIdOP2(projectId);
                int count_lecture_op1 = capstoneProjectDetailService.countLecturersIdAndCapstoneProjectIdOP1(projectId);
                int count_lecture_op2 = capstoneProjectDetailService.countLecturersIdAndCapstoneProjectIdOP2(projectId);
                if (count_lecture_op1 != 0) {
                    check_lecture_op1 = false;
                }
                if (count_lecture_op2 != 0) {
                    check_lecture_op2 = false;
                }
                model.addAttribute("check_lecture_op1", check_lecture_op1);
                model.addAttribute("check_lecture_op2", check_lecture_op2);
                CapstoneProjects capstoneProject = capstoneProjectDetailService.findCapstoneProjectByUserId(userId);
                String statusProject = capstoneProject.getStatus().getName();
                //check total lecture bookded
                int count = capstoneProjectDetailService.countLecturersByProjectId(projectId);
                if (count >= 2 && statusProject.equals("registering_capstone")) {
                    model.addAttribute("notification", "Your request has been submitted, please wait for the response from the Training Department.");
                }
                if (lecture1 != null && lecture2 != null) {
//                    String lecture1Id = lecture1.getId();
//                    String lecture2Id = lecture2.getId();
//               //     phan trang
//                    int currentPage = page.orElse(1);
//                    int pageSize = size.orElse(6);
//                    Page<Users> lecturersPage = userService.findPaginatedNotLecture2Booked(PageRequest.of(currentPage - 1, pageSize), lecture1Id, lecture2Id);
//                    model.addAttribute("lecturersPage", lecturersPage);
//                    int totalPages = lecturersPage.getTotalPages();
//                    if (totalPages > 0) {
//                        List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
//                        model.addAttribute("pageNumbers", pageNumbers);
//                    }
                    int currentPage = page.orElse(1);
                    int pageSize = size.orElse(6);
                    Page<Users> lecturersPage = userService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
                    model.addAttribute("lecturersPage", lecturersPage);
                    int totalPages = lecturersPage.getTotalPages();
                    if (totalPages > 0) {
                        List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
                        model.addAttribute("pageNumbers", pageNumbers);
                    }
                } else {
                    if (lecture1 == null && lecture2 == null) {
                        //phan trang
                        int currentPage = page.orElse(1);
                        int pageSize = size.orElse(6);
                        Page<Users> lecturersPage = userService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
                        model.addAttribute("lecturersPage", lecturersPage);
                        int totalPages = lecturersPage.getTotalPages();
                        if (totalPages > 0) {
                            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
                            model.addAttribute("pageNumbers", pageNumbers);
                        }
                    } else if (lecture1 != null) {
                        String lecture1Id = lecture1.getId();
                        //phan trang
                        int currentPage = page.orElse(1);
                        int pageSize = size.orElse(6);
                        Page<Users> lecturersPage = userService.findPaginatedNotLectureBooked(PageRequest.of(currentPage - 1, pageSize), lecture1Id);
                        model.addAttribute("lecturersPage", lecturersPage);
                        int totalPages = lecturersPage.getTotalPages();
                        if (totalPages > 0) {
                            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
                            model.addAttribute("pageNumbers", pageNumbers);
                        }
                    } else if (lecture2 != null) {
                        String lecture2Id = lecture2.getId();
                        //phan trang
                        int currentPage = page.orElse(1);
                        int pageSize = size.orElse(6);
                        Page<Users> lecturersPage = userService.findPaginatedNotLectureBooked(PageRequest.of(currentPage - 1, pageSize), lecture2Id);
                        model.addAttribute("lecturersPage", lecturersPage);
                        int totalPages = lecturersPage.getTotalPages();
                        if (totalPages > 0) {
                            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
                            model.addAttribute("pageNumbers", pageNumbers);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            boolean check_user_register = false;
            model.addAttribute("check_user_register", check_user_register);
            //phan trang
            int currentPage = page.orElse(1);
            int pageSize = size.orElse(6);
            Page<Users> lecturersPage = userService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
            model.addAttribute("lecturersPage", lecturersPage);
            int totalPages = lecturersPage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
            }
        }

        return "home/listlecturers";
    }

    @RequestMapping(value = "/listlecturersprojectop1/{id}", method = RequestMethod.GET)
    public String bookLecturersOp1(@PathVariable("id") String id, UserDTO dto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal, HttpServletRequest request) {
        if (principal == null) {
            return "redirect:/login";
        }

        Users user = userService.findByEmail(principal.getName());
        String userId = user.getId();
        HistoryRecords historyRecords = historyRecordService.findHistoryByUserId(userId);
        int projectId;
        Users userLecture = userService.findById(id);
        String baseUrl = String.format("%s://%s:%d/", request.getScheme(), request.getServerName(), request.getServerPort());
        if (historyRecords != null) {
            projectId = historyRecords.getCapstoneProject().getId();
            int count = capstoneProjectDetailService.countLecturersByProjectId(projectId);
            List<CapstoneProjectDetails> listUser = capstoneProjectDetailService.getUserByCapstioneID(projectId);
            for (int i = 0; i < listUser.size(); i++) {
                if (listUser.get(i).getUser().getId().equals(id)) {
                    redirectAttributes.addFlashAttribute("notification", "lecture already booked. Please choose another lecture");
                    return "redirect:/lecturers";
                }
            }
            if (count >= 2) {
                redirectAttributes.addFlashAttribute("notification", "Your request has been submitted, please wait for the response from the Training Department.");
                return "redirect:/lecturers";
            } else {
                //booking lecture
                CapstoneProjectDetails cpd = new CapstoneProjectDetails();
                cpd.setCapstoneProject(capstoneProjectService.getCapstonProjectById(projectId));
                cpd.setUser(userLecture);
                cpd.setSupType("Main Lecture");
                cpd.setDesAction("booking lecturers");
                cpd.setStatus(statusService.getStatusById(4));
                capstoneProjectDetailService.addCapstonprojectDetail(cpd);
                //save history records
                HistoryRecords records = new HistoryRecords();
                Date date = new Date();
                records.setContent("Booking Lecture");
                records.setCreatedDate(date);
                records.setUser(userService.findById(userId));
                records.setCapstoneProject(capstoneProjectService.getCapstonProjectById(projectId));
                historyRecordService.save(records);
                try {
                    String title = user.getUsername() + " wants to select you as the project group guide";
                    String content = "Dear Supervisors, \n" +
                            "I want to choose the teacher as a guide for our project group in this term. \n"
                            + "Click " + "<a href=\"" + baseUrl + "project-detail/" + projectId + "\">view project description</a>";
                    SendingMail.sendEmail(userLecture.getEmail(), "[FPTU Capstone Project] " + title, content);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        return "redirect:/lecturers";
    }

    @RequestMapping(value = "/listlecturersprojectop2/{id}", method = RequestMethod.GET)
    public String bookLecturersOp2(@PathVariable("id") String id, UserDTO dto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal, HttpServletRequest request) {
        if (principal == null) {
            return "redirect:/login";
        }

        Users user = userService.findByEmail(principal.getName());
        String userId = user.getId();
        HistoryRecords historyRecords = historyRecordService.findHistoryByUserId(userId);
        int projectId;
        Users userLecture = userService.findById(id);
        String baseUrl = String.format("%s://%s:%d/", request.getScheme(), request.getServerName(), request.getServerPort());
        if (historyRecords != null) {
            projectId = historyRecords.getCapstoneProject().getId();
            int count = capstoneProjectDetailService.countLecturersByProjectId(projectId);
            List<CapstoneProjectDetails> listUser = capstoneProjectDetailService.getUserByCapstioneID(projectId);
            for (int i = 0; i < listUser.size(); i++) {
                if (listUser.get(i).getUser().getId().equals(id)) {
                    redirectAttributes.addFlashAttribute("notification", "lecture already booked. Please choose another lecture");
                    return "redirect:/lecturers";
                }
            }
            if (count >= 2) {
                redirectAttributes.addFlashAttribute("notification", "Can't choose over 2 lecturers.");
                return "redirect:/lecturers";
            } else {
                //booking lecture
                CapstoneProjectDetails cpd = new CapstoneProjectDetails();
                Users user_id = userService.findById(id);
                cpd.setCapstoneProject(capstoneProjectService.getCapstonProjectById(projectId));
                cpd.setUser(user_id);
                cpd.setSupType("Assistant Lecture");
                cpd.setDesAction("booking lecturers");
                cpd.setStatus(statusService.getStatusById(4));
                capstoneProjectDetailService.addCapstonprojectDetail(cpd);
                //save history records
                HistoryRecords records = new HistoryRecords();
                Date date = new Date();
                records.setContent("Booking Lecture");
                records.setCreatedDate(date);
                records.setUser(userService.findById(userId));
                records.setCapstoneProject(capstoneProjectService.getCapstonProjectById(projectId));
                historyRecordService.save(records);
                try {
                    String title = user.getUsername() + " wants to select you as the project group guide";
                    String content = "Dear Supervisors, \n" +
                            "I want to choose the teacher as a guide for our project group in this term. \n"
                            + "Click " + "<a href=\"" + baseUrl + "project-detail/" + projectId + "\">view project description</a>";
                    SendingMail.sendEmail(userLecture.getEmail(), "[FPTU Capstone Project] " + title, content);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        return "redirect:/lecturers";
    }
}
