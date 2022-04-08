package com.stock.movement.dto;

import com.stock.movement.response.Stock;

import lombok.Data;

@Data
public class StockFormDTO {
	
	private Long productId;
	private double price;
	private double exitPrice;
	private int stockQuantity;
    
public StockFormDTO(Stock entity){
		
		this.productId = entity.getProductId();
		this.price = entity.getPrice();
		this.exitPrice = entity.getExitPrice();
		this.stockQuantity = entity.getStockQuantity();
		}
}
