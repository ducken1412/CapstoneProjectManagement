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

    @Autowired
    private NotificationsService notificationsService;

    @Autowired
    private NotificationDetailService notificationDetailService;


    @GetMapping("/report")
    public String report(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        try {
            model.addAttribute("commentDTO", new CommentDTO());
            model.addAttribute("report", new Reports());
            Users user = userService.findByEmail(principal.getName());
            List<String> roles = userRoleService.getRoleNamesByEmail(principal.getName());
            for (String role : roles) {
                if (role.equals("student_leader")) {
                    model.addAttribute("check_role", true);
                    break;
                }
            }
        } catch (Exception e) {

        }
        return "home/add-report";
    }

    @RequestMapping(value = "/report/{id}", method = RequestMethod.GET)
    public String viewRoport(@PathVariable("id") Integer id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Reports report = reportService.getReportsById(id);
        List<Comments> comments = commentService.getCommentsByReportId(id);
        model.addAttribute("report_id", id);
        String title = report.getTitle();
        String content = report.getContent();
        model.addAttribute("title", title);
        model.addAttribute("content", content);
        model.addAttribute("comments", comments);
        return "home/report-detail";
    }

    @RequestMapping(value = "/report-detail/{id}", method = RequestMethod.GET)
    public String viewRoportDetails(@PathVariable("id") Integer id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Reports report = reportService.getReportsById(id);
        List<Comments> comments = commentService.getCommentsByReportId(id);
        model.addAttribute("report_id", id);
        String title = report.getTitle();
        String content = report.getContent();
        model.addAttribute("title", title);
        model.addAttribute("content", content);
        model.addAttribute("comments", comments);
        return "home/report-detail-container";
    }

    @RequestMapping(value = "/add-report", method = RequestMethod.POST)
    public String addReport(MultipartFile file, Reports reportForm, Model model, BindingResult result, Principal principal, HttpServletRequest request) {
        if (principal == null) {
            return "redirect:/login";
        }
        Users user = userService.findByEmail(principal.getName());
        String userId = user.getId();
        CapstoneProjects capstoneProject = capstoneProjectDetailService.findCapstoneProjectByUserId(userId);
        String type = "daily report";
        List<String> roles = userRoleService.getRoleNamesByEmail(principal.getName());
        for (String role : roles) {
            if (role.equals("student_leader")) {
                type = "weekly report";
                break;
            }
        }

        Reports report = new Reports();
        Date date = new Date();
        List<HistoryRecords> historyRecords = new ArrayList<>();
        List<Users> userRecipients = new ArrayList<>();
        try {
            userRecipients = capstoneProjectDetailService.getUserStudentMemberByProjectId(capstoneProject.getId());
            //post report
            report.setTitle(reportForm.getTitle());
            report.setContent(reportForm.getContent());
            report.setType(type);
            report.setCreatedDate(date);
            report.setUser(user);
            historyRecords.add(new HistoryRecords(user, date, null, "Create report", capstoneProject, report, null, null, null));
            report.setReportRecipients(userRecipients);
            report.setHistoryRecords(historyRecords);
            if (!reportService.addReport(report)) {
                return "home/add-report";
            }

            //Import Excel
            if (file != null) {
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
                    CapstoneProjects capstoneProjects = capstoneProjectDetailService.findCapstoneProjectByUserId(userId);
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
                    int countTask = 0;

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
                        countTask = countTask + 1;

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
        } catch (Exception e) {
            System.out.println(e);
        }

        //send notification to member project team
        String userName = user.getUsername();
        int reportId = report.getId();
        String baseUrl = String.format("%s://%s:%d/", request.getScheme(), request.getServerName(), request.getServerPort());
        String title = userName + " report at " + date;
        String content = "Report by " + userName + " at " + date + " Click " + "<a href=\"" + baseUrl + "report/" + reportId + "\">view report detail.</a>";
        String typeReport = "private";
        Notifications notifications = new Notifications();
        notifications.setContent(content);
        notifications.setTitle(title);
        notifications.setCreated_date(date);
        notifications.setType(type);
        notificationsService.addNotification(notifications);
        List<NotificationDetails> detailsList = new ArrayList<>();
        NotificationDetails notificationDetails = null;
        for (Users users : userRecipients) {
            if (!users.equals(user)) {
                notificationDetails = new NotificationDetails();
                notificationDetails.setType(typeReport);
                notificationDetails.setNotification(notifications);
                notificationDetails.setUser(users);
                detailsList.add(notificationDetails);
                try {
                    SendingMail.sendEmail(users.getEmail(), "[FPTU Capstone Project] " + title, content);
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        }
        notificationDetailService.saveAllNotificationDetails(detailsList);
        return "redirect:/report/" + reportId;
    }

    @RequestMapping(value = "/list-reports", method = RequestMethod.GET)
    public String listRoport(Model model, @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Users user = userService.findByEmail(principal.getName());
        String userId = user.getId();

        //phan trang
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(20);
        Page<Reports> reportsPage = reportService.findReportByUserId(PageRequest.of(currentPage - 1, pageSize), userId);
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
        int reportId = dto.getPostId();
        Comments comment = new Comments();
        Date date = new Date();
//        Reports report = reportDetailService.getReportDetailByReportId(report_detail_id);
        Reports reports = reportService.finReportsById(reportId);
        comment.setContent(dto.getContent());
        comment.setSender(user);
        comment.setReport(reports);
        comment.setCreatedDate(date);
        commentService.save(comment);

        return "redirect:/report-detail/" + reportId;
    }


    @RequestMapping(value = "/my-reports", method = RequestMethod.GET)
    public String myReport(Model model, @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Users user = userService.findByEmail(principal.getName());
        String userId = user.getId();

        //phan trang
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(20);
        Page<Reports> reportsPage = reportService.findReportsByUserId(PageRequest.of(currentPage - 1, pageSize), userId);
        model.addAttribute("reportsPage", reportsPage);
        int totalPages = reportsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "home/my-reports";
    }

}
