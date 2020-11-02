package com.fa.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotSpecialChar implements
  ConstraintValidator<NotSepecialCharConstraint, String> {
 
    @Override
    public void initialize(NotSepecialCharConstraint contactNumber) {
    }
 
    @Override
    public boolean isValid(String contactField,
      ConstraintValidatorContext cxt) {
        return contactField != null && contactField.matches("[a-zA-Z0-9 ]+");
    }
 
}