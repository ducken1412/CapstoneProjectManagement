package com.fpt.test.service;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.fpt.entity.CapstoneProjectDetails;
import com.fpt.entity.NotificationDetails;
import com.fpt.entity.Semesters;
import com.fpt.entity.Users;
import com.fpt.repository.CapstoneProjectDetailRepository;
import com.fpt.repository.UserRepository;
import com.fpt.repository.UserRolesRepository;
import com.fpt.service.UserService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
	@MockBean
	private UserRepository userRepository;
	@MockBean
	private UserRolesRepository userRoleRepository;
	@Autowired
	private UserService userService;

/*	@Test
	 void testgetDetailByCapstoneProjectId() {
	Integer id = 2;
	List<Users> list = new ArrayList<>();
	Users user = new Users();
	list.add(user);
	
	when(userRepository.findAll()).thenReturn(list);
	List<Users> listResult = userRepository.getAllUser();
	assertEquals(list, listResult);
}
	*/
	@Test
	public void testfindById() {
		String id="1";
		Users user= new Users();
		user.setId("1");
		Optional<Users> optionausers = Optional.of(user);
		when(userRepository.findById(id)).thenReturn(optionausers);
		assertEquals(user, userService.findById(id));
	}
	@Test
	public void testgetAllUser() {
	
	List<Users> list = new ArrayList<Users>();
	
	
	when(userRepository.findAll()).thenReturn(list);
	
	assertEquals(list, userService.getAllUser());
}
	//error
/*	@Test
	public void testgetAllUserStudent() {
	
	List<Users> list = new ArrayList<Users>();	
	assertEquals(list, null);
}
	*/


public void testdeleteeUserSuccess() {
		String id="1";
		Mockito.doNothing().when(userRepository).deleteById(id);
		assertEquals(true, userService.deleteUser(id));
	}
	@Test
	public void testSaveUserSuccess() {
		Users user = new Users();
		when(userRepository.save(user)).thenReturn(user);
		assertEquals(true, userService.save(user));
	}
	@Test
	public void testfindAll() {
		List<Users> list = new ArrayList<Users>();
		when(userRepository.findAll()).thenReturn(list);
		assertEquals(list, userService.findAll());
	}
	
	@Test
	public void testgetUserByRoleId() {
		Integer id=2;
		List<Users> list = new ArrayList<Users>();
		when(userRoleRepository.getUserByRoleId(id)).thenReturn(list);
		assertEquals(list, userService.getUserByRoleId(id));
	}
	
	@Test
	public void testfindByUsername() {
		String username="se05904";
		List<Users> list = new ArrayList<Users>();
		when(userRepository.findByUsername(username)).thenReturn(list);
		assertEquals(list, userService.findByUsername(username));
	}

	@Test
	public void testfindByEmail() {
		String email="1";
		Users user= new Users();
		user.setEmail("1");
		Optional<Users> optionausers = Optional.of(user);
		when(userRepository.findByEmail(email)).thenReturn(optionausers);
		assertEquals(user, userService.findByEmail(email));
	}

	
	
	/*@Test
	void testfindPaginated() {
		int papageSize=1;
		int currentPage=2;
		Page <Users> user= new 
		
		when(userRepository.findByEmail(email));
		assertEquals(user, userService.findByUsername(email));
	}*/
/*	@Test
	void testfindPaginatedNotLecture2Booked() {
		
		Users user= new Users();
		when(userRepository.findByEmail(email));
		assertEquals(user, userService.findByUsername(email));
	}*/
	/*@Test
	void testfindPaginatedNotLectureBooked() {
		String email="khangnmse05904@fpt.edu.vn";
		Users user= new Users();
		when(userRepository.findByEmail(email));
		assertEquals(user, userService.findByUsername(email));
	}*/
	@Test
	public void testgetUserByUserName() {
		String id="2";
		Users user= new Users();
		when(userRepository.getUserByUserName(id)).thenReturn(user);
		assertEquals(user, userService.getUserByUserName(id));
	}

	
	
	@Test
	public void testgetUserByUserRoleAndProjectId() {
		Integer id=1;
		Integer cid=2;
		List<Users> list = new ArrayList<Users>();
		when(userRepository.getUserByUserRoleAndProjectId(id,cid)).thenReturn(list);
		assertEquals(list, userService.getUserByUserRoleAndProjectId(id,cid));
	}
}
