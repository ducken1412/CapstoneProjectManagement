package com.fa.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.fa.entity.Role;
import com.fa.entity.RoleUser;
import com.fa.entity.RoleUserKey;
import com.fa.entity.Users;
import com.fa.repository.RoleUserRepository;
import com.fa.service.RoleUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
class RoleUserServiceTest {
	@MockBean
	private RoleUserRepository roleUserRepository;
	
	@Autowired
	private RoleUserService roleUserServiceImpl;
	
	@Test
	public void testGetRoleNamesByUserIdSuccess() {
	  List<String> roles = new ArrayList<String>();
	  
	  when(roleUserRepository.getRoleNamesByUserId(1)).thenReturn(roles);
	  assertEquals(roles, roleUserServiceImpl.getRoleNamesByUserId(1));
	}
	
	@Test
	public void testGetRoleNamesByUserIdFail() {
		List<String> roles = null;
		when(roleUserRepository.getRoleNamesByUserId(1)).thenReturn(roles);
		assertEquals(roles, roleUserServiceImpl.getRoleNamesByUserId(1));
	}
	
	@Test
	public void testSaveRoleUserSuccess() {
		RoleUser roleUser = new RoleUser();
		when(roleUserRepository.save(roleUser)).thenReturn(roleUser);
		assertTrue(roleUserServiceImpl.saveRoleUser(roleUser));
	}
	
	@Test
	public void testSaveRoleUserFail() {
		RoleUser roleUser = null;
		when(roleUserRepository.save(roleUser)).thenThrow(IllegalArgumentException.class);
		assertFalse(roleUserServiceImpl.saveRoleUser(roleUser));
	}
	
	@Test
	public void testRemoveAllRoleOfUserByUserIdSuccess() {
		doNothing().when(roleUserRepository).removeByUserId(1);
		assertTrue(roleUserServiceImpl.removeAllRoleOfUserByUserId(1));
	}
	
	@Test
	public void testRemoveAllRoleOfUserByUserIdFail() {
		doThrow(RuntimeException.class).when(roleUserRepository).removeByUserId(1);
		assertFalse(roleUserServiceImpl.removeAllRoleOfUserByUserId(1));
	}
	
	@Test
	public void testIsExistsSuccess() {
		Users user = new Users();
		user.setUserName("user1");
		
		Role role = new Role();
		role.setName("ROLE_USER");
		
		RoleUserKey roleUserKey = new RoleUserKey(role,user);
		RoleUser roleUser = new RoleUser(roleUserKey);
		
		Optional<RoleUser> optionalRoleUser = Optional.of(roleUser);
		when(roleUserRepository.findById(roleUserKey)).thenReturn(optionalRoleUser);
		assertEquals(roleUser.getRoleUserKey().getRole().getName(), 
				roleUserServiceImpl.isExists(roleUserKey).getRoleUserKey().getRole().getName());
		assertEquals(roleUser.getRoleUserKey().getUser().getUserName(), 
				roleUserServiceImpl.isExists(roleUserKey).getRoleUserKey().getUser().getUserName());
	}
	
	@Test
	public void testIsExistsFail1() {
		Users user = new Users();
		user.setUserName("user1");
		
		Role role = new Role();
		role.setName("ROLE_USER");
		
		RoleUserKey roleUserKey = new RoleUserKey(role,user);
		
		Optional<RoleUser> optionalRoleUser = Optional.empty();
		when(roleUserRepository.findById(roleUserKey)).thenReturn(optionalRoleUser);
		assertEquals(null, roleUserServiceImpl.isExists(roleUserKey));
	}
	
	@Test
	public void testIsExistsFail2() {		
		RoleUserKey roleUserKey = null;
		
		Optional<RoleUser> optionalRoleUser = Optional.empty();
		when(roleUserRepository.findById(roleUserKey)).thenReturn(optionalRoleUser);
		assertEquals(null, roleUserServiceImpl.isExists(roleUserKey));
	}
}
