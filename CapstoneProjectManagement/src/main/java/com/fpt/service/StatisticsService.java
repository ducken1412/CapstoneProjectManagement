package com.fpt.service;

import com.fpt.entity.CapstoneProjects;
import com.fpt.entity.Statistics;
import com.fpt.entity.TaskDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StatisticsService {
    boolean saveStatistics(Statistics statistics);

    List<Statistics> getStatisticsWithWeek(int week);

    Page<Statistics> getStatisticsWithWeekPage(Pageable pageable,  int week,Integer sites,Integer semesters,String nameSearch,String userSearch);

    List<Statistics> getStatisticsWithWeekByLecture(int week,String email);

    Page<Statistics> getStatisticsWithWeekPageByLecture(int week,String email,Pageable pageable,Integer sites,Integer semesters,String nameSearch,String userSearch);

    Integer findMaxWeekByCap(Integer capId);

    void deleteStatisticsByCapstoneProjectAndWeek(CapstoneProjects cid, Integer week);
}
