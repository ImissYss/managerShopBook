package com.maidat.mybooks.controller;

import com.maidat.mybooks.domain.Vote;
import com.maidat.mybooks.domain.dto.VoteDTO;
import com.maidat.mybooks.service.VoteService;
import com.maidat.mybooks.utils.dto.ObjectMapperUtils;
import com.maidat.mybooks.utils.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vote_book")
public class VoteController {

    @Autowired
    VoteService voteService;

    @PostMapping("/")
    public Response addVote(@RequestBody VoteDTO voteDTO){

        Vote vote = voteService.addVote(voteDTO);
        Response response = new Response();
        if(vote != null)
            response.set(ObjectMapperUtils.map(vote, VoteDTO.class));

        return response;
    }
}
