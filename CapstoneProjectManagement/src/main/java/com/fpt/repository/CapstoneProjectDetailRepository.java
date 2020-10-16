package com.fpt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpt.entity.CapstoneProjectDetails;
import com.fpt.entity.CapstoneProjects;
import com.fpt.entity.Users;
import com.fpt.entity.Status;

import org.springframework.stereotype.Repository;

@Repository
public interface CapstoneProjectDetailRepository extends JpaRepository<CapstoneProjectDetails, CapstoneProjects>{
	//query loi
	@Query("SELECT ru.user FROM CapstoneProjectDetails ru WHERE ru.capstoneProject.id = ?1")
	List<Users> getUserByCapstoneProjectDetailId(Integer id);
//	@Query("SELECT ru.status FROM CapstoneProjectDetails ru WHERE ru.capstoneProject.id = ?1")
//	List<Status> getStatusByCapStoneProjrectDeatailId(Integer id);
	
	
}
