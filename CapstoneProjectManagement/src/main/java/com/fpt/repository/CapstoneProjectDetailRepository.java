package com.fpt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpt.entity.CapstoneProjectDetails;
import com.fpt.entity.CapstoneProjects;
import com.fpt.entity.Users;
import org.springframework.stereotype.Repository;

@Repository
public interface CapstoneProjectDetailRepository extends JpaRepository<CapstoneProjectDetails, Integer>{
	//query loi
	@Query("SELECT ru FROM CapstoneProjectDetails ru WHERE ru.capstoneProject.id = ?1")
	List<CapstoneProjectDetails> getUserByCapstoneProjectDetailId(Integer id);
	
	
}
