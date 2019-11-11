package com.money.spendingapi.service;

import com.money.spendingapi.model.Entry;
import com.money.spendingapi.model.Person;
import com.money.spendingapi.repository.EntryRepository;
import com.money.spendingapi.repository.PersonRepository;
import com.money.spendingapi.repository.filter.EntryFilter;
import com.money.spendingapi.service.exception.InexistentOrInactivePersonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EntryService {

    @Autowired
    private EntryRepository repository;

    @Autowired
    private PersonRepository personRepository;

    public Page<Entry> filter(EntryFilter entryFilter, Pageable pageable) {
        return repository.filter(entryFilter, pageable);
    }

    public Entry findByCode(Long code) {
        return repository.findByCode(code);
    }

    public Entry create(Entry entry) {
        Person foundPerson = personRepository.findByCode(entry.getPerson().getCode());
        if(foundPerson == null || !foundPerson.getActive()) {
            throw new InexistentOrInactivePersonException();
        }

        return repository.save(entry);
    }

    public void remove(Long code) {
        repository.deleteById(code);
    }
}
