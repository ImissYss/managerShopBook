package com.maidat.mybooks.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "author_id")
    )

    private Collection<Author> authors;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")

    private User user;

    private String title;

    private String description;

    private String cover;

    private BigDecimal price;

    private Integer pages;

    @Column(name = "publish_year")
    private String publishYear;

    private String originalTitle;

    private Float stars;

    @Column(name = "rating_count")
    private Integer ratingCount;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "view_count")
    private Long viewCount;

    @OneToMany(mappedBy = "book")
    private Collection<Vote> votes;

    @OneToMany(mappedBy = "book")
    private Collection<Rating> ratings;

    @OneToMany(mappedBy = "book")
    private Collection<Review> reviews;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id.equals(book.id) && title.equals(book.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
