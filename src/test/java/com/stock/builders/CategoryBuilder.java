package com.stock.builders;

import com.stock.dto.CategoryDTO;
import com.stock.dto.CategoryFormDTO;
import com.stock.entities.Category;

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
