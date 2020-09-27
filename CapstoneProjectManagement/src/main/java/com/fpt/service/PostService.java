package com.fpt.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fpt.entity.Posts;

public interface PostService {

	Posts findById(Integer id);

	boolean deletePost(Integer id);

	boolean save(Posts post);

	List<Posts> findAll();

	Page<Posts> findPaginated(Pageable pageable);
}