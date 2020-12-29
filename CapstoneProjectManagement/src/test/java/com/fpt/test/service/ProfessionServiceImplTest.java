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

import com.fpt.entity.Profession;
import com.fpt.repository.ProfessionRepository;
import com.fpt.service.ProfessionService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProfessionServiceImplTest {

	@Autowired
	private ProfessionService professionService;
	@MockBean
	private ProfessionRepository professionRepository;
	@Test
	public void testfindAll() {
		List<Profession> list= new ArrayList<Profession>();
		when(professionRepository.findAll()).thenReturn(list);
		assertEquals(list, professionService.findAll());
	}

	
	@Test
	public void testfindByName() {
		String name="khang";
		Profession profession= new Profession();
		when(professionRepository.findByName(name)).thenReturn(profession);
		assertEquals(profession, professionService.findByName(name));
	}

	@Test
	public void testfindById() {
		Integer id=2;
		Profession profession= new Profession();
		profession.setId(2);
		Optional<Profession> optionalprofession = Optional.of(profession);
		when(professionRepository.findById(id)).thenReturn(optionalprofession);
		assertEquals(profession, professionService.findById(id));
	}
}
