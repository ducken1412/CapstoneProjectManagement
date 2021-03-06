package com.fpt.repository;

import java.util.List;
import java.util.Optional;

import com.fpt.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.fpt.entity.CapstoneProjectDetails;
import com.fpt.entity.CapstoneProjects;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CapstoneProjectRepository extends JpaRepository<CapstoneProjects, Integer>{
	//list capstone project search by name
	List<CapstoneProjects> findByName(String name);
	@Query("SELECT ru.capstoneProject.name FROM CapstoneProjectDetails ru WHERE ru.user.id = ?1")
	List<String> getCapstoneProjectNameByUserId(String UserId);
	//get id capstone project
	public Optional<CapstoneProjects> findById(Integer id);

	//kienbt4 add code capstone start
	@Query(value = "SELECT ru.id, ru.description_action ,ru.description,ru.document,ru.name,ru.name_abbreviation," +
			"ru.name_lang_other ,ru.name_vi,ru.program,ru.specialty,ru.profession_id,ru.status_id,ru.name_changing,ru.name_vi_changing," +
			"st.name as nameStatus,p.subject_code as subjectCode FROM capstone_projects ru " +
			"LEFT JOIN status st ON st.id = ru.status_id " +
			"LEFT JOIN profession p ON p.id = ru.profession_id " +
			"WHERE (ru.status_id = ?1 OR ?1 = -1 ) " +
			"AND (ru.profession_id = ?2 OR ?2 = -1 ) " +
			"AND ru.name like ?3  " , nativeQuery = true)
	List<Object[]> getAllCap(Integer status, Integer profession, String nameSearch);

	@Query(value = "SELECT ru.id, ru.description_action ,ru.description,ru.document,ru.name,ru.name_abbreviation," +
			"ru.name_lang_other ,ru.name_vi,ru.program,ru.specialty,ru.profession_id,ru.status_id,ru.name_changing,ru.name_vi_changing," +
			"st.name as nameStatus,p.subject_code as subjectCode, count(de.id) as countDetail FROM capstone_projects ru " +
			"LEFT JOIN capstone_project_details de ON de.capstone_project_id = ru.id " +
			"LEFT JOIN status st ON st.id = ru.status_id " +
			"LEFT JOIN profession p ON p.id = ru.profession_id " +
			"WHERE (de.user_id = ?1 OR ?1 = '-1' ) " +
			"AND (ru.status_id = ?4 OR ?4 = -1 ) " +
			"AND (ru.profession_id = ?5 OR ?5 = -1 ) " +
			"AND ru.name like ?6  " +
			"group by ru.id,ru.description_action,ru.description,ru.document,ru.name,ru.name_abbreviation,ru.name_lang_other,ru.name_vi,ru.program,ru.specialty,ru.profession_id,ru.status_id,st.name LIMIT ?2,?3", nativeQuery = true)
	List<Object[]> getAll(String name, Integer PageIndex, Integer PageSize,Integer status,Integer profession,String nameSearch);

	@Query(value = "SELECT count(ru.id) as countCap FROM capstone_projects ru " +
			"LEFT JOIN capstone_project_details de ON de.capstone_project_id = ru.id " +
			"LEFT JOIN status st ON st.id = ru.status_id " +
			"LEFT JOIN profession p ON p.id = ru.profession_id " +
			"WHERE (de.user_id = ?1 OR ?1 = '-1' ) " +
			"AND (ru.status_id = ?2 OR ?2 = -1 ) " +
			"AND (ru.profession_id = ?3 OR ?3 = -1 ) " +
			"AND ru.name like ?4  " +
			"group by ru.id,ru.description_action,ru.description,ru.document,ru.name,ru.name_abbreviation,ru.name_lang_other,ru.name_vi,ru.program,ru.specialty,ru.profession_id,ru.status_id,st.name", nativeQuery = true)
	List<Integer> countCapAll(String name,Integer status,Integer profession,String nameSearch);


	@Query(value = "SELECT COUNT(capd.id) FROM `capstone_project_details` capd \n" +
			"INNER JOIN `users` u ON capd.`user_id` = u.`id` INNER JOIN `user_roles` ur ON ur.`user_id` = u.`id` WHERE capd.`capstone_project_id` = ?1 AND ur.`role_id` <> 4",nativeQuery = true)
	Integer getCountStudent(Integer id);
	//kienbt4 add code capstone end
	@Query("SELECT ru.capstoneProject FROM CapstoneProjectDetails ru WHERE ru.user.id = ?1")
	CapstoneProjects getCapstoneProjectByUserId(String userId);

	//update status project send training department
	@Transactional
	@Modifying
	@Query(value = "update capstone_projects set status_id = 5 where id = ?1", nativeQuery = true)
	void updateStatusCapstoneProjectSendTD(Integer id);

	//update status project send training department
	@Transactional
	@Modifying
	@Query(value = "update capstone_projects set status_id = 13 where id = ?1", nativeQuery = true)
	void updateStatusCapstoneProjectChangingName(Integer id);

	//delete user not approve capstone when submit to training department
	@Transactional
	@Modifying
	@Query("delete from CapstoneProjectDetails c where c.status.id = 4 and c.capstoneProject.id = ?1 and c.supType = null")
	void deleteUserNotSubmitCapstone(Integer id);

	//change name project send training department
	@Transactional
	@Modifying
	@Query("update CapstoneProjects c set c.nameChanging = ?1 , c.nameViChanging = ?2 , c.status = 13 where c.id = ?3")
	void capstoneProjectChangingName(String nameC, String nameV, Integer id);

	@Query("SELECT ru.capstoneProject FROM CapstoneProjectDetails ru WHERE ru.user.id = ?1 and ru.capstoneProject.status.name = 'registering_capstone'")
	CapstoneProjects getCapstoneProjecRegistingtByUserId(String userId);

	@Query("SELECT ru.capstoneProject FROM CapstoneProjectDetails ru WHERE ru.user.id = ?1 and ru.capstoneProject.status.name <> 'registering_capstone'")
	CapstoneProjects getCapstoneProjectRegistedByUserId(String userId);

	@Query("SELECT ru.user FROM CapstoneProjectDetails ru WHERE ru.capstoneProject.id = ?1 and ru.status.name <> 'registering_capstone'")
	List<Users> findUserByCapstoneProjectId(Integer id);

	@Query("SELECT ru.capstoneProject FROM CapstoneProjectDetails ru WHERE ru.user.id = ?1 and ru.status.name <> 'registering_capstone'")
	List<CapstoneProjects> findCapstoneProjectRegistedBySupervisorId(String userId);

	//delete user not approve capstone when submit to training department
	@Transactional
	@Modifying
	@Query("update CapstoneProjectDetails c set c.status.id = 5 where c.capstoneProject.id = ?1 and (c.supType = 'Main Lecture' or c.supType = 'Assistant Lecture')")
	void updateSupervisorsSubmitCapstone(Integer id);

}
