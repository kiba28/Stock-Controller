package com.stock.product.builders;

import com.stock.product.response.Stock;

public class StockBuilder {
	
	public static Stock getStock() {
		Stock stock = new Stock();
		stock.setProductId(ProductBuilder.getProduct().getId());
		stock.setPrice(0);
		stock.setExitPrice(0);
		stock.setStockQuantity(0);
		
		return stock;
	}


}
