package com.stock.dto;

import lombok.Data;

@Data
public class StockFormDTO {
	
	private Long productId;
	private double price;
	private double exitPrice;
	private int stockQuantity;
    
}
