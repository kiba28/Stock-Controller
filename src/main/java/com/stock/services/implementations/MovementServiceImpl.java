package com.stock.services.implementations;

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

import com.stock.dto.MovementDTO;
import com.stock.dto.MovementFormDTO;
import com.stock.entities.Movement;
import com.stock.entities.Stock;
import com.stock.enums.Status;
import com.stock.exceptions.ResourceNotFoundException;
import com.stock.repositories.MovementRepository;
import com.stock.repositories.StockRepository;
import com.stock.services.MovementService;

@Service
public class MovementServiceImpl implements MovementService {

	@Autowired
	private MovementRepository repository;

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public MovementDTO save(MovementFormDTO body) {

		Stock stock = stockRepository.findById(body.getProductId())
				.orElseThrow(() -> new ResourceNotFoundException("Produto " + body.getProductId() + " Não existe!"));

		stock.setExitPrice(body.getExitPrice());
		stock.setPrice(body.getPrice());
		if (body.getStatus() == Status.ENTRANCE) {
			stock.entrance(body.getAmount());
		} else {
			stock.exit(body.getAmount());
		}

		stockRepository.save(stock);

		return mapper.map(repository.save(mapper.map(body, Movement.class)), MovementDTO.class);
	}

	@Override
	public MovementDTO updateEntrance(Long id, MovementFormDTO body) {
		Movement movement = repository.findById(id)
				.orElseThrow(() -> new com.stock.exceptions.ResourceNotFoundException("Id not found " + id));
		Stock stock = stockRepository.findById(body.getProductId())
				.orElseThrow(() -> new ResourceNotFoundException("Produto " + body.getProductId() + " Não existe!"));
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

		stockRepository.save(stock);

		return mapper.map(repository.save(mapper.map(body, Movement.class)), MovementDTO.class);
	}

	@Override
	public MovementDTO findById(Long id) {
		Movement movement = repository.findById(id)
				.orElseThrow(() -> new com.stock.exceptions.ResourceNotFoundException("Id not found " + id));
		return mapper.map(movement, MovementDTO.class);

	}

	@Override
	public void deleteEntrance(Long id) {
		Movement movement = repository.findById(id)
				.orElseThrow(() -> new com.stock.exceptions.ResourceNotFoundException("Id not found " + id));
		Stock stock = stockRepository.findById(movement.getProductId()).orElseThrow(
				() -> new com.stock.exceptions.ResourceNotFoundException("Id not found " + movement.getProductId()));
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
