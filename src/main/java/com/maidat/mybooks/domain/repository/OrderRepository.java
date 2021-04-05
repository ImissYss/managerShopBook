package com.maidat.mybooks.domain.repository;

import com.maidat.mybooks.domain.Order;
import com.maidat.mybooks.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

}
