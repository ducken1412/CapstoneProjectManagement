package com.fa.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

//import org.junit.Test;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.fa.dto.UserDTO;
import com.fa.entity.Role;
import com.fa.entity.RoleUser;
import com.fa.entity.RoleUserKey;
import com.fa.entity.Users;
import com.fa.repository.RoleRepository;
import com.fa.repository.RoleUserRepository;
import com.fa.repository.UserRepository;
import com.fa.service.RoleService;
import com.fa.service.RoleUserService;
import com.fa.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {

	@MockBean
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@MockBean
	private RoleUserRepository roleUserRepository;

	@MockBean
	private RoleRepository roleRepositoty;

	@MockBean
	private UsernamePasswordAuthenticationToken principal;

	@MockBean
	RoleUserService roleUserServiceImpl;
	@MockBean
	RoleService roleServiceImpl;
	
	

	@Test
	void testFindByEmailSuccess() {
		Users user = new Users();
		when(userRepository.findByEmail("usertemp4@gmail.com")).thenReturn(user);
		Users actualUser = userService.findByEmail("usertemp4@gmail.com");
		assertEquals(user, actualUser);
	}

	@Test
	void testFindByEmailFail() {
		when(userRepository.findByEmail("usertemp4@gmail.com")).thenReturn(null);
		Users actualUser = userService.findByEmail("usertemp4@gmail.com");
		assertEquals(null, actualUser);
	}

	@Test
	void testFindByUserNameSuccess() {
		Users user = new Users();
		when(userRepository.findByUserName("usertemp4@gmail.com")).thenReturn(user);
		Users actualUser = userService.findByUserName("usertemp4@gmail.com");
		assertEquals(user, actualUser);
	}

	@Test
	void testFindByUserNameFail() {
		when(userRepository.findByUserName("usertemp4@gmail.com")).thenReturn(null);
		Users actualUser = userService.findByUserName("usertemp4@gmail.com");
		assertEquals(null, actualUser);
	}

	@Test
	void testFindByUserNameAndStatusSuccess() {
		Users user = new Users();
		when(userRepository.findByUserNameAndStatus("username", 1)).thenReturn(user);
		Users actualUser = userService.findByUserNameAndStatus("username");
		assertEquals(user, actualUser);
	}

	@Test
	void testFindByUserNameAndStatusFail() {
		when(userRepository.findByUserNameAndStatus("username", 1)).thenReturn(null);
		Users actualUser = userService.findByUserNameAndStatus("admin13");
		assertEquals(null, actualUser);
	}

	@Test
	void testFindByStatusSuccess() {
		List<Users> users = new ArrayList<>();
		users.add(new Users());
		when(userRepository.findByStatus(1)).thenReturn(users);
		List<Users> actuaList = userService.findByStatus();
		assertEquals(users, actuaList);
	}

	@Test
	void testFindByStatusFail() {
		List<Users> users = null;
		when(userRepository.findByStatus(1)).thenReturn(users);
		List<Users> actuaList = userService.findByStatus();
		assertEquals(users, actuaList);
	}

	@Test
	void testGetAllUserDTOActiveSuccess() {
		List<UserDTO> usersDTO = new ArrayList<>();
		usersDTO.add(new UserDTO());
		when(userRepository.findAllUserDTOByStatus(1)).thenReturn(usersDTO);
		List<UserDTO> actuaList = userService.getAllUserDTOActive();
		assertEquals(usersDTO, actuaList);
	}

	@Test
	void testGetAllUserDTOActiveFail() {
		List<UserDTO> users = null;
		when(userRepository.findAllUserDTOByStatus(1)).thenReturn(users);
		List<UserDTO> actuaList = userService.getAllUserDTOActive();
		assertEquals(users, actuaList);
	}

	@Test
	void testFindAll() {

	}

	@Test
	void testFindByEmailAndStatusTwoSuccess() {
		Users user = new Users();
		when(userRepository.findByUserNameAndStatus("username", 1)).thenReturn(user);
		Users actualUser = userService.findByUserNameAndStatus("username", 1);
		assertEquals(user, actualUser);
	}

	@Test
	void testFindByUserNameAndStatusTwoFail() {
		when(userRepository.findByUserNameAndStatus("username", 1)).thenReturn(null);
		Users actualUser = userService.findByUserNameAndStatus("admin13", 1);
		assertEquals(null, actualUser);
	}

	@Test
	void testFindByEmailAndStatusSuccess() {
		Users user = new Users();
		when(userRepository.findByEmailAndStatus("email@gmail.com", 1)).thenReturn(user);
		Users actualUser = userService.findByEmailAndStatus("email@gmail.com", 1);
		assertEquals(user, actualUser);
	}

	@Test
	void testFindByEmailAndStatusFail() {
		when(userRepository.findByEmailAndStatus("email@gmail.com", 1)).thenReturn(null);
		Users actualUser = userService.findByEmailAndStatus("email@gmail.com", 1);
		assertEquals(null, actualUser);
	}

	@Test
	void testSaveUserSuccess() {
		Users user = new Users();
		when(userRepository.save(user)).thenReturn(user);
		assertTrue(userService.saveUser(user));
	}

	@Test
	void testSaveUserFail() {
		Users user = null;
		when(userRepository.save(user)).thenReturn(user);
		assertFalse(userService.saveUser(user));
	}

	@Test
	void testFindByIdSuccess() {
		Users user = new Users();
		when(userRepository.getOne(2)).thenReturn(user);
		assertEquals(user, userService.findById(2));
	}

	@Test
	void testFindByIdFail() {
		when(userRepository.getOne(2)).thenReturn(null);
		assertEquals(null, userService.findById(2));
	}

	@Test
	void testEditUserSuccess() {
		String roleName = "ROLE_USER";
		Role role = new Role();

		UserDTO userDto = new UserDTO();
		userDto.setId(1);
		userDto.setFirstName("firstName");
		userDto.setLastName("last Name");
		userDto.setAddress("address");
		userDto.setPhone("phone");
		userDto.setGender(1);
		userDto.setBirthDate(new Date());

		Users user = new Users();

		RoleUserKey roleUserKey = new RoleUserKey(role, user);
		RoleUser roleUser = new RoleUser(roleUserKey);
		List<String> roles = new ArrayList<>();
		roles.add("ROLE_USER");
		roles.add("ROLE_ADMIN"); 

		when(userRepository.getOne(1)).thenReturn(user);
		when(userRepository.save(user)).thenReturn(user);
		doNothing().when(roleUserRepository).removeByUserId(1);
		when(roleRepositoty.findByName(roleName)).thenReturn(role);
		when(roleUserRepository.save(roleUser)).thenReturn(roleUser);

		assertTrue(userService.editUser(userDto, roles));
	}

	/*
	 * userDto = null
	 */
	@Test
	void testEditUserTwoFail1() {
		String roleName = "ROLE_USER";
		Role role = new Role();
		Users user = new Users();
		UserDTO userDto = null;
		RoleUserKey roleUserKey = new RoleUserKey(role, user);
		RoleUser roleUser = new RoleUser(roleUserKey);
		List<String> roles = new ArrayList<String>();

		when(userRepository.getOne(1)).thenReturn(user);
		when(userRepository.save(user)).thenReturn(user);
		doNothing().when(roleUserRepository).removeByUserId(1);
		when(roleRepositoty.findByName(roleName)).thenReturn(role);
		when(roleUserRepository.save(roleUser)).thenReturn(roleUser);

		assertFalse(userService.editUser(userDto, roles));
	}

	/*
	 * userDto.getId() = null
	 */
	@Test
	void testEditUserFail2() {
		String roleName = "ROLE_USER";
		Role role = new Role();
		Users user = new Users();
		UserDTO userDto = new UserDTO();
		userDto.setId(null);
		RoleUserKey roleUserKey = new RoleUserKey(role, user);
		RoleUser roleUser = new RoleUser(roleUserKey);
		List<String> roles = new ArrayList<String>();

		when(userRepository.getOne(1)).thenReturn(user);
		when(userRepository.save(user)).thenReturn(user);
		doNothing().when(roleUserRepository).removeByUserId(1);
		when(roleRepositoty.findByName(roleName)).thenReturn(role);
		when(roleUserRepository.save(roleUser)).thenReturn(roleUser);

		assertFalse(userService.editUser(userDto, roles));
	}

	/*
	 * roles == null
	 */
	@Test
	void testEditUserFail3() {
		String roleName = "ROLE_USER";
		Role role = new Role();
		Users user = new Users();
		UserDTO userDto = new UserDTO();
		RoleUserKey roleUserKey = new RoleUserKey(role, user);
		RoleUser roleUser = new RoleUser(roleUserKey);
		List<String> roles = null;

		when(userRepository.getOne(1)).thenReturn(user);
		when(userRepository.save(user)).thenReturn(user);
		doNothing().when(roleUserRepository).removeByUserId(1);
		when(roleRepositoty.findByName(roleName)).thenReturn(role);
		when(roleUserRepository.save(roleUser)).thenReturn(roleUser);

		assertFalse(userService.editUser(userDto, roles));
	}

	/*
	 * roles.size() == 0
	 */
	@Test
	void testEditUserFail4() {
		String roleName = "ROLE_USER";
		Role role = new Role();
		Users user = new Users();
		UserDTO userDto = new UserDTO();
		RoleUserKey roleUserKey = new RoleUserKey(role, user);
		RoleUser roleUser = new RoleUser(roleUserKey);
		List<String> roles = new ArrayList<String>();

		when(userRepository.getOne(1)).thenReturn(user);
		when(userRepository.save(user)).thenReturn(user);
		doNothing().when(roleUserRepository).removeByUserId(1);
		when(roleRepositoty.findByName(roleName)).thenReturn(role);
		when(roleUserRepository.save(roleUser)).thenReturn(roleUser);

		assertFalse(userService.editUser(userDto, roles));
	}

	@Test
	void testUnActiveUserSuccess() {
		Users user = new Users();
		when(userRepository.save(user)).thenReturn(user);
		assertTrue(userService.unActiveUser(user));
	}

	@Test
	void testUnActiveUserFail() {
		Users user = null;
		when(userRepository.save(user)).thenReturn(user);
		assertFalse(userService.unActiveUser(user));
	}

	@Test
	void testIsAdmin() {
		Users user = new Users();
		user.setUserName("user1");
		user.setEmail("email@gmail.com");

		Role role = new Role();
		role.setName("ROLE_USER");
		role.setId(1);
		RoleUserKey roleUserKey = new RoleUserKey(role, user);
		RoleUser roleUser = new RoleUser(roleUserKey);
		roleUser.setRoleUserKey(roleUserKey);
		when(roleServiceImpl.getRoleByName("ROLE_ADMIN")).thenReturn(role);
		//Optional<RoleUser> optionalRoleUser = Optional.of(roleUser);
		//when(roleUserRepository.findById(roleUserKey)).thenReturn(optionalRoleUser);
		when(roleUserServiceImpl.isExists(roleUserKey)).thenReturn(roleUser);
		System.out.println("++++++++++++++"+roleUser);
		assertFalse(userService.isAdmin(user));
	}

	@Test
	void testEditProfileSuccess() {
		UserDTO userDto = new UserDTO();
		userDto.setFirstName("firstName");
		userDto.setLastName("last Name");
		userDto.setAddress("address");
		userDto.setPhone("phone");
		userDto.setGender(1);
		userDto.setBirthDate(new Date());

		Users user = new Users();
		when(userRepository.save(user)).thenReturn(user);
		assertTrue(userService.editProfile(userDto, user));
	}

	@Test
	void testEditProfileFail1() {
		UserDTO userDto = null;
		Users user = new Users();
		when(userRepository.save(user)).thenReturn(user);
		assertFalse(userService.editProfile(userDto, user));
	}

	@Test
	void testEditProfileFail2() {
		UserDTO userDto = new UserDTO();
		userDto.setFirstName("firstName");
		userDto.setLastName("last Name");
		userDto.setAddress("address");
		userDto.setPhone("phone");
		userDto.setGender(1);
		userDto.setBirthDate(new Date());
		Users user = null;
		when(userRepository.save(user)).thenReturn(user);
		assertFalse(userService.editProfile(userDto, user));
	}

	@Test
	void testChangePasswordSuccess() throws Exception {
		String oldPassword = "123456789";
		String newPassword = "12345678";
		String confirmNewPassword = "12345678";

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Users user = new Users();
		user.setEncrytedPassword(encoder.encode(oldPassword));
		when(userRepository.save(user)).thenReturn(user);
		assertTrue(userService.changePassword(oldPassword, newPassword, confirmNewPassword, user));
	}

	// @Test(expected = RuntimeException.class)
	@Test
	void testChangePasswordFail1() throws Exception {
		String oldPassword = "123456789";
		String newPassword = "12345679";
		String confirmNewPassword = "12345678";

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Users user = new Users();
		user.setEncrytedPassword(encoder.encode(oldPassword));
		doThrow(IndexOutOfBoundsException.class).when(userRepository).save(user);
		assertThrows(IndexOutOfBoundsException.class, () -> {
			userService.changePassword(oldPassword, newPassword, confirmNewPassword, user);
		});
	}

	@Test
	void testChangePasswordFail2() throws Exception {
		String oldPassword = "123456789";
		String newPassword = "123";
		String confirmNewPassword = "123";

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Users user = new Users();
		user.setEncrytedPassword(encoder.encode(oldPassword));
		doThrow(IndexOutOfBoundsException.class).when(userRepository).save(user);
		assertThrows(IndexOutOfBoundsException.class, () -> {
			userService.changePassword(oldPassword, newPassword, confirmNewPassword, user);
		});
	}

	@Test
	void testChangePasswordFail3() throws Exception {
		String oldPassword = "12345678";
		String newPassword = "123456789";
		String confirmNewPassword = "123456789";

		Users user = new Users();
		user.setEncrytedPassword("zqwertyuiopzxcvbnm");
		doThrow(IndexOutOfBoundsException.class).when(userRepository).save(user);
		assertThrows(IndexOutOfBoundsException.class, () -> {
			userService.changePassword(oldPassword, newPassword, confirmNewPassword, user);
		});
	}

	@Test
	void testChangePasswordFail4() throws Exception {
		String oldPassword = "123";
		String newPassword = "123456789";
		String confirmNewPassword = "123456789";

		Users user = new Users();
		user.setEncrytedPassword("zqwertyuiopzxcvbnm");
		doThrow(IndexOutOfBoundsException.class).when(userRepository).save(user);
		assertThrows(IndexOutOfBoundsException.class, () -> {
			userService.changePassword(oldPassword, newPassword, confirmNewPassword, user);
		});
	}

	@Test
	void testChangePasswordFail5() throws Exception {
		String oldPassword = null;
		String newPassword = "123456789";
		String confirmNewPassword = "123456789";

		Users user = new Users();
		user.setEncrytedPassword("zqwertyuiopzxcvbnm");
		doThrow(IndexOutOfBoundsException.class).when(userRepository).save(user);
		assertThrows(IndexOutOfBoundsException.class, () -> {
			userService.changePassword(oldPassword, newPassword, confirmNewPassword, user);
		});
	}

	@Test
	void testChangePasswordFail6() throws Exception {
		String oldPassword = "123 456";
		String newPassword = "123456789";
		String confirmNewPassword = "123456789";

		Users user = new Users();
		user.setEncrytedPassword("zqwertyuiopzxcvbnm");
		doThrow(IndexOutOfBoundsException.class).when(userRepository).save(user);
		assertThrows(IndexOutOfBoundsException.class, () -> {
			userService.changePassword(oldPassword, newPassword, confirmNewPassword, user);
		});
	}

	@Test
	void testChangePasswordFail7() throws Exception {
		String oldPassword = "";
		String newPassword = "123456789";
		String confirmNewPassword = "123456789";

		Users user = new Users();
		user.setEncrytedPassword("zqwertyuiopzxcvbnm");
		doThrow(IndexOutOfBoundsException.class).when(userRepository).save(user);
		assertThrows(IndexOutOfBoundsException.class, () -> {
			userService.changePassword(oldPassword, newPassword, confirmNewPassword, user);
		});
	}

	@Test
	void testChangePasswordFail8() throws Exception {
		String oldPassword = "1234567890123456789012345678901234567";
		String newPassword = "123456789";
		String confirmNewPassword = "123456789";

		Users user = new Users();
		user.setEncrytedPassword("zqwertyuiopzxcvbnm");
		doThrow(IndexOutOfBoundsException.class).when(userRepository).save(user);
		assertThrows(IndexOutOfBoundsException.class, () -> {
			userService.changePassword(oldPassword, newPassword, confirmNewPassword, user);
		});
	}

	@Test
	void testGetUserByIdSuccess() {
		UserDTO userDto = new UserDTO();
		when(userRepository.getUser(1)).thenReturn(userDto);
		assertEquals(userDto, userService.getUser(1));
	}

	@Test
	void testGetUserByUserNameSuccess() {
		UserDTO userDto = new UserDTO();
		when(userRepository.getUser("User")).thenReturn(userDto);
		assertEquals(userDto, userService.getUser("User"));
	}

}
