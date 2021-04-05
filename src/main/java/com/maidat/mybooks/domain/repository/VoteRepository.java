package com.maidat.mybooks.domain.repository;

import com.maidat.mybooks.domain.Book;
import com.maidat.mybooks.domain.User;
import com.maidat.mybooks.domain.Vote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    Vote findByBookAndUser(Book book, User user);

    @Query(
            value = "select vote_type, COUNT(*) as count FROM vote WHERE book_id = :bookID GROUP BY vote_type",
            nativeQuery = true
    )
    List<Object[]> countAllVote(Long bookID);

    List<Vote> findAllByUserAndVoteType(User user, String voteType, Pageable pageable);
}
