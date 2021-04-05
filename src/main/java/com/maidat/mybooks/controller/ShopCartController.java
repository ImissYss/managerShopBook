package com.maidat.mybooks.controller;

import com.maidat.mybooks.domain.Book;
import com.maidat.mybooks.domain.Order;
import com.maidat.mybooks.domain.OrderItem;
import com.maidat.mybooks.domain.User;
import com.maidat.mybooks.domain.dto.AddressInfo;
import com.maidat.mybooks.domain.repository.BookRepository;
import com.maidat.mybooks.domain.repository.OrderItemRepository;
import com.maidat.mybooks.service.BookService;
import com.maidat.mybooks.service.OrderService;
import com.maidat.mybooks.service.ShoppingCartService;
import com.maidat.mybooks.utils.auth.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
public class ShopCartController {

    @Autowired
    ShoppingCartService shoppingCartService;
    @Autowired
    OrderService orderService;
    @Autowired
    BookService bookService;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthUser auth;
    @Autowired
    OrderItemRepository orderItemRepository;

    @GetMapping("/shoppingCart")
    public ModelAndView shoppingCart(){
        ModelAndView mv = new ModelAndView("shoppingCart");
        mv.addObject("books",shoppingCartService.getAllBookInCart());
        mv.addObject("total", shoppingCartService.getTotal());
        Map<Book, Integer> map = shoppingCartService.getAllBookInCart();
        map.entrySet().forEach((v) -> System.out.println(v.getKey()+" "+v.getValue()));
        return mv;
    }

    @GetMapping("/shoppingCart/addBook/{bookID}")
    public ModelAndView addBookInCart(@PathVariable("bookID") Long bookID){
        bookRepository.findById(bookID).ifPresent(shoppingCartService::addBookInCart);

        return shoppingCart();
    }

    @GetMapping("/shoppingCart/removeBook/{bookID}")
    public ModelAndView removeBookInCat(@PathVariable("bookID") Long bookID){
        bookRepository.findById(bookID).ifPresent(shoppingCartService::removeBookInCart);
        return shoppingCart();
    }

    @GetMapping("/checkInCart")
    public String checkCart(Model model){
        Map<Book,Integer> cart = shoppingCartService.getAllBookInCart();
        model.addAttribute("cart", cart);
        model.addAttribute("total",shoppingCartService.getTotal());

        return "checkCart";
    }

    @GetMapping("/editAddress")
    public String editAddressOrder(Model model){
        model.addAttribute("addressInfo", new AddressInfo());
        return "editAddress";

    }
    @PostMapping("/editAddress")
    public String editAddressOrder(@ModelAttribute("addressInfo") AddressInfo addressInfo,Model model){
        model.addAttribute("addressInfo", addressInfo);
        model.addAttribute("editAddress", true);
        model.addAttribute("cart", shoppingCartService.getAllBookInCart());
        model.addAttribute("total", shoppingCartService.getTotal());
        return "checkCart";
    }

    @PostMapping("/order")
    public String saveOrder(Model model, AddressInfo addressInfo){

        Map<Book, Integer> cart = shoppingCartService.getAllBookInCart();
        Collection<OrderItem> orderItem = new ArrayList<>();
        orderItem = orderService.addOrderItem(cart);
        User user = auth.getUserInfo().getUser();

        Order order = orderService.addOrder(user, addressInfo, orderItem);
        for(OrderItem o: orderItem){
            o.setOrders(order);
        }
        orderItemRepository.saveAll(orderItem);
        for(Map.Entry map: cart.entrySet()){
            shoppingCartService.removeCart((Book) map.getKey());
        }

        return "yourCart";




    }





}
