package com.fpt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.fpt.entity.CapstoneProjectDetails;
import com.fpt.entity.CapstoneProjects;
import com.fpt.entity.Users;
import com.fpt.entity.Status;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

	//coutn lecturers by project id
	@Query("SELECT count (u.userRoleKey.role.id) FROM CapstoneProjectDetails ru INNER JOIN ru.user.roleUser as u WHERE u.userRoleKey.user.id = ru.user.id and u.userRoleKey.role.id = 4 and ru.capstoneProject.id = ?1")
	Integer countLecturersByCapstoneProjectId(Integer id);

	@Query("SELECT ru FROM CapstoneProjectDetails ru WHERE ru.capstoneProject.id = ?1")
	List<CapstoneProjectDetails> getUserIdByCapstoneProjectDetailId(Integer id);

	//update status user approve capstone table capstone project detail
	@Transactional
	@Modifying
	@Query("UPDATE CapstoneProjectDetails c SET c.status.id = 4 WHERE c.user.id = ?1 and c.capstoneProject.id = ?2")
	Integer updateStatusUserProject(String uid, Integer pid);
}
