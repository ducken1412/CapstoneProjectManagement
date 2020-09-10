package com.fa.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.fa.entity.Users;

@Component
public class RegisterValidator implements Validator{
  
  private static final String VALID_SPECIAL_CHAR = "^[a-zA-Z0-9_]+$";

  @Override
  public boolean supports(Class<?> clazz) {
    return clazz.isAssignableFrom(Users.class);
  }

  /**
   * Validate Users
   */
  @Override
  public void validate(Object target, Errors errors) {
        Users user = (Users) target;
        Pattern pattern = Pattern.compile(VALID_SPECIAL_CHAR);
        Matcher matcher = pattern.matcher(user.getUserName());
        if (!matcher.matches()) {
          errors.reject("userName", "Username must not contain special character");
        }
  }

}
