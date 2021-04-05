package com.maidat.mybooks.controller;

import com.maidat.mybooks.domain.Comment;
import com.maidat.mybooks.domain.rest.CommentRequest;
import com.maidat.mybooks.domain.rest.PageResponse;
import com.maidat.mybooks.service.CommentService;
import com.maidat.mybooks.utils.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentRestController {

    @Autowired
    CommentService commentService;

    @PostMapping("/add")
    public Response addComment(@RequestBody CommentRequest commentReq){
        Comment comment = commentService.addComment(commentReq);
        Response response = new Response();
        if(comment != null){
            response.setStatus(Response.DONE);
        }
        return response;

    }

    @GetMapping("/get/{reviewID}")
    public Response getComments(@PageableDefault(size=5, sort = "addDate", direction = Sort.Direction.DESC)Pageable pageable,
                                @RequestParam(required = false) String sort,
                                @RequestParam(required = false) String size,
                                @PathVariable Long reviewID){

        Response response = new Response();

        PageResponse pageResponse = commentService.getComments(reviewID, pageable);

        if(pageResponse == null)
            return response;

        pageResponse.setSort(sort);
        pageResponse.setSize(size);

        response.set(pageResponse);

        return response;

    }
}
