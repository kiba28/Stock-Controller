package com.stock.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stock.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
