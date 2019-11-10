package com.money.spendingapi.repository;

import com.money.spendingapi.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    public Category findByCode(Long code);

}
