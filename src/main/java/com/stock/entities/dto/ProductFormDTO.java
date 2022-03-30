package com.stock.entities.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.stock.entities.Category;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductFormDTO {

	@Size(min = 5, max = 150, message = "O onome deve conter de 5 a 150 caracteres")
	@NotBlank(message = "Campo Obrigatório")
	private String name;
	@Positive(message = "Preço deve ser um valor positivo")
	private double price;
	@NotBlank(message = "Campo Obrigatório")
	private String unity;
	@Positive(message = "Estoque deve ser um valor positivo")
	private double minStock;
	private long categoryID;
	private Category category;
}
