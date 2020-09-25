package com.fpt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.fpt.dto.PostDTO;
import com.fpt.entity.HistoryRecords;
import com.fpt.entity.Posts;
import com.fpt.service.PostService;

@Controller
public class ForumController {
	
	@Autowired
	private PostService postService;
	
	@GetMapping("/forum")
	public String forum() {
		return "home/forum";
	}
	
	@PostMapping("/add-post")
	public String addPost(@ModelAttribute PostDTO dto) {
		Posts post = new Posts();
		post.setTitle(dto.getTitle());
		post.setDescription(dto.getDescription());
		HistoryRecords records = new HistoryRecords();
		return "home/add-post";
	}
	
	@GetMapping("/add-post")
	public String addPost(Model model) {
		model.addAttribute("post", new PostDTO());
		return "home/add-post";
	}
}
