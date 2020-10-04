package com.fpt.config;

import java.util.Optional;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.fpt.entity.Users;
import com.fpt.repository.UserRepository;
import com.fpt.repository.UserRolesRepository;
  

@Component
public class AppUserValidator implements Validator {

	private EmailValidator emailValidator = EmailValidator.getInstance();

	@Autowired
	private Optional<Users> userAccount ;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRolesRepository userRolesRepository;


	@Override
	public boolean supports(Class<?> clazz) {
		return clazz == AppUserForm.class;
	}

	@Override
	public void validate(Object target, Errors errors) {

		AppUserForm form = (AppUserForm) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "", "Email is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "", "User name is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "", "First name is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "", "Last name is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "", "Password is required");

		if (errors.hasErrors()) {
			return;
		}

		if (!emailValidator.isValid(form.getEmail())) {

			errors.rejectValue("email", "", "Email is not valid");
			return;
		}

		userAccount = userRepository.findById(form.getUserName());
		if (userAccount != null) {
			if (form.getUserId() == null) {
				errors.rejectValue("userName", "", "User name is not available");
				return;
			}
		}

	
	}

}
