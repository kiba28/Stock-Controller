package com.stock.builders;

import com.stock.entities.Role;

public class RoleBuilder {
	public static Role getRole() {
		Role role = new Role();
		role.setId(1L);
		role.setName("Gerente");
		
		return role;
	}
}
