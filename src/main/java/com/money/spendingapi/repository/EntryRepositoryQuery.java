package com.money.spendingapi.repository;

import com.money.spendingapi.model.Entry;
import com.money.spendingapi.repository.filter.EntryFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EntryRepositoryQuery {

    public Page<Entry> filter(EntryFilter entryFilter, Pageable pageable);
}
