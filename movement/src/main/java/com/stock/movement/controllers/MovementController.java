package com.stock.movement.controllers;

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

import com.stock.movement.dto.MovementDTO;
import com.stock.movement.dto.MovementFormDTO;
import com.stock.movement.services.MovementService;

@RestController
@RequestMapping("/movement-service")
public class MovementController {

	@Autowired
	private MovementService movementService;

	@PostMapping
	public ResponseEntity<MovementDTO> saveEntrance(@RequestBody MovementFormDTO entrance) {

		MovementDTO saved = movementService.save(entrance);

		return ResponseEntity.ok(saved);
	}

	@GetMapping
	public ResponseEntity<Page<MovementDTO>> listAllEntrancePage(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction

	) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), "id");

		Page<MovementDTO> list = movementService.listEntrance(pageRequest);

		return ResponseEntity.ok().body(list);
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<MovementDTO> updateEntrance(@PathVariable Long id, @RequestBody MovementFormDTO entrance) {

		MovementDTO prod = movementService.updateEntrance(id, entrance);

		return ResponseEntity.status(HttpStatus.OK).body(prod);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<MovementDTO> searchEntrance(@PathVariable Long id) {

		MovementDTO dto = movementService.findById(id);

		return ResponseEntity.ok().body(dto);

	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<MovementDTO> deleteEntrance(@PathVariable Long id) {

		movementService.deleteEntrance(id);

		return ResponseEntity.noContent().build();

	}

}
