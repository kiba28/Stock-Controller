package com.stock.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stock.entities.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {

}
