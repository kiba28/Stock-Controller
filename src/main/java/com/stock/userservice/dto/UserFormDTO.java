package com.stock.userservice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFormDTO {

	@NotBlank(message = "Required field")
	@Size(min = 6, max = 30)
	private String name;
	@NotBlank(message = "Required field")
	private String email;
	@NotBlank(message = "Required field")
	@Size(min = 8)
	private String password;

}
