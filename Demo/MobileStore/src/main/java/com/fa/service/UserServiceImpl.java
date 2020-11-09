package com.fa.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fa.common.RoleConstant;
import com.fa.dto.UserDTO;
import com.fa.entity.Role;
import com.fa.entity.RoleUser;
import com.fa.entity.RoleUserKey;
import com.fa.entity.Users;
import com.fa.repository.UserRepository;
import com.fa.validator.UserValidate;

@Service
public class UserServiceImpl implements UserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleUserService roleUserServiceImpl;
	
	@Autowired
	private RoleService roleServiceImpl;
	
	@Override
	public Users findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public Users findByUserName(String username) {
		return userRepository.findByUserName(username);
	}

	@Override
	public Users findByUserNameAndStatus(String username) {
		return userRepository.findByUserNameAndStatus(username, 1);
	}

	@Override
	public List<Users> findByStatus() {
		return userRepository.findByStatus(1);
	}
	
	@Override
	public List<UserDTO> getAllUserDTOActive() {
//		return userRepository.findAll().stream().map(u -> new UserDTO(u)).collect(Collectors.toList());
		return userRepository.findAllUserDTOByStatus(1);
	}

//	@Override
//	public Page<Users> findAll(Integer page, Integer size) {
//		return userRepository.findAll(PageRequest.of(page, size));
//	}

	@Override
	public Users findByEmailAndStatus(String email, int status) {
		return userRepository.findByEmailAndStatus(email, status);
	}

	@Override
	public Users findByUserNameAndStatus(String userName, int status) {
		return userRepository.findByUserNameAndStatus(userName, status);
	}

	@Override
	public boolean saveUser(Users user) {
		if(user != null) {
			userRepository.save(user);
			return true;
		}
		return false;
	}

	@Override
	public Users findById(int id) {
		return userRepository.getOne(id);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean unActiveUser(Users user) {
		
		try {
			user.setStatus(0);
			user.setUserName("" + System.currentTimeMillis());
			user.setEmail(System.currentTimeMillis() + "@gmail.com");
			user.setUpdateTime(new Date());
			saveUser(user);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public boolean isAdmin(Users user) {
		Role role = roleServiceImpl.getRoleByName(RoleConstant.ROLE_ADMIN);
		RoleUserKey roleUserKey = new RoleUserKey(role,  user);
		RoleUser roleUser = roleUserServiceImpl.isExists(roleUserKey);				
		return roleUser != null;		
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean editProfile(UserDTO user, Users userActual){
		if(user == null || userActual == null) {
			return false;
		}
		userActual.setFirstName(user.getFirstName());
		userActual.setLastName(user.getLastName());
		userActual.setBirthDate(user.getBirthDate());
		userActual.setGender(user.getGender());
		userActual.setPhone(user.getPhone());
		userActual.setAddress(user.getAddress());
		userActual.setUpdateTime(new Date());
		return saveUser(userActual);
	}

	/* @Transactional(rollbackFor = Exception.class) */
	@Override
	public boolean changePassword(String oldPassword, String newPassword, String confirmNewPassword, Users user) throws Exception {		
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		String username;
//		if (principal instanceof UserDetails) {
//		  username = ((UserDetails)principal).getUsername();
//		} else {
//		  username = principal.toString();
//		}
//		
//		Users user = findByUserName(username);
		UserValidate.validatePasswordInfo(user.getEncrytedPassword(), newPassword, oldPassword, confirmNewPassword);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setEncrytedPassword(encoder.encode(newPassword));
		saveUser(user);
		return true;
	}

	@Override
	public UserDTO getUser(int id) {
//		try {
//			return userRepository.getUser(id);
//		}catch (Exception e) {
//			return null;
//		}
		return userRepository.getUser(id);
	}
	
	@Override
	public UserDTO getUser(String userName) {
//		try {
//			return userRepository.getUser(userName);
//		}catch (Exception e) {
//			return null;
//		}
		return userRepository.getUser(userName);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean editUser(UserDTO userDto, List<String> listRole) {
		
		try {
			if(userDto == null || userDto.getId() == null || listRole == null || listRole.size() == 0) {
				throw new Exception("User is null or user haven't any role.");
			}
			
			Users user = findById(userDto.getId());
			
			user.setFirstName(userDto.getFirstName());
			user.setLastName(userDto.getLastName());
			user.setAddress(userDto.getAddress());
			user.setPhone(userDto.getPhone());
			user.setGender(userDto.getGender());
			user.setBirthDate(userDto.getBirthDate());
			user.setCreateDate(new Date());
			saveUser(user);
			
			Role role = null;
			RoleUser roleUser = null;
			RoleUserKey roleUserKey = null;
			
			roleUserServiceImpl.removeAllRoleOfUserByUserId(user.getId());
			
			for (int i = 0; i < listRole.size(); i++) {
				role = roleServiceImpl.getRoleByName(listRole.get(i));
				roleUserKey = new RoleUserKey(role, user);
				roleUser = new RoleUser(roleUserKey);
				roleUserServiceImpl.saveRoleUser(roleUser);
			}
			return true;
		}catch (Exception e) {
			LOGGER.error("Error in function editUser", e);
			return false;
		}
	}	
}
