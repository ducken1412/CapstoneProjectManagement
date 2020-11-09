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
	@GetMapping("/agile")
	public String viewAgile() {
		return "about/agile";
	}
	@GetMapping("/iterative")
	public String viewIterative() {
		return "about/iterative";
	}
	@GetMapping("/rad")
	public String viewRAD() {
		return "about/rad";
	}
	@GetMapping("/spiral")
	public String viewSpiral() {
		return "about/spiral";
	}
	@GetMapping("/v")
	public String viewV() {
		return "about/v";
	}
	@GetMapping("/waterfall")
	public String viewWaterfall() {
		return "about/waterfall";
	}
	

}
