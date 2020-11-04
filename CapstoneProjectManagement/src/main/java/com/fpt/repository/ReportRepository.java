package com.fpt.repository;

import com.fpt.entity.Reports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface ReportRepository extends JpaRepository<Reports, Integer> {

    @Query("select r from Reports r where r.id= ?1")
    Reports getReportsById(Integer id);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO report_user VALUES (?1, ?2)", nativeQuery = true)
    void addReportUserTable(Integer rid, String uid);

    Reports findAllById(Integer id);
}
