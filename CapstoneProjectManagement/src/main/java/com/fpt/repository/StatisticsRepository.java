package com.fpt.repository;

import com.fpt.entity.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StatisticsRepository extends JpaRepository<Statistics, Integer> {

}
