package com.maidat.mybooks.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ReviewDTO {

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @NotEmpty
    private String content;
}
