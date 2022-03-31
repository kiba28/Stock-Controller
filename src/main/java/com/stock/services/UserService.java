package com.stock.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.stock.dto.UserDTO;
import com.stock.dto.UserForm;
import com.stock.entities.Role;

public interface UserService {

	UserDTO saveUser(UserForm body);

	UserDTO updateUser(Long id, UserForm body);

	Role saveRole(Role role);

	void addRoleToUser(String username, String roleName);

	UserDTO getUser(String name);

	Page<UserDTO> listUsers(PageRequest pageRequest);

	void deleteUser(Long id);

}
