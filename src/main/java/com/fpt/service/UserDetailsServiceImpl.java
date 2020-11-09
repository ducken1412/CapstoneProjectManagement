package com.fpt.service;

import java.util.ArrayList;
import java.util.List;

import com.fpt.entity.Users;
import com.fpt.utils.Constant;
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



@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private UserService userService;

	@Autowired
	private UserRoleService userRolesService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		Users appUser = this.userService.findByEmail(username);

		if (appUser == null) {
			System.out.println("User not found! " + username);
			throw new UsernameNotFoundException("User " + username + " was not found in the database");
		}


		List<String> roleNames = this.userRolesService.getRoleNamesByEmail(username);

		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		if (roleNames != null) {
			for (String role : roleNames) {
				switch (role) {
					case Constant.ROLE_HEAD_DB:
						role = Constant.ROLE_HEAD;
						break;
					case Constant.ROLE_LECTURERS_DB:
						role = Constant.ROLE_LECTURERS;
						break;
					case Constant.ROLE_STUDENT_LEADER_DB:
						role = Constant.ROLE_STUDENT_LEADER;
						break;
					case Constant.ROLE_STUDENT_MEMBER_DB:
						role = Constant.ROLE_STUDENT_MEMBER;
						break;
					case Constant.ROLE_TRAINING_DEP_DB:
						role = Constant.ROLE_TRAINING_DEP;
						break;
					default:
						break;
				}
				System.out.println(role);
				GrantedAuthority authority = new SimpleGrantedAuthority(role);
				grantList.add(authority);
			}
		}
		UserDetails userDetail =(UserDetails) new User(username,
				"", enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, grantList);
		return userDetail;
	}

}