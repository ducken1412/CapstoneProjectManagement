package com.fpt.controller;


import com.fpt.common.NotificationCommon;
import com.fpt.common.SendingMail;
import com.fpt.dto.CommentDTO;
import com.fpt.dto.NotificationDTO;
import com.fpt.dto.ReportDTO;
import com.fpt.entity.*;
import com.fpt.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fpt.entity.Reports;
import com.fpt.entity.Users;
import com.fpt.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


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

    @RequestMapping(value = "/report/{id}", method = RequestMethod.GET)
    public String viewRoport(@PathVariable("id") Integer id, Model model,Principal principal){
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

    @RequestMapping(value = "/report-detail/{id}", method = RequestMethod.GET)
    public String viewRoportDetails(@PathVariable("id") Integer id, Model model, Principal principal){
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
        return "home/report-detail-container";
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
        String userId = user.getId();
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
        int project_id_user_login = capstoneProjectDetailService.getOneProjectIdByUserId(userId);
        List<Users> userByProject = capstoneProjectDetailService.getUserStudentMemberByProjectId(project_id_user_login);
        Integer reportId =  reports.getId();
        Integer reportDetailId = reportDetails.getReport().getId();
        String baseUrl = String.format("%s://%s:%d/",request.getScheme(),  request.getServerName(), request.getServerPort());
        String title = "";
        String content = "";
//        for (int i = 0; i < userByProject.size(); i++){
//            if(!userByProject.get(i).equals(user)){
//                title = userId + " report at " + date;
//                content = "Report by " + userId + " at " + date + " Click " + "<a href=\"" + baseUrl + "report-detail/" + reportDetailId + "\">view</a>";
//                NotificationCommon.sendNotification(user,title,content,userByProject.get(i).getId());
//                try{
//                    SendingMail.sendEmail(userByProject.get(i).getEmail(),"[FPTU Capstone Project] " + title, content);
//                }catch (Exception ex) {
//                    System.out.println(ex);
//                }
//            }
//        }
        for (Users u : userByProject) {
            if(!userByProject.equals(user)){
                title = userId + " report at " + date;
                content = "Report by " + userId + " at " + date + " Click " + "<a href=\"" + baseUrl + "report/" + reportDetailId + "\">view</a>";
                NotificationCommon.sendNotification(user,title,content,u.getId());
                try{
                    SendingMail.sendEmail(u.getEmail(),"[FPTU Capstone Project] " + title, content);
                }catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        }
        for (Users u : userByProject) {
            reportService.addReportUserTable(reportId,u.getId());
        }
        return "redirect:/report-detail/" + reportId;
    }

    @RequestMapping(value = "/list-reports", method = RequestMethod.GET)
    public String listRoport(Model model, @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size,Principal principal){
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

        //phan trang
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(20);
        Page<ReportDetails> reportsPage = reportDetailService.getTitlePagginByUserId(PageRequest.of(currentPage - 1, pageSize),user_id_login);
        model.addAttribute("reportsPage", reportsPage);
        int totalPages = reportsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
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
