package com.fpt.test.service;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fpt.entity.CapstoneProjectDetails;
import com.fpt.entity.CapstoneProjects;
import com.fpt.entity.Status;
import com.fpt.entity.UserRoles;
import com.fpt.entity.Users;
import com.fpt.repository.CapstoneProjectDetailRepository;
import com.fpt.service.CapstoneProjectDetailService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CapstoneProjectDetailServiceTest {
	
	@MockBean
	private CapstoneProjectDetailRepository capstoneProjectDetailRepository;
	@Autowired
	private CapstoneProjectDetailService capstoneProjectDetailService;


	@Test
	 public void testaddCapstonprojectDetailsuccess() {
	CapstoneProjectDetails cp= new CapstoneProjectDetails();
	when(capstoneProjectDetailRepository.save(cp)).thenReturn(cp);
	assertEquals(true, capstoneProjectDetailService.addCapstonprojectDetail(cp));
}
	@Test
	public void testaddCapstonprojectDetailfail() {
		CapstoneProjectDetails capstoneProjectDetails =null;
		when(capstoneProjectDetailRepository.save(capstoneProjectDetails)).thenThrow(NullPointerException.class);
		assertEquals(false, capstoneProjectDetailService.addCapstonprojectDetail(capstoneProjectDetails));
	}
	@Test
	 public void testgetDetailByCapstoneProjectId() {
	Integer id = 2;
	List<CapstoneProjectDetails> list = new ArrayList<CapstoneProjectDetails>();
	
	when(capstoneProjectDetailRepository.getDetailByCapstoneProjectId(id)).thenReturn(list);
	assertEquals(list, capstoneProjectDetailService.getDetailByCapstoneProjectId(id));
}
	@Test
	public void testgetUserByCapstioneID() {
	Integer id = 2;
	List<CapstoneProjectDetails> list = new ArrayList<CapstoneProjectDetails>();
	
	when(capstoneProjectDetailRepository.getUserIdByCapstoneProjectDetailId(id)).thenReturn(list);
	assertEquals(list, capstoneProjectDetailService.getUserByCapstioneID(id));
}
	@Test
	public void testgetProjectIdByUserId() {
	String id = "2";
	List<Integer> list = new ArrayList<Integer>();
	when(capstoneProjectDetailRepository.getIdProjectByUserID(id)).thenReturn(list);
	assertEquals(list, capstoneProjectDetailService.getProjectIdByUserId(id));
}
	@Test
	public void testgetStatuByCapstoneProjectDetailIdAndUserIdsucess() {
		Integer integer= new Integer(1);
	when(capstoneProjectDetailRepository.getStatuByCapstoneProjectDetailIdAndUserId(1,1)).thenReturn(integer);
	Integer integeractual = capstoneProjectDetailService.getStatuByCapstoneProjectDetailIdAndUserId(1,1);
	assertEquals(integer, integeractual);
}
	@Test
	public void testgetStatuByCapstoneProjectDetailIdAndUserIdfail() {
	when(capstoneProjectDetailRepository.getStatuByCapstoneProjectDetailIdAndUserId(1,1)).thenReturn(null);
	Integer integeractual = capstoneProjectDetailService.getStatuByCapstoneProjectDetailIdAndUserId(1,1);
	assertEquals(null, integeractual);
}
	@Test
	public void testgetUserByCapstoneProjectDetailId() {
	Integer id = 2;
	List<Users> list = new ArrayList<Users>();
	when(capstoneProjectDetailRepository.getUserByCapstoneProjectDetailId(id)).thenReturn(list);
	assertEquals(list, capstoneProjectDetailService.getUserByCapstoneProjectDetailId(id));
}
	@Test
	public void testcountLecturersIdAndCapstoneProjectIdOP1() {
		Integer cid =2;
		Integer integer= 2;
	when(capstoneProjectDetailRepository.countLecturersIdAndCapstoneProjectIdOP1(cid)).thenReturn(integer);
	assertEquals(integer, capstoneProjectDetailService.countLecturersIdAndCapstoneProjectIdOP1(cid));
}
	@Test
	public void testcountLecturersIdAndCapstoneProjectIdOP2() {
		Integer cid =2;
		
		Integer integer= 2;
	when(capstoneProjectDetailRepository.countLecturersIdAndCapstoneProjectIdOP2(cid)).thenReturn(integer);
	assertEquals(integer, capstoneProjectDetailService.countLecturersIdAndCapstoneProjectIdOP2(cid));
}
	@Test
	public void testcountLecturersByProjectId() {
		Integer id =2;
		
		Integer integer= new Integer(2);
	when(capstoneProjectDetailRepository.countLecturersByCapstoneProjectId(id)).thenReturn(integer);
	assertEquals(integer, capstoneProjectDetailService.countLecturersByProjectId(id));
}
	@Test
	public void testupdateStatusUserProject() {
		Comparable c= mock(Comparable.class);
		String uid ="1";
		Integer pid =2;
		CapstoneProjectDetails cp= new CapstoneProjectDetails();
	when(c.compareTo(capstoneProjectDetailRepository.updateStatusUserProject(uid,pid)));
	assertEquals(cp, capstoneProjectDetailService.updateStatusUserProject(uid,pid));
}
	@Test
	public void testfindUserByStatusRegisted() {
		String id ="1";
		
		Users cp= new Users();
	when(capstoneProjectDetailRepository.findUserByStatusRegisted(id)).thenReturn(cp);
	assertEquals(cp, capstoneProjectDetailService.findUserByStatusRegisted(id));
}
	@Test
	public void testdeleteCapstoneProjectDetailsByUserId() {
		Comparable c= mock(Comparable.class);
		String uid ="1";
		Integer pid =2;
		CapstoneProjectDetails cp= new CapstoneProjectDetails();
	when(c.compareTo(capstoneProjectDetailRepository.deleteCapstoneProjectDetailsByUserId(uid,pid)));
	assertEquals(cp, capstoneProjectDetailService.deleteCapstoneProjectDetailsByUserId(uid,pid));
}
	@Test
	public void testdeleteRejectCapstoneProjectDetailsByUserId() {
		Comparable c= mock(Comparable.class);
		String uid ="1";
		Integer pid =2;
		CapstoneProjectDetails cp= new CapstoneProjectDetails();
	when(c.compareTo(capstoneProjectDetailRepository.deleteRejectCapstoneProjectDetailsByUserId(uid,pid)));
	assertEquals(cp, capstoneProjectDetailService.deleteRejectCapstoneProjectDetailsByUserId(uid,pid));
}
	@Test
	public void testfindCapstoneProjectByUserId() {
		
		String id ="1";
		
		CapstoneProjects cp= new CapstoneProjects();
	when(capstoneProjectDetailRepository.findCapstoneProjectByUserId(id)).thenReturn(cp);
	assertEquals(cp, capstoneProjectDetailService.findCapstoneProjectByUserId(id));
}
	@Test
	public void testgetUserStudentMemberByProjectId() {
	Integer id = 2;
	List<Users> list = new ArrayList<Users>();
	when(capstoneProjectDetailRepository.getUserStudentMemberByProjectId(id)).thenReturn(list);
	assertEquals(list, capstoneProjectDetailService.getUserStudentMemberByProjectId(id));
}
/*	@Test
	public void testgetOneProjectIdByUserId() {
		
		String id ="2";
		
		Integer cp= 2;
		when(capstoneProjectDetailRepository.getOneProjectIdByUserId(id)).thenReturn(cp);
	assertEquals(cp, capstoneProjectDetailService.getOneProjectIdByUserId(id));
}*/
	@Test
	public void testgetIdProjectByUserIDCheckApprove() {
		
	String id = "2";
	List<Integer> list = new ArrayList<Integer>();
	when(capstoneProjectDetailRepository.getIdProjectByUserIDCheckApprove(id)).thenReturn(list);
	assertEquals(list, capstoneProjectDetailService.getIdProjectByUserIDCheckApprove(id));
}
	@Test
	public void testgetUserById() {
	Integer id = 2;
	List<Users> list = new ArrayList<Users>();
	when(capstoneProjectDetailRepository.getUserById(id)).thenReturn(list);
	assertEquals(list, capstoneProjectDetailService.getUserById(id));
}
	@Test
	public void testuserLecturersIdAndCapstoneProjectIdOP1() {
	Integer id = 2;
	Users user= new Users();
	when(capstoneProjectDetailRepository.userLecturersIdAndCapstoneProjectIdOP1(id)).thenReturn(user);
	assertEquals(user, capstoneProjectDetailService.userLecturersIdAndCapstoneProjectIdOP1(id));
}
	@Test
	public void testuserLecturersIdAndCapstoneProjectIdOP2() {
	Integer id = 2;
	Users user= new Users();
	when(capstoneProjectDetailRepository.userLecturersIdAndCapstoneProjectIdOP2(id)).thenReturn(user);
	assertEquals(user, capstoneProjectDetailService.userLecturersIdAndCapstoneProjectIdOP2(id));
}
	@Test
	public void testgetStatusById() {
	Integer id = 2;
	List<Status> list = new ArrayList<Status>();
	when(capstoneProjectDetailRepository.getStatusById(id)).thenReturn(list);
	assertEquals(list, capstoneProjectDetailService.getStatusById(id));
}
	
	@Test
	public void testgetByProjectId() {
	Integer id = 2;
	List<Object[]> list = new ArrayList<Object[]>();
	when(capstoneProjectDetailRepository.getByProjectId(id)).thenReturn(list);
	assertEquals(list, capstoneProjectDetailService.getByProjectId(id));
}
	
	@Test
	public void testfindById() {
	Integer id = 2;
	CapstoneProjectDetails cp= new CapstoneProjectDetails();
	when(capstoneProjectDetailRepository.getUserByCapstoneProjectDetailId1(id)).thenReturn(cp);
	assertEquals(cp, capstoneProjectDetailService.findById(id));
}
	
	@Test
	public void testgetUserWaitingApproveByCapstoneProjectDetailId() {
	Integer id = 2;
	List<Users> list = new ArrayList<Users>();
	when(capstoneProjectDetailRepository.getUserWaitingApproveByCapstoneProjectDetailId(id)).thenReturn(list);
	assertEquals(list, capstoneProjectDetailService.getUserWaitingApproveByCapstoneProjectDetailId(id));
}
	@Test
	 public void testsavesuccess() {
	CapstoneProjectDetails cp= new CapstoneProjectDetails();
	when(capstoneProjectDetailRepository.save(cp)).thenReturn(cp);
	assertEquals(true, capstoneProjectDetailService.save(cp));
}
	@Test
	public void testsavefail() {
		CapstoneProjectDetails capstoneProjectDetails =null;
		when(capstoneProjectDetailRepository.save(capstoneProjectDetails)).thenThrow(NullPointerException.class);
		assertEquals(false, capstoneProjectDetailService.save(capstoneProjectDetails));
	}
	@Test
	public void testgetUserByCapstoneProject() {
	Integer id = 2;
	List<Users> list = new ArrayList<Users>();
	when(capstoneProjectDetailRepository.getUserByCapstoneProject(id)).thenReturn(list);
	assertEquals(list, capstoneProjectDetailService.getUserByCapstoneProject(id));
}
	
	  @Test
	  public void testlistUserRoleByProjectId() {
		Integer id = 2;
		List<UserRoles> list = new ArrayList<UserRoles>();
		when(capstoneProjectDetailRepository.listUserRoleByProjectId(id)).thenReturn(list);
		assertEquals(list, capstoneProjectDetailService.listUserRoleByProjectId(id));
	}
	   @Test
	   public void testgetUserEditByCapstoneProject() {
		Integer id = 2;
		List<Object[]> list = new ArrayList<Object[]>();
		when(capstoneProjectDetailRepository.getUserEditByCapstoneProject(id)).thenReturn(list);
		assertEquals(list, capstoneProjectDetailService.getUserEditByCapstoneProject(id));
	}
	   @Test
	   public void testfindUserByCapstoneProjectDetailId() {
		Integer id = 2;
		List<Users> list = new ArrayList<Users>();
		when(capstoneProjectDetailRepository.findUserByCapstoneProjectDetailId(id)).thenReturn(list);
		assertEquals(list, capstoneProjectDetailService.findUserByCapstoneProjectDetailId(id));
	}
}
