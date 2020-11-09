package com.fpt.repository;

import com.fpt.entity.ReportDetails;
import com.fpt.entity.Reports;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query(value = "SELECT * FROM reports AS r , report_user AS ru, report_details AS rd WHERE rd.report_id= r.id AND r.id = ru.report_id AND ru.user_id = ?1 ORDER BY r.id DESC", nativeQuery = true)
    Page<ReportDetails> getTitlePagginByUserId(Pageable pageable, String id);

}
