package com.fpt.repository;

import java.util.List;

import com.fpt.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.entity.Posts;

@Repository
public interface PostRepository extends JpaRepository<Posts, Integer> {
    List<Posts> findByOrderByIdDesc();

    @EntityGraph(value = "post-entity-graph-with-comment-users")
    Page<Posts> findAll(Pageable pageable);

    @EntityGraph(value = "post-entity-graph-with-comment-users")
    Page<Posts> findByTitleContains(Pageable pageable, String title);

    @Query(value = "SELECT p.author FROM Posts p WHERE p.id = ?1")
    Users findAuthorByPostId(Integer postId);

}
