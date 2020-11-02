package com.fa.test.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import com.fa.entity.Users;
import com.fa.repository.RoleUserRepository;
import com.fa.repository.UserRepository;
import com.fa.service.UserDetailsServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserDetailServiceTest {
	
	@MockBean
	private UserRepository userRepository;
	
	@Autowired
	private UserDetailsServiceImpl userDetailServiceImpl;
	
	@MockBean
	private RoleUserRepository roleUserRepository;
	
	@Test
	void testLoadUserByUserName() {
		String userName = "admin";
		Users user = new Users();
		user.setId(1);
		user.setUserName(userName);
		user.setEncrytedPassword("123123123");
		
		when(userRepository.findByUserNameAndStatus(userName, user.getId())).thenReturn(user);
		
		List<String> roleNames = new ArrayList<>();
		roleNames.add("ROLE_ADMIN");
		when(roleUserRepository.getRoleNamesByUserId(user.getId())).thenReturn(roleNames);
		
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		if (roleNames != null) {
			for (String role : roleNames) {
				// ROLE_USER, ROLE_ADMIN,..
				GrantedAuthority authority = new SimpleGrantedAuthority(role);
				grantList.add(authority);
			}
		}
		
		UserDetails userDetails = (UserDetails) new User(user.getUserName(), user.getEncrytedPassword(), grantList);
		assertEquals(userDetails, userDetailServiceImpl.loadUserByUsername(userName));
	}
	
}
