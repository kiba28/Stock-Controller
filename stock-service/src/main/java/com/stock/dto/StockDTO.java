package com.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDTO {

	private Long productId;
	private double lastEntrancePrice;
	private double lastExitPrice;
	private int stockQuantity;
	
}
