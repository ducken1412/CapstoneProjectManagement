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
public interface CapstoneProjectDetailRepository extends JpaRepository<CapstoneProjectDetails, Integer>{

	@Query("SELECT ru FROM CapstoneProjectDetails ru WHERE ru.capstoneProject.id = ?1")
	List<CapstoneProjectDetails> getDetailByCapstoneProjectId(Integer id);

	//get id project by user id
	@Query("SELECT ru.capstoneProject.id FROM CapstoneProjectDetails ru WHERE ru.user.id = ?1")
	Integer getIdProjectByUserID(String id);

	@Query("SELECT ru.user FROM CapstoneProjectDetails ru WHERE ru.capstoneProject.id = ?1 and ru.user.id = ?1")
	Integer getStatuByCapstoneProjectDetailIdAndUserId(Integer cpId, Integer userId);

	@Query("SELECT ru.user FROM CapstoneProjectDetails ru WHERE ru.capstoneProject.id = ?1")
	List<Users> getUserByCapstoneProjectDetailId(Integer id);
	
}
