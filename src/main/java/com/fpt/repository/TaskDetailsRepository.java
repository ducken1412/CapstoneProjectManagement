package com.fpt.repository;

import com.fpt.entity.CapstoneProjects;
import com.fpt.entity.TaskDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TaskDetailsRepository extends JpaRepository<TaskDetails, Integer> {
    @Query(value = "SELECT MAX(r.week)  FROM task_details as r", nativeQuery = true)
    Integer getMaxWeek();

    @Query(value = "SELECT t FROM TaskDetails t WHERE t.capstoneProject.id = ?1 AND t.week = ?2")
    List<TaskDetails> findTaskDetailsByCapstoneProjectId(Integer projectId, int week);

    @Query(value = "SELECT DISTINCT(t.week) FROM TaskDetails t")
    List<Integer> findDistinctByWeek();

    @Transactional
    @Modifying
//    @Query("delete from TaskDetails t where t.capstoneProject.id = ?1 and t.week = ?2")
    void deleteTaskDetailsByCapstoneProjectAndWeek(CapstoneProjects capstoneProjects, Integer week);
}
