package com.fpt.controller;

import com.fpt.dto.StatisticsTotalDTO;
import com.fpt.entity.*;
import com.fpt.service.*;
import com.fpt.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class DashboardController {

    @Autowired
    private TaskDetailsService taskDetailsService;
    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private UserService userService;

    @Autowired
    private SitesService sitesService;
    @Autowired
    private SemestersService semestersService;
  
    @GetMapping("/db/dashboard")
    public String getDashboard(Model model, Principal principal){
        try {
            //get sites and semesters
            List<Sites> sites = sitesService.findAll();
            model.addAttribute("sites", sites);

            List<Semesters> semesters = semestersService.findAll();
            model.addAttribute("semesters", semesters);

            //get max week of statics
            Integer week =  taskDetailsService.findMaxWeek();
            Users users = userService.findByEmail(principal.getName());
            List<Statistics>  statisticsList ;
            boolean roleHead =false;
            for (UserRoles role : users.getRoleUser()){
                if(role.getUserRoleKey().getRole().getName().equalsIgnoreCase(Constant.ROLE_HEAD_DB)){
                    roleHead =true;
                    break;
                }
            }
            if (roleHead){
                statisticsList = statisticsService.getStatisticsWithWeek(week);
            } else{
                statisticsList = statisticsService.getStatisticsWithWeekByLecture(week, users.getEmail());
            }

            StatisticsTotalDTO  statisticsTotalDTO = new StatisticsTotalDTO();
            int countOnSchedule = 0;
            int countWarning= 0;
            int countSerious= 0;

            for (Statistics statistics : statisticsList){
                if (statistics.getTimeTrackingCurrent() >= 80){
                    countOnSchedule = countOnSchedule + 1;
                }  else if (statistics.getTimeTrackingCurrent() >= 50 && statistics.getTimeTrackingCurrent() < 80){
                    countWarning = countWarning + 1 ;
                }  else {
                    countSerious = countSerious + 1;
                }

            }

            if (countOnSchedule != 0 && statisticsList.stream().count() != 0){
                Double perOnSchedule = Double.valueOf(countOnSchedule) / Double.valueOf(statisticsList.stream().count()) * 100;
                statisticsTotalDTO.setTotalOnSchedule(perOnSchedule);

            }else {
                statisticsTotalDTO.setTotalOnSchedule(0.0);
            }

            if (countWarning != 0 && statisticsList.stream().count() != 0){
                Double perTotalWarning = Double.valueOf(countWarning) / Double.valueOf(statisticsList.stream().count()) * 100 ;
                statisticsTotalDTO.setTotalWarning(perTotalWarning);

            }else {
                statisticsTotalDTO.setTotalWarning(0.0);
            }
            if (countSerious != 0 && statisticsList.stream().count() != 0){
                Double perTotalSerious = Double.valueOf(countSerious) / Double.valueOf(statisticsList.stream().count()) * 100;
                statisticsTotalDTO.setTotalSerious(perTotalSerious);
            }else {
                statisticsTotalDTO.setTotalSerious(0.0);
            }
            statisticsTotalDTO.setCountOnSchedule(countOnSchedule);
            statisticsTotalDTO.setCountSerious(countSerious);
            statisticsTotalDTO.setCountWarning(countWarning);
            statisticsTotalDTO.setNumProject(String.valueOf(statisticsList.stream().count()));

            model.addAttribute("StatisticsTotalDTO", statisticsTotalDTO);


        }catch (Exception e){

        }
        return "admin/dashboard-project";
    }

    @GetMapping("/db/listCap")
    public String getDashboardCap(Model model, Principal principal, @RequestParam("page") String page, @RequestParam("size") String size,
                               @RequestParam("sites") Integer sitesSearch,
                               @RequestParam("semesters") Integer semestersSearch, @RequestParam("nameSearch") String nameSearch ,@RequestParam("userSearch") String userSearch){
        if(principal == null) {
            return "redirect:/login";
        }

        //get max week of statics
        Integer week =  taskDetailsService.findMaxWeek();
        Users users = userService.findByEmail(principal.getName());
        //phan trang
        int currentPage = 1;
        int pageSize = 10;
        try {
            currentPage = Integer.parseInt(page);
            pageSize = Integer.parseInt(size);
        } catch (Exception ex) {

        }
        Page<Statistics> statisticsPage;
        boolean roleHead =false;
        for (UserRoles role : users.getRoleUser()){
            if(role.getUserRoleKey().getRole().getName().equalsIgnoreCase(Constant.ROLE_HEAD_DB)){
                roleHead =true;
                break;
            }
        }
        if (roleHead){
            statisticsPage = statisticsService.getStatisticsWithWeekPage(PageRequest.of(currentPage - 1, pageSize)
                    ,week, sitesSearch, semestersSearch,nameSearch,userSearch);
        } else{
            statisticsPage = statisticsService.getStatisticsWithWeekPageByLecture(week,users.getEmail(),
                    PageRequest.of(currentPage - 1, pageSize),sitesSearch,semestersSearch,nameSearch,userSearch);
        }


        model.addAttribute("statisticsPage", statisticsPage);
        int totalPages = statisticsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "admin/list-capstoneProject";
    }


}
