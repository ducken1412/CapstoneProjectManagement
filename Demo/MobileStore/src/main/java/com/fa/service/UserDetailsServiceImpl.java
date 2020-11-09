package com.fa.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fa.entity.Users;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	private UserService userServiceImpl;
	
	@Autowired
	private RoleUserService roleUserServiceImpl;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = this.userServiceImpl.findByUserNameAndStatus(username);

		if (user == null) {
			LOGGER.info("User not found! " + username);
			throw new UsernameNotFoundException("User: " + username + " was not found in the database");
		}
		
		LOGGER.info("Found User: " + user);

		// [ROLE_USER, ROLE_ADMIN,..]
		List<String> roleNames = this.roleUserServiceImpl.getRoleNamesByUserId(user.getId());
		
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		if (roleNames != null) {
			for (String role : roleNames) {
				// ROLE_USER, ROLE_ADMIN,..
				GrantedAuthority authority = new SimpleGrantedAuthority(role);
				grantList.add(authority);
			}
		}

		UserDetails userDetails = (UserDetails) new User(user.getUserName(), //
				user.getEncrytedPassword(), grantList);

		return userDetails;
	}

}