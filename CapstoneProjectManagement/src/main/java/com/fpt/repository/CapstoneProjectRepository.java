package com.fpt.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

	//kienbt4 add code capstone start
	@Query(value = "SELECT ru.*,st.name as nameStatus, count(de.id) as countDetail FROM CapstoneProject.capstone_projects ru " +
			"LEFT JOIN CapstoneProject.capstone_project_details de ON de.capstone_project_id = ru.id " +
			"LEFT JOIN CapstoneProject.status st ON st.id = ru.status_id " +
			"WHERE (de.user_id = ?1 OR ?1 = '-1' ) " +
			"AND (ru.status_id = ?4 OR ?4 = -1 ) " +
			"AND (ru.profession_id = ?5 OR ?5 = -1 ) " +
			"AND ru.name like ?6  " +
			"group by ru.id,ru.description_action,ru.description,ru.document,ru.name,ru.name_abbreviation,ru.name_lang_other,ru.name_vi,ru.program,ru.specialty,ru.profession_id,ru.status_id,st.name LIMIT ?2,?3", nativeQuery = true)
	List<Object[]> getAll(String name, Integer PageIndex, Integer PageSize,Integer status,Integer profession,String nameSearch);

	@Query("SELECT count(ru.id) FROM CapstoneProjectDetails ru WHERE ru.capstoneProject.id = ?1 and ru.user.roleUser.size <> 4")
	Integer getCountStudent(Integer id);
	//kienbt4 add code capstone end

	@Query("select c.capstoneProject from CapstoneProjectDetails c where c.user.id = ?1")
	CapstoneProjects getCapstoneProjectByUserID(String id);
}
