package com.stock.movement.dto;

import com.stock.movement.entities.Movement;
import com.stock.movement.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovementDTO {

	private Long id;
	private Integer amount;
	private double price;
	private double exitPrice;
	private Long productId;
	private Status status;
	
	public MovementDTO(Movement entity) {
		this.id = entity.getId();
		this.amount = entity.getAmount();
		this.price = entity.getPrice();
		this.exitPrice = entity.getExitPrice();
		this.productId = entity.getProductId();
		this.status = entity.getStatus();
	}

	
}
