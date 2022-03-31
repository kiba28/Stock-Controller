package com.stock.dto;

import java.util.List;

import com.stock.entities.Role;
import com.stock.entities.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {

	private Long id;
	private String username;
	private String email;
	private List<Role> roles;

	public UserDTO(User entity) {
		this.id = entity.getId();
		this.username = entity.getName();
		this.email = entity.getEmail();
		this.roles = entity.getRoles();
	}

}
