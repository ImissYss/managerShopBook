package com.maidat.mybooks.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class VoteDTO {

    @NotNull
    @NotEmpty
    private String voteType;

    @NotNull
    @NotEmpty
    private Long bookID;
}
