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
	List<Integer> getIdProjectByUserID(String id);

	//
		@Query("SELECT ru.capstoneProject.id FROM CapstoneProjectDetails ru WHERE ru.user.id = ?1 and ru.status.id <> 5")
		List<Integer> getIdProjectByUserIDCheckApprove(String id);
	
	@Query("SELECT ru.user FROM CapstoneProjectDetails ru WHERE ru.capstoneProject.id = ?1 and ru.user.id = ?1")
	Integer getStatuByCapstoneProjectDetailIdAndUserId(Integer cpId, Integer userId);

	@Query("SELECT ru.user FROM CapstoneProjectDetails ru WHERE ru.capstoneProject.id = ?1 and ru.status.name = 'registed_capstone'")
	List<Users> getUserByCapstoneProjectDetailId(Integer id);


	//coutn lecturers by project id
	@Query("SELECT count (u.userRoleKey.role.id) FROM CapstoneProjectDetails ru INNER JOIN ru.user.roleUser as u WHERE u.userRoleKey.user.id = ru.user.id and u.userRoleKey.role.id = 4 and ru.capstoneProject.id = ?1")
	Integer countLecturersByCapstoneProjectId(Integer id);

	@Query("SELECT ru FROM CapstoneProjectDetails ru WHERE ru.capstoneProject.id = ?1")
	List<CapstoneProjectDetails> getUserIdByCapstoneProjectDetailId(Integer id);

	//update status user approve capstone table capstone project detail
	@Transactional
	@Modifying
	@Query("UPDATE CapstoneProjectDetails c SET c.status.id = 5, c.desAction = 'registing' WHERE c.user.id = ?1 and c.capstoneProject.id = ?2")
	Integer updateStatusUserProject(String uid, Integer pid);

	@Query("SELECT ru.user FROM CapstoneProjectDetails ru WHERE ru.user.id = ?1 AND ru.status.name = 'registed_capstone'")
	Users findUserByStatusRegisted(String id);


//	@Transactional
//	@Modifying
//	@Query("DELETE FROM CapstoneProjectDetails c WHERE c.id in (SELECT c1.id FROM CapstoneProjectDetails c1 WHERE c1.user.id = ?1) and c.status.name <> 'registed_capstone'")
//	Integer deleteCapstoneProjectDetailsByUserId(String id);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM capstone_project_details AS a WHERE a.user_id = ?1 AND a.capstone_project_id <> ?2", nativeQuery = true)
	Integer deleteCapstoneProjectDetailsByUserId(String uid, Integer cid);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM capstone_project_details AS a WHERE a.user_id = ?1 AND a.capstone_project_id = ?2", nativeQuery = true)
	Integer deleteRejectCapstoneProjectDetailsByUserId(String uid, Integer cid);

	@Query("SELECT ru.capstoneProject FROM CapstoneProjectDetails ru WHERE ru.user.id = ?1")
	CapstoneProjects findCapstoneProjectByUserId(String id);

	@Query("select c.user from CapstoneProjectDetails c where c.user.roleUser.size = 1 or c.user.roleUser.size = 2 and c.capstoneProject.id = ?1")
	List<Users> getUserStudentMemberByProjectId(Integer id);

	@Query("select c.capstoneProject.id from CapstoneProjectDetails c where c.user.id = ?1")
	Integer getOneProjectIdByUserId(String id);

	//KienBT4 add code start
	@Query(value = "SELECT distinct ru.id,ru.description_action,ru.capstone_project_id,ru.status_id,ru.user_id,de.email,de.first_name,de.gender,de.image,de.last_name,de.phone,de.user_name,ro.id as roleid,ro.name as rolename,st.name as nameStatus FROM CapstoneProject.capstone_project_details ru LEFT JOIN CapstoneProject.users de ON de.id = ru.user_id LEFT JOIN CapstoneProject.user_roles deRole ON de.id = deRole.user_id LEFT JOIN CapstoneProject.roles ro ON ro.id = deRole.role_id   LEFT JOIN CapstoneProject.status st ON st.id = ru.status_id  WHERE ru.capstone_project_id = ?1  ORDER BY ro.id DESC", nativeQuery = true)
	List<Object[]> getByProjectId(Integer id);

	@Query("SELECT ru FROM CapstoneProjectDetails ru WHERE ru.id = ?1")
	CapstoneProjectDetails getUserByCapstoneProjectDetailId1(Integer id);

	@Query("SELECT ru.user FROM CapstoneProjectDetails ru WHERE ru.id = 99")
	List<Users> getUserById(Integer id);

	@Query("SELECT ru.status FROM CapstoneProjectDetails ru WHERE ru.id = ?1")
	List<Status> getStatusById(Integer id);
	//KienBT4 add code end

}
