package com.maidat.mybooks.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Entity
@Getter
@Setter
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @OneToMany(mappedBy = "user")
    private Collection<Order> orders;

    private String email;

    private String password;

    @Column(name = "first_name")
    private String firstName;

    private String address;

    private String phone;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "display_name")
    private String displayName;

    private String description;

    @Column(name = "join_date")
    private LocalDateTime joinDate;

    @OneToMany(mappedBy = "user")
    private Collection<Review> reviews;

    @OneToMany(mappedBy = "user")
    private Collection<Comment> comments;

    @OneToMany(mappedBy = "user")
    private Collection<Rating> ratings;

    @OneToMany(mappedBy = "user")
    private Collection<Vote> votes;

    @OneToMany(mappedBy = "user")
    private Collection<Book> books;

    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "role_id")
    )
    private Collection<Role> roles;

    public String getDisplayName() {
       if(displayName != null&& !displayName.equals(""))
           return displayName;
       else if(!firstName.isEmpty() || !lastName.isEmpty())
           return firstName + " " + lastName;
       else
           return email;
    }

    public String getDate(){
        return joinDate
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

}
