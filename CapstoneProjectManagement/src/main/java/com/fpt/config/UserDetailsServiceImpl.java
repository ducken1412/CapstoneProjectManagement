package com.fpt.config;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.fpt.entity.Roles;
import com.fpt.entity.Users;
import com.fpt.repository.UserRepository;
import com.fpt.repository.UserRolesRepository;
import com.fpt.utils.Constant;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRolesRepository userRolesRepository;


	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Users appUser = this.userRepository.findById(userName).orElse(null);

		if (appUser == null) {
			System.out.println("User not found! " + userName);
			throw new UsernameNotFoundException("User " + userName + " was not found in the database");
		}


		List<String> roleNames = this.userRolesRepository.getRoleNamesByUserId(userName);

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

		SocialUserDetailsImpl userDetails = new SocialUserDetailsImpl(appUser, roleNames);

		return userDetails;
	}

}
