package com.fpt.repository;

import java.util.Optional;

import com.fpt.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fpt.entity.Status;
import org.springframework.data.jpa.repository.Query;

public interface StatusRepository extends JpaRepository<Status, Integer>{
	Optional<Status> findById(Integer id);

	Status findByName(String name);

	Status findByUsers(Users users);

	@Query(value = "SELECT s.* FROM status AS s\n" +
			"JOIN capstone_projects AS cap ON s.id = cap.status_id\n" +
			"JOIN capstone_project_details AS capD ON cap.id = capD.capstone_project_id\n" +
			"JOIN users AS u ON u.id = capD.user_id\n" +
			"WHERE u.id = ?1",nativeQuery = true)
	Status findStatusByUserId(String userId);

	@Query(value = "SELECT s.* FROM status AS s\n" +
			"JOIN capstone_projects AS cap ON s.id = cap.status_id\n" +
			"WHERE cap.id = ?1",nativeQuery = true)
	Status findStatusByCapstoneProject(Integer id);
}
