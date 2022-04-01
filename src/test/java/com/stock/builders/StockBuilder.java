package com.stock.builders;

import com.stock.entities.Stock;

public class StockBuilder {

	public static Stock getStock() {

		Stock sto = new Stock();
		sto.setProductId(1L);
		sto.setPrice(2.44);
		sto.setExitPrice(3.22);
		sto.setStockQuantity(22);

		return sto;

	}

}
