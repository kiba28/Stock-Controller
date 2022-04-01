package com.stock.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stock.entities.Movement;

public interface MovementRepository extends JpaRepository<Movement, Long> {

}
