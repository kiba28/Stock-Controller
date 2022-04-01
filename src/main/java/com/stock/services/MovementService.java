package com.stock.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.stock.dto.MovementDTO;
import com.stock.dto.MovementFormDTO;

public interface MovementService {

	MovementDTO save(MovementFormDTO body);

	MovementDTO updateEntrance(Long id, MovementFormDTO body);

	MovementDTO findById(Long id);

	void deleteEntrance(Long id);

	Page<MovementDTO> listEntrance(Pageable paginacao);
}
