package com.fpt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.entity.Comments;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Integer>{
	public List<Comments> findByPostId(Integer id);

	@Query("select c from Comments c where c.reportDetail.id = ?1")
	List<Comments> getCommentsByReportDetatilId(Integer id);
}
