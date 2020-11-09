package com.fpt.service;

import java.util.List;

import com.fpt.entity.Comments;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {

	Comments findById(Integer id);

	boolean deleteComment(Integer id);

	boolean save(Comments comment);

	List<Comments> findAll();

	List<Comments> findByPostId(Integer id);

	List<Comments> getCommentsByReportDetatilId(Integer id);
}