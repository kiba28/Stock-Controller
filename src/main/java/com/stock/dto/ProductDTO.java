package com.stock.dto;

import java.io.Serializable;

import com.stock.entities.Category;
import com.stock.entities.Product;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private double price;
	private String unity;
	private double minStock;

	private Category category;

	public ProductDTO(Product entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.minStock = entity.getMinStock();
		this.price = entity.getPrice();
		this.unity = entity.getUnity();
	}
}
