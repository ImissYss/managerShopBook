package com.maidat.mybooks.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter
@Setter
public class RatingDTO {

    @NotNull
    @NotEmpty
    private Integer stars;

    @NotNull
    @NotEmpty
    private Long bookID;
}
