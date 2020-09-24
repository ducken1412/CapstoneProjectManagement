package com.fpt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForumController {
	@GetMapping("/forum")
	public String forum() {
		return "home/forum";
	}
	
	@GetMapping("/add-topic")
	public String addTopic() {
		return "home/add-topic";
	}
}
