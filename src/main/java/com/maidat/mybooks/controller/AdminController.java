package com.maidat.mybooks.controller;

import com.maidat.mybooks.domain.User;
import com.maidat.mybooks.domain.repository.UserRepository;
import com.maidat.mybooks.utils.auth.AuthUser;
import com.maidat.mybooks.utils.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Secured({UserRole.ADMIN, UserRole.MODERATOR})
public class AdminController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthUser authUser;

    @RequestMapping("/admin")
    public String adminPanel(Model model){
        model.addAttribute("title", "Panel Admin");
        return "adminPanel";
    }

    @GetMapping("/admin/users")
    public String users(@PageableDefault(size = 2, sort = "email", direction = Sort.Direction.DESC) Pageable pageable,
                        @RequestParam(required = false) String sort,
                        @RequestParam(required = false) String size,
                        Model model){
        Page<User> users = userRepository.findAll(pageable);
        int page = pageable.getPageNumber()+1;
        int totalPage = users.getTotalPages();
        if(page > totalPage)
            return "404";

        model.addAttribute("title", "List User | page "+page);
        model.addAttribute("users", users.getContent());
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("sort", sort);
        model.addAttribute("size", size);

        return "users";
    }
}
