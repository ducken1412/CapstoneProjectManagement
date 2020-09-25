package com.fpt.service;

import java.util.List;

import com.fpt.entity.Posts;

public interface PostService {

	Posts findById(Integer id);

	boolean deletePost(Integer id);

	boolean save(Posts post);

	List<Posts> findAll();

}