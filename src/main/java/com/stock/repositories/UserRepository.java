package com.stock.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stock.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByName(String name);

}
