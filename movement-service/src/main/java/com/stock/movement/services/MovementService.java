package com.stock.movement.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.stock.movement.dto.MovementDTO;
import com.stock.movement.dto.MovementFormDTO;

public interface MovementService {

	MovementDTO save(MovementFormDTO body);

	MovementDTO updateEntrance(Long id, MovementFormDTO body);

	MovementDTO findById(Long id);

	void deleteEntrance(Long id);

	Page<MovementDTO> listEntrance(Pageable paginacao);
}
