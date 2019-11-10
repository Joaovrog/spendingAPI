package com.money.spendingapi.controller;

import com.money.spendingapi.event.CreatedResourceEvent;
import com.money.spendingapi.model.Person;
import com.money.spendingapi.service.PersonService;
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
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonService service;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public ResponseEntity<?> list() {
        List<Person> categories = service.list();
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Person> create(@Valid @RequestBody Person person, HttpServletResponse response) {
        Person personSaved = service.create(person);
        publisher.publishEvent(new CreatedResourceEvent(this, response, personSaved.getCode()));
        return ResponseEntity.status(HttpStatus.CREATED).body(personSaved);

    }


    @GetMapping("/{code}")
    public ResponseEntity<Person> getByCode(@PathVariable("code") Long code) {
        Person categoryFound = service.findByCode(code);
        if (categoryFound == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryFound);
    }

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable(name = "code") Long code) {
        service.remove(code);
    }

    @PutMapping("/{code}")
    public ResponseEntity<Person> update(@PathVariable(name = "code") Long code, @Valid @RequestBody Person person) {
        Person savedPerson = service.update(code, person);
        return ResponseEntity.ok(savedPerson);
    }

    @PutMapping("/{code}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateActiveProperty(@PathVariable(name="code") Long code, @RequestBody Boolean active) {
        service.updateActiveProperty(code, active);
    }
}
