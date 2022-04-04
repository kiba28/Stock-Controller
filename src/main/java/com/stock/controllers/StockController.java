package com.stock.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stock.dto.StockDTO;
import com.stock.services.StockService;

@RestController
@RequestMapping("/stock")
public class StockController {

	@Autowired
	private StockService stockService;

	@GetMapping(path = "/{id}")
	public ResponseEntity<StockDTO> searchStock(@PathVariable Long id) {

		StockDTO dto = stockService.findById(id);

		return ResponseEntity.ok().body(dto);

	}

}
