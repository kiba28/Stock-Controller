package com.stock.movement.dto;

import com.stock.movement.enums.Status;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MovementFormDTO {

	private Integer amount;
	private double price;
	private double exitPrice;
	private Long productId;
	private Status status;
	
}
