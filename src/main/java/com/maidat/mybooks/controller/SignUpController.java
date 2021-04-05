package com.maidat.mybooks.controller;

import com.maidat.mybooks.domain.User;
import com.maidat.mybooks.domain.dto.UserSignUpDTO;
import com.maidat.mybooks.service.UserSignUpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class SignUpController {

    @Autowired
    UserSignUpServiceImpl userSignUpService;

    @GetMapping("/signup")
    public String signUpPage(Model model){
        model.addAttribute("user", new UserSignUpDTO());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("user") @Valid UserSignUpDTO userDto,
                         BindingResult result, HttpServletRequest req,Model model){
        User registered = null;
        model.addAttribute("added", false);

        if(!result.hasErrors()){
            registered = createUserAccount(userDto);
        }
        if(registered != null){
            model.addAttribute("added", true);
        }

        return "signup";
    }

    public User createUserAccount(UserSignUpDTO userDto){
        User registered = null;
        try{
            registered = userSignUpService.registerNewUserAccount(userDto);
        }catch(Exception e){
            return null;
        }
        return registered;
    }
}
