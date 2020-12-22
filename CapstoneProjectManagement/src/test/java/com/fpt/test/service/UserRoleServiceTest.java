package com.fpt.test.service;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fpt.entity.UserRoles;
import com.fpt.repository.UserRepository;
import com.fpt.repository.UserRolesRepository;
import com.fpt.service.UserRoleService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRoleServiceTest {

	@MockBean
	private UserRepository userRepository;
	@MockBean
	private UserRolesRepository userRolesRepository;
	@Autowired
	private UserRoleService userRoleService;

	@Test
	public void testSaveUserRoleSuccess() {
		UserRoles userRole = new UserRoles();
		when(userRolesRepository.save(userRole)).thenReturn(userRole);
		assertEquals(true, userRoleService.saveRoleUser(userRole));
	}
	@Test
	public void testremoveAllRoleOfUserByUserId() {
		String userId="1";
		Mockito.doNothing().when(userRolesRepository).removeByUserId(userId);
		assertEquals(true, userRoleService.removeAllRoleOfUserByUserId(userId));
	}
	/*@Test
	void testIsExistsSuccess() {
		Users user = new Users();
		user.setUsername("user1");
		
		UserRoles role= new UserRoles();
		
		
		UserRoleKey roleUserKey = new UserRoleKey(user,user);
		RoleUser roleUser = new RoleUser(roleUserKey);
		
		Optional<RoleUser> optionalRoleUser = Optional.of(roleUser);
		when(roleUserRepository.findById(roleUserKey)).thenReturn(optionalRoleUser);
		assertEquals(roleUser.getRoleUserKey().getRole().getName(), 
				roleUserServiceImpl.isExists(roleUserKey).getRoleUserKey().getRole().getName());
		assertEquals(roleUser.getRoleUserKey().getUser().getUserName(), 
				roleUserServiceImpl.isExists(roleUserKey).getRoleUserKey().getUser().getUserName());
	}*/
	@Test
	public void testgetRoleNamesByEmail() {
		String email="khangnmse05904@fpt.edu.vn";
		List<String> list = new ArrayList<String>();
		when(userRolesRepository.getRoleNamesByEmail(email)).thenReturn(list);
		assertEquals(list, userRoleService.getRoleNamesByEmail(email));
	}
	/*@Test
	void testupdateRoleStudentReject() {
		String id="1";
		  Users role= new Users();
		Mockito.doNothing().when(userRolesRepository).updateRoleStudentReject(id);
		assertEquals(role, userRoleService.updateRoleStudentReject(id));
	}*/
	
	/*@Test
	void testupdateRoleLeader() {
		
	}*/
	
	@Test
	public void testgetRoleNamesByUserId() {
		String userId="se05904";
		List<String> list = new ArrayList<String>();
		when(userRolesRepository.getRoleNamesByUserId(userId)).thenReturn(list);
		assertEquals(list, userRoleService.getRoleNamesByUserId(userId));
	}
	
	}

