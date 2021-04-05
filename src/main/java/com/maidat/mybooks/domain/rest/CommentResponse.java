package com.maidat.mybooks.domain.rest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponse {
    private Long id;
    private String content;
    private Long reviewID;
    private Long userID;
    private String userName;
    private String addDate;

}
