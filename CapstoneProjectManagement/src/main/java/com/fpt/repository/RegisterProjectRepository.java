package com.fpt.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.entity.CapstoneProjects;



@Repository
public interface RegisterProjectRepository extends JpaRepository<CapstoneProjects, Long>{
	List<CapstoneProjects> findByName(String name);

}
