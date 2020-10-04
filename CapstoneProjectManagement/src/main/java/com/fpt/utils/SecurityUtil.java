package com.fpt.utils;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.security.SocialUserDetails;

import com.fpt.entity.Users;
import com.fpt.config.*;

public class SecurityUtil {

	public static void logInUser(Users user, List<String> roleNames) {
		SocialUserDetails userDetails = new SocialUserDetailsImpl(user, roleNames);
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
