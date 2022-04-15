package com.stock.movement.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.stock.movement.response.Stock;

@FeignClient(name = "stock-service")
public interface StockProxy {

	@GetMapping(value = "/stock-service")
	public Stock getStock();

	@GetMapping(value = "/stock-service/{productId}")
	public Stock getStockid(
			@PathVariable("productId") Long productId);

	@PostMapping(value = "/stock-service")
	public ResponseEntity<Stock> saveStock(@RequestBody Stock stock);

}
