package com.money.spendingapi.repository;

import com.money.spendingapi.model.Entry;
import com.money.spendingapi.repository.filter.EntryFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class EntryRepositoryImpl implements EntryRepositoryQuery {

    @Autowired
    private EntityManager manager;

    @Override
    public List<Entry> filter(EntryFilter entryFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Entry> criteria = builder.createQuery(Entry.class);
        Root<Entry> root = criteria.from(Entry.class);


        Predicate[] predicates = createConstraintsForFilter(entryFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Entry> query = manager.createQuery(criteria);
        return query.getResultList();
    }

    private Predicate[] createConstraintsForFilter(EntryFilter entryFilter, CriteriaBuilder builder, Root<Entry> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (!StringUtils.isEmpty(entryFilter.getDescription())) {
            predicates.add(builder.like(
                    builder.lower(root.get("description")), "%"+entryFilter.getDescription().toLowerCase()+"%"));
        }

        if (entryFilter.getExperyDateFrom() != null) {
            predicates.add(
                    builder.greaterThanOrEqualTo(root.get("experyDate"), entryFilter.getExperyDateFrom()));
        }

        if(entryFilter.getExperyDateTo() != null) {
            predicates.add(
                    builder.lessThanOrEqualTo(root.get("experyDate"), entryFilter.getExperyDateTo()));
        }

        return predicates.toArray(new Predicate[ predicates.size() ]);
    }
}
