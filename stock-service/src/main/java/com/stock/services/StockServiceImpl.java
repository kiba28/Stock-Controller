package com.stock.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import com.stock.dto.StockDTO;
import com.stock.dto.StockFormDTO;
import com.stock.entity.Stock;
import com.stock.exceptions.ResourceNotFoundException;
import com.stock.repository.StockRepository;

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

	@Override
	public StockDTO save(StockFormDTO body) {
		return mapper.map(stockRepository.save(mapper.map(body, Stock.class)), StockDTO.class);
	}

	@Override
	public Page<StockDTO> listStock(@PageableDefault(sort = "id", direction = Direction.ASC) Pageable paginacao) {
		Page<Stock> page = stockRepository.findAll(paginacao);

		List<StockDTO> list = page.getContent().stream().map(Stock -> mapper.map(Stock, StockDTO.class))
				.collect(Collectors.toList());
		return new PageImpl<>(list);
	}

}
