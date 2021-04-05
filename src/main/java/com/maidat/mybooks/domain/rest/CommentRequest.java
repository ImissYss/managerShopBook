package com.maidat.mybooks.domain.rest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {

    private String content;
    private Long reviewID;
}
