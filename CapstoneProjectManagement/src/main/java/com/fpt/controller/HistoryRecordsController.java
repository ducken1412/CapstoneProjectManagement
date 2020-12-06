package com.fpt.controller;

import com.fpt.entity.HistoryRecords;
import com.fpt.service.HistoryRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HistoryRecordsController {

    @Autowired
    private HistoryRecordService historyRecordService;

    @RequestMapping(value = "/history-records", method = RequestMethod.GET)
    public String loadHistory(Model model, Integer role, Principal principal){
        if(principal == null) {
            return "redirect:/login";
        }
//        ArrayList<String> roles = new ArrayList<>();
//        roles.add("Student");
//        roles.add("Trainning Department");
//        roles.add("Head, Supervisors");
//        model.addAttribute("roles", roles);
//        model.addAttribute("roleSelect", role);
        List<HistoryRecords> historyRecords;
        try {
            historyRecords = historyRecordService.getDataRoleStudent();
            model.addAttribute("historyRecords", historyRecords);
        }catch (Exception e){

        }

        return "home/history-records";
    }

    @RequestMapping(value = "/history-records", method = RequestMethod.POST)
    public String loadDataTableToTheDropdown(Model model, Integer role, Principal principal){
        if(principal == null) {
            return "redirect:/login";
        }
        List<HistoryRecords> historyRecords;
        if(role == 1){
            historyRecords = historyRecordService.getDataRoleStudent();
            model.addAttribute("historyRecords", historyRecords);
        }
        if(role == 3){
            historyRecords = historyRecordService.getDataRoleHead();
            model.addAttribute("historyRecords", historyRecords);
        }
        if(role == 4){
            historyRecords = historyRecordService.getDataRoleLecture();
            model.addAttribute("historyRecords", historyRecords);
        }
        if(role == 5){
            historyRecords = historyRecordService.getDataRoleTrainingDepartment();
            model.addAttribute("historyRecords", historyRecords);
        }
        System.out.println(role);
        return "home/history-component";
    }

}
