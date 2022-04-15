package com.stock.movement.builders;

import org.springframework.http.ResponseEntity;

import com.stock.movement.response.Stock;

public class StockBuilder {
	
	public static ResponseEntity<Stock> getStock() {

		Stock stock = new Stock();
		stock.setProductId(1L);
		stock.setPrice(2.55);
		stock.setExitPrice(2.57);
		stock.setStockQuantity(10);	

		return ResponseEntity.ok(stock);

	}

}
