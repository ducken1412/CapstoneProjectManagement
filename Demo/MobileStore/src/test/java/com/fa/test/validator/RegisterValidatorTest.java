package com.fa.test.validator;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.Errors;

import com.fa.entity.Users;
import com.fa.validator.RegisterValidator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegisterValidatorTest {

	@Autowired
	private RegisterValidator registerValidator;

	@MockBean
	Errors errors;
	
	

	@Test
	void testValidateSuccess() {
		Users user = new Users();
		user.setUserName("validUsername");		
		registerValidator.validate(user, errors);
	}

	@Test
	void testValidateError() {
		Users user = new Users();
		user.setUserName("InvalidUsername_@#$");
		Mockito.doNothing().when(errors).reject("userName", "Username must not contain special character");
//		doAnswer(new Answer() {
//
//			@Override
//			public Object answer(InvocationOnMock invocation) throws Throwable {
//			Object[] args=  invocation.getArguments();
//			((Errors)args[0]).hasErrors() ;
//				return null;
//			}
//			
//		}).when(errors).reject("userName", "Username must not contain special character");

		registerValidator.validate(user, errors);
	}
	
	@Test
	void testSupportsSuccess() {
		Class<Users> clazz = Users.class ;	
		registerValidator.supports(clazz);
	}
}
