package com.stock.dto;

import com.stock.enums.Status;

import lombok.Data;

@Data
public class MovementFormDTO {

	private Long productId;
	private Integer amount;
	private double price;
	private double exitPrice;
	private Status status;
}
