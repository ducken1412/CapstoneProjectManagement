package com.fpt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.entity.Comments;
import com.fpt.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepository commentRepository;

	@Override
	public Comments findById(Integer id) {
		return commentRepository.findById(id).orElse(null);
	}

	@Override
	public boolean deleteComment(Integer id) {
		try {
			commentRepository.deleteById(id);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean save(Comments comment) {
		try {
			commentRepository.save(comment);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	@Override
	public List<Comments> findAll() {
		return commentRepository.findAll();
	}
	
	@Override
	public List<Comments> findByPostId(Integer id) {
		return commentRepository.findByPostId(id);
	}

}