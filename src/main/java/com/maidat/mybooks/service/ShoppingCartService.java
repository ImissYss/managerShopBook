package com.maidat.mybooks.service;

import com.maidat.mybooks.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ShoppingCartService {

    @Autowired
    BookService bookService;

    private Map<Book, Integer> books = new HashMap<>();

    public void addBookInCart(Book book){
        if(books.containsKey(book)){
            books.replace(book, books.get(book)+1);
        }else{
            books.put(book,1);
        }
    }

    public void removeBookInCart(Book book){
        if(books.containsKey(book)){
            if(books.get(book) > 1){
                books.replace(book, books.get(book) - 1);
            }else if(books.get(book) == 1){
                books.remove(book);
            }
        }
    }

    public Map<Book, Integer> getAllBookInCart(){
        return Collections.unmodifiableMap(books);
    }

    public BigDecimal getTotal(){
        return books.entrySet().stream()
                .map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
    public void removeCart(Book book){
        if(books.containsKey(book)){
            books.remove(book);
        }

    }

}
