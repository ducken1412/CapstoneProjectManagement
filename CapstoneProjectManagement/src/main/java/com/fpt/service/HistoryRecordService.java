package com.fpt.service;

import java.util.List;

import com.fpt.entity.HistoryRecords;

public interface HistoryRecordService {

	HistoryRecords findById(Integer id);

	boolean deleteHistoryRecord(Integer id);

	boolean save(HistoryRecords historyRecords);

	List<HistoryRecords> findAll();

	List<HistoryRecords> getHistoryRecordsByUserId(String id);

	HistoryRecords findHistoryByProjectId(Integer id);

	HistoryRecords findHistoryByUserId(String id);

	HistoryRecords getByReportId(Integer id);

	HistoryRecords findHistoryByUserIdCapstoneId(String id,Integer cid);

	List<HistoryRecords> getDataRoleStudent();

	List<HistoryRecords> getDataRoleHead();

	List<HistoryRecords> getDataRoleTrainingDepartment();

	List<HistoryRecords> getDataRoleLecture();

	List<HistoryRecords> getCapstoneRegister();

	List<HistoryRecords> getCapstoneApprove();

	List<HistoryRecords> getCapstoneReject();

	List<HistoryRecords> getCapstoneBookingSupervisors();

	List<HistoryRecords> getAddSupervisorsForCapstone();
}