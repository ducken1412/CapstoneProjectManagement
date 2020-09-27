package com.fpt.service;

import java.util.List;

import com.fpt.entity.HistoryRecords;

public interface HistoryRecordService {

	HistoryRecords findById(Integer id);

	boolean deleteHistoryRecord(Integer id);

	boolean save(HistoryRecords historyRecords);

	List<HistoryRecords> findAll();
	

}