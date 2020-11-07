package com.fpt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class AboutController {
	@GetMapping("/about")
	public String viewAbout() {
		return "about/aboutmodel";
	}
	@GetMapping("/model")
	public String viewModel() {
		return "about/model_suggestion";
	}

}
