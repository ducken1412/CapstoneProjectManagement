package com.fpt.controller;

import com.fpt.dto.StatisticsTotalDTO;
import com.fpt.entity.Statistics;
import com.fpt.service.StatisticsService;
import com.fpt.service.TaskDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private TaskDetailsService taskDetailsService;
    @Autowired
    private StatisticsService statisticsService;
  
    @GetMapping("/db/dashboard")
    public String getDashboard(Model model){

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
            statisticsTotalDTO.setTotalOnSchedule(String.valueOf (perOnSchedule)+"%");

        }else {
            statisticsTotalDTO.setTotalOnSchedule("0%");
        }

        if (countWarning != 0 && statisticsList.stream().count() != 0){
            Double perTotalWarning = Double.valueOf(countWarning) / Double.valueOf(statisticsList.stream().count()) * 100 ;
            statisticsTotalDTO.setTotalWarning(String.valueOf(perTotalWarning)+"%");

        }else {
            statisticsTotalDTO.setTotalWarning("0%");
        }
        if (countSerious != 0 && statisticsList.stream().count() != 0){
            Double perTotalSerious = Double.valueOf(countSerious) / Double.valueOf(statisticsList.stream().count()) * 100;
            statisticsTotalDTO.setTotalSerious(String.valueOf(perTotalSerious)+"%");
        }else {
            statisticsTotalDTO.setTotalSerious("0%");
        }
        statisticsTotalDTO.setNumProject(String.valueOf(statisticsList.stream().count()));

        model.addAttribute("StatisticsTotalDTO", statisticsTotalDTO);

        return "admin/dashboard-project";
    }
}
