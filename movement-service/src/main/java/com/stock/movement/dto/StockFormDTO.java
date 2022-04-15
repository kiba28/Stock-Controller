package com.stock.movement.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.stock.movement.response.Stock;

import lombok.Data;

@Data
public class StockFormDTO {
	
	@NotNull
	@NotBlank(message = "Campo Obrigatório")
	private Long productId;
	@NotNull
	@NotBlank(message = "Campo Obrigatório")
	private double price;
	@NotNull
	@NotBlank(message = "Campo Obrigatório")
	private double exitPrice;
	
	@Positive
	private int stockQuantity;
    
public StockFormDTO(Stock entity){
		
		this.productId = entity.getProductId();
		this.price = entity.getPrice();
		this.exitPrice = entity.getExitPrice();
		this.stockQuantity = entity.getStockQuantity();
		}
}
