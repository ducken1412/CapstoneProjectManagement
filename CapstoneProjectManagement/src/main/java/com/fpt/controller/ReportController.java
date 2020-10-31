package com.fpt.controller;

import com.fpt.entity.Reports;
import com.fpt.entity.Users;
import com.fpt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class ReportController {
    @Autowired
    private UserService userService;

    @GetMapping("/add-report")
    public String report(Model model, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("report", new Reports());
        Users user = userService.findByEmail(principal.getName());
        return "home/add-report";
    }
}
