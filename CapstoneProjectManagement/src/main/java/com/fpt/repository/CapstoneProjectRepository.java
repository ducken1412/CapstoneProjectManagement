package com.fpt.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fpt.entity.CapstoneProjectDetails;
import com.fpt.entity.CapstoneProjects;

@Repository
public interface CapstoneProjectRepository extends JpaRepository<CapstoneProjects, Integer>{
	//list capstone project search by name
	List<CapstoneProjects> findByName(String name);
	@Query("SELECT ru.capstoneProject.name FROM CapstoneProjectDetails ru WHERE ru.user.id = ?1")
	List<String> getCapstoneProjectNameByUserId(String UserId);
	//get id capstone project
	public Optional<CapstoneProjects> findById(Integer id);
}
