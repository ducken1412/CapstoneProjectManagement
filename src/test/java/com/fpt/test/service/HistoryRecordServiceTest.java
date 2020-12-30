package com.fpt.test.service;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.fpt.entity.HistoryRecords;
import com.fpt.repository.HistoryRecordsRepository;
import com.fpt.service.HistoryRecordService;;
@RunWith(SpringRunner.class)
@SpringBootTest
public class HistoryRecordServiceTest {

	@Autowired
	private HistoryRecordService historyRecordService;
	@MockBean
	private  HistoryRecordsRepository historyRecordsRepository;
	@Test
	public void testfindById() {
		HistoryRecords history = new HistoryRecords();
		history.setId(1);
		Integer id=1;
		Optional<HistoryRecords> optionalhistory = Optional.of(history);
		when(historyRecordsRepository.findById(id)).thenReturn(optionalhistory);
		assertEquals(history, historyRecordService.findById(id));
	}
/*	@Test
	public void testdeleteHistoryRecordsuccess() {
		Integer id=1;
		HistoryRecords history = new HistoryRecords();
		when(historyRecordsRepository.deleteById(id)).thenReturn(history);
		assertEquals(true, historyRecordService.deleteHistoryRecord(id));
	}
	@Test
	public void testdeleteHistoryRecordfail() {
		Integer id=null;
		HistoryRecords history = new HistoryRecords();
		when(historyRecordsRepository.deleteById(id)).thenThrow(NullPointerException.class);
		assertEquals(false, notificationDetailService.addNotificationDetail(notificationDetails));
	}*/
	@Test
	public void testsavesuccess() {
		HistoryRecords history = new HistoryRecords();
		when(historyRecordsRepository.save(history)).thenReturn(history);
		assertEquals(false, historyRecordService.save(history));
	}
	@Test
	public void testsavesucfail() {
		HistoryRecords history = null;
		when(historyRecordsRepository.save(history)).thenThrow(NullPointerException.class);
		assertEquals(true, historyRecordService.save(history));
	}
	@Test
	public void testfindAll() {
		 List<HistoryRecords> list= new ArrayList<HistoryRecords>();
			when(historyRecordsRepository.findAll()).thenReturn(list);
			assertEquals(list, historyRecordService.findAll());
		}
	@Test
	public void testgetHistoryRecordsByUserId() {
		String id="2";
		 List<HistoryRecords> list= new ArrayList<HistoryRecords>();
			when(historyRecordsRepository.getHistoryRecordsByUserId(id)).thenReturn(list);
			assertEquals(list, historyRecordService.getHistoryRecordsByUserId(id));
		}
	@Test
	public void testfindHistoryByProjectId() {
		Integer id=1;
		HistoryRecords history = new HistoryRecords();
		when(historyRecordsRepository.findHistoryByProjectId(id)).thenReturn(history);
		assertEquals(history, historyRecordService.findHistoryByProjectId(id));
	}
	@Test
	public void testfindHistoryByUserId() {
		String id="2";
		HistoryRecords history = new HistoryRecords();
		when(historyRecordsRepository.findHistoryByUserId(id)).thenReturn(history);
		assertEquals(history, historyRecordService.findHistoryByUserId(id));
	}
	@Test
	public void testgetByReportId() {
		Integer id=2;
		HistoryRecords history = new HistoryRecords();
		when(historyRecordsRepository.getByReportId(id)).thenReturn(history);
		assertEquals(history, historyRecordService.getByReportId(id));
	}
	public void testfindHistoryByUserIdCapstoneId() {
		Integer cid=2;
		String id="1";
		HistoryRecords history = new HistoryRecords();
		when(historyRecordsRepository.findHistoryByUserIdCapstoneId(id,cid)).thenReturn(history);
		assertEquals(history, historyRecordService.findHistoryByUserIdCapstoneId(id,cid));
	}
	@Test
	public void testgetDataRoleStudent() {
		 List<HistoryRecords> list= new ArrayList<HistoryRecords>();
			when(historyRecordsRepository.getDataRoleStudent()).thenReturn(list);
			assertEquals(list, historyRecordService.getDataRoleStudent());
		
}
	@Test
	public void testgetDataRoleHead() {
		 List<HistoryRecords> list= new ArrayList<HistoryRecords>();
			when(historyRecordsRepository.getDataRoleHead()).thenReturn(list);
			assertEquals(list, historyRecordService.getDataRoleHead());
	
	}
	@Test
	public void testgetDataRoleTrainingDepartment() {
		 List<HistoryRecords> list= new ArrayList<HistoryRecords>();
			when(historyRecordsRepository.getDataRoleTrainingDepartment()).thenReturn(list);
			assertEquals(list, historyRecordService.getDataRoleTrainingDepartment());
	
	}
	@Test
	public void testgetCapstoneRegister() {
		 List<HistoryRecords> list= new ArrayList<HistoryRecords>();
			when(historyRecordsRepository.getCapstoneRegister()).thenReturn(list);
			assertEquals(list, historyRecordService.getCapstoneRegister());
	
	}
	@Test
	public void testgetCapstoneApprove() {
		 List<HistoryRecords> list= new ArrayList<HistoryRecords>();
			when(historyRecordsRepository.getCapstoneApprove()).thenReturn(list);
			assertEquals(list, historyRecordService.getCapstoneApprove());
	
	}
	@Test
	public void testgetCapstoneReject() {
		 List<HistoryRecords> list= new ArrayList<HistoryRecords>();
			when(historyRecordsRepository.getCapstoneReject()).thenReturn(list);
			assertEquals(list, historyRecordService.getCapstoneReject());
	
	}
	@Test
	public void testgetCapstoneBookingSupervisors() {
		 List<HistoryRecords> list= new ArrayList<HistoryRecords>();
			when(historyRecordsRepository.getCapstoneBookingSupervisors()).thenReturn(list);
			assertEquals(list, historyRecordService.getCapstoneBookingSupervisors());
	
	}
	@Test
	public void testgetAddSupervisorsForCapstone() {
		 List<HistoryRecords> list= new ArrayList<HistoryRecords>();
			when(historyRecordsRepository.getAddSupervisorsForCapstone()).thenReturn(list);
			assertEquals(list, historyRecordService.getAddSupervisorsForCapstone());
	
	}
	@Test
	public void testgetDataRoleLecture() {
		 List<HistoryRecords> list= new ArrayList<HistoryRecords>();
			when(historyRecordsRepository.getDataRoleLecture()).thenReturn(list);
			assertEquals(list, historyRecordService.getDataRoleLecture());
	
	}
}
