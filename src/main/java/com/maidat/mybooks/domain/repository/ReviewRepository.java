package com.maidat.mybooks.domain.repository;

import com.maidat.mybooks.domain.Review;
import com.maidat.mybooks.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findAll(Pageable pageable);


    List<Review> findAllByUser(User user, Pageable pageable);

    Page<Review> findAllByTitleIgnoreCaseContainingOrContentIgnoreCaseContaining(String title, String content, Pageable pageable);
}
