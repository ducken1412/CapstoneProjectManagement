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
}
