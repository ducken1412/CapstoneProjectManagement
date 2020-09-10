package com.fa.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fa.entity.Role;
import com.fa.entity.RoleUser;
import com.fa.entity.RoleUserKey;
import com.fa.entity.UserRegister;
import com.fa.entity.Users;
import com.fa.repository.RoleUserRepository;
import com.fa.repository.UserRegisterRepository;
import com.fa.repository.UserRepository;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private UserRegisterRepository userRegisterRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleUserRepository roleUserRepo;

	public MailServiceImpl() {
	}

	@Override
	public int confirmEmail(String userId, String encryptedCode) {
		Date currentDate = new Date();
		long diff = 0;
		int result = 0;
		String userRegisterCode = "";
		try {
			UserRegister userRegister = userRegisterRepo.findById(Integer.parseInt(userId));
			Date registerDate = userRegister.getDate();
			//Get code 
			 userRegisterCode = userRegister.getCode();						
			// Compute validate time
			diff = currentDate.getTime() - registerDate.getTime();
		} catch (Exception e) {
			result = 3;
			e.printStackTrace();
		}
		try {
			String email = userRepo.findByIdAndStatus(Integer.parseInt(userId), 0).getEmail();			
			Users user = userRepo.findByEmailAndStatus(email, 1);			
			/*
			 * Check Email not active yet when use same email and register code must equal
			 * code send by email 
			 */ 
			if (user == null && userRegisterCode.equals(encryptedCode)) {				
				if (diff < 1000 * 60 * 5) {
					try {
						// Update status for user
						Users userActive = userRepo.findById(Integer.parseInt(userId));
						userActive.setStatus(1);
						userRepo.save(userActive);
						// Add role for user (Add 1 time only)
						Role role = new Role();
						// User : id = 2
						role.setId(2);
						RoleUserKey roleUserKey = new RoleUserKey(role, userActive);
						RoleUser roleUser = new RoleUser(roleUserKey);
						roleUserRepo.save(roleUser);
						result = 1;
					} catch (Exception e) {
						// Null point or parse exception
						result = 3;
						e.printStackTrace();
					}
					return result;
				} else {
					// expire time
					result = 2;
					return result;
				}
			} else {				
				// email is used already
				return result;
			}
		} catch (Exception e) {
			result = 3;
			e.printStackTrace();
			return result;
		}

	}
}
