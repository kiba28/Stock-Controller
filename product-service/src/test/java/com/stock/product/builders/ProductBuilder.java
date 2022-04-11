package com.stock.product.builders;

import com.stock.product.dto.ProductDTO;
import com.stock.product.dto.ProductFormDTO;
import com.stock.product.entities.Category;
import com.stock.product.entities.Product;

public class ProductBuilder {
	
	public ProductDTO getProductDTO() {
		
		Category category = new Category();
		category.setId(1L);
		category.setName("utensilio");
		category.setListOfProducts(null);
		
		
		ProductDTO dto = new ProductDTO();
		dto.setId(1L);
		dto.setName("mochila");
		dto.setUnity("KG");
		dto.setCategory(category);
		
	
		
		return dto;
	}
	
	public Product getProduct() {
		
		Product product = new Product();
		
		product.setId(1L);
		product.setName("mochila gigante");
		product.setUnity("KG");
		product.setCategory(null);
		
		return product;
		
	}
	
	public ProductFormDTO getProductFormDTO() {
		
		ProductFormDTO form = new ProductFormDTO();
		form.setName("lala");
		form.setCategoryId(1L);
		form.setUnity("KG");
		
		return form;
		
	}
	

	

	
	

}
