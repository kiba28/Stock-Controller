package com.stock.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.dto.StockDTO;
import com.stock.entities.Stock;
import com.stock.exceptions.ResourceNotFoundException;
import com.stock.repositories.StockRepository;

@Service
public class StockServiceImpl implements StockService {

	@Autowired
	private StockRepository stockRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public StockDTO findById(Long id) {
		Stock stock = stockRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
		return mapper.map(stock, StockDTO.class);
	}

}
