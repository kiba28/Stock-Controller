package com.stock.builders;

import com.stock.entities.Product;
import com.stock.entities.dto.ProductDTO;
import com.stock.entities.dto.ProductFormDTO;

public final class ProductBuilder {
	public static Product getProduct() {
		Product prod = new Product();
		prod.setId(1L);
		prod.setName("Mochila");
		prod.setPrice(50.99);
		prod.setUnity("UN");
		prod.setMinStock(5);
		prod.setCategory(CategoryBuilder.getCategory());

		return prod;
	}

	public static ProductDTO getProductDTO() {
		ProductDTO prod = new ProductDTO();
		prod.setId(1L);
		prod.setName("Mochila");
		prod.setPrice(50.99);
		prod.setUnity("UN");
		prod.setMinStock(5);
		prod.setCategory(CategoryBuilder.getCategory());

		return prod;
	}

	public static ProductFormDTO getProductFormDTO() {
		ProductFormDTO prod = new ProductFormDTO();
		prod.setName("Mochila");
		prod.setPrice(50.99);
		prod.setUnity("UN");
		prod.setMinStock(5);
		prod.setCategoryID(1L);
		prod.setCategory(CategoryBuilder.getCategory());

		return prod;
	}
}
