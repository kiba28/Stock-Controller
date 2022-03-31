package com.stock.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stock.dto.EntranceDTO;
import com.stock.dto.EntranceFormDTO;
import com.stock.services.EntranceService;

@RestController
@RequestMapping("/entrance")
public class EntranceController {

	@Autowired
	private EntranceService entranceService;

	@PostMapping
	public ResponseEntity<EntranceDTO> saveProduct(@RequestBody EntranceFormDTO entrance) {

		EntranceDTO saved = entranceService.save(entrance);

		return ResponseEntity.ok(saved);
	}

	@GetMapping
	public ResponseEntity<Page<EntranceDTO>> listAllEntrancePage(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction
		

	) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),"id");

		Page<EntranceDTO> list = entranceService.listEntrance(pageRequest);

		return ResponseEntity.ok().body(list);
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<EntranceDTO> updateEntrance(@PathVariable Long id, @RequestBody EntranceFormDTO entrance) {

		EntranceDTO prod = entranceService.updateEntrance(id, entrance);

		return ResponseEntity.status(HttpStatus.OK).body(prod);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<EntranceDTO> searchEntrance(@PathVariable Long id) {

		EntranceDTO dto = entranceService.findById(id);

		return ResponseEntity.ok().body(dto);

	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<EntranceDTO> deleteProduct(@PathVariable Long id) {

		entranceService.deleteEntrance(id);

		return ResponseEntity.noContent().build();

	}

}
