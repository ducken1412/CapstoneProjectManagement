package com.fpt.service;

import com.fpt.entity.Statistics;
import com.fpt.entity.Status;
import com.fpt.repository.StatisticsRepository;
import com.fpt.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

	@Override
	public List<Statistics> getStatisticsWithWeek(int week) {
		List<Statistics> statistics = statisticsRepository.findByWeekOrderByTimeTrackingCurrentAsc(week);
		return statistics;
	}

	@Override
	public Page<Statistics> getStatisticsWithWeekPage(Pageable pageable, int week) {
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		Pageable pageStatics = PageRequest.of(currentPage, pageSize);
		return statisticsRepository.findByWeekOrderByTimeTrackingCurrentAsc(pageStatics, week);
	}
}
