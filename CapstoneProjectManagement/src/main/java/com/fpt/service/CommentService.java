package com.fpt.service;

import java.util.List;

import com.fpt.entity.Comments;

public interface CommentService {

	Comments findById(Integer id);

	boolean deleteComment(Integer id);

	boolean save(Comments comment);

	List<Comments> findAll();

	List<Comments> findByPostId(Integer id);

}