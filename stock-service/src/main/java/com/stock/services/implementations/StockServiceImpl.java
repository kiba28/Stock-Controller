package com.stock.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
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
import com.stock.services.StockService;

@Service
public class StockServiceImpl implements StockService {
   
	private	 String actionException = "Id not found ";

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public StockDTO search(Long id) {
		Stock stock = stockRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(actionException + id));
		return mapper.map(stock, StockDTO.class);
	}

	@Override
	public StockDTO save(StockFormDTO body) {
		return mapper.map(stockRepository.save(mapper.map(body, Stock.class)), StockDTO.class);
	}

	@Override
	public Page<StockDTO> listAsPage(@PageableDefault(sort = "id", direction = Direction.ASC) Pageable pageInfo) {
		Page<Stock> page = stockRepository.findAll(pageInfo);

		List<StockDTO> list = page.getContent().stream().map(Stock -> mapper.map(Stock, StockDTO.class))
				.collect(Collectors.toList());
		return new PageImpl<>(list);
	}

	@Override
	public void delete(Long id) {
		Stock stock = stockRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(actionException + id));
		if (stock.getStockQuantity() == 0) {
			stockRepository.deleteById(id);
		} else {
			throw new ResourceNotFoundException("Cannot delete stock with products in it. Still "
					+ stock.getStockQuantity() + " products in stock.");
		}

	}

	@Override
	public StockDTO update(Long id, StockFormDTO body) {
		Stock stock = stockRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(actionException + id));

		BeanUtils.copyProperties(body, stock, "id");
		return mapper.map(stockRepository.save(stock), StockDTO.class);
	}

}
