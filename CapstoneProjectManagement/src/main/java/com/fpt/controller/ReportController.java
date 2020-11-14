package com.fpt.controller;


import com.fpt.common.NotificationCommon;
import com.fpt.common.SendingMail;
import com.fpt.dto.*;
import com.fpt.entity.*;
import com.fpt.service.*;
import com.fpt.utils.ExcelHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.google.api.tasks.TaskList;
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
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private TaskDetailsService taskDetailsService;
    @Autowired
    private StatisticsService statisticsService;


    @GetMapping("/report")
    public String report(Model model, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }

        model.addAttribute("commentDTO", new CommentDTO());
        model.addAttribute("reportDetail", new ReportDTO());
        Users user = userService.findByEmail(principal.getName());
        boolean check = false;
        List<String> role = userRoleService.getRoleNamesByEmail(principal.getName());
        for(int i = 0; i < role.size(); i++){
            if(role.get(i).equals("student_leader")){
                check = true;
            }
        }
        model.addAttribute("check_role",check);
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
    public String addReport(MultipartFile file, ReportDTO dto, Model model, BindingResult result, Principal principal, HttpServletRequest request){
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


            //Import Excel
            if(file != null) {
                String message = "";

                Workbook workbook = new XSSFWorkbook(file.getInputStream());
                String SHEET = "Sheet1";
                Sheet sheet = workbook.getSheet(SHEET);
                if (ExcelHelper.hasExcelFormat(file)) {
                    List<String> errorList = ExcelHelper.checkErrorExcelImport(sheet);
                    if (errorList == null) {
                        workbook.close();
                        return "home/add-report";
                    }
                    List<TaskDetails> taskDetailsList = ExcelHelper.excelToStatistics(sheet);

                    Integer week = taskDetailsService.findMaxWeek();
                    CapstoneProjects capstoneProjects = capstoneProjectDetailService.findCapstoneProjectByUserId(user_id_login);
                    List<TaskDetails> taskDetailsListAddDB = new ArrayList<>();
                    double timeTrackingCurrent = 0;
                    double timeTrackingProcess = 0;
                    double timeTrackingTodo = 0;
                    double timeTrackingDone = 0;
                    //count to div %
                    int countTrackingCurrent = 0;
                    int countTrackingProcess = 0;
                    int countTrackingTodo = 0;
                    int countTrackingDone = 0;
                    int countTask =0;

                    Date startDate = taskDetailsList.get(0).getStartDate();
                    Date endDate = taskDetailsList.get(0).getEndDate();

                    for (TaskDetails taskDetails : taskDetailsList) {
                        if (week != null) {
                            taskDetails.setWeek(week + 1);
                        } else {
                            taskDetails.setWeek(1);
                        }

                        if (startDate.compareTo(taskDetails.getStartDate()) < 0) {
                            startDate = taskDetails.getStartDate();
                        }
                        if (endDate.compareTo(taskDetails.getEndDate()) < 0) {
                            endDate = taskDetails.getEndDate();
                        }
                        taskDetails.setCapstoneProject(capstoneProjects);
                        taskDetailsListAddDB.add(taskDetails);
                        Date dateCurrent = new Date();
                        if (taskDetails.getEndDate().compareTo(dateCurrent) <= 0) {
                            timeTrackingCurrent = timeTrackingCurrent + taskDetails.getTimeTracking();
                            countTrackingCurrent = countTrackingCurrent + 1;
                        }
                        if (taskDetails.getStatus().equalsIgnoreCase("To Do")) {
                            timeTrackingTodo = timeTrackingTodo + taskDetails.getTimeTracking();
                            countTrackingTodo = countTrackingTodo + 1;
                        }
                        if (taskDetails.getStatus().equalsIgnoreCase("Done")) {
                            timeTrackingDone = timeTrackingDone + taskDetails.getTimeTracking();
                            countTrackingDone = countTrackingDone + 1;
                        }
                        if (taskDetails.getStatus().equalsIgnoreCase("In Progress")) {
                            timeTrackingProcess = timeTrackingProcess + taskDetails.getTimeTracking();
                            countTrackingProcess = countTrackingProcess + 1;
                        }
                        countTask = countTask +1;

                    }
                    if (countTrackingCurrent != 0) {
                        timeTrackingCurrent = timeTrackingCurrent / countTrackingCurrent;
                    }

                    if (countTrackingDone != 0 && countTask != 0) {
                        timeTrackingDone = (Double.valueOf(countTrackingDone) / Double.valueOf(countTask) * 100);
                    }

                    if (countTrackingProcess != 0 && countTask != 0) {
                        timeTrackingProcess = (Double.valueOf(countTrackingProcess) / Double.valueOf(countTask) * 100);
                    }

                    if (countTrackingTodo != 0 && countTask != 0) {
                        timeTrackingTodo = (Double.valueOf(countTrackingTodo) / Double.valueOf(countTask) * 100);
                    }
                    if (taskDetailsListAddDB != null) {
                        taskDetailsService.saveTaskDetails(taskDetailsListAddDB);
                        Statistics statistics = new Statistics();
                        statistics.setStartDate(startDate);
                        statistics.setEndDate(endDate);
                        statistics.setTimeTrackingCurrent(timeTrackingCurrent);
                        statistics.setTimeTrackingTodo(timeTrackingTodo);
                        statistics.setTimeTrackingProgress(timeTrackingProcess);
                        statistics.setTimeTrackingDone(timeTrackingDone);
                        if (week != null) {
                            statistics.setWeek(week + 1);
                        } else {
                            statistics.setWeek(1);
                        }
                        statistics.setCapstoneProject(capstoneProjects);
                        statisticsService.saveStatistics(statistics);
                    }

                } else {
                    workbook.close();
                    return "home/add-report";
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }

        //send notification to member project team
        int project_id_user_login = capstoneProjectDetailService.getOneProjectIdByUserId(user_id_login);
        List<Users> user_by_project = capstoneProjectDetailService.getUserStudentMemberByProjectId(project_id_user_login);
        String userName = user.getUsername();
        String baseUrl = String.format("%s://%s:%d/",request.getScheme(),  request.getServerName(), request.getServerPort());
        for (int i = 0; i < user_by_project.size(); i++){
            if(!user_by_project.get(i).equals(user)){
                String title = userName + " report at " + date;
                String content = "Report by " + userName + " at " + date + " Click " + "<a href=\"" + baseUrl + "report-detail/" + reportDetails.getReport().getId() + "\">view</a>";
                NotificationCommon.sendNotification(user,title,content,user_by_project.get(i).getId());
                try{
                    SendingMail.sendEmail(user_by_project.get(i).getEmail(),"[FPTU Capstone Project] " + title, content);
                }catch (Exception ex) {
                    System.out.println(ex);
                }
            }
            reportService.addReportUserTable(reports.getId(),user_by_project.get(i).getId());
        }
        return "redirect:/report/" + reports.getId();
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
