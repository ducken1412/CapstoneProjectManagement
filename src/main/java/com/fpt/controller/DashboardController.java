package com.fpt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/db/dashboard")
    public String getDashboard(){
        return "admin/dashboard-project";
    }
}
