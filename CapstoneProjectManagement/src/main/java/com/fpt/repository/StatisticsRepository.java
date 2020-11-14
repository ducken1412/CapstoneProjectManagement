package com.fpt.repository;

import com.fpt.entity.Statistics;
import com.fpt.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StatisticsRepository extends JpaRepository<Statistics, Integer> {
    List<Statistics> findByWeek(int week);
}
