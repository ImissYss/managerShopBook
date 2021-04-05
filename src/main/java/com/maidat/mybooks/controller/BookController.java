package com.maidat.mybooks.controller;

import com.maidat.mybooks.MybooksApplication;
import com.maidat.mybooks.domain.Author;
import com.maidat.mybooks.domain.Book;
import com.maidat.mybooks.domain.Category;
import com.maidat.mybooks.domain.dto.AuthorSearchDTO;
import com.maidat.mybooks.domain.dto.BookDTO;
import com.maidat.mybooks.domain.repository.AuthorRepository;
import com.maidat.mybooks.domain.repository.BookRepository;
import com.maidat.mybooks.domain.repository.CategoryRepository;
import com.maidat.mybooks.service.BookService;
import com.maidat.mybooks.service.CategoryService;
import com.maidat.mybooks.utils.auth.AuthUser;
import com.maidat.mybooks.utils.dto.ObjectMapperUtils;
import com.maidat.mybooks.utils.user.UserInfo;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorRepository authorRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    CategoryService categoryService;

    @Autowired
    AuthUser authUser;

    @ModelAttribute("authors")
    public List<AuthorSearchDTO> getAllAuthors(){
        List<Author> authors = authorRepo.findAllByOrderByLastNameAscFirstNameAsc();
        return ObjectMapperUtils.mapAll(authors, AuthorSearchDTO.class);
    }

    @ModelAttribute("cats")
    public List<Category> getAllCats(){
        return categoryService.getAllCat();

    }

    @ModelAttribute("authors")
    public List<AuthorSearchDTO> allAuthors(){
        List<Author> authors = authorRepo.findAllByOrderByLastNameAscFirstNameAsc();
        return ObjectMapperUtils.mapAll(authors, AuthorSearchDTO.class);
    }

    @ModelAttribute("cats")
    public List<Category> allCats(){
        return categoryRepo.findAll();
    }

    @GetMapping("/books/add")
    public String addBook(Model model){
        model.addAttribute("title", "Add new book");

        BookDTO bookDTO = new BookDTO();

        model.addAttribute("book", bookDTO);

        return "addBook";
    }

    @PostMapping("/books/add")
    public String addBook(@ModelAttribute("book") @Valid BookDTO bookDTO,@RequestParam("price") String price,
                          BindingResult result,HttpServletRequest req,Model model)  {

        Book newBook = null;
        model.addAttribute("added", false);
        MultipartFile upload = bookDTO.getUpload();
        String homeUrl = new ApplicationHome(MybooksApplication.class).getDir()+"\\static\\theme\\images";
        Path rootLocation = Paths.get(homeUrl);

        if (!Files.exists(rootLocation)) {
            try {
                Files.createDirectory(rootLocation);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (upload != null && !upload.isEmpty()) {
            try {
                String imageName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(upload.getOriginalFilename());
                Files.copy(upload.getInputStream(), rootLocation.resolve(imageName));
                bookDTO.setCover("/images/" + imageName);
            } catch (Exception ex) {
                result.rejectValue("upload", "", "Problem on saving product picture.");
            }
        }
        bookDTO.setPrice(new BigDecimal(price));
        if(!result.hasErrors()){
            newBook = bookService.addBook(bookDTO);
        }

        if(newBook != null){
            model.addAttribute("added", true);
            model.addAttribute("bookID", newBook.getId());
            model.addAttribute("bookTitle", newBook.getTitle());
            model.addAttribute("book", new BookDTO());
        }




        return "addBook";

    }

    @RequestMapping("/book/{bookId}")
    public String getBookById(@PathVariable Long bookId, Model model){
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new UsernameNotFoundException("Not found book by id "+bookId));

        if(book !=null){
            model.addAttribute("title",book.getTitle() + "| Book");
            model.addAttribute("book", book);


            //TODO COUNT VOTE

            //TODO FIND RATING

            return "book";

        }
        return "404";
    }

    @GetMapping("/books/edit/{bookId}")
    public String editBook(@PathVariable("bookId") Long bookId,Model model){

        if(!authUser.isLoggedIn()){
            return "404";
        }


        Book book = bookRepository.findById(bookId).orElseThrow(() -> new UsernameNotFoundException("Not found book by id "+bookId));
        UserInfo user = authUser.getUserInfo();
        if(book == null)
            return "404";

        if( user.isAdminOrModerator()){
            model.addAttribute("book", book);
            model.addAttribute("edit", true);
            model.addAttribute("title", "Edit book: "+book.getTitle());

            return "addBook";
        }

        return "404";


    }

    @PostMapping("/books/edit/{bookId}")
    public String editBook(@PathVariable("bookId") Long bookId,
                           @ModelAttribute("book") Book book,
                           HttpServletRequest req,
                           Model model){

        if(!authUser.isLoggedIn())
            return "404";

        Book book1 = bookRepository.findById(bookId).orElse(null);
        UserInfo user = authUser.getUserInfo();
        if(book1 == null)
            return "404";
        if(user.isAdminOrModerator()){

            model.addAttribute("edited", false);
            model.addAttribute("edit", true);
            Book editBook = bookService.editBook(book, book1);
            if(editBook != null){
                model.addAttribute("edited", true);
                model.addAttribute("bookId", editBook.getId());
                model.addAttribute("bookTitle", editBook.getTitle());
                model.addAttribute("book", new Book());
            }

        }

        return "addBook";


    }

    @GetMapping("/books")
    public String getAllBooks(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)Pageable pageable,
                              Model model){
        Page<Book> books = bookRepository.findAll(pageable);
        int page = pageable.getPageNumber() + 1;
        int totalPage = books.getTotalPages();
        if(page > totalPage)
            return "404";

        model.addAttribute("title","Book | page "+page);
        model.addAttribute("books",books.getContent());
        model.addAttribute("page",page);
        model.addAttribute("totalPage", totalPage);

        return "books";
    }

    @GetMapping("/books/search")
    public String searchBooks(@PageableDefault(size = 10, sort = "title", direction = Sort.Direction.DESC)Pageable pageable,
                              @RequestParam(required = false) String searchTerm,
                              Model model){
        if(searchTerm != null && !searchTerm.isEmpty()){
            Page<Book> books = bookRepository.findAllByTitleIgnoreCaseContaining(searchTerm,pageable);
            int page = pageable.getPageNumber() + 1;
            int totalPage = books.getTotalPages();

            model.addAttribute("title", "Search book with title "+searchTerm);
            model.addAttribute("books", books.getContent());
            model.addAttribute("page", page);
            model.addAttribute("totalPage", totalPage);
            model.addAttribute("searchTerm", searchTerm);
            model.addAttribute("resultCount", books.getTotalElements());
            model.addAttribute("search", true);
            System.out.println("founded");
        }else{
            model.addAttribute("title","Search book");
            model.addAttribute("search", false);
            System.out.println("not found");
        }

        return "searchBook";
    }



}
