package com.fpt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterProjectController {
	@GetMapping("/registerproject")
	public String registerProject() {
		return "home/register-project";
	}
}
