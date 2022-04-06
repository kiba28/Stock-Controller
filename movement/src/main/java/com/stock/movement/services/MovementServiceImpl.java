package com.stock.movement.services;

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

import com.stock.movement.dto.MovementDTO;
import com.stock.movement.dto.MovementFormDTO;
import com.stock.movement.entities.Movement;
import com.stock.movement.enums.Status;
import com.stock.movement.exceptions.ResourceNotFoundException;
import com.stock.movement.proxy.StockProxy;
import com.stock.movement.repository.MovementRepository;


@Service
public class MovementServiceImpl implements MovementService {

	@Autowired
	private MovementRepository repository;

//	@Autowired
//	private StockRepository stockRepository;
	
	@Autowired
	private StockProxy stockProxy;

	@Autowired
	private ModelMapper mapper;

	@Override
	public MovementDTO save(MovementFormDTO body) {

	  	var stock = stockProxy.getStockid(body.getProductId());
				if(stock == null) {
					new ResourceNotFoundException("Produto  " + body.getProductId() + " Não existe no estoque!");	
				} 

		stock.setExitPrice(body.getExitPrice());
		stock.setPrice(body.getPrice());
		if (body.getStatus() == Status.ENTRANCE) {
			stock.entrance(body.getAmount());
		} else {
			stock.exit(body.getAmount());
		}

		stockProxy.saveStock(body.getProductId(),body.getPrice(),body.getExitPrice(),body.getAmount());

		return mapper.map(repository.save(mapper.map(body, Movement.class)), MovementDTO.class);
	}

	@Override
	public MovementDTO updateEntrance(Long id, MovementFormDTO body) {
		Movement movement = repository.findById(id)
				.orElseThrow(() -> new com.stock.movement.exceptions.ResourceNotFoundException("Id not found " + id));
		var stock = stockProxy.getStockid(body.getProductId());
		if(stock == null) {
			new ResourceNotFoundException("Produto  " + body.getProductId() + " Não existe no estoque!");	
		} 
		if (movement.getStatus() == Status.ENTRANCE) {
			stock.exit(movement.getAmount());
		} else {
			stock.entrance(movement.getAmount());
		}
		stock.setExitPrice(body.getExitPrice());
		stock.setPrice(body.getPrice());
		if (body.getStatus() == Status.ENTRANCE) {
			stock.entrance(body.getAmount());
		} else {
			stock.exit(body.getAmount());
		}

		stockProxy.saveStock(body.getProductId(),body.getPrice(),body.getExitPrice(),body.getAmount());

		return mapper.map(repository.save(mapper.map(body, Movement.class)), MovementDTO.class);
	}

	@Override
	public MovementDTO findById(Long id) {
		Movement movement = repository.findById(id)
				.orElseThrow(() -> new com.stock.movement.exceptions.ResourceNotFoundException("Id not found " + id));
		return mapper.map(movement, MovementDTO.class);

	}

	@Override
	public void deleteEntrance(Long id) {
		Movement movement = repository.findById(id)
				.orElseThrow(() -> new com.stock.movement.exceptions.ResourceNotFoundException("Id not found " + id));
		var stock = stockProxy.getStockid(movement.getProductId());
		if(stock == null) {
			new ResourceNotFoundException("Produto  " + movement.getProductId() + " Não existe no estoque!");	
		} 
		if (movement.getStatus() == Status.ENTRANCE) {
			stock.exit(movement.getAmount());
		} else {
			stock.entrance(movement.getAmount());
		}

		repository.delete(movement);
	}

	@Override
	public Page<MovementDTO> listEntrance(@PageableDefault(sort = "id", direction = Direction.ASC) Pageable paginacao)

	{
		Page<Movement> page = repository.findAll(paginacao);

		List<MovementDTO> list = page.getContent().stream().map(Entrance -> mapper.map(Entrance, MovementDTO.class))
				.collect(Collectors.toList());
		return new PageImpl<>(list);
	}

}
