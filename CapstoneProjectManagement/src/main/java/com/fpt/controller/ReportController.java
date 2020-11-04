package com.fpt.controller;


import com.fpt.common.NotificationCommon;
import com.fpt.common.SendingMail;
import com.fpt.dto.CommentDTO;
import com.fpt.dto.NotificationDTO;
import com.fpt.dto.ReportDTO;
import com.fpt.entity.*;
import com.fpt.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
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

    @Autowired
    private CommentService commentService;

    @GetMapping("/report")
    public String report(Model model, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }

        model.addAttribute("commentDTO", new CommentDTO());
        model.addAttribute("reportDetail", new ReportDTO());
        Users user = userService.findByEmail(principal.getName());
        return "home/add-report";
    }

    @RequestMapping(value = "/report-detail/{id}", method = RequestMethod.GET)
    public String viewRoport(@PathVariable("id") Integer id, Model model, Principal principal){
        if(principal == null) {
            return "redirect:/login";
        }
        ReportDetails details = reportDetailService.getReportDetailByReportId(id);
        List<Comments> comments = commentService.getCommentsByReportDetatilId(details.getId());
        model.addAttribute("report_id", id);
        String title =  details.getReport().getName();
        String content = details.getContent();
        model.addAttribute("title", title);
        model.addAttribute("content", content);
        model.addAttribute("comments", comments);
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
        Reports reports = new Reports();
        Date date = new Date();
        HistoryRecords records = new HistoryRecords();
        ReportDetails reportDetails = new ReportDetails();
        try {
            //post report
            reports.setName(dto.getName());
            reports.setType(type);
            reportService.addReport(reports);

            //post report detail
            reportDetails.setContent(dto.getContent());
            reportDetails.setUser(user);
            reportDetails.setCreatedDate(date);
            reportDetails.setLastModifiedDate(date);
            reportDetails.setReport(reports);
            reportDetailService.addReportDetail(reportDetails);

            //save history
            records.setUser(user);
            records.setContent("Post report");
            records.setReport(reports);
            records.setCreatedDate(date);
            recordService.save(records);
        }catch (Exception e){
            System.out.println(e);
        }

        //send notification to member project team
        int project_id_user_login = capstoneProjectDetailService.getOneProjectIdByUserId(user_id_login);
        List<Users> user_by_project = capstoneProjectDetailService.getUserStudentMemberByProjectId(project_id_user_login);

        String baseUrl = String.format("%s://%s:%d/",request.getScheme(),  request.getServerName(), request.getServerPort());
        for (int i = 0; i < user_by_project.size(); i++){
            if(!user_by_project.get(i).equals(user)){
                String title = user_id_login + " report at " + date;
                String content = "Report by " + user_id_login + " at " + date + " Click " + "<a href=\"" + baseUrl + "report-detail/" + reportDetails.getReport().getId() + "\">view</a>";
                NotificationCommon.sendNotification(user,title,content,user_by_project.get(i).getId());
                try{
                    SendingMail.sendEmail(user_by_project.get(i).getEmail(),"[FPTU Capstone Project] " + title, content);
                }catch (Exception ex) {
                    System.out.println(ex);
                }
            }
            reportService.addReportUserTable(reports.getId(),user_by_project.get(i).getId());
        }
        return "redirect:/report-detail/" + reports.getId();
    }

    @RequestMapping(value = "/list-reports", method = RequestMethod.GET)
    public String listRoport(Model model, Principal principal){
        if(principal == null) {
            return "redirect:/login";
        }
        Users user = userService.findByEmail(principal.getName());
        String user_id_login = user.getId();
        List<Integer> list_report_id =  reportDetailService.getListReportIdByUserId(user_id_login);
        List<ReportDetails> reportDetails = new ArrayList<>();
        for (int i = 0; i < list_report_id.size(); i++){
            ReportDetails details = reportDetailService.getReportDetailByReportId(list_report_id.get(i));
            reportDetails.add(details);
        }
        model.addAttribute("reportDetails",reportDetails);
        return "home/list-reports";
    }

    @RequestMapping(value = "/report-comment", method = RequestMethod.POST)
    public String commentReport(CommentDTO dto, Model model, BindingResult result, Principal principal, HttpServletRequest request) {
        if (principal == null) {
            return "redirect:/login";
        }
        Users user = userService.findByEmail(principal.getName());
        String user_id_login = user.getId();
        int report_detail_id = dto.getPostId();
        Comments comment = new Comments();
        HistoryRecords records = new HistoryRecords();
        Date date = new Date();
        ReportDetails reportDetail = reportDetailService.getReportDetailByReportId(report_detail_id);
        Reports reports = reportService.finReportsById(report_detail_id);
        comment.setContent(dto.getContent());
        comment.setSender(user);
        comment.setReportDetail(reportDetail);
        comment.setCreatedDate(date);
        commentService.save(comment);
        records.setUser(user);
        records.setContent("Creat comment report");
        records.setCreatedDate(date);
        records.setReport(reports);
        recordService.save(records);
        return "redirect:/report-detail/" +report_detail_id;
    }

}
