package com.maidat.mybooks.controller;

import com.maidat.mybooks.domain.Author;
import com.maidat.mybooks.domain.dto.AuthorDTO;
import com.maidat.mybooks.domain.repository.AuthorRepository;
import com.maidat.mybooks.service.AuthorService;
import com.maidat.mybooks.utils.auth.AuthUser;
import com.maidat.mybooks.utils.user.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    AuthUser authUser;

    @GetMapping("/authors/add")
    public String addAuthor(Model model){
        model.addAttribute("title", "Add new author");
        model.addAttribute("author", new AuthorDTO());

        return "addAuthor";
    }

    @PostMapping("/authors/add")
    public String addAuthor(@ModelAttribute("author") @Valid AuthorDTO authorDTO,
                            BindingResult result, HttpServletRequest req, Model model){

        Author author = null;
        model.addAttribute("added", false);

        if(!result.hasErrors()){
            author = authorService.addAuthor(authorDTO);
        }

        if(author != null){
            model.addAttribute("added", true);
            model.addAttribute("authorPath", author.getId());
            model.addAttribute("authorName", author.getDisplayName());
            model.addAttribute("author", new AuthorDTO());
        }

        return "addAuthor";

    }

    @GetMapping("/author/{authorID}")
    public String getAuthor(@PathVariable("authorID") Long authorID,
                            Model model){
        Author author = authorRepository.findById(authorID).orElse(null);

        if(author == null)
            return "404";

        model.addAttribute("title", author.getDisplayName() +"| Author");
        model.addAttribute("author", author);
        return "authorInfo";
    }

    @GetMapping("/authors/edit/{authorID}")
    public String editAuthor(@PathVariable("authorID") Long authorID,
                             Model model){
        if(!authUser.isLoggedIn())
            return "404";

        Author author = authorRepository.findById(authorID).orElse(null);

        UserInfo user = authUser.getUserInfo();

        if(author == null)
            return "404";

        if(user.getUser().getId() == author.getUser().getId() || user.isAdminOrModerator()){
            AuthorDTO editAuthor = authorService.getAuthorToEdit(author);

            model.addAttribute("title", "Edit author: "+author.getDisplayName());
            model.addAttribute("author", editAuthor);
            model.addAttribute("edit", true);

            return "addAuthor";
        }
        return "404";
    }

    @PostMapping("/authors/edit/{authorID}")
    public String editAuthor(@PathVariable("authorID") Long authorID,
                             @ModelAttribute("author") AuthorDTO authorDTO,
                             BindingResult result,Model model){

        if(!authUser.isLoggedIn())
            return "404";

        Author author = authorRepository.findById(authorID).orElse(null);

        UserInfo user = authUser.getUserInfo();

        if(author == null)
            return "404";

        if(user.getUser().getId() == author.getUser().getId() || user.isAdminOrModerator()){
            Author authorEdit = null;
            model.addAttribute("edited", false);
            model.addAttribute("edit", true);

            if(!result.hasErrors()){
                authorEdit = authorService.editAuthor(authorDTO, author);
            }
            if(authorEdit != null){
                model.addAttribute("edited", true);
                model.addAttribute("authorPath", authorEdit.getId());
                model.addAttribute("author", new AuthorDTO());
                model.addAttribute("authorName", authorEdit.getDisplayName());

            }
        }
        return "addAuthor";
    }

    @GetMapping("/authors/search")
    public String searchAuthor(@PageableDefault(size = 1,sort = "lastName",direction = Sort.Direction.DESC)Pageable pageable,
                               @RequestParam(required = false) String searchTerm,
                               Model model){
        if(searchTerm != null && !searchTerm.isEmpty()){
            Page<Author> authors = authorRepository.findAllByFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContaining(searchTerm, searchTerm, pageable);
            int page = pageable.getPageNumber() + 1;
            int totalPage = authors.getTotalPages();

            model.addAttribute("title","Search author: \"" + searchTerm +"\" | Page" +page);
            model.addAttribute("authors", authors.getContent());
            model.addAttribute("page", page);
            model.addAttribute("totalPage", totalPage);
            model.addAttribute("searchTerm", searchTerm);
            model.addAttribute("resultCount", authors.getTotalElements());
            model.addAttribute("search", true);

        }else{
            model.addAttribute("title", "search author");
            model.addAttribute("search", false);
        }
        return "searchAuthors";

    }
    @GetMapping("/authors")
    public String authors(@PageableDefault(size = 1,sort = "lastName",direction = Sort.Direction.DESC)Pageable pageable,
                          @RequestParam(required = false) String sort,
                          @RequestParam(required = false) String size,
                          Model model){
        Page<Author> authors = authorRepository.findAll(pageable);
        int page = pageable.getPageNumber() + 1;
        int totalPage = authors.getTotalPages();
        if(page > totalPage)
            return "404";

        model.addAttribute("title", "Author | book "+page);
        model.addAttribute("authors", authors.getContent());
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("sort", sort);
        model.addAttribute("size", size);

        return "listAuthor";

    }
}
