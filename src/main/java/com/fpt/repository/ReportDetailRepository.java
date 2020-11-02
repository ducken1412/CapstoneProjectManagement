package com.fpt.repository;

import com.fpt.entity.ReportDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportDetailRepository extends JpaRepository<ReportDetails, Integer> {
    @Query("select r from ReportDetails r where r.id = ?1")
    ReportDetails getReportDetailById(int id);
}
