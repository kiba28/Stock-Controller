package com.stock.builders;

import java.util.Arrays;

import com.stock.dto.UserDTO;
import com.stock.dto.UserFormDTO;
import com.stock.entities.User;

public class UserBuilder {
	public static User getUser() {
		User user = new User();
		user.setId(1L);
		user.setName("Jose");
		user.setEmail("user@email.com");
		user.setPassword("12345678");

		return user;
	}

	public static UserDTO getUsertDTO() {
		UserDTO user = new UserDTO();
		user.setId(1L);
		user.setName("Jose");
		user.setEmail("user@email.com");
		user.setRoles(Arrays.asList(RoleBuilder.getRole()));

		return user;
	}

	public static UserFormDTO getUserFormDTO() {
		UserFormDTO user = new UserFormDTO();
		user.setName("Jose");
		user.setEmail("user@email.com");
		user.setPassword("12345678");

		return user;
	}
	
}
