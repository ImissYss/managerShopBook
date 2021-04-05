package com.maidat.mybooks.service;

import com.maidat.mybooks.domain.Category;
import com.maidat.mybooks.domain.dto.CategoryDTO;
import com.maidat.mybooks.domain.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Transactional
    public Category addCategory(CategoryDTO categoryDTO){
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setBookCount(0l);

        return categoryRepository.save(category);

    }
    public List<Category> getAllCat(){
        return categoryRepository.findAll();
    }
}
