package com.fpt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.entity.Posts;
import com.fpt.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRepository postRepository;

	@Override
	public Posts findById(Integer id) {
		return postRepository.findById(id).orElse(null);
	}
	
	@Override
	public boolean deletePost(Integer id) {
		try {
			postRepository.deleteById(id);
		}catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean save(Posts post) {
		try {
			postRepository.save(post);
		}catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}
	
	@Override
	public List<Posts> findAll() {
		return postRepository.findAll();
	}
}