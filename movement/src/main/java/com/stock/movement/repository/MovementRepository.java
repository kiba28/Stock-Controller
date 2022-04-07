package com.stock.movement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stock.movement.entities.Movement;

public interface MovementRepository extends JpaRepository<Movement, Long> {

}
