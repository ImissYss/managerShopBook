package com.maidat.mybooks.validator;

import com.maidat.mybooks.domain.User;
import com.maidat.mybooks.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailExistsValidator implements ConstraintValidator<EmailExists, String> {

    @Autowired
    private UserRepository userRepository;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        User user = userRepository.findByEmail(value);
        return (user == null) ? true:false;
    }
}
