package com.maidat.mybooks.controller;

import com.maidat.mybooks.domain.Order;
import com.maidat.mybooks.domain.User;
import com.maidat.mybooks.domain.repository.OrderRepository;
import com.maidat.mybooks.service.OrderService;
import com.maidat.mybooks.utils.auth.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CartController {

    @Autowired
    AuthUser authUser;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @GetMapping("/listCart")
    public String getBookInCart(Model model){
        User user = authUser.getUserInfo().getUser();

        List<Order> orderList = orderRepository.findByUser(user);
        model.addAttribute("listOrder", orderList);
        return "yourOrder";
    }
}
