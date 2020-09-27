package com.fpt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.entity.Posts;

@Repository
public interface PostRepository extends JpaRepository<Posts, Integer>{
	public List<Posts> findByOrderByIdDesc();
}
