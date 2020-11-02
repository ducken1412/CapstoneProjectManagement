package com.fpt.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.entity.Posts;

@Repository
public interface PostRepository extends JpaRepository<Posts, Integer>{
	public List<Posts> findByOrderByIdDesc();

	@EntityGraph(value = "post-entity-graph-with-comment-users")
	public Page<Posts> findAll(Pageable pageable);
}
