package com.maidat.mybooks.controller;

import com.maidat.mybooks.domain.Rating;
import com.maidat.mybooks.domain.dto.RatingDTO;
import com.maidat.mybooks.service.RatingService;
import com.maidat.mybooks.utils.dto.ObjectMapperUtils;
import com.maidat.mybooks.utils.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rating")
public class RatingRestController {

    @Autowired
    RatingService ratingService;

    @PostMapping("/add")
    public Response addRating(@RequestBody RatingDTO ratingDTO){
        Rating rating = ratingService.addRating(ratingDTO);
        Response response = new Response();

        if(rating != null)
            response.set(ObjectMapperUtils.map(rating, RatingDTO.class));

        return response;
    }

}
