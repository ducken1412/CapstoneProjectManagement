package com.fa.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.fa.entity.Role;
import com.fa.repository.ProductRepository;
import com.fa.repository.RoleRepository;
import com.fa.service.RoleService;

@RunWith(SpringRunner.class)
@SpringBootTest
class RoleServiceTest {
	
	@MockBean
	private RoleRepository roleRepository;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	ProductRepository productRepository;
	
	@Test
	void testSaveRoleSuccess(){
		Role role = new Role();
		when(roleRepository.save(role)).thenReturn(role);
		assertTrue(roleService.saveRole(role));
	}
	
	@Test
	void testSaveRoleFail(){
		Role role = null;
		doThrow(RuntimeException.class).when(roleRepository).save(role);
		assertFalse(roleService.saveRole(role));
	}
	
	@Test
	void testGetAllSuccess(){
		List<Role> roles = new ArrayList<>();
		roles.add(new Role(1, "ROLE_USER"));
		when(roleRepository.findAll()).thenReturn(roles);
		assertEquals(roles, roleService.getAll());
	}
	
	@Test
	void testGetAllFail(){
		List<Role> roles = new ArrayList<>();
		when(roleRepository.findAll()).thenReturn(roles);
		assertEquals(0, roleService.getAll().size());
	}
	
	@Test
	void testGetRoleByNameSuccess(){
		String roleName = "ROLE_USER";
		Role role = new Role(1, roleName);
		when(roleRepository.findByName(roleName)).thenReturn(role);
		assertEquals(role, roleService.getRoleByName(roleName));
	}
	
	@Test
	void testGetRoleByNameFail(){
		String roleName = null;
		Role role = null;
		when(roleRepository.findByName(roleName)).thenReturn(null);
		assertEquals(role, roleService.getRoleByName(roleName));
	}
}
