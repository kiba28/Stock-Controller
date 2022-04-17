package com.stock.product.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.stock.product.entities.Category;
import com.stock.product.entities.Product;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductFormDTO {

	@Size(min = 5, max = 150, message = "O onome deve conter de 5 a 150 caracteres")
	@NotBlank(message = "Campo Obrigatório")
	private String name;
	@NotBlank(message = "Campo Obrigatório")
	private String unity;
	private long categoryId;
	private Category category;
	
	public ProductFormDTO(Product entity){
		this.name = entity.getName();
		this.unity = entity.getUnity();
	}
	
}
