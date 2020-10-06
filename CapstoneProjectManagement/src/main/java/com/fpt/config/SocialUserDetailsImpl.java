package com.fpt.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUserDetails;


import com.fpt.entity.Users;

public class SocialUserDetailsImpl implements SocialUserDetails {

	private static final long serialVersionUID = 1L;
	private List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
	private Users appUser;

	public SocialUserDetailsImpl(Users appUser, List<String> roleNames) {
		this.appUser = appUser;

		for (String roleName : roleNames) {

			GrantedAuthority grant = new SimpleGrantedAuthority(roleName);
			this.list.add(grant);
		}
	}

	@Override
	public String getUserId() {
		return this.appUser.getId() + "";
	}

	@Override
	public String getUsername() {
		return appUser.getUsername();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return list;
	}

	@Override
	public String getPassword() {
		return appUser.getEncrytedPassword();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}