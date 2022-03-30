package com.stock.builders;

import com.stock.entities.Category;

public final class CategoryBuilder {
	public static Category getCategory() {
		Category category = new Category();
		category.setId(1L);
		category.setName("Escolares");
		
		return category;
	}
}
