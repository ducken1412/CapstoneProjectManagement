package com.fpt.repository;

import com.fpt.entity.Statistics;
import com.fpt.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface StatisticsRepository extends JpaRepository<Statistics, Integer> {

    List<Statistics> findByWeekOrderByTimeTrackingCurrentAsc(int week);

    @Query(value = "SELECT DISTINCT  s FROM Statistics s " +
            "join CapstoneProjects cap on s.capstoneProject.id = cap.id " +
            "join CapstoneProjectDetails capd on cap.id = capd.capstoneProject.id " +
            "WHERE  s.week = ?1 " +
            "AND (s.capstoneProject.site.id = ?2 OR ?2 = -1)" +
            "AND (s.capstoneProject.semester.id = ?3 OR ?3 = -1)" +
            "AND (s.capstoneProject.name like ?4)" +
            "AND (capd.user.username like ?5)" +
            "order by  s.timeTrackingCurrent asc ")
    Page<Statistics> findByWeekOrderByTimeTrackingCurrentAsc(int week,Integer sites,Integer semesters,String nameSearch,String userSearch,Pageable pageable);

    @Query(value = "SELECT s FROM Statistics s " +
            "join CapstoneProjectDetails capd on s.capstoneProject.id = capd.capstoneProject.id " +
            "WHERE  s.week = ?1 AND  capd.user.email = ?2 order by  s.timeTrackingCurrent asc ")
    List<Statistics> findByWeekOrderByTimeTrackingCurrentAscByLecture(int week,String email);

    @Query(value = "SELECT DISTINCT  s FROM Statistics s " +
            "join CapstoneProjects cap on s.capstoneProject.id = cap.id " +
            "join CapstoneProjectDetails capd on cap.id = capd.capstoneProject.id " +
            "WHERE  s.week = ?1 AND  capd.user.email = ?2 " +
            "AND (s.capstoneProject.site.id = ?3 OR ?3 = -1)" +
            "AND (s.capstoneProject.semester.id = ?4 OR ?4 = -1)" +
            "AND (s.capstoneProject.name like ?5)" +
            "AND (capd.user.username like ?6)" +
            "order by  s.timeTrackingCurrent asc ")
    Page<Statistics> findByWeekPagingOrderByTimeTrackingCurrentAscByLecture(int week,String email,Integer sites,Integer semesters,String nameSearch,String userSearch,Pageable pageable);





}
