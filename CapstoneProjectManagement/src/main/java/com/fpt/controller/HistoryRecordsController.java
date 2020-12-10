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
    public String loadHistory(Model model, Integer role, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<HistoryRecords> historyRecords;
        List<HistoryRecords> historyRecordss = historyRecordService.getCapstoneApprove();
        try {
            historyRecords = historyRecordService.getDataRoleStudent();
            model.addAttribute("historyRecords", historyRecords);
        } catch (Exception e) {

        }

        return "home/history-records";
    }

    @RequestMapping(value = "/history-records", method = RequestMethod.POST)
    public String loadDataTableToTheDropdown(Model model, Integer role, Integer type, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        List<HistoryRecords> historyRecords;
        if (role != null) {
            switch (role) {
                case 1:
                    historyRecords = historyRecordService.getDataRoleStudent();
                    model.addAttribute("historyRecords", historyRecords);
                    break;
                case 3:
                    historyRecords = historyRecordService.getDataRoleHead();
                    model.addAttribute("historyRecords", historyRecords);
                    break;
                case 4:
                    historyRecords = historyRecordService.getDataRoleLecture();
                    model.addAttribute("historyRecords", historyRecords);
                    break;
                case 5:
                    historyRecords = historyRecordService.getDataRoleTrainingDepartment();
                    model.addAttribute("historyRecords", historyRecords);
                    break;

            }

        }

        if (type != null) {
            switch (type) {
                case 1:
                    historyRecords = historyRecordService.getCapstoneRegister();
                    model.addAttribute("historyRecords", historyRecords);
                    break;
                case 2:
                    historyRecords = historyRecordService.getCapstoneApprove();
                    model.addAttribute("historyRecords", historyRecords);
                    break;
                case 3:
                    historyRecords = historyRecordService.getCapstoneReject();
                    model.addAttribute("historyRecords", historyRecords);
                    break;
                case 4:
                    historyRecords = historyRecordService.getCapstoneBookingSupervisors();
                    model.addAttribute("historyRecords", historyRecords);
                    break;
                case 5:
                    historyRecords = historyRecordService.getAddSupervisorsForCapstone();
                    model.addAttribute("historyRecords", historyRecords);
                    break;
            }
        }
        return "home/history-component";
    }

}
