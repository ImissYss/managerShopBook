package com.maidat.mybooks.domain.dto;

import com.maidat.mybooks.domain.Category;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CategoryDTO {

    @NotNull
    @NotEmpty
    private String name;

    //private Category parent;
}
