package com.stock.controller;

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

import com.stock.dto.StockDTO;
import com.stock.dto.StockFormDTO;
import com.stock.services.StockService;

@RestController
@RequestMapping("/stock-service")
public class StockController {

	@Autowired
	private StockService stockService;

	@GetMapping
	public ResponseEntity<Page<StockDTO>> listAllStock(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), "productId");

		Page<StockDTO> list = stockService.listStock(pageRequest);

		return ResponseEntity.ok().body(list);
	}

	@PostMapping
	public ResponseEntity<StockDTO> saveStock(@RequestBody @Valid StockFormDTO stock) {

		StockDTO saved = stockService.save(stock);

		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<StockDTO> searchStock(@PathVariable Long id) {

		StockDTO dto = stockService.findByIdStock(id);

		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteStock(@PathVariable Long id) {
		this.stockService.delete(id);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<StockDTO> updateStock(@PathVariable Long id, @RequestBody StockFormDTO body) {
		
		StockDTO stockUpdated = this.stockService.update(id, body);
		
		return ResponseEntity.status(HttpStatus.OK).body(stockUpdated);
	}

}
