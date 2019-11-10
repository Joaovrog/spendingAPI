package com.money.spendingapi.repository;

import com.money.spendingapi.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryRepository extends JpaRepository<Entry, Long> {

    public Entry findByCode(Long code);
}
