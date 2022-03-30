package com.stock.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stock.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
