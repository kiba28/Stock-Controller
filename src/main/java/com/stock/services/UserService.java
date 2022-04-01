package com.stock.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.stock.dto.UserDTO;
import com.stock.dto.UserFormDTO;
import com.stock.entities.Role;

public interface UserService {

	UserDTO saveUser(UserFormDTO body);

	UserDTO updateUser(Long id, UserFormDTO body);

	Role saveRole(Role role);

	void addRoleToUser(String username, String roleName);

	UserDTO getUser(String name);

	Page<UserDTO> listUsers(PageRequest pageRequest);

	void deleteUser(Long id);

}
