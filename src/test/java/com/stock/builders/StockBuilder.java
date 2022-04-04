package com.stock.builders;

import com.stock.entities.Stock;

public class StockBuilder {

	public static Stock getStock() {

		Stock stock = new Stock();
		stock.setProductId(1L);
		stock.setPrice(2.44);
		stock.setExitPrice(3.22);
		stock.setStockQuantity(22);

		return stock;

	}

}
