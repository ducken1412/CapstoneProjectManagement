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

import com.fpt.entity.Sites;
import com.fpt.repository.SitesRepository;
import com.fpt.service.SitesService;
@RunWith(SpringRunner.class)
@SpringBootTest
public class SitesServiceImplTest {

	@MockBean
	private SitesRepository sitesRepository;
	@Autowired
	private SitesService sitesService;
	@Test
	public void testfindAll() {
		List<Sites> list= new ArrayList<>();
		when(sitesRepository.findAll()).thenReturn(list);
		assertEquals(list, sitesService.findAll());
	}
	@Test
	public void testfindByName() {
		String name="khang";
		Sites site = new Sites();
		when(sitesRepository.findByName(name)).thenReturn(site);
		assertEquals(site, sitesService.findByName(name));
	}

	
	@Test
	public void testfindById() {
		Integer id=1;
		
		Sites site = new Sites();
		site.setId(1);
		Optional<Sites> optionalSites = Optional.of(site);
		when(sitesRepository.findById(id)).thenReturn(optionalSites);
		assertEquals(site, sitesService.findById(id));
	}

}
