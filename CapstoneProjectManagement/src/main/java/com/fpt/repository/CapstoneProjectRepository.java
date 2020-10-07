package com.fpt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.entity.CapstoneProjectDetails;
import com.fpt.entity.CapstoneProjects;
@Repository
public interface CapstoneProjectRepository extends JpaRepository<CapstoneProjects, String> {
	@Query("SELECT ru.capstoneProject.name FROM CapstoneProjectDetails ru WHERE ru.user.id = ?1")
	List<String> getCapstoneProjectNameByUserId(String UserId);
	
	
	

}
