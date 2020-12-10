package com.fpt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.entity.HistoryRecords;
import com.fpt.repository.HistoryRecordsRepository;

@Service
public class HistoryRecordServiceImpl implements HistoryRecordService {
	@Autowired
	private HistoryRecordsRepository historyRecordsRepository;

	@Override
	public HistoryRecords findById(Integer id) {
		return historyRecordsRepository.findById(id).orElse(null);
	}

	@Override
	public boolean deleteHistoryRecord(Integer id) {
		try {
			historyRecordsRepository.deleteById(id);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean save(HistoryRecords historyRecords) {
		try {
			historyRecordsRepository.save(historyRecords);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	@Override
	public List<HistoryRecords> findAll() {
		return historyRecordsRepository.findAll();
	}

	@Override
	public List<HistoryRecords> getHistoryRecordsByUserId(String id) {
		return historyRecordsRepository.getHistoryRecordsByUserId(id);
	}

	@Override
	public HistoryRecords findHistoryByProjectId(Integer id) {
		return historyRecordsRepository.findHistoryByProjectId(id);
	}

	@Override
	public HistoryRecords findHistoryByUserId(String id) {
		return historyRecordsRepository.findHistoryByUserId(id);
	}

	@Override
	public HistoryRecords getByReportId(Integer id) {
		return historyRecordsRepository.getByReportId(id);
	}

	@Override
	public HistoryRecords findHistoryByUserIdCapstoneId(String id, Integer cid) {
		return historyRecordsRepository.findHistoryByUserIdCapstoneId(id,cid);
	}

	@Override
	public List<HistoryRecords> getDataRoleStudent() {
		return historyRecordsRepository.getDataRoleStudent();
	}

	@Override
	public List<HistoryRecords> getDataRoleHead() {
		return historyRecordsRepository.getDataRoleHead();
	}

	@Override
	public List<HistoryRecords> getDataRoleTrainingDepartment() {
		return historyRecordsRepository.getDataRoleTrainingDepartment();
	}

	@Override
	public List<HistoryRecords> getDataRoleLecture() {
		return historyRecordsRepository.getDataRoleLecture();
	}

	@Override
	public List<HistoryRecords> getCapstoneRegister() {
		return historyRecordsRepository.getCapstoneRegister();
	}

	@Override
	public List<HistoryRecords> getCapstoneApprove() {
		return historyRecordsRepository.getCapstoneApprove();
	}

	@Override
	public List<HistoryRecords> getCapstoneReject() {
		return historyRecordsRepository.getCapstoneReject();
	}

	@Override
	public List<HistoryRecords> getCapstoneBookingSupervisors() {
		return historyRecordsRepository.getCapstoneBookingSupervisors();
	}

	@Override
	public List<HistoryRecords> getAddSupervisorsForCapstone() {
		return historyRecordsRepository.getAddSupervisorsForCapstone();
	}


}
