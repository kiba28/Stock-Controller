package com.stock.movement.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.stock.movement.response.Stock;

@FeignClient(name = "stock-service")
public interface StockProxy {

	@GetMapping(value = "/stock-service")
	public Stock getStock();

	@GetMapping(value = "/stock-service/{productId}")
	public Stock getStockid(
			@PathVariable("productId") Long productId);

	@PostMapping(value = "/stock-service/{productId}/{price}/{exitPrice}/{stockQuantity}")
	public Stock saveStock(
			@PathVariable("productId") Long productId, 
			@PathVariable("price") double price,
			@PathVariable("exitPrice") double exitPrice, 
			@PathVariable("stockQuantity") int stockQuantity

	);

	@PostMapping(value = "/stock-service/{productId}/{price}/{exitPrice}/{stockQuantity}")
	public Stock updateStock(
			@PathVariable("productId") Long productId, 
			@PathVariable("price") double price,
			@PathVariable("exitPrice") double exitPrice, 
			@PathVariable("stockQuantity") int stockQuantity

	);

}
