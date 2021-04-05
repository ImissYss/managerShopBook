package com.maidat.mybooks.service;

import com.maidat.mybooks.domain.Author;
import com.maidat.mybooks.domain.Book;
import com.maidat.mybooks.domain.Category;
import com.maidat.mybooks.domain.User;
import com.maidat.mybooks.domain.dto.BookDTO;
import com.maidat.mybooks.domain.repository.AuthorRepository;
import com.maidat.mybooks.domain.repository.BookRepository;
import com.maidat.mybooks.domain.repository.CategoryRepository;
import com.maidat.mybooks.utils.auth.AuthUser;
import com.maidat.mybooks.utils.dto.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthUser authUser;

    @Autowired
    AuthorRepository authorRepository;

    public Book saveBook(Book book){
        return bookRepository.save(book);
    }

    @Transactional
    public Book editBook(Book book, Book editBook){

        editBook.setTitle(book.getTitle());
        editBook.setDescription(book.getDescription());
        editBook.setPages(book.getPages());
        //editBook.setIBSN(book.getIBSN());
        editBook.setCover(book.getCover());
        editBook.setStars(0f);
        editBook.setRatings(null);
        editBook.setCategory(null);
        editBook.setRatingCount(0);

        Book book1 = bookRepository.save(editBook);

        return book1;

    }

    public Book addBook(BookDTO bookDTO){

        User user = authUser.getUserInfo().getUser();

        Book book = ObjectMapperUtils.map(bookDTO, Book.class);
        book.setAuthors(getAuthorsFromString(bookDTO.getAuthors()));
        book.setCreateDate(LocalDateTime.now());
        book.setViewCount(0L);
        book.setRatingCount(0);
        book.setStars(0.f);
        book.setUser(user);

        Book newBook = bookRepository.save(book);
        if(newBook != null){

            Category cat = newBook.getCategory();
            cat.setBookCount(cat.getBookCount()+1);
            categoryRepository.save(cat);
        }

        return newBook;


    }
    private List<Author> getAuthorsFromString(String data) {
        List<Author> authors = new ArrayList<>();

        if(data != null) {
            List<Long> ids = getIDsFromString(data);
            authors = authorRepository.findByIdIn(ids);
        }

        return authors;
    }

    private List<Long> getIDsFromString(String data) {

        return Arrays.stream(data.split(","))
                .filter(s -> (!s.equals("") && s.chars().allMatch( Character::isDigit )))
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }


}
