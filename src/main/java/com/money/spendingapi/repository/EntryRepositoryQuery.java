package com.money.spendingapi.repository;

import com.money.spendingapi.model.Entry;
import com.money.spendingapi.repository.filter.EntryFilter;

import java.util.List;

public interface EntryRepositoryQuery {

    public List<Entry> filter(EntryFilter entryFilter);
}
