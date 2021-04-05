package com.maidat.mybooks.service;

import com.maidat.mybooks.domain.Book;
import com.maidat.mybooks.domain.Rating;
import com.maidat.mybooks.domain.User;
import com.maidat.mybooks.domain.dto.RatingDTO;
import com.maidat.mybooks.domain.repository.BookRepository;
import com.maidat.mybooks.domain.repository.RatingRepository;
import com.maidat.mybooks.utils.auth.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class RatingService {

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    AuthUser authUser;
    @Autowired
    BookRepository bookRepository;

    @Transactional
    public Rating addRating(RatingDTO ratingDTO){

        User user = authUser.getUserInfo().getUser();
        Book book = bookRepository.findById(ratingDTO.getBookID()).orElse(null);

        if(book == null)
            return null;

        Rating rating = ratingRepository.findByBookAndUser(book, user);

        if(rating == null){
            rating = new Rating();
            rating.setBook(book);
            rating.setStars(ratingDTO.getStars());
            rating.setUser(user);
        }else{
            rating.setStars(ratingDTO.getStars());
        }
        rating.setAddDate(LocalDateTime.now());
        Rating newRating = ratingRepository.save(rating);

        if(newRating == null)
            return null;

        Long allStars = ratingRepository.getStarsSum(book);
        Integer ratingCount = ratingRepository.getRatingCount(book);

        if(allStars < 0 || ratingCount == 0)
            return null;

        float stars = (float) allStars / ratingCount;

        book.setStars(stars);
        book.setRatingCount(ratingCount);
        book = bookRepository.save(book);

        if(book == null)
            return null;

        return newRating;


    }
}
