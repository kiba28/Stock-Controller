package com.stock.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stock.entities.Entrance;

public interface EntranceRepository extends JpaRepository<Entrance, Long> {

}
