package com.fpt.controller;


import com.fpt.common.NotificationCommon;
import com.fpt.common.SendingMail;
import com.fpt.dto.NotificationDTO;
import com.fpt.dto.ReportDTO;
import com.fpt.entity.*;
import com.fpt.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import com.fpt.entity.Reports;
import com.fpt.entity.Users;
import com.fpt.service.UserService;

import javax.servlet.http.HttpServletRequest;


@Controller
public class ReportController {
    @Autowired
    private UserService userService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportDetailService reportDetailService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private CapstoneProjectDetailService capstoneProjectDetailService;

    @Autowired
    private HistoryRecordService recordService;

    @GetMapping("/report")
    public String report(Model model, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }

        model.addAttribute("reportDetail", new ReportDTO());
        Users user = userService.findByEmail(principal.getName());
        return "home/add-report";
    }

    @RequestMapping(value = "/report-detail/{id}", method = RequestMethod.GET)
    public String viewRoport(@PathVariable("id") int id, Model model, Principal principal){
        if(principal == null) {
            return "redirect:/login";
        }
        ReportDetails details = reportDetailService.getReportDetailsById(id);
        String title =  details.getReport().getName();
        String content = details.getContent();
        model.addAttribute("title", title);
        model.addAttribute("content", content);
        return "home/report-detail";
    }

    @RequestMapping(value = "/add-report", method = RequestMethod.POST)
    public String addReport(ReportDTO dto, Model model, BindingResult result, Principal principal, HttpServletRequest request){
        if(principal == null) {
            return "redirect:/login";
        }
        if(result.hasErrors()) {
            model.addAttribute("reportDetail", new ReportDetails());
            return "home/add-report";
        }
        Users user = userService.findByEmail(principal.getName());
        String user_id_login = user.getId();
        String type = "daily report";
        List<String> role = userRoleService.getRoleNamesByEmail(principal.getName());
        for (int i = 0; i < role.size(); i++){
            if (role.get(i).equals("student_leader")){
                type = "weekly report";
            }
        }
        //post report
        Reports reports = new Reports();
        reports.setName(dto.getName());
        reports.setType(type);
        reportService.addReport(reports);

        //post report detail
        Date date = new Date();
        ReportDetails reportDetails = new ReportDetails();
        reportDetails.setContent(dto.getContent());
        reportDetails.setCreatedDate(date);
        reportDetails.setLastModifiedDate(date);
        reportDetails.setReport(reports);
        reportDetailService.addReportDetail(reportDetails);

        //save history
        HistoryRecords records = new HistoryRecords();
        records.setUser(user);
        records.setContent("Post report");
        records.setReport(reports);
        records.setCreatedDate(date);
        recordService.save(records);

        //send notification to member project team
        int project_id_user_login = capstoneProjectDetailService.getOneProjectIdByUserId(user_id_login);
        List<Users> user_by_project = capstoneProjectDetailService.getUserStudentMemberByProjectId(project_id_user_login);

        String baseUrl = String.format("%s://%s:%d/",request.getScheme(),  request.getServerName(), request.getServerPort());
        for (int i = 0; i < user_by_project.size(); i++){
            if(user_by_project.get(i).equals(user)){
                String title = "You report success.";
                String content = "report by " + user_id_login + " at " +date;
                    NotificationCommon.sendNotification(user,title,content,user_id_login);
            }else {
                String title = user_id_login + " report at " + date;
                String content = "Report by " + user_id_login + " at " + date + " Click " + "<a href=\"" + baseUrl + "report-detail/" + reportDetails.getId() + "\">view</a>";
                NotificationCommon.sendNotification(user,title,content,user_by_project.get(i).getId());
                try{
                    SendingMail.sendEmail(user_by_project.get(i).getEmail(),"[FPTU Capstone Project] " + title, content);
                }catch (Exception ex) {
                    System.out.println(ex);
                }
            }
            reportService.addReportUserTable(reports.getId(),user_by_project.get(i).getId());
        }
        return "redirect:/report-detail/" + reportDetails.getId();
    }
}
