package com.fa.validator;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserValidate {
	public static boolean validatePasswordInfo(String encrytedPassword, String newPassword, String oldPassword, String confirmNewPassword) throws Exception {
		if(newPassword == null || newPassword.contains(" ") || "".equals(newPassword) || 8 > newPassword.length() || 30 < newPassword.length()) {
			throw new IndexOutOfBoundsException("New password is in wrong format.");
		}
		
		if(!newPassword.equals(confirmNewPassword)) {
			throw new IndexOutOfBoundsException("The password confirmation does not match the new password.");
		}
		
		if(oldPassword == null || oldPassword.contains(" ") || "".equals(oldPassword) || 8 > oldPassword.length() || 30 < oldPassword.length()) {
			throw new IndexOutOfBoundsException("Old password is in wrong format.");
		}
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if(!encoder.matches(oldPassword, encrytedPassword)) {
			throw new IndexOutOfBoundsException("Old password incorrect.");
		}
		return true;
	}
}
