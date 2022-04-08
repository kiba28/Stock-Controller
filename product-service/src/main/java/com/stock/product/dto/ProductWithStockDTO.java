package com.stock.product.dto;

import java.io.Serializable;

import com.stock.product.entities.Category;
import com.stock.product.response.Stock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductWithStockDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String unity;
	private String categoryName;
	private double price;
	private double exitPrice;
	private int stockQuantity;

}
