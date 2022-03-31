package com.stock.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
