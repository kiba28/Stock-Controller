package com.stock.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserForm {

	@NotBlank(message = "Required field")
	@Size(min = 6, max = 30)
	private String name;
	@NotBlank(message = "Required field")
	private String email;
	@NotBlank(message = "Required field")
	@Size(min = 8)
	private String password;

}
