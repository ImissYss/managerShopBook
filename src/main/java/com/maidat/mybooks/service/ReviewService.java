package com.maidat.mybooks.service;

import com.maidat.mybooks.domain.Book;
import com.maidat.mybooks.domain.Review;
import com.maidat.mybooks.domain.User;
import com.maidat.mybooks.domain.dto.ReviewDTO;
import com.maidat.mybooks.domain.repository.ReviewRepository;
import com.maidat.mybooks.utils.auth.AuthUser;
import com.maidat.mybooks.utils.dto.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    AuthUser authUser;

    public Review addReview(ReviewDTO reviewDTO, Book book){
        User user = authUser.getUserInfo().getUser();

        Review review = ObjectMapperUtils.map(reviewDTO, Review.class);
        review.setBook(book);
        review.setViewCount(0L);
        review.setCreateDate(LocalDateTime.now());
        review.setUser(user);


        return reviewRepository.save(review);
    }

    //TODO edit review
    public ReviewDTO getReviewToEdit(Review review){
        ReviewDTO reviewDTO = ObjectMapperUtils.map(review, ReviewDTO.class);
        return reviewDTO;
    }

    public Review editReview(ReviewDTO reviewDTO, Review review){
        review.setTitle(reviewDTO.getTitle());
        review.setContent(reviewDTO.getContent());

        Review editReview = reviewRepository.save(review);

        return editReview;
    }

    //TODO count view review

    public void incrementViewCount(Review review){
        review.setViewCount(review.getViewCount()+1);
        reviewRepository.save(review);
    }
}
