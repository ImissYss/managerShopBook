package com.maidat.mybooks.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;

    @Column(name = "book_count")
    private Long bookCount = 0L;


    @OneToMany(mappedBy = "category")
    private Collection<Book> books;


}
