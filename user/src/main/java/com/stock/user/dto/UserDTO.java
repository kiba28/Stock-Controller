package com.stock.user.dto;

import java.util.List;

import com.stock.user.entities.Role;
import com.stock.user.entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	private Long id;
	private String name;
	private String email;
	private List<Role> roles;

	public UserDTO(User entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.email = entity.getEmail();
		this.roles = entity.getRoles();
	}

}