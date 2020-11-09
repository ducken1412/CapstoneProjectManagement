package com.fa.test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.fa.controller.home.RegisterController;
import com.fa.entity.UserRegister;
import com.fa.entity.Users;
import com.fa.service.MailServiceImpl;
import com.fa.service.UserRegisterServiceImpl;
import com.fa.service.UserServiceImpl;
import com.fa.utils.EncrytedPasswordUtils;
import com.fa.utils.SendEmailUtils;
import com.fa.validator.RegisterValidator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegisterControllerTest {

	@Autowired
	private RegisterController registerController;

	@MockBean
	private Model model;

	@MockBean
	private RegisterValidator registerValidator;

	@MockBean
	BindingResult br;

	@MockBean
	UserServiceImpl userService;

	@MockBean
	EncrytedPasswordUtils encrypt;

	@MockBean
	UserRegisterServiceImpl userRegisterService;

	@MockBean
	MailServiceImpl mailService;

	@MockBean
	SendEmailUtils sendMailUtils;

	@Test
	void testRegisterPageSuccess() {
		assertEquals("home/register1", registerController.registerPage(model));
	}

	@Test
	void testRegisterSuccess() {
		Users user = new Users();
		user.setId(1);
		user.setEmail("TestEmail@gmail.com");
		user.setEncrytedPassword("password");
		Mockito.doNothing().when(registerValidator).validate(user, br);
		when(br.hasErrors()).thenReturn(false);
		when(userService.findByEmailAndStatus(user.getEmail(), 1)).thenReturn(null);
		when(userService.findByUserNameAndStatus(user.getUserName(), 1)).thenReturn(null);
		UserRegister userRegister = new UserRegister();
		Mockito.doNothing().when(userRegisterService).saveUserRegister(userRegister);
		assertEquals("login/loginPage", registerController.register(model, user, br));
	}

	@Test
	void testRegisterValidateError() {
		Users user = new Users();
		user.setId(1);
		user.setEmail("TestEmail@gmail.com");
		user.setEncrytedPassword("password");
		Mockito.doNothing().when(registerValidator).validate(user, br);
		when(br.hasErrors()).thenReturn(true);
		assertEquals("home/register1", registerController.register(model, user, br));
	}

	@Test
	void testRegisterEmailIsUse() {
		Users user = new Users();
		user.setId(1);
		user.setEmail("TestEmail@gmail.com");
		user.setEncrytedPassword("password");
		Mockito.doNothing().when(registerValidator).validate(user, br);
		when(br.hasErrors()).thenReturn(false);
		when(userService.findByEmailAndStatus(user.getEmail(), 1)).thenReturn(user);
		when(userService.findByUserNameAndStatus(user.getUserName(), 1)).thenReturn(null);
		assertEquals("home/register1", registerController.register(model, user, br));
	}

	@Test
	void testRegisterUsernameIsUse() {
		Users user = new Users();
		user.setId(1);
		user.setEmail("TestEmail@gmail.com");
		user.setEncrytedPassword("password");
		Mockito.doNothing().when(registerValidator).validate(user, br);
		when(br.hasErrors()).thenReturn(false);
		when(userService.findByEmailAndStatus(user.getEmail(), 1)).thenReturn(null);
		when(userService.findByUserNameAndStatus(user.getUserName(), 1)).thenReturn(user);
		assertEquals("home/register1", registerController.register(model, user, br));
	}

	@Test
	void testRegisterUsernameAndEmailIsUse() {
		Users user = new Users();
		user.setId(1);
		user.setEmail("TestEmail@gmail.com");
		user.setEncrytedPassword("password");
		Mockito.doNothing().when(registerValidator).validate(user, br);
		when(br.hasErrors()).thenReturn(false);
		when(userService.findByEmailAndStatus(user.getEmail(), 1)).thenReturn(user);
		when(userService.findByUserNameAndStatus(user.getUserName(), 1)).thenReturn(user);
		assertEquals("home/register1", registerController.register(model, user, br));
	}

	@Test
	void testConfirmRegisterSuccess() {
		String id = "1";
		String code = "code";
		when(mailService.confirmEmail(id, code)).thenReturn(1);
		assertEquals("login/loginPage", registerController.confirmRegister(model, id, code));
	}

	@Test
	void testConfirmRegisterExpire() {
		String id = "1";
		String code = "code";
		when(mailService.confirmEmail(id, code)).thenReturn(2);
		assertEquals("login/loginPage", registerController.confirmRegister(model, id, code));
	}

	@Test
	void testConfirmRegisterEmailInUse() {
		String id = "1";
		String code = "code";
		when(mailService.confirmEmail(id, code)).thenReturn(0);
		assertEquals("login/loginPage", registerController.confirmRegister(model, id, code));
	}

	@Test
	void testConfirmRegisterException() {
		String id = "1";
		String code = "code";
		when(mailService.confirmEmail(id, code)).thenReturn(3);
		assertEquals("login/loginPage", registerController.confirmRegister(model, id, code));
	}
}
