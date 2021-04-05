package com.maidat.mybooks.domain.rest;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;


@Getter
@Setter
public class PageResponse<T> {

    private int page;
    private int totalPage;
    private int count;
    private Long totalCount;
    private int perPage;
    private String sort;
    private String size;
    private Collection<T> content;

    public void SetCount(){
        count = content.size();
    }
}
