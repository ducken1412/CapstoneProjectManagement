package com.fpt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class GuideController {
	@GetMapping("/createcapstone")
	public String viewCreatecapstone() {
		return "guide/createcapstone";
	}
	@GetMapping("/createreport")
	public String viewCreatereport() {
		return "guide/createreport";
	}
	@GetMapping("/jira")
	public String viewJiratool() {
		return "guide/jira";
	}
	
	

}
