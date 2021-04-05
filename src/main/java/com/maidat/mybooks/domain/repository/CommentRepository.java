package com.maidat.mybooks.domain.repository;

import com.maidat.mybooks.domain.Comment;
import com.maidat.mybooks.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByReview(Review review, Pageable pageable);

}
