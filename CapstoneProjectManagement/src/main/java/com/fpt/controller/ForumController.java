package com.fpt.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpt.dto.CommentDTO;
import com.fpt.dto.PostDTO;
import com.fpt.entity.Comments;
import com.fpt.entity.HistoryRecords;
import com.fpt.entity.Posts;
import com.fpt.entity.Users;
import com.fpt.service.CommentService;
import com.fpt.service.HistoryRecordService;
import com.fpt.service.PostService;
import com.fpt.service.UserService;

@Controller
public class ForumController {

	@Autowired
	private PostService postService;

	@Autowired
	private UserService userService;

	@Autowired
	private HistoryRecordService recordService;

	@Autowired
	private CommentService commentService;

	@GetMapping("/forum")
	public String forum() {
		return "home/forum";
	}

	@GetMapping("/list-post")
	public String getPosts(Model model, @RequestParam("page") String page, @RequestParam("size") String size) {
		// get user logged
		List<Users> users = userService.findByUsername("ducddse04936");
		if (!users.isEmpty()) {
			model.addAttribute("loggedUser", users.get(0).getId());
		} else {
			return "error/403Page";
		}

		int currentPage = 1;
		int pageSize = 5;
		try {
			currentPage = Integer.parseInt(page);
			pageSize = Integer.parseInt(size);
		} catch (Exception ex) {

		}
		Page<Posts> postPage = postService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
		model.addAttribute("postPage", postPage);
		int totalPages = postPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		return "home/list-post";
	}

	@PostMapping("/add-post")
	public String addPost(@ModelAttribute PostDTO dto, Model model) {
		int currentPage = 1;
		int pageSize = 5;
		Posts post;
		Date date = new Date();
		if (dto.getId() != null) {
			post = postService.findById(dto.getId());
			post.setTitle(dto.getTitle());
			post.setDescription(dto.getDescription());
			HistoryRecords records = new HistoryRecords();
			records.setLastModifiedDate(date);
			records.setContent("Edit post");
			records.setPost(post);
			if (postService.save(post)) {
				recordService.save(records);
				return getPosts(model, String.valueOf(currentPage), String.valueOf(pageSize)); 
			} else {
				return "error/403Page";
			}
		}
		
		post = new Posts();
		HistoryRecords records = new HistoryRecords();
		// get user logged
		List<Users> author = userService.findByUsername("ducddse04936");
		if (!author.isEmpty()) {
			post.setAuthor(author.get(0));
			records.setUser(author.get(0));
		} else {
			return "error/403Page";
		}
		post.setTitle(dto.getTitle());
		post.setDescription(dto.getDescription());
		post.setCreated_date(date);
		records.setCreatedDate(date);
		records.setContent("Create post");
		records.setPost(post);
		
		if (postService.save(post)) {
			recordService.save(records);
		} else {
			return "error/403Page";
		}
		return getPosts(model, String.valueOf(currentPage), String.valueOf(pageSize));
	}

	@PostMapping("/add-comment")
	public String addComment(@ModelAttribute CommentDTO dto, Model model) {
		int currentPage = 1;
		int pageSize = 5;
		Date date = new Date();
		Posts post = postService.findById(dto.getPostId());
		Comments comment = new Comments();
		comment.setCreatedDate(date);
		comment.setContent(dto.getContent());
		comment.setPost(post);
		commentService.save(comment);
		List<Comments> comments = post.getComments();
		comments.add(comment);
		post.setComments(comments);
		return getPosts(model, String.valueOf(currentPage), String.valueOf(pageSize));
	}

	@GetMapping("/add-post")
	public String addPost(Model model) {
		model.addAttribute("post", new Posts());
		return "home/add-post";
	}

	@GetMapping("/edit-post/{postId}")
	public String editPost(@PathVariable Integer postId, Model model) {
		Posts post = postService.findById(postId);
		if (post != null) {
			model.addAttribute("post", post);
		} else {
			model.addAttribute("post", new Posts());
		}
		return "home/add-post";
	}
}
