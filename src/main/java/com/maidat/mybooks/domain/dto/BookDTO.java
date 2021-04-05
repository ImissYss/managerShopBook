package com.maidat.mybooks.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maidat.mybooks.domain.Category;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
public class BookDTO {

    @NotNull
    @NotEmpty
    private String title;

    private String authors;

    @NotNull
    private Category category;

    private String description;

    private String publishYear;

    private Integer pages;

    private String originalTitle;

    private String cover;

    private BigDecimal price;

    @JsonIgnore
    private MultipartFile upload;

}
