package com.maidat.mybooks.service;

import com.maidat.mybooks.domain.Author;
import com.maidat.mybooks.domain.User;
import com.maidat.mybooks.domain.dto.AuthorDTO;
import com.maidat.mybooks.domain.repository.AuthorRepository;
import com.maidat.mybooks.utils.auth.AuthUser;
import com.maidat.mybooks.utils.dto.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AuthorService {

    @Autowired
    AuthUser authUser;

    @Autowired
    AuthorRepository authorRepository;

    @Transactional
    public Author addAuthor(AuthorDTO authorDTO){
        User user = authUser.getUserInfo().getUser();

        Author author = ObjectMapperUtils.map(authorDTO, Author.class);
        author.setUser(user);

        return authorRepository.save(author);

    }

    public AuthorDTO getAuthorToEdit(Author author){
        AuthorDTO editAuthor = ObjectMapperUtils.map(author, AuthorDTO.class);
        return editAuthor;
    }

    @Transactional
    public Author editAuthor( AuthorDTO authorDTO, Author author){

        author.setFirstName(authorDTO.getFirstName());
        author.setLastName(authorDTO.getLastName());
        author.setDescription(authorDTO.getDescription());
        author.setDateOfBirth(authorDTO.getDateOfBirth());
        author.setPhoto(authorDTO.getPhoto());

        Author editAuthor = authorRepository.save(author);
        return editAuthor;
    }
}
