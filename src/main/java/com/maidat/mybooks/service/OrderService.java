package com.maidat.mybooks.service;

import com.maidat.mybooks.domain.Book;
import com.maidat.mybooks.domain.Order;
import com.maidat.mybooks.domain.OrderItem;
import com.maidat.mybooks.domain.User;
import com.maidat.mybooks.domain.dto.AddressInfo;
import com.maidat.mybooks.domain.repository.OrderItemRepository;
import com.maidat.mybooks.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderService {

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrderRepository orderRepository;


    @Transactional
    public Collection<OrderItem> addOrderItem(Map<Book, Integer> cart){
        Collection<OrderItem> orderItems = new ArrayList<>();
        for(Map.Entry book: cart.entrySet()){
            OrderItem orderItem = new OrderItem();
            orderItem.setBook((Book) book.getKey());
            orderItem.setPrice(((Book) book.getKey()).getPrice());
            orderItem.setQuantity((Integer) book.getValue());

            orderItems.add(orderItem);

        }
        return orderItems;

    }

    @Transactional
    public Order addOrder(User user, AddressInfo addressInfo, Collection<OrderItem> orderItems){
        Order order = new Order();
        BigDecimal sum =
        orderItems.stream().map(k -> k.getPrice().multiply(BigDecimal.valueOf(k.getQuantity())))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        order.setUser(user);
        order.setOrderItems(orderItems);
        order.setAddressShip(addressInfo.getAddress());
        order.setPhone(addressInfo.getPhone());
        order.setTotalPrice(sum);

        return orderRepository.save(order);

    }

}
