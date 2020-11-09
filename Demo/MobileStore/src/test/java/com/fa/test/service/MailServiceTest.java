package com.fa.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import com.fa.dto.ProductDTO;
import com.fa.entity.Orders;
import com.fa.entity.UserRegister;
import com.fa.entity.Users;
import com.fa.repository.RoleRepository;
import com.fa.repository.RoleUserRepository;
import com.fa.repository.UserRegisterRepository;
import com.fa.repository.UserRepository;
import com.fa.service.MailService;
import com.fa.utils.SendEmailUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {

	@Autowired
	private SendEmailUtils sendMailUtil;

	@Autowired
	private MailService mailService;
	@MockBean
	private UserRegisterRepository userRegisterRepo;
	@MockBean
	private UserRepository userRepo;
	@MockBean
	private RoleRepository roleRepo;
	@MockBean
	private RoleUserRepository roleUserRepo;
	@MockBean
	Orders orders;
	@MockBean
	MimeMessage message;
	@MockBean
	MimeMessageHelper helper;

	private UserRegister userRegister;

	private Users user;
	@MockBean
	private JavaMailSender emailSender;


	@Test
	void testComfirmEmailExist() {
		Date currentDate = new Date();
		UserRegister userRegister = new UserRegister(1, "code", currentDate);
		when(userRegisterRepo.findById(1)).thenReturn(userRegister);
		user = new Users();
		String email = "existedEmail@gmail.com";
		when(userRepo.findByIdAndStatus(1, 0)).thenReturn(user);
		when(userRepo.findByEmailAndStatus(email, 1)).thenReturn(user);
		int result = mailService.confirmEmail("1", "error-code");
		assertEquals(0, result);
	}


	@Test
	void testConfirmEmailExpire() throws ParseException {
		String userRegisterDateStr = "02-Dec-2019 23:37:50";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

		Date date = sdf.parse(userRegisterDateStr);
		userRegister = new UserRegister(1, "code", date);
		when(userRegisterRepo.findById(1)).thenReturn(userRegister);
		user = new Users();
		when(userRepo.findByIdAndStatus(1, 0)).thenReturn(user);
		when(userRepo.findByEmailAndStatus("existedEmail@gmail.com", 1)).thenReturn(user);
		int result = mailService.confirmEmail("1", "code");
		assertEquals(2, result);
	}

	@Test
	void testComfirmEmailSuccess() {
		Date currentDate = new Date();
		userRegister = new UserRegister(1, "code", currentDate);
		when(userRegisterRepo.findById(1)).thenReturn(userRegister);
		user = new Users();
		when(userRepo.findByIdAndStatus(1, 0)).thenReturn(user);
		when(userRepo.findByEmailAndStatus("existedEmail@gmail.com", 1)).thenReturn(user);

		when(userRepo.findById(1)).thenReturn(user);
		int result = mailService.confirmEmail("1", "code");
		assertEquals(1, result);
	}

	@Test
	void testComfirmEmailException1() throws ParseException {
		Date currentDate = new Date();
		userRegister = new UserRegister(1, "code", currentDate);
		when(userRegisterRepo.findById(1)).thenReturn(userRegister);
		int result = mailService.confirmEmail("1", "code");
		assertEquals(3, result);
	}

	@Test
	void testComfirmEmailException2() throws ParseException {
		int result = mailService.confirmEmail("1", "code");
		assertEquals(3, result);
	}

	@Test
	void testComfirmEmailException3() {
		Date currentDate = new Date();
		userRegister = new UserRegister(1, "code", currentDate);
		when(userRegisterRepo.findById(1)).thenReturn(userRegister);
		user = new Users();
		when(userRepo.findByIdAndStatus(1, 0)).thenReturn(user);
		when(userRepo.findByEmailAndStatus("existedEmail@gmail.com", 1)).thenReturn(user);
		when(userRepo.findById(1)).thenReturn(null);
		int result = mailService.confirmEmail("1", "code");
		assertEquals(3, result);
	}

	@Test
	void testSendMailRegisterSuccess() {
		Users userSending = new Users(1, "userName", "pass", "fname", "lname", null, 1, null, "userEmail",
				"userAddress", 1, null, null);
		String code = "code";
		when(emailSender.createMimeMessage()).thenReturn(message);	
		sendMailUtil.sendMail(userSending, code);
	}

	@Test
	void testSendRegisterMailFail() {
		user = null;
		String code = "";
		when(emailSender.createMimeMessage()).thenReturn(message);	
		sendMailUtil.sendMail(user, code);
	}


}