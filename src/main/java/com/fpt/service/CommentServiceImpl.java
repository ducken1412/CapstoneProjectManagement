package com.fpt.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fpt.entity.Comments;
import com.fpt.repository.CommentRepository;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepository commentRepository;

	@Override
	@Cacheable(value="commentCache")
	public Comments findById(Integer id) {
		return commentRepository.findById(id).orElse(null);
	}

	@Override
	@CacheEvict(value = { "commentCache", "postCache" },allEntries = true) 
	public boolean deleteComment(Integer id) {
		try {
			commentRepository.deleteById(id);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	@Override
	@CacheEvict(value = { "commentCache", "postCache" },allEntries = true) 
	public boolean save(Comments comment) {
		try {
			commentRepository.save(comment);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	@Override
	@Cacheable(value="commentCache")
	public List<Comments> findAll() {
		return commentRepository.findAll();
	}
	
	@Override
	@Cacheable(value="commentCache")
	public List<Comments> findByPostId(Integer id) {
		return commentRepository.findByPostId(id);
	}

	@Override
	public List<Comments> getCommentsByReportDetatilId(Integer id) {
		return commentRepository.getCommentsByReportDetatilId(id);
	}

}