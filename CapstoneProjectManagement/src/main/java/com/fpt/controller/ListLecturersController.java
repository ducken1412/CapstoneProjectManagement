package com.fpt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller

public class ListLecturersController {
	@GetMapping("/listlecturersproject")
	public String registerProject() {
		return "home/listlecturers";
	}

}
