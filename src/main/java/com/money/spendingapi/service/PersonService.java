package com.money.spendingapi.service;


import com.money.spendingapi.model.Person;
import com.money.spendingapi.repository.PersonRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonService {

    @Autowired
    private PersonRepository repository;

    public List<Person> list() {
        return repository.findAll();
    }

    public Person create(Person person) {
        return repository.save(person);
    }

    public Person findByCode(Long code) {
        return repository.findByCode(code);
    }

    public void remove(Long code) {
        repository.deleteById(code);
    }

    public Person update(Long code, Person person) {
        Person savedPerson = getPersonByCode(code);
        BeanUtils.copyProperties(person, savedPerson, "code");
        return repository.save(savedPerson);
    }

    public void updateActiveProperty(Long code, Boolean active) {
        Person savedPerson = getPersonByCode(code);
        savedPerson.setActive(active);
        repository.save(savedPerson);
    }

    public Person getPersonByCode(Long code) {
        Person savedPerson = repository.findByCode(code);
        if (null == savedPerson) {
            throw new EmptyResultDataAccessException(1);
        }
        return savedPerson;
    }

}
