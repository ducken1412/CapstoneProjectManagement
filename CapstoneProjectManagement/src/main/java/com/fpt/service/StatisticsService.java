package com.fpt.service;

import com.fpt.entity.Statistics;
import com.fpt.entity.TaskDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StatisticsService {
    boolean saveStatistics(Statistics statistics);

}
