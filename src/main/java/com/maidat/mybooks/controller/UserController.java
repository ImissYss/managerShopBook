package com.maidat.mybooks.controller;

import com.maidat.mybooks.domain.Rating;
import com.maidat.mybooks.domain.Review;
import com.maidat.mybooks.domain.User;
import com.maidat.mybooks.domain.Vote;
import com.maidat.mybooks.domain.dto.UserDTO;
import com.maidat.mybooks.domain.repository.RatingRepository;
import com.maidat.mybooks.domain.repository.ReviewRepository;
import com.maidat.mybooks.domain.repository.UserRepository;
import com.maidat.mybooks.domain.repository.VoteRepository;
import com.maidat.mybooks.service.UserService;
import com.maidat.mybooks.utils.auth.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Autowired
    AuthUser authUser;

    @GetMapping("/user/{ID}")
    public String user(@PathVariable Long ID, Model model){

        User user = userRepository.findById(ID).orElse(null);
        if(user == null){
            return "404";
        }

        Pageable reviewPage = PageRequest.of(0,2, Sort.Direction.DESC, "createDate");
        List<Review> reviews = reviewRepository.findAllByUser(user, reviewPage);

        Pageable ratingsPage = PageRequest.of(0,2,Sort.Direction.DESC,"addDate");
        List<Rating> ratings = ratingRepository.findAllByUser(user, ratingsPage);

        Pageable voteReadPage = PageRequest.of(0,4,Sort.Direction.DESC,"addDate");
        List<Vote> voteRead = voteRepository.findAllByUserAndVoteType(user, "READ", voteReadPage);

        Pageable voteReadingPage = PageRequest.of(0,4,Sort.Direction.DESC,"addDate");
        List<Vote> voteReading = voteRepository.findAllByUserAndVoteType(user,"READING", voteReadingPage);

        Pageable voteWantReadPage = PageRequest.of(0,4,Sort.Direction.DESC,"addDate");
        List<Vote> voteWantRead = voteRepository.findAllByUserAndVoteType(user, "WANT_READ",voteWantReadPage);

        model.addAttribute("user", user);
        model.addAttribute("reviews", reviews);
        model.addAttribute("rating", ratings);
        model.addAttribute("voteRead", voteRead);
        model.addAttribute("voteReading", voteReading);
        model.addAttribute("voteWantRead", voteWantRead);
        return "user";
    }

    @GetMapping("/user/{ID}/edit")
    public String editUser(@PathVariable Long ID, Model model){
        if(!authUser.isLoggedIn())
            return "404";
        if (authUser.getUserInfo().getUser().getId() != ID){
            if(!authUser.getUserInfo().isAdminOrModerator())
                return "404";
        }

        User user = userRepository.findById(ID).orElse(null);
        if(user == null)
            return "404";

        UserDTO editUser = userService.getUserToEdit(user);
        model.addAttribute("title", "Edit user: "+user.getEmail());
        model.addAttribute("user", editUser);
        model.addAttribute("userName", user.getEmail());

        return "edituser";
    }

    @PostMapping("/user/{ID}/edit")
    public String editUser(@PathVariable Long ID, @ModelAttribute("user") @Valid UserDTO userDTO,
                           BindingResult result, HttpServletRequest req,Model model){
        if(!authUser.isLoggedIn())
            return "404";
        if(authUser.getUserInfo().getUser().getId() != ID)
            if(!authUser.getUserInfo().isAdminOrModerator())
                return "404";

        User user = userRepository.findById(ID).orElse(null);

        if(user == null)
            return "404";

        User editUser = null;
        model.addAttribute("edited", false);

        if(!result.hasErrors()){
            editUser = userService.editUser(userDTO, user);
        }
        if(editUser != null){
            model.addAttribute("edited", true);
            model.addAttribute("userPath",editUser.getId());
            model.addAttribute("userName", editUser.getEmail());
            model.addAttribute("user",new UserDTO());
        }

        return "edituser";
    }

    @GetMapping("/user/change-password")
    public String changePassword( Model model){
        if(!authUser.isLoggedIn())
            return "404";
        model.addAttribute("title", "change password");
        return "changePassword";
    }

    @PostMapping("/user/change-password")
    public String changePassword(HttpServletRequest req,Model model){
        if(!authUser.isLoggedIn())
            return "404";
        model.addAttribute("changed", false);

        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        String newPassword2 = req.getParameter("newPassword2");

        if(oldPassword.isEmpty() || newPassword.isEmpty() || newPassword2.isEmpty()){
            model.addAttribute("errorMsg","please input password");
            return "changePassword";
        }
        if(!passwordEncoder.matches(oldPassword, authUser.getUserInfo().getUser().getPassword())){
            model.addAttribute("errorMsg","Password is not valid");
            return "changePassword";
        }

        if(!newPassword.equals(newPassword2)){
            model.addAttribute("errorMsg","password is not valid");
            return "changePassword";
        }

        User user = userService.changePassword(req.getParameter("newPassword"));
        if(user != null){
            model.addAttribute("changed", true);
            model.addAttribute("userPath", user.getId());
        }
        return "changePassword";
    }
}
