package com.maidat.mybooks.domain.repository;

import com.maidat.mybooks.domain.Book;
import com.maidat.mybooks.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {


    List<Book> findAllByCategory(Category cat);

    Page<Book> findAllByTitleIgnoreCaseContaining(String title, Pageable pageable);
}
