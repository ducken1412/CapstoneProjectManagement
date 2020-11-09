package com.fpt.service;

import com.fpt.entity.Statistics;
import com.fpt.entity.Status;
import com.fpt.repository.StatisticsRepository;
import com.fpt.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService{
	@Autowired
	private StatisticsRepository statisticsRepository;


	@Override
	public boolean saveStatistics(Statistics statistics) {
		try{
			statisticsRepository.save(statistics);
			return true;
		}catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}
}
