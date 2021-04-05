package com.maidat.mybooks.validator;

import com.maidat.mybooks.domain.dto.UserSignUpDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        UserSignUpDTO user = (UserSignUpDTO) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
