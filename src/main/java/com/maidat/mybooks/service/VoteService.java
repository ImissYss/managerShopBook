package com.maidat.mybooks.service;

import com.maidat.mybooks.domain.Book;
import com.maidat.mybooks.domain.User;
import com.maidat.mybooks.domain.Vote;
import com.maidat.mybooks.domain.dto.VoteDTO;
import com.maidat.mybooks.domain.repository.BookRepository;
import com.maidat.mybooks.domain.repository.VoteRepository;
import com.maidat.mybooks.utils.VoteBook;
import com.maidat.mybooks.utils.auth.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class VoteService {

    @Autowired
    AuthUser authUser;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    VoteRepository voteRepository;

    @Transactional
    public Vote addVote(VoteDTO voteDTO){

        VoteBook voteBook = null;
        for(VoteBook v : VoteBook.values()){
            if(v.is(voteDTO.getVoteType())){
                voteBook = v;
                break;
            }
        }

        if(voteBook == null)
            return null;

        User user = authUser.getUserInfo().getUser();
        Book book = bookRepository.findById(voteDTO.getBookID()).orElse(null);

        if(book == null)
            return null;

        Vote vote = voteRepository.findByBookAndUser(book, user);
        if(vote == null){
            vote = new Vote();
            vote.setBook(book);
            vote.setVoteType(voteBook.getName());
            vote.setUser(user);
        }
        else{
            if(vote.getVoteType().equals(voteBook.getName()))
                return null;

            vote.setVoteType(voteBook.getName());
        }

        vote.setAddDate(LocalDateTime.now());

        if(voteBook.is(VoteBook.REMOVE.getName())){
            voteRepository.delete(vote);
            return vote;
        }

        Vote newVote = voteRepository.save(vote);

        if(newVote == null)
            return null;

        return newVote;

    }
}
