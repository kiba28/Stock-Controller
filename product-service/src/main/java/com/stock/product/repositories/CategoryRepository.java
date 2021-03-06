package com.stock.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stock.product.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
