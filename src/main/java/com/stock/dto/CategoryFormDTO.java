package com.stock.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryFormDTO {
	@Size(min = 5, max = 150, message = "O onome deve conter de 5 a 150 caracteres")
	@NotBlank(message = "Campo Obrigatório")
	private String name;

}
