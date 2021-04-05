package com.maidat.mybooks.domain.repository;

import com.maidat.mybooks.domain.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a From Author a WHERE a.firstName LIKE %:name% OR a.lastName LIKE %:name% ORDER BY a.lastName ASC ")
    List<Author> searchAuthors(String name);

    List<Author> findAllByOrderByLastNameAscFirstNameAsc();

    List<Author> findByIdIn(List<Long> ids);


    Page<Author> findAllByFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContaining(String firstName, String lastName, Pageable pageable);
}
