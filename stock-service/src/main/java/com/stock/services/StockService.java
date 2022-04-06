package com.stock.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.stock.dto.StockDTO;
import com.stock.dto.StockFormDTO;

public interface StockService {

	StockDTO save(StockFormDTO body);
	
	StockDTO findById(Long id);
	
	Page<StockDTO> listStock(Pageable paginacao);

}
