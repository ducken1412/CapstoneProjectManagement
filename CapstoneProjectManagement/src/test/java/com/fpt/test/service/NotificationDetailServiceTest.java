package com.fpt.test.service;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fpt.entity.NotificationDetails;
import com.fpt.repository.NotificationDetailRepository;
import com.fpt.service.NotificationDetailService;
@RunWith(SpringRunner.class)
@SpringBootTest

public class NotificationDetailServiceTest {

	@MockBean
	private NotificationDetailRepository notificationDetailRepository;
	@Autowired
	private NotificationDetailService notificationDetailService;
	@Test
	public void testgetIdNotification() {
		String id="2";
		List<NotificationDetails> list= new ArrayList<NotificationDetails>();
		when(notificationDetailRepository.getIdNotification(id)).thenReturn(list);
		assertEquals(list, notificationDetailService.getIdNotification(id));
	}
	
	@Test
	public void testaddNotificationDetail() {
		NotificationDetails notificationDetails = new NotificationDetails();
		when(notificationDetailRepository.save(notificationDetails)).thenReturn(notificationDetails);
		assertEquals(true, notificationDetailService.addNotificationDetail(notificationDetails));
	}
	@Test
	public void testaddNotificationDetailFail() {
		NotificationDetails notificationDetails =null;
		when(notificationDetailRepository.save(notificationDetails)).thenThrow(NullPointerException.class);
		assertEquals(false, notificationDetailService.addNotificationDetail(notificationDetails));
	}

	@Test
	public void testgetIdNotificationByTop5() {
			String id="2";
			List<NotificationDetails> list= new ArrayList<NotificationDetails>();
			when(notificationDetailRepository.getIdNotificationByTop5(id)).thenReturn(list);
			assertEquals(list, notificationDetailService.getIdNotificationByTop5(id));
		}
	@Test
	public void testcountNotificationDetailByUserId() {
		String id="2";
		Integer integer = new Integer(2);
		when(notificationDetailRepository.countNotificationDetailByUserId(id)).thenReturn(integer);
		assertEquals(integer, notificationDetailService.countNotificationDetailByUserId(id));
		}
	@Test
	public void testsaveAllNotificationDetails() {
		 List<NotificationDetails> notificationDetails= new ArrayList<NotificationDetails>();
			List<NotificationDetails> list= new ArrayList<NotificationDetails>();
			when(notificationDetailRepository.saveAll(notificationDetails)).thenReturn(list);
			assertEquals(list, notificationDetailService.saveAllNotificationDetails(notificationDetails));
		}
}
	 

	/* void testaddNotificationDetailNativeQuery() {
		String type="2";
		Integer nid=2;
		String userId="2";
		when(notificationDetailRepository.addNotificationDetailNativeQuery(type,nid,userId));
		assertEquals(null, notificationDetailService.addNotificationDetailNativeQuery(type,nid,userId));
	

}*/
