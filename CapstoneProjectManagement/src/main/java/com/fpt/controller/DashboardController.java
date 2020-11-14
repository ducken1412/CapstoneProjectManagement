package com.fpt.controller;

import com.fpt.dto.StatisticsTotalDTO;
import com.fpt.entity.ReportDetails;
import com.fpt.entity.Statistics;
import com.fpt.service.StatisticsService;
import com.fpt.service.TaskDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
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
    @GetMapping("/db/dashboard")
    public String getDashboard(Model model, @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size, Principal principal){
        if(principal == null) {
            return "redirect:/login";
        }
        Integer week =  taskDetailsService.findMaxWeek();
        List<Statistics> statisticsList = statisticsService.getStatisticsWithWeek(week);
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


        //phan trang
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Statistics> statisticsPage = statisticsService.getStatisticsWithWeekPage(PageRequest.of(currentPage - 1, pageSize),week);
        model.addAttribute("statisticsPage", statisticsPage);
        int totalPages = statisticsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "admin/dashboard-project";
    }
}
