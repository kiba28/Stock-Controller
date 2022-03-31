package com.stock.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.stock.dto.EntranceDTO;
import com.stock.dto.EntranceFormDTO;

public interface EntranceService {

	EntranceDTO save(EntranceFormDTO body);

	EntranceDTO updateEntrance(Long id, EntranceFormDTO body);

	EntranceDTO findById(Long id);

	void deleteEntrance(Long id);

	Page<EntranceDTO> listEntrance(Pageable paginacao);
}
