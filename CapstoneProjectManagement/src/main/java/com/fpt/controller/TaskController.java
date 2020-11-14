package com.fpt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TaskController {
    @GetMapping("/db/statistic/{id}")
    public String getStatistic() {
        return "admin/statistics-project";
    }
}