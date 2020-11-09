package com.fa.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncrytedPasswordUtils {

	// Encryte Password with BCryptPasswordEncoder
	public static String encrytePassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}

	public static void main(String[] args) {
		System.out.println(encrytePassword("123456789"));
		String a = "123";
		String b = null;
		if(b== null || !b.equals(a)) {
			System.out.println("a != b");
		}
		
		if(!a.equals(b)) {
			System.out.println("b != a");
		}
	}

}