package com.stock.userservice.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.stock.userservice.dto.UserDTO;
import com.stock.userservice.dto.UserFormDTO;
import com.stock.userservice.entities.Role;

public interface UserService {

	UserDTO saveUser(UserFormDTO body);

	UserDTO updateUser(Long id, UserFormDTO body);

	Role saveRole(Role role);

	void addRoleToUser(String username, String roleName);

	UserDTO getUser(String name);

	Page<UserDTO> listUsers(PageRequest pageRequest);

	void deleteUser(Long id);

}
