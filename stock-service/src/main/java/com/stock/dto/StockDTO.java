package com.stock.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockDTO {

	private Long productId;
	private double price;
	private double exitPrice;
	private int stockQuantity;
}
