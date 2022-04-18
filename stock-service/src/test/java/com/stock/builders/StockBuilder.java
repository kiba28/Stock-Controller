package com.stock.builders;

import com.stock.dto.StockFormDTO;
import com.stock.entity.Stock;

public class StockBuilder {

	public static Stock getStock() {
		Stock stock = new Stock();
		stock.setProductId(1L);
		stock.setLastEntrancePrice(2);
		stock.setLastExitPrice(0);
		stock.setStockQuantity(2);

		return stock;
	}

	public static StockFormDTO getStockFormDTO() {

		StockFormDTO stock = new StockFormDTO();
		stock.setProductId(1L);
		stock.setLastEntrancePrice(0);
		stock.setLastExitPrice(0);
		stock.setStockQuantity(0);

		return stock;
	}

}
