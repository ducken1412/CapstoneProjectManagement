package com.fpt.test.service;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.fpt.entity.Users;
import com.fpt.repository.UserRepository;
import com.fpt.service.ListLecturersService;
@RunWith(SpringRunner.class)
@SpringBootTest

public class ListLecturersServiceTest {

	@Autowired
	private ListLecturersService listLecturersService;
	@MockBean
	private  UserRepository listlecturerRepository;
	@Test
	public void testgetAllLecturersDTOActive() {
		List<Users> list= new ArrayList<>();
		when(listlecturerRepository.findAll()).thenReturn(list);
		assertEquals(list, listLecturersService.getAllLecturersDTOActive());
	}

}
