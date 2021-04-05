package com.maidat.mybooks.domain.repository;

import com.maidat.mybooks.domain.Book;
import com.maidat.mybooks.domain.Rating;
import com.maidat.mybooks.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("SELECT SUM(r.stars) FROM Rating r WHERE r.book = :book")
    Long getStarsSum(Book book);

    @Query("SELECT COUNT(r) FROM Rating r WHERE r.book = :book")
    Integer getRatingCount(Book book);

    Rating findByBookAndUser(Book book, User user);

    List<Rating> findAllByUser(User user, Pageable pageable);

}
