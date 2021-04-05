package com.maidat.mybooks.controller;

import com.maidat.mybooks.domain.Book;
import com.maidat.mybooks.domain.Review;
import com.maidat.mybooks.domain.repository.BookRepository;
import com.maidat.mybooks.domain.repository.ReviewRepository;
import com.maidat.mybooks.exception.UserNotFoundException;
import com.maidat.mybooks.utils.auth.AuthUser;
import com.maidat.mybooks.utils.user.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Controller
public class HomeController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @RequestMapping("/")
    public String homePage(Model model){
        Pageable reviewsPage = PageRequest.of(0,3,Sort.Direction.DESC,"createDate");
        Page<Review> reviews = reviewRepository.findAll(reviewsPage);

        Pageable ratingsPage = PageRequest.of(0,8, Sort.Direction.DESC, "stars");
        Page<Book> books = bookRepository.findAll(ratingsPage);

        model.addAttribute("reviews", reviews.getContent());
        model.addAttribute("books", books.getContent());

        model.addAttribute("title","Home");

        return "home";
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model, HttpServletRequest request){
        if(error != null){
            model.addAttribute("errorMsg", "Email or password is invalid");
        }
        else{
            String referrer = request.getHeader("Referrer");
            request.getSession().setAttribute("previousURL", referrer);
        }
        return "login";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView handlerError(HttpServletRequest request, UserNotFoundException ex){
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", ex.getMsg());
        mav.setViewName("login");
        return mav;
    }

    @GetMapping("/logout")
    public String logoutPage(Model model){
        return "login";
    }

    @Autowired
    AuthUser authUser;

    @RequestMapping("/userpage")
    public String userPage(Model model){

        if(!authUser.isLoggedIn())
            return "foo";

        UserInfo user = authUser.getUserInfo();
        StringBuilder sb = new StringBuilder();
        sb.append("UserName:").append(user.getUsername());

        Collection<GrantedAuthority> authorities = user.getAuthorities();
        if(authorities != null && !authorities.isEmpty()){
            sb.append("(");
            boolean first = true;
            for(GrantedAuthority a : authorities){
                if(first){
                    sb.append(a.getAuthority());
                    first = false;
                }else{
                    sb.append(", ").append(a.getAuthority());
                }
            }
            sb.append(")");
        }

        sb.append("new: "+user.getAllUserRoles());

        if(user.isAdmin()){
            sb.append("| Admin");
        }
        model.addAttribute("greeting", sb.toString());

        model.addAttribute("title", "Logged");

        return "foo";
    }

    @GetMapping(value = "/loginfailed")
    public String loginerror(Model model){
        model.addAttribute("error","true");
        return "login";
    }

    @GetMapping(value = "/access-denied")
    public String accessDenied(Model model){
        return "accessDenied";
    }

    @RequestMapping("/foo")
    public String fooPage(Model model){
        model.addAttribute("title","Foo");
        model.addAttribute("greeting","Foo greeting");

        return "foo";
    }


}
