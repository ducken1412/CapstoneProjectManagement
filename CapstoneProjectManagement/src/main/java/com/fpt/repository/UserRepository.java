package com.fpt.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.fpt.dto.UserManagementDTO;
import com.fpt.entity.Reports;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.entity.Users;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository  extends JpaRepository<Users, String>{	

	@Query(value = "SELECT \n" +
			"  u.id id, u.user_name userName, u.first_name firstName, u.last_name lastName, u.gender gender, u.phone phone, u. email, u.image image, cp.name nameCapstone, s.name site, sem.name semester, sta.name status, cp.id capstoneId, u.created_date createdDate \n" +
			"FROM\n" +
			"  users u \n" +
			"  INNER JOIN user_roles ur \n" +
			"    ON u.id = ur.user_id \n" +
			"  INNER JOIN roles r \n" +
			"    ON ur.role_id = r.id\n" +
			"  LEFT JOIN capstone_project_details cpd \n" +
			"    ON u.id = cpd.user_id\n" +
			"  LEFT JOIN capstone_projects cp\n" +
			"\tON cp.id = cpd.capstone_project_id\n" +
			"LEFT JOIN sites s\n" +
			"\tON s.id = u.site_id\n" +
			"LEFT JOIN semesters sem\n" +
			"\tON sem.id = u.semester_id\n" +
			"LEFT JOIN status sta\n" +
			"\tON sta.id = u.status_id\n" +
			"WHERE (r.name = 'student_leader' \n" +
			"  OR r.name = 'student_member') and (s.name = ?1 OR ?1 = '-1')  and (sem.name = ?2 OR ?2 = '-1')", nativeQuery = true)
	List<UserManagementDTO> getAllUserStudent(String site, String semester);
	List<Users> findByUsername(String username);
	Optional<Users> findByEmail(String email);
	
	//List<UserDTO> getAllUserStudent;
		@Query("SELECT ru.userRoleKey.user FROM UserRoles ru WHERE ru.userRoleKey.role.id = ?1")
		Page<Users> getUserByRoleId(Pageable pageable,Integer id);

	@Query("select r from Users r where r.id = ?1")
	Page<Reports> findReportByUserId(Pageable pageable,String id);

	@Query("SELECT ru.userRoleKey.user FROM UserRoles ru WHERE ru.userRoleKey.role.id = ?1 and ru.userRoleKey.user.id <> ?2 and ru.userRoleKey.user.id <> ?3")
	Page<Users> getLectureNoteBooked2ByRoleId(Pageable pageable,Integer id, String lId1, String lId2);

	@Query("SELECT ru.userRoleKey.user FROM UserRoles ru WHERE ru.userRoleKey.role.id = ?1 and ru.userRoleKey.user.id <> ?2")
	Page<Users> getLectureNoteBookedByRoleId(Pageable pageable,Integer id, String lId);

	@Query("select u from Users u where u.username =?1")
	Users getUserByUserName(String id);

	@Query(value = "select u.* \n" +
			"from users as u \n" +
			"join user_roles as ur on u.id = ur.user_id\n" +
			"join roles as r on r.id = ur.role_id\n" +
			"join capstone_project_details as cp on u.id = cp.user_id\n" +
			"join capstone_projects as c on c.id = cp.capstone_project_id\n" +
			"where r.id = ?1 and c.id =?2",nativeQuery = true)
	List<Users> getUserByUserRoleAndProjectId(Integer id, Integer cid);

	@Query(value = "select u.id id, u.user_name userName, u.first_name firstName, u.last_name lastName, u.gender gender, u.phone phone, u. email, u.image image, cp.name nameCapstone, s.name site, sem.name semester, sta.name status, cp.id capstoneId, u.created_date createdDate " +
			"from users as u\n" +
			"join user_roles as ur on u.id = ur.user_id and (ur.role_id = 2 or ur.role_id = 1)\n" +
			"join capstone_project_details as cpd on cpd.user_id = u.id \n" +
			"join capstone_projects cp on cp.id = cpd.capstone_project_id and cp.status_id = ?1\n" +
			"join sites s on s.id = u.site_id\n" +
			"join semesters sem on sem.id = u.semester_id\n" +
			"join status sta on sta.id = u.status_id", nativeQuery = true)
	List<UserManagementDTO> getUserStudentByStatusId(Integer id);

	@Query(value = "select count(*) " +
			"from users as u\n" +
			"join user_roles as ur on u.id = ur.user_id and (ur.role_id = 2 or ur.role_id = 1)\n" +
			"join capstone_project_details as cpd on cpd.user_id = u.id \n" +
			"join capstone_projects cp on cp.id = cpd.capstone_project_id and cp.status_id = ?1\n" +
			"join sites s on s.id = u.site_id\n" +
			"join semesters sem on sem.id = u.semester_id\n" +
			"join status sta on sta.id = u.status_id " +
			"where (s.name = ?2 or  ?2 = '-1') and (sem.name = ?3 OR ?3 = '-1')", nativeQuery = true)
	Integer countStudent(Integer id,String site, String semester);

	@Query(value = "SELECT \n" +
			"  u.id id, u.user_name userName, u.first_name firstName, u.last_name lastName, u.gender gender, u.phone phone, u. email, u.image image, cp.name nameCapstone, s.name site, sem.name semester, sta.name status, cp.id capstoneId, u.created_date createdDate \n" +
			"FROM\n" +
			"  users u \n" +
			"  INNER JOIN user_roles ur \n" +
			"    ON u.id = ur.user_id \n" +
			"  INNER JOIN roles r \n" +
			"    ON ur.role_id = r.id\n" +
			"  LEFT JOIN capstone_project_details cpd \n" +
			"    ON u.id = cpd.user_id\n" +
			"  LEFT JOIN capstone_projects cp\n" +
			"\tON cp.id = cpd.capstone_project_id\n" +
			"LEFT JOIN sites s\n" +
			"\tON s.id = u.site_id\n" +
			"LEFT JOIN semesters sem\n" +
			"\tON sem.id = u.semester_id\n" +
			"LEFT JOIN status sta\n" +
			"\tON sta.id = u.status_id\n" +
			"WHERE (r.name = 'student_leader' \n" +
			"  OR r.name = 'student_member') and cp.name is Null and sta.name = 'eligible_capstone' and (s.name = ?1 OR ?1 = '-1') and (sem.name = ?2 OR ?2 = '-1')", nativeQuery = true)
	List<UserManagementDTO> getAllUserStudentHasNoTeam(String site, String semester);

	@Query(value = "SELECT \n" +
			" count(*) \n" +
			"FROM\n" +
			"  users u \n" +
			"  INNER JOIN user_roles ur \n" +
			"    ON u.id = ur.user_id \n" +
			"  INNER JOIN roles r \n" +
			"    ON ur.role_id = r.id\n" +
			"  LEFT JOIN capstone_project_details cpd \n" +
			"    ON u.id = cpd.user_id\n" +
			"  LEFT JOIN capstone_projects cp\n" +
			"\tON cp.id = cpd.capstone_project_id\n" +
			"LEFT JOIN sites s\n" +
			"\tON s.id = u.site_id\n" +
			"LEFT JOIN semesters sem\n" +
			"\tON sem.id = u.semester_id\n" +
			"LEFT JOIN status sta\n" +
			"\tON sta.id = u.status_id\n" +
			"WHERE (r.name = 'student_leader' \n" +
			"  OR r.name = 'student_member') and cp.name is Null and sta.name = 'eligible_capstone' and (s.name = ?1 OR ?1 = '-1') and (sem.name = ?2 OR ?2 = '-1')", nativeQuery = true)
	Integer countStudentHasNoTeam(String site, String semester);

	@Query(value = "SELECT \n" +
			"  count(*) \n" +
			"FROM\n" +
			"  users u \n" +
			"  INNER JOIN user_roles ur \n" +
			"    ON u.id = ur.user_id \n" +
			"  INNER JOIN roles r \n" +
			"    ON ur.role_id = r.id\n" +
			"  LEFT JOIN capstone_project_details cpd \n" +
			"    ON u.id = cpd.user_id\n" +
			"  LEFT JOIN capstone_projects cp\n" +
			"\tON cp.id = cpd.capstone_project_id\n" +
			"LEFT JOIN sites s\n" +
			"\tON s.id = u.site_id\n" +
			"LEFT JOIN semesters sem\n" +
			"\tON sem.id = u.semester_id\n" +
			"LEFT JOIN status sta\n" +
			"\tON sta.id = u.status_id\n" +
			"WHERE (r.name = 'student_leader' \n" +
			"  OR r.name = 'student_member') and (s.name = ?1 OR ?1 = '-1') and (sem.name = ?2 OR ?2 = '-1')", nativeQuery = true)
	Integer countAllStudent(String site, String semester);

	@Query(value = "SELECT \n" +
			"  count(*) \n" +
			"FROM\n" +
			"  users u \n" +
			"  INNER JOIN user_roles ur \n" +
			"    ON u.id = ur.user_id \n" +
			"  INNER JOIN roles r \n" +
			"    ON ur.role_id = r.id\n" +
			"  LEFT JOIN capstone_project_details cpd \n" +
			"    ON u.id = cpd.user_id\n" +
			"  LEFT JOIN capstone_projects cp\n" +
			"\tON cp.id = cpd.capstone_project_id\n" +
			"LEFT JOIN sites s\n" +
			"\tON s.id = u.site_id\n" +
			"LEFT JOIN semesters sem\n" +
			"\tON sem.id = u.semester_id\n" +
			"LEFT JOIN status sta\n" +
			"\tON sta.id = u.status_id\n" +
			"WHERE (r.name = 'student_leader' \n" +
			"  OR r.name = 'student_member') and sta.name = 'eligible_capstone' and (s.name = ?1 OR ?1 = '-1') and (sem.name = ?2 OR ?2 = '-1')", nativeQuery = true)
	Integer countStudentEligibleCapstone(String site, String semester);

	@Transactional
	@Modifying
	@Query("update Users u set u.description = ?1, u.phone = ?2, u.address = ?3, u.image = ?4, u.birthDate = ?5 where u.id = ?6")
	void updateProfileByUserId(String des, String phone, String address, String img, Date date,String uid);

	@Transactional
	@Modifying
	@Query(value = "update users u set u.status_id = ?1 where u.id = ?2",nativeQuery = true)
	void updateStatusByUserId(int status,String uid);

	@Query(value = "SELECT COUNT(*) \n" +
			"FROM user_roles ur JOIN capstone_project_details cpd ON ur.user_id = cpd.user_id AND ur.role_id = 1 AND ur.user_id = ?1 AND cpd.capstone_project_id = ?2", nativeQuery = true)
	Integer checkCountLeader(String uId, Integer cpId);
}
