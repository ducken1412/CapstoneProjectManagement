package com.fpt.test.service;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.fpt.entity.Roles;
import com.fpt.repository.RoleRepository;
import com.fpt.service.RoleService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleServiceImplTest {

	@Autowired
	private RoleService roleService;
	@MockBean
	private RoleRepository roleRepository;
	@Test
	public void testfindByName() {
		String name="khang";
		Roles role= new Roles();
		when(roleRepository.findRolesByName(name)).thenReturn(role);
		assertEquals(role, roleService.findByName(name));
	}
}
