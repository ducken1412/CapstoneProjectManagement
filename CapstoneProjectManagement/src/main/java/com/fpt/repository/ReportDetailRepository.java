package com.fpt.repository;

import com.fpt.entity.ReportDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportDetailRepository extends JpaRepository<ReportDetails, Integer> {
    @Query("select r from ReportDetails r where r.report.id = ?1")
    ReportDetails getReportDetailByReportId(int id);

    @Query(value = "SELECT r.report_id FROM report_user AS r WHERE r.user_id = ?1 ORDER BY r.report_id DESC", nativeQuery = true)
    List<Integer> getListReportIdByUserId(String id);


}
