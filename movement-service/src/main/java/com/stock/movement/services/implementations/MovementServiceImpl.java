package com.stock.movement.services.implementations;

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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stock.movement.dto.MovementDTO;
import com.stock.movement.dto.MovementFormDTO;
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

		ResponseEntity<Stock> stockResponseEntity = stockProxy.searchStock(body.getProductId());

		Stock stock = stockResponseEntity.getBody();
		Movement movement = mapper.map(body, Movement.class);
		if (movement.getStatus() == Status.ENTRANCE) {
			stock.entrance(movement.getAmount());
			stock.setPrice(movement.getPrice());
		} else {
			if (movement.getAmount() > stock.getStockQuantity()) {
				throw new ResourceNotFoundException("Stock don't have enough quantity for this movement");
			}
			stock.exit(movement.getAmount());
			stock.setExitPrice(movement.getPrice());
		}
		stockProxy.updateStock(stock.getProductId(), stock);
		return mapper.map(repository.save(movement), MovementDTO.class);
	}

	@Override
	public MovementDTO updateMovement(Long id, MovementFormDTO body) {
		Movement movement = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
		ResponseEntity<Stock> stockResponseEntity = stockProxy.searchStock(movement.getProductId());
		Stock stock = stockResponseEntity.getBody();

		if (movement.getStatus() == Status.ENTRANCE) {
			stock.exit(movement.getAmount());
		} else {
			stock.entrance(movement.getAmount());
		}
		if (stock.getProductId() == body.getProductId()) {
			BeanUtils.copyProperties(body, movement, "id");
			if (movement.getStatus() == Status.ENTRANCE) {
				stock.entrance(movement.getAmount());
				stock.setPrice(movement.getPrice());
			} else {
				if (movement.getAmount() > stock.getStockQuantity()) {
					throw new ResourceNotFoundException("Stock don't have enough quantity for this movement");
				}
				stock.exit(movement.getAmount());
				stock.setExitPrice(movement.getPrice());
			}
			stockProxy.updateStock(stock.getProductId(), stock);
			return mapper.map(repository.save(movement), MovementDTO.class);
		} else {
			stockProxy.updateStock(stock.getProductId(), stock);

			stockResponseEntity = stockProxy.searchStock(body.getProductId());
			stock = stockResponseEntity.getBody();
			BeanUtils.copyProperties(body, movement, "id");
			if (movement.getStatus() == Status.ENTRANCE) {
				stock.entrance(movement.getAmount());
				stock.setPrice(movement.getPrice());
			} else {
				if (movement.getAmount() > stock.getStockQuantity()) {
					throw new ResourceNotFoundException("Stock don't have enough quantity for this movement");
				}
				stock.exit(movement.getAmount());
				stock.setExitPrice(movement.getPrice());
			}
			return mapper.map(repository.save(movement), MovementDTO.class);
		}
	}

	@Override
	public MovementDTO findById(Long id) {
		Movement movement = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
		return mapper.map(movement, MovementDTO.class);

	}

	@Override
	public void deleteLastMovement() {
		Movement lastMovement = repository.findTopByOrderByIdDesc();
		ResponseEntity<Stock> stockResponseEntity = stockProxy.searchStock(lastMovement.getProductId());
		Stock stock = stockResponseEntity.getBody();

		repository.delete(lastMovement);
		Movement movement = repository.findTopByStatusOrderByIdDesc(lastMovement.getStatus());

		if (lastMovement.getStatus() == Status.ENTRANCE) {
			stock.exit(lastMovement.getAmount());
			stock.setPrice(movement.getPrice());
		} else {
			stock.entrance(lastMovement.getAmount());
			stock.setExitPrice(movement.getPrice());
		}
		stockProxy.updateStock(stock.getProductId(), stock);

	}

	@Override
	public Page<MovementDTO> listMovements(@PageableDefault(sort = "id", direction = Direction.ASC) Pageable pageInfo) {
		Page<Movement> page = repository.findAll(pageInfo);

		List<MovementDTO> list = page.getContent().stream().map(Entrance -> mapper.map(Entrance, MovementDTO.class))
				.collect(Collectors.toList());
		return new PageImpl<>(list);
	}

}
