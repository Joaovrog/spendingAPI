package com.money.spendingapi.controller;

import com.money.spendingapi.event.CreatedResourceEvent;
import com.money.spendingapi.exception.ValidationException;
import com.money.spendingapi.model.Entry;
import com.money.spendingapi.repository.filter.EntryFilter;
import com.money.spendingapi.service.EntryService;
import com.money.spendingapi.service.exception.InexistentOrInactivePersonException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("entries")
public class EntryController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private EntryService service;

    @Autowired
    private ApplicationEventPublisher publisher; // publisher to call an event in Spring!


    @GetMapping
    public ResponseEntity<?> search(EntryFilter entryFilter) {
        List<Entry> categories = service.filter(entryFilter);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Entry> getByCode(@PathVariable("code") Long code) {
        Entry categoryFound = service.findByCode(code);
        if (categoryFound == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryFound);
    }

    @PostMapping
    public ResponseEntity<Entry> create(@Valid @RequestBody Entry entry, HttpServletResponse response) {
        Entry savedEntry = service.create(entry);
        publisher.publishEvent(new CreatedResourceEvent(this, response, savedEntry.getCode()));
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEntry);
    }

    // Validating that exception in Controller: InexistentOrInactivePersonException will only occurs here.
    @ExceptionHandler( { InexistentOrInactivePersonException.class  })
    public ResponseEntity<Object> handleInexistentOrInactivePersonException(InexistentOrInactivePersonException ex) {
        String errorMessageForUser = messageSource.getMessage("person.inexistent-or-inactive", null, LocaleContextHolder.getLocale());
        String errorMessageForDev = ex.toString();
        List<ValidationException.ErrorMessageFor> errors = Arrays.asList(new ValidationException.ErrorMessageFor(errorMessageForUser, errorMessageForDev));
        return ResponseEntity.badRequest().body(errors);
    }

}
