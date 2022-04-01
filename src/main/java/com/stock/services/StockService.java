package com.stock.services;

import com.stock.dto.StockDTO;

public interface StockService {

	StockDTO findById(Long id);
	
}
