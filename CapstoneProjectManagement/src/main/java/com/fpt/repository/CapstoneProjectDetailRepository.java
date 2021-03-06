package com.fpt.repository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.fpt.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
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
		@Query("SELECT ru.capstoneProject.id FROM CapstoneProjectDetails ru WHERE ru.user.id = ?1 and ru.status.id = 4")
		List<Integer> getIdProjectByUserIDCheckApprove(String id);
	
	@Query("SELECT ru.user FROM CapstoneProjectDetails ru WHERE ru.capstoneProject.id = ?1 and ru.user.id = ?1")
	Integer getStatuByCapstoneProjectDetailIdAndUserId(Integer cpId, Integer userId);

	@Query("SELECT ru.user FROM CapstoneProjectDetails ru WHERE ru.capstoneProject.id = ?1 and ru.status.name = 'registed_capstone'")
	List<Users> getUserByCapstoneProjectDetailId(Integer id);

	@Query("SELECT ru.user FROM CapstoneProjectDetails ru WHERE ru.capstoneProject.id = ?1 and ru.status.name = 'registering_capstone'")
	List<Users> getUserWaitingApproveByCapstoneProjectDetailId(Integer id);

	@Query("SELECT ru.user FROM CapstoneProjectDetails ru WHERE ru.capstoneProject.id = ?1 and ru.status.name = 'doing_capstone' or ru.status.name = 'changing_name_capstone'")
	List<Users> getUserByCapstoneProject(Integer id);

	//count lecturers by project id
	@Query("SELECT count (u.userRoleKey.role.id) FROM CapstoneProjectDetails ru INNER JOIN ru.user.roleUser as u WHERE u.userRoleKey.user.id = ru.user.id and u.userRoleKey.role.id = 4 and ru.capstoneProject.id = ?1")
	Integer countLecturersByCapstoneProjectId(Integer id);

	//count lecture option 1
	@Query("SELECT count (c.user.id) from CapstoneProjectDetails c where c.capstoneProject.id = ?1 and c.supType = 'Main Lecture'")
	Integer countLecturersIdAndCapstoneProjectIdOP1(Integer cid);

	//count lecture option 2
	@Query("SELECT count (c.user.id) from CapstoneProjectDetails c where c.capstoneProject.id = ?1 and c.supType = 'Assistant Lecture'")
	Integer countLecturersIdAndCapstoneProjectIdOP2(Integer cid);

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

	@Query("select c.user from CapstoneProjectDetails c where c.capstoneProject.id = ?1")
	List<Users> getUserStudentMemberByProjectId(Integer id);

	@Query("select c.capstoneProject.id from CapstoneProjectDetails c where c.user.id = ?1")
	Integer getOneProjectIdByUserId(String id);

	//KienBT4 add code start
	@Query(value = "SELECT distinct ru.id,ru.description_action,ru.capstone_project_id,ru.status_id,ru.user_id,de.email,de.first_name,de.gender,de.image,de.last_name,de.phone,de.user_name,ro.id as roleid,ro.name as rolename,st.name as nameStatus FROM capstone_project_details ru LEFT JOIN users de ON de.id = ru.user_id LEFT JOIN user_roles deRole ON de.id = deRole.user_id LEFT JOIN roles ro ON ro.id = deRole.role_id   LEFT JOIN status st ON st.id = ru.status_id  WHERE ru.capstone_project_id = ?1  ORDER BY ro.id DESC", nativeQuery = true)
	List<Object[]> getByProjectId(Integer id);

	@Query("SELECT ru FROM CapstoneProjectDetails ru WHERE ru.id = ?1")
	CapstoneProjectDetails getUserByCapstoneProjectDetailId1(Integer id);

	@Query("SELECT ru.user FROM CapstoneProjectDetails ru WHERE ru.id = ?1")
	List<Users> getUserById(Integer id);

	@Query("SELECT ru.status FROM CapstoneProjectDetails ru WHERE ru.id = ?1")
	List<Status> getStatusById(Integer id);
	//KienBT4 add code end

	//lecture option 1
	@Query("SELECT c.user from CapstoneProjectDetails c where c.capstoneProject.id = ?1 and c.supType = 'Main Lecture'")
	Users userLecturersIdAndCapstoneProjectIdOP1(Integer cid);

	//lecture option 2
	@Query("SELECT c.user from CapstoneProjectDetails c where c.capstoneProject.id = ?1 and c.supType = 'Assistant Lecture'")
	Users userLecturersIdAndCapstoneProjectIdOP2(Integer cid);

	@Query(value = "select u.user_id, u.role_id from capstone_project_details as c inner join user_roles as u where " +
			"(c.user_id = u.user_id and u.role_id = 1) or (c.user_id = u.user_id and u.role_id = 2) and c.capstone_project_id = ?1", nativeQuery = true)
	List<UserRoles> listUserRoleByProjectId(Integer id);

	@Query(value = "select u.user_name, r.name as role, s.name as status from users as u \n" +
			"join capstone_project_details as c on c.user_id = u.id \n" +
			"join user_roles as us on u.id = us.user_id\n" +
			"join roles as r on r.id = us.role_id\n" +
			"join status as s on s.id = c.status_id\n" +
			"where c.capstone_project_id = ?1 and c.user_id = us.user_id and c.user_id = u.id and (us.role_id = 2 or us.role_id = 1)", nativeQuery = true)
	List<Object[]> getUserEditByCapstoneProject(Integer id);

	@Query("select c from CapstoneProjectDetails c where c.user.username = ?1 and c.capstoneProject.id = ?2")
	CapstoneProjectDetails checkUserCapstoneDetail(String username, Integer id);


	@Transactional
	@Modifying
	/*@Query(value = "DELETE FROM capstone_project_details AS a " +
			"join users as u on a.user_id = u.id  " +
			"WHERE u.user_name not in (:listIdMem) ", nativeQuery = true)*/
	void deleteCapstoneProjectDetailsByUserNotIn(Collection<Users> users);

	@Query("SELECT ru.user FROM CapstoneProjectDetails ru WHERE ru.capstoneProject.id = ?1 and ru.status.name <> 'registering_capstone'")
	List<Users> findUserByCapstoneProjectDetailId(Integer id);
}
