package com.stock.movement.controllers;

import javax.validation.Valid;

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
	public ResponseEntity<MovementDTO> saveMovement(@RequestBody @Valid MovementFormDTO movement) {
		MovementDTO saved = movementService.save(movement);

		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	@GetMapping
	public ResponseEntity<Page<MovementDTO>> listAllMovementsAsPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), "id");

		Page<MovementDTO> list = movementService.listMovements(pageRequest);

		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<MovementDTO> updateMovement(@PathVariable Long id,
			@RequestBody @Valid MovementFormDTO movement) {
		MovementDTO prod = movementService.updateMovement(id, movement);

		return ResponseEntity.status(HttpStatus.OK).body(prod);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<MovementDTO> searchMovement(@PathVariable Long id) {
		MovementDTO dto = movementService.findById(id);

		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Object> deleteMovement(@PathVariable Long id) {
		movementService.deleteLastMovement();

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
