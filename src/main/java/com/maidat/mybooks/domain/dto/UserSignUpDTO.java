package com.maidat.mybooks.domain.dto;

import com.maidat.mybooks.validator.EmailExists;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
public class UserSignUpDTO {

    private String name;

    @Size(min = 9, max = 10)
    private String phone;

    private String address;

    private String lastName;

    @Email(message = "{UserSignUp.email.Email}")
    @NotNull
    @NotEmpty(message = "{UserSignUp.email.NotEmpty}")
    @EmailExists
    private String email;

    @NotNull
    @NotEmpty(message = "{UserSignUp.password.NotEmpty}")
    private String password;

    @NotNull
    @NotEmpty(message = "{UserSignUp.matchingPassword.NotEmpty}")
    private String matchingPassword;
}
