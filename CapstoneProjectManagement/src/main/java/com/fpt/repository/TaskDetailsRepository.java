package com.fpt.repository;

import com.fpt.entity.TaskDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskDetailsRepository extends JpaRepository<TaskDetails, Integer> {
    @Query(value = "SELECT MAX(r.week)  FROM task_details", nativeQuery = true)
    Integer getMaxWeek();
}
