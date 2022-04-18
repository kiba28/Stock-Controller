package com.stock.movement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stock.movement.entities.Movement;
import com.stock.movement.enums.Status;

public interface MovementRepository extends JpaRepository<Movement, Long> {
	
	Movement findTopByOrderByIdDesc();
	Movement findTopByStatusOrderByIdDesc(Status status);

}
