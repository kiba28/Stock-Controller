package com.stock.product.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.stock.product.response.Stock;


@FeignClient(name = "stock-service")
public interface StockProxy {

	@GetMapping(value = "/stock-service/{id}")
	public ResponseEntity<Stock> searchStock(@PathVariable Long id);
	
	@PostMapping(value = "/stock-service")
	public ResponseEntity<Stock> saveStock(@RequestBody Stock stock);
	
	@DeleteMapping(value = "/stock-service/{id}")
	public ResponseEntity<Object> deleteStock(@PathVariable Long id);
}
