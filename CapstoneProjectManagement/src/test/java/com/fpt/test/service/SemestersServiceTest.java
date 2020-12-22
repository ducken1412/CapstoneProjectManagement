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
import com.fpt.entity.Semesters;
import com.fpt.repository.SemestersRepository;
import com.fpt.service.SemestersService;
@RunWith(SpringRunner.class)
@SpringBootTest
public class SemestersServiceTest {

	@Autowired
	private SemestersService semestersService;
	@MockBean
	private SemestersRepository semestersRepository;
	@Test
	public void testfindAll() {
		List<Semesters> list= new ArrayList<>();
		when(semestersService.findAll()).thenReturn(list);
		assertEquals(list, semestersService.findAll());
	}
	@Test
	public void testfindByName() {
		String name="khang";
		Semesters semesters= new Semesters();
		when(semestersRepository.findByName(name)).thenReturn(semesters);
		assertEquals(semesters, semestersService.findByName(name));
	}
	
	@Test
	public void testfindById() {
		Semesters semesters= new Semesters();
		semesters.setId(1);
		Integer id=1;
		Optional<Semesters> optionalsemesters = Optional.of(semesters);
		when(semestersRepository.findById(id)).thenReturn(optionalsemesters);
		assertEquals(semesters, semestersService.findById(id));
	}
	@Test
	public void testgetSemesterByUserId() {
		String userId ="2";
		Semesters semesters= new Semesters();
		when(semestersRepository.getSemesterByUserId(userId)).thenReturn(semesters);
		assertEquals(semesters, semestersService.getSemesterByUserId(userId));
	}
	@Test
    public void name() {
        List<Semesters> list = new ArrayList<>();
        when(semestersRepository.findAll()).thenReturn(list);
        assertEquals(list, semestersService.findAll());
    }
	

}
