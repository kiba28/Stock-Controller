package com.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockFormDTO {
	
	private Long productId;
	private double lastEntrancePrice;
	private double lastExitPrice;
	private int stockQuantity;
    
}
