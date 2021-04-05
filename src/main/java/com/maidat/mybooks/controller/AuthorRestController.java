package com.maidat.mybooks.controller;

import com.maidat.mybooks.domain.Author;
import com.maidat.mybooks.domain.dto.AuthorDTO;
import com.maidat.mybooks.domain.dto.AuthorSearchDTO;
import com.maidat.mybooks.service.AuthorService;
import com.maidat.mybooks.utils.dto.ObjectMapperUtils;
import com.maidat.mybooks.utils.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/author")
public class AuthorRestController {

    @Autowired
    AuthorService authorService;

    @PostMapping("/add")
    public Response addAuthor(@RequestBody AuthorSearchDTO authorSearchDTO){
        Author author = authorService.addAuthor(ObjectMapperUtils.map(authorSearchDTO, AuthorDTO.class));
        Response response = new Response();
        if(author != null)
            response.set(ObjectMapperUtils.map(author, AuthorSearchDTO.class));
        return response;
    }
}
