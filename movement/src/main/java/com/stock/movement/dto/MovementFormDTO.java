package com.stock.movement.dto;

import com.stock.movement.enums.Status;

import lombok.Data;

@Data
public class MovementFormDTO {

	private Long productId;
	private Integer amount;
	private double price;
	private double exitPrice;
	private Status status;
}
