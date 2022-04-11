package com.stock.product.builders;

import com.stock.product.dto.CategoryDTO;
import com.stock.product.entities.Category;

public class CategoryBuilder {
	
	public Category getCategory() {
		
		Category category = new Category();
		category.setId(1L);
		category.setName("eletronicos");
		category.setListOfProducts(null);
		
		return category;
		
	}
	
	public CategoryDTO getCategoryDTO() {
		
		CategoryDTO dto = new CategoryDTO();

		dto.setId(1L);
		dto.setName("eletronicos");
		
		return dto;
		
	}

}
