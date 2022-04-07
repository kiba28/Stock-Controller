package com.stock.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stock.product.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
