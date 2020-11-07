package com.fpt.controller;

import com.fpt.dto.CommentDTO;
import com.fpt.dto.PostDTO;
import com.fpt.entity.*;
import com.fpt.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@CrossOrigin("http://localhost:8080")
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

    @Autowired
    FilesStorageService storageService;
    @Autowired
    private FilesService filesService;
    @GetMapping("/forum")
    public String forum(Principal principal) {
        if(principal == null) {
			return "redirect:/login";
		}
        return "home/forum";
    }

    @GetMapping("/list-post")
    public String getPosts(Model model, @RequestParam("page") String page, @RequestParam("size") String size, Principal principal) {
        if(principal == null) {
			return "redirect:/login";
		}
        // get user logged
        Users users = userService.findByEmail(principal.getName());
        if (users != null) {
            model.addAttribute("loggedUser", users.getId());
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

    @ResponseBody
    @PostMapping("/add-post")
    public String addPost(@ModelAttribute PostDTO dto ,Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
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
                return String.valueOf(dto.getId());
            } else {
                return "error/403Page";
            }
        }

        post = new Posts();
        HistoryRecords records = new HistoryRecords();
        // get user logged
        Users author = userService.findByEmail(principal.getName());
        if (author != null) {
            post.setAuthor(author);
            records.setUser(author);
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
        return  String.valueOf(post.getId());
    }

    @ResponseBody
    @PostMapping(value = "/add-file-post/{postId}")
    public String addFilesPost(MultipartHttpServletRequest request,
                               HttpServletResponse response, @PathVariable Integer postId ,Principal principal) throws Exception {
        if(principal == null) {
            return "redirect:/login";
        }
        filesService.deleteAllByPostId(postId);
        Map< String, MultipartFile > filesMap = new HashMap< String, MultipartFile >();
        filesMap = request.getFileMap();
        for(MultipartFile file : filesMap.values()){
            long date = new Date().getTime();
            List<String> fileNames = new ArrayList<>();
            String fileName = file.getOriginalFilename() + "-" + String.valueOf(date) + postId;
            storageService.save(file,fileName);
            fileNames.add(file.getOriginalFilename());
            Files dbFile = new Files();
            dbFile.setFileName(fileName);
            Posts post = postService.findById(postId);
            if (post!= null){
                dbFile.setPost(post);
                filesService.saveFiles(dbFile);
            }
        }

        return "The post has been added successfully";
    }

    @ResponseBody
    @PostMapping("/add-comment")
    public String addComment(@ModelAttribute CommentDTO dto,Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        Date date = new Date();
        Posts post = postService.findById(dto.getPostId());
        Comments comment = new Comments();
        Users author = userService.findByEmail(principal.getName());
        if (author != null) {
            comment.setSender(author);
        } else {
            return "error/403Page";
        }
        comment.setCreatedDate(date);
        comment.setContent(dto.getContent());
        comment.setPost(post);
        commentService.save(comment);
        List<Comments> comments = post.getComments();
        comments.add(comment);
        post.setComments(comments);
        return "success";
    }

    @GetMapping("/add-post")
    public String addPost(Model model ,Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("post", new Posts());
        return "home/add-post";
    }

    @GetMapping("/edit-post/{postId}")
    public String editPost(@PathVariable Integer postId, Model model,Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        Posts post = postService.findById(postId);
        if (post != null) {
            model.addAttribute("post", post);
        } else {
            model.addAttribute("post", new Posts());
        }
        return "home/add-post";
    }

    @ResponseBody
    @GetMapping("/delete-post/{postId}")
    public String deletePost(@PathVariable Integer postId, Model model,Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        if(postService.deletePost(postId)) {
            return "The post has been successfully deleted";
        }
        return "error";
    }
}
