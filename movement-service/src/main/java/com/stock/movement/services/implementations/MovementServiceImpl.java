package com.stock.movement.services.implementations;

import java.math.BigDecimal;
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
import com.stock.movement.dto.MovementFormExitDTO;
import com.stock.movement.entities.Movement;
import com.stock.movement.enums.Status;
import com.stock.movement.exceptions.ResourceNotFoundException;
import com.stock.movement.proxy.StockProxy;
import com.stock.movement.repository.MovementRepository;
import com.stock.movement.response.Stock;
import com.stock.movement.services.MovementService;

@Service
public class MovementServiceImpl implements MovementService {

	@Autowired
	private MovementRepository repository;

	@Autowired
	private StockProxy stockProxy;

	@Autowired
	private ModelMapper mapper;

	@Override
	public MovementDTO save(MovementFormDTO body) {

		Stock stock = stockProxy.getStockid(body.getProductId());
		
		Movement move = new Movement();
		copyDtoToEntity(body, move);
		
		stock.entrance(body.getAmount());
		double valor =(move.percenctagePrice((body.getPercentage()* move.getPrice())/100)*move.getPrice());
		stock.setExitPrice(valor);
		stock.setPrice(body.getPrice());
		
		
		stockProxy.saveStock(stock);

		move.setStatus(Status.ENTRANCE);
		move = repository.save(move);

		MovementDTO dto = new MovementDTO(move);
		dto.setTotal(move.getPrice()*move.getAmount());
		
		return dto;
	}
	
	@Override
	public MovementDTO saveExit(MovementFormExitDTO body) {
       Stock stock = stockProxy.getStockid(body.getProductId());
		
		Movement move = new Movement();
		copyDtoToEntityExit(body, move);
		
		stock.exit(body.getAmount());
		
		
		stockProxy.saveStock(stock);

		move.setStatus(Status.EXIT);
		move = repository.save(move);

		MovementDTO dto = new MovementDTO(move);
		dto.setTotal(move.getExitPrice()*move.getAmount());
		return dto;
	}

	@Override
	public MovementDTO updateEntrance(Long id, MovementFormDTO body) {
		Movement movement = repository.findById(id)
				.orElseThrow(() -> new com.stock.movement.exceptions.ResourceNotFoundException("Id not found " + id));
		var stock = stockProxy.getStockid(body.getProductId());
		if (stock == null) {
			throw new ResourceNotFoundException("Produto  " + body.getProductId() + " Não existe no estoque!");
		}
		if (movement.getStatus() == Status.ENTRANCE) {
			stock.exit(movement.getAmount());
		} else {
			stock.entrance(movement.getAmount());
		}
		stock.setExitPrice(movement.percenctagePrice(body.getPercentage()));
		stock.setPrice(body.getPrice());
		if (body.getStatus() == Status.ENTRANCE) {
			stock.entrance(body.getAmount());
		} else {
			stock.exit(body.getAmount());
		}

		stockProxy.saveStock(stock);

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
		if (stock == null) {
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

	private void copyDtoToEntity(MovementFormDTO dto, Movement entity) {
		entity.setProductId(dto.getProductId());
		entity.setPrice(dto.getPrice());
		entity.setExitPrice(entity.percenctagePrice(dto.getPercentage()));
		entity.setAmount(dto.getAmount());
		entity.setStatus(dto.getStatus());
	}
	
	private void copyDtoToEntityExit(MovementFormExitDTO dto, Movement entity) {
		Stock stock = stockProxy.getStockid(dto.getProductId());
		entity.setProductId(dto.getProductId());
		entity.setPrice(stock.getPrice());
		entity.setExitPrice(stock.getExitPrice());
		entity.setAmount(dto.getAmount());
		entity.setStatus(Status.EXIT);
	}

	
}
