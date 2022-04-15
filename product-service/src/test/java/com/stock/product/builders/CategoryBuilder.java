package com.stock.product.builders;

import com.stock.product.dto.CategoryDTO;
import com.stock.product.dto.CategoryFormDTO;
import com.stock.product.entities.Category;

public final class CategoryBuilder {
	public static Category getCategory() {
		Category category = new Category();
		category.setId(1L);
		category.setName("Escolar");

		return category;
	}
	
	public static CategoryDTO getCategoryDTO() {
		CategoryDTO category = new CategoryDTO();
		category.setId(1L);
		category.setName("Escolar");

		return category;
	}
	
	public static CategoryFormDTO getCategoryFormDTO() {
		CategoryFormDTO category = new CategoryFormDTO();
		category.setName("Escolar");

		return category;
	}
}