package com.fpt.repository;

import com.fpt.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fpt.entity.HistoryRecords;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistoryRecordsRepository extends JpaRepository<HistoryRecords, Integer>{

    @Query("SELECT h FROM HistoryRecords h WHERE h.user.id = ?1")
    List<HistoryRecords> getHistoryRecordsByUserId(String id);

    @Query("SELECT h FROM HistoryRecords h WHERE h.capstoneProject.id = ?1 and h.content = 'Register Capstone'")
    HistoryRecords findHistoryByProjectId(Integer id);

    @Query("SELECT h FROM HistoryRecords h WHERE h.user.id = ?1 and h.content = 'Register Capstone'")
    HistoryRecords findHistoryByUserId(String id);

    @Query("select h from HistoryRecords h where h.report.id = ?1 and h.content ='Post report'")
    HistoryRecords getByReportId(Integer id);

    @Query("SELECT h FROM HistoryRecords h WHERE h.user.id = ?1 and h.content = 'Register Capstone' and h.capstoneProject.id = ?2")
    HistoryRecords findHistoryByUserIdCapstoneId(String id,Integer cid);

//    @Query("select h from HistoryRecords h where h.content = 'Register Capstone' or h.content = 'Booking Lecture' or  h.content = 'Reject Project' or  h.content = 'Approve Project'")
//    List<HistoryRecords> getDataRoleStudent();

    @Query(value = "select h.id, h.content, h.created_date, h.last_modified_date, h.capstone_project_id, h.evaluation_id, h.notification_id, h.post_id, h.report_id, h.user_id from history_records as h inner join user_roles as u where (h.user_id = u.user_id and u.role_id = 2 or h.user_id = u.user_id and u.role_id = 1);",nativeQuery = true)
    List<HistoryRecords> getDataRoleStudent();

    @Query(value = "select h.id, h.content, h.created_date, h.last_modified_date, h.capstone_project_id, h.evaluation_id, h.notification_id, h.post_id, h.report_id, h.user_id from history_records as h inner join user_roles as u where h.user_id = u.user_id and u.role_id = 5;",nativeQuery = true)
    List<HistoryRecords> getDataRoleTrainingDepartment();

    @Query(value = "select h.id, h.content, h.created_date, h.last_modified_date, h.capstone_project_id, h.evaluation_id, h.notification_id, h.post_id, h.report_id, h.user_id from history_records as h inner join user_roles as u where h.user_id = u.user_id and u.role_id = 3;",nativeQuery = true)
    List<HistoryRecords> getDataRoleHead();

    @Query(value = "select h.id, h.content, h.created_date, h.last_modified_date, h.capstone_project_id, h.evaluation_id, h.notification_id, h.post_id, h.report_id, h.user_id from history_records as h inner join user_roles as u where h.user_id = u.user_id and u.role_id = 4;",nativeQuery = true)
    List<HistoryRecords> getDataRoleLecture();
}
