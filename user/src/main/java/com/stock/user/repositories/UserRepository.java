package com.stock.user.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stock.user.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByName(String name);

}

