package com.fpt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpt.entity.CapstoneProjectDetails;
import com.fpt.entity.CapstoneProjects;
import com.fpt.entity.Users;

public interface CapstoneProjectDetailRepository extends JpaRepository<CapstoneProjectDetails, CapstoneProjects>{
	@Query("SELECT ru.CapstoneProjectDetails.user FROM CapstoneProjects ru WHERE ru.capstoneProjects.id = ?1")
	List<String> getUserByCapstoneProjectDetailId(Integer id);
	
	
}
