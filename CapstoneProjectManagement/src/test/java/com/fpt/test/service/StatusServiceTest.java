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
import com.fpt.entity.Status;
import com.fpt.repository.StatusRepository;
import com.fpt.service.StatusService;
@RunWith(SpringRunner.class)
@SpringBootTest
public class StatusServiceTest {

	@MockBean
	private StatusRepository statusRepository;
	@Autowired
	private StatusService statusService;

	public void testgetStatusById() {
		int id=1;
		Status status = new Status();
		status.setId(1);
		Optional<Status> optionalStatus = Optional.of(status);
		when(statusRepository.findById(id)).thenReturn(optionalStatus);
		assertEquals(status, statusService.getStatusById(id));
	}
	@Test
	public void testfindByName() {
		String name="khang";
		Status status = new Status();
		
		when(statusRepository.findByName(name)).thenReturn(status);
		assertEquals(status, statusService.findByName(name));
	}
	
	@Test
	public void testgetAll() {
		
		List<Status> list = new ArrayList<>();
		
		when(statusRepository.findAll()).thenReturn(list);
		assertEquals(list, statusService.getAll());
	}

	
	

}
