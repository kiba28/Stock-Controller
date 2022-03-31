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

import com.stock.dto.EntranceDTO;
import com.stock.dto.EntranceFormDTO;
import com.stock.entities.Entrance;
import com.stock.entities.Product;
import com.stock.repositories.EntranceRepository;
import com.stock.repositories.ProductRepository;

@Service
public class EntranceServiceImpl implements EntranceService {
	
	@Autowired
	private EntranceRepository repository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public EntranceDTO save(EntranceFormDTO body) {
		Product product = productRepository.findById(body.getProductId())
				.orElseThrow(() -> new com.stock.exceptions.ResourceNotFoundException("Id not found " + body.getProductId()));
		product.entrance(body.getAmount());
		
		return mapper.map(repository.save(mapper.map(body, Entrance.class)), EntranceDTO.class);
	}

	@Override
	public EntranceDTO updateEntrance(Long id, EntranceFormDTO body) {
		Entrance entrance = repository.findById(id)	
				.orElseThrow(() -> new com.stock.exceptions.ResourceNotFoundException("Id not found " + id));
		Product product = productRepository.findById(body.getProductId())
				.orElseThrow(() -> new com.stock.exceptions.ResourceNotFoundException("Id not found " + id));
		product.exit(entrance.getAmount());
		product.entrance(body.getAmount());
		entrance.setAmount(body.getAmount());
		
		return mapper.map(repository.save(entrance), EntranceDTO.class);
	}

	@Override
	public EntranceDTO findById(Long id) {
		Entrance entrance = repository.findById(id)
				.orElseThrow(() -> new com.stock.exceptions.ResourceNotFoundException("Id not found " + id));
		return mapper.map(entrance.getId(), EntranceDTO.class);

	}

	@Override
	public void deleteEntrance(Long id) {
		Entrance entrance = repository.findById(id)
				.orElseThrow(() -> new com.stock.exceptions.ResourceNotFoundException("Id not found " + id));
		repository.delete(entrance);
	}

	@Override
	public Page<EntranceDTO> listEntrance(	@PageableDefault(sort = "id", direction = Direction.ASC) Pageable paginacao) 
	
	{
		Page<Entrance> page = repository.findAll(paginacao);

		List<EntranceDTO> list = page.getContent().stream().map(Entrance -> mapper.map(Entrance, EntranceDTO.class))
				.collect(Collectors.toList());
		return new PageImpl<>(list);
	}

}
