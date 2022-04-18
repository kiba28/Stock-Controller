package com.stock.movement.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.stock.movement.response.Stock;

@FeignClient(name = "stock-service")
public interface StockProxy {

	@GetMapping(value = "/stock-service/{id}")
	public ResponseEntity<Stock> searchStock(@PathVariable Long id);

	@PutMapping(value = "/stock-service/{id}")
	public ResponseEntity<Stock> updateStock(@PathVariable Long id, @RequestBody Stock body);

}
