package com.maidat.mybooks.service;

import com.maidat.mybooks.domain.Comment;
import com.maidat.mybooks.domain.Review;
import com.maidat.mybooks.domain.User;
import com.maidat.mybooks.domain.repository.CommentRepository;
import com.maidat.mybooks.domain.repository.ReviewRepository;
import com.maidat.mybooks.domain.rest.CommentRequest;
import com.maidat.mybooks.domain.rest.CommentResponse;
import com.maidat.mybooks.domain.rest.PageResponse;
import com.maidat.mybooks.utils.auth.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    AuthUser auth;

    @Autowired
    ReviewRepository reviewRepo;

    @Autowired
    CommentRepository commentRepo;

    @Transactional
    public Comment addComment(CommentRequest commentRequest){
        Assert.notNull(commentRequest,"Comment null");
        Assert.notNull(auth.getUserInfo(), "User null");

        if(commentRequest.getContent().equals(""))
            return null;

        User user = auth.getUserInfo().getUser();

        Review review = reviewRepo.findById(commentRequest.getReviewID()).orElseThrow(() -> new UsernameNotFoundException("Not found review by id"+commentRequest.getReviewID()));

        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        comment.setReview(review);
        comment.setUser(user);
        comment.setAddDate(LocalDateTime.now());

        System.out.println(review.getId());

        return commentRepo.save(comment);
    }

    public PageResponse getComments(Long reviewID, Pageable pageable){

        Review review = reviewRepo.findById(reviewID).orElse(null);

        if(review == null)
            return null;

        Page<Comment> comments = commentRepo.findAllByReview(review, pageable);

        int page = pageable.getPageNumber() +1;
        int totalPage = comments.getTotalPages();
        if(page> totalPage)
            return null;

        PageResponse<CommentResponse> pageResponse= new PageResponse<>();
        pageResponse.setContent(commentToResponse(comments.getContent()));
        pageResponse.setCount(comments.getNumberOfElements());
        pageResponse.setTotalCount(comments.getTotalElements());
        pageResponse.setPage(page);
        pageResponse.setTotalPage(totalPage);
        pageResponse.setPerPage(comments.getSize());

        return pageResponse;
    }

    private Collection<CommentResponse> commentToResponse(List<Comment> comments) {

        List<CommentResponse> commentResponses = new ArrayList<>();

        for(Comment comment : comments) {
            commentResponses.add(commentToResponse(comment));
        }

        return commentResponses;

    }
    private CommentResponse commentToResponse(Comment comment) {

        if(comment == null)
            return null;

        CommentResponse res = new CommentResponse();
        res.setId(comment.getId());
        res.setContent(comment.getContent());
        res.setAddDate(comment.getDate());
        res.setReviewID(comment.getReview().getId());
        res.setUserID(comment.getUser().getId());
        res.setUserName(comment.getUser().getDisplayName());

        return res;
    }


}
