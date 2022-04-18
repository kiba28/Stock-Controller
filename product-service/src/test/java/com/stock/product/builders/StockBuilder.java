package com.stock.product.builders;

import com.stock.product.response.Stock;

public class StockBuilder {
	
	public static Stock getStock() {
		Stock stock = new Stock();
		stock.setProductId(ProductBuilder.getProduct().getId());
		stock.setLastEntrancePrice(0);
		stock.setLastExitPrice(0);
		stock.setStockQuantity(0);
		
		return stock;
	}


}
