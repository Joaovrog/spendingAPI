package com.money.spendingapi.repository;

import com.money.spendingapi.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

    public Person findByCode(Long code);
}
