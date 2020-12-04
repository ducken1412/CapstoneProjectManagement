package com.fpt.service;

import com.fpt.entity.CapstoneProjects;
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
	public Page<Statistics> getStatisticsWithWeekPage(Pageable pageable, int week,Integer sites,Integer semesters,String nameSearch,String userSearch) {
		nameSearch = '%' + nameSearch + '%';
		userSearch = '%' + userSearch + '%';
		return statisticsRepository.findByWeekOrderByTimeTrackingCurrentAsc(week,sites,semesters,nameSearch,userSearch,pageable);
	}

	@Override
	public List<Statistics> getStatisticsWithWeekByLecture(int week,String email) {
		List<Statistics> statistics = statisticsRepository.findByWeekOrderByTimeTrackingCurrentAscByLecture(week,email);
		return statistics;
	}


	@Override
	public Page<Statistics> getStatisticsWithWeekPageByLecture(int week, String email, Pageable pageable,Integer sites,Integer semesters,String nameSearch,String userSearch) {
		nameSearch = '%' + nameSearch + '%';
		userSearch = '%' + userSearch + '%';
		return statisticsRepository.findByWeekPagingOrderByTimeTrackingCurrentAscByLecture(week,email,sites,semesters,nameSearch,userSearch,pageable);
	}

	@Override
	public Integer findMaxWeekByCap(Integer capId) {
		try{
			return statisticsRepository.getMaxWeekByCapstoneProject(capId);
		}catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	@Override
	public void deleteStatisticsByCapstoneProjectAndWeek(CapstoneProjects cid, Integer week) {
		statisticsRepository.deleteStatisticsByCapstoneProjectAndWeek(cid, week);
	}
}
