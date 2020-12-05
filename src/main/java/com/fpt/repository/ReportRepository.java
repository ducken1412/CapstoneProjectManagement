package com.fpt.repository;

import com.fpt.entity.Notifications;
import com.fpt.entity.Reports;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface ReportRepository extends JpaRepository<Reports, Integer> {



    @Transactional
    @Modifying
    @Query(value = "INSERT INTO report_user VALUES (?1, ?2)", nativeQuery = true)
    void addReportUserTable(Integer rid, String uid);

    Reports findAllById(Integer id);

    @Query(value = "SELECT * FROM reports AS r INNER JOIN report_details AS rd ON r.id = rd.report_id WHERE rd.user_id = ?1", nativeQuery = true)
    Page<Reports> findReportByUserId(Pageable pageable,String id);

    Page<Reports> findReportsByUserId(Pageable pageable,String userId);

    @Transactional
    @Modifying
    @Query(value = "update reports set title = ?1, content = ?2 where id = ?3", nativeQuery = true)
    void updateReportById(String title, String content, Integer id);


    @Query(value = "select count(*) from reports where created_by = ?1 and id = ?2", nativeQuery = true)
    Integer checkUserReportByUserIdReportId(String uid, Integer rid);

    @Query("select max(r.id) from Reports r where r.user.id = ?1")
    Integer getReportByUserIdMax(String id);
}
