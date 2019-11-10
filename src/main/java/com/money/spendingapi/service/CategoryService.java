package com.money.spendingapi.service;


import com.money.spendingapi.model.Category;
import com.money.spendingapi.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public List<Category> list() {
        return repository.findAll();
    }

    public Category create(Category category) {
        return repository.save(category);
    }

    public Category findByCode(Long code) {
        return repository.findByCode(code);
    }
}
