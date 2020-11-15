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

    Page<Statistics> findByWeekOrderByTimeTrackingCurrentAsc(Pageable pageable,int week);

    @Query(value = "SELECT s FROM Statistics s " +
            "join CapstoneProjectDetails capd on s.capstoneProject.id = capd.capstoneProject.id " +
            "WHERE  s.week = ?1 AND  capd.user.email = ?2 order by  s.timeTrackingCurrent asc ")
    List<Statistics> findByWeekOrderByTimeTrackingCurrentAscByLecture(int week,String email);

    @Query(value = "SELECT s FROM Statistics s " +
            "join CapstoneProjectDetails capd on s.capstoneProject.id = capd.capstoneProject.id " +
            "WHERE  s.week = ?1 AND  capd.user.email = ?2 order by  s.timeTrackingCurrent asc ")
    Page<Statistics> findByWeekPagingOrderByTimeTrackingCurrentAscByLecture(int week,String email,Pageable pageable);





}
