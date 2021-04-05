package com.maidat.mybooks.exception;

public class UserNotFoundException extends RuntimeException{

    public final String msg = "Email or password is invalid2";
    private String email;

    public UserNotFoundException(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }
    public String getMsg(){
        return msg;
    }
}
