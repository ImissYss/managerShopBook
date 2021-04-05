package com.maidat.mybooks.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    private String content;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "view_count")
    private Long viewCount;

    @OneToMany(mappedBy = "review")
    private Collection<Comment> comments;

    private String slug = "";


    public String getShortContent(){
        return content.substring(0, Math.min(content.length(),320)) + "...";
    }

    public String getDate(){
        return createDate
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

}
