package com.maidat.mybooks.controller;

import com.maidat.mybooks.domain.Book;
import com.maidat.mybooks.domain.Category;
import com.maidat.mybooks.domain.dto.CategoryDTO;
import com.maidat.mybooks.domain.repository.BookRepository;
import com.maidat.mybooks.domain.repository.CategoryRepository;
import com.maidat.mybooks.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;

@Controller
public class CategoryController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/cat/add")
    public String addCat(Model model){
        model.addAttribute("title", "Add new category");
        model.addAttribute("cat", new CategoryDTO());

        return "addCat";

    }

    @PostMapping("/cat/add")
    public String addCat(@ModelAttribute("cat") @Valid CategoryDTO categoryDTO,
                         BindingResult result, HttpServletRequest req, Model model){
        Category category = null;
        model.addAttribute("added", false);

        if(!result.hasErrors()){
            category = categoryService.addCategory(categoryDTO);
        }

        if(category != null){
            model.addAttribute("added", true);
            model.addAttribute("catPath", category.getId());
            model.addAttribute("catName",category.getName());
            model.addAttribute("cat", new CategoryDTO());
        }

        return "addCat";
    }

    @GetMapping("/cat/{catPath}")
    public String getCat(@PathVariable("catPath") Long catID,
                         Model model){
        Category cat = categoryRepository.findById(catID).orElse(null);

        if(cat == null)
            return "404";

        Collection<Book> books = bookRepository.findAllByCategory(cat);

        model.addAttribute("title", cat.getName() + "| Category");
        model.addAttribute("cat", cat);
        model.addAttribute("books", books);
        return "cat";
    }
}
