package com.stock.product.dto;

import java.io.Serializable;

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
	private double lastEntrancePrice;
	private double lastExitPrice;
	private int stockQuantity;

}
