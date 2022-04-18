package com.stock.product.builders;

import com.stock.product.dto.ProductDTO;
import com.stock.product.dto.ProductFormDTO;
import com.stock.product.entities.Product;

public final class ProductBuilder {
	public static Product getProduct() {
		Product prod = new Product();
		prod.setId(1L);
		prod.setName("Mochila");
		prod.setUnity("UN");
		prod.setCategory(CategoryBuilder.getCategory());

		return prod;
	}

	public static ProductDTO getProductDTO() {
		ProductDTO prod = new ProductDTO();
		prod.setId(1L);
		prod.setName("Mochila");
		prod.setUnity("UN");
		prod.setCategory(CategoryBuilder.getCategory());

		return prod;
	}

	public static ProductFormDTO getProductFormDTO() {
		ProductFormDTO prod = new ProductFormDTO();
		prod.setName("Mochila");
		prod.setUnity("UN");
		prod.setCategoryId(1L);
		prod.setCategory(CategoryBuilder.getCategory());

		return prod;
	}
}