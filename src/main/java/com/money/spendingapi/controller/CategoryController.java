package com.money.spendingapi.controller;


import com.money.spendingapi.event.CreatedResourceEvent;
import com.money.spendingapi.model.Category;
import com.money.spendingapi.repository.filter.EntryFilter;
import com.money.spendingapi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @Autowired
    private ApplicationEventPublisher publisher; // publisher to call an event in Spring!

    @GetMapping
    public ResponseEntity<?> list() {
        List<Category> categories = service.list();
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Category> create(@Valid @RequestBody Category category, HttpServletResponse response) {
        Category categorySaved = service.create(category);
        publisher.publishEvent(new CreatedResourceEvent(this, response, categorySaved.getCode()));
        return ResponseEntity.status(HttpStatus.CREATED).body(categorySaved);

    }

    @GetMapping("/{code}")
    public ResponseEntity<Category> getByCode(@PathVariable("code") Long code) {
        Category categoryFound = service.findByCode(code);
        if (categoryFound == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryFound);
    }


}
