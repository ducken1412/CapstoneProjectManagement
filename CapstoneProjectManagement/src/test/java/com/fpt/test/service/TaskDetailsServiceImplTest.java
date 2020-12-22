package com.fpt.test.service;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.fpt.entity.CapstoneProjects;
import com.fpt.entity.TaskDetails;
import com.fpt.entity.UserRoles;
import com.fpt.repository.TaskDetailsRepository;
import com.fpt.repository.UserRepository;
import com.fpt.service.TaskDetailsService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskDetailsServiceImplTest {

	@MockBean
	private TaskDetailsRepository taskDetailsRepository;
	@Autowired
	private TaskDetailsService taskDetailsService;
	
	@Test
	public void testsaveTaskDetails() {
		List <TaskDetails> taskDetails = new ArrayList<TaskDetails>();
		when(taskDetailsRepository.saveAll(taskDetails)).thenReturn(taskDetails);
		assertEquals(true, taskDetailsService.saveTaskDetails(taskDetails));
	}
	/*@Test
	void testfindMaxWeek() {
		
		when(taskDetailsRepository.getMaxWeek(id)).thenReturn(taskDetails);
		assertEquals(true, taskDetailsService.findMaxWeek(findMaxWeek));
	}*/
	@Test
	public void testfindTaskDetailsByCapstoneProjectId() {
		List <TaskDetails> taskDetails = new ArrayList<TaskDetails>();
		Integer projectId=1;
		int week=2;
		when(taskDetailsRepository.findTaskDetailsByCapstoneProjectId(projectId,week)).thenReturn(taskDetails);
		assertEquals(taskDetails, taskDetailsService.findTaskDetailsByCapstoneProjectId(projectId,week));
	}
	
	 @Test
	 public void testfindDistinctByWeek() {
			List <Integer> list = new ArrayList<Integer>();
			
			when(taskDetailsRepository.findDistinctByWeek()).thenReturn(list);
			assertEquals(list, taskDetailsService.findDistinctByWeek());
		}
	/* @Test
		void testdeleteTaskDetailsByCapstoneProjectAndWeek() {
			Integer week=1;
			CapstoneProjects cid= new CapstoneProjects();
			Mockito.doNothing().when(taskDetailsRepository).deleteTaskDetailsByCapstoneProjectAndWeek(cid,week);
			assertEquals(true, taskDetailsService.deleteTaskDetailsByCapstoneProjectAndWeek(cid,week));
		}
	 */
}
