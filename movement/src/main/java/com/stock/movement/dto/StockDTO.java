package com.stock.movement.dto;

import com.stock.movement.response.Stock;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockDTO {

	private Long productId;
	private double price;
	private double exitPrice;
	private int stockQuantity;
	
	public StockDTO(Stock entity){
		
		this.productId = entity.getProductId();
		this.price = entity.getPrice();
		this.exitPrice = entity.getExitPrice();
		this.stockQuantity = entity.getStockQuantity();
		}
	}
