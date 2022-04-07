package com.stock.product.dto;

import com.stock.product.entities.Category;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDTO {

	private Long id;
	private String name;

	public CategoryDTO(Category entity) {
		this.id = entity.getId();
		this.name = entity.getName();
	}
}
