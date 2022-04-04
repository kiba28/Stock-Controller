package com.stock.dto;

import com.stock.enums.Status;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MovementDTO {

	private Long id;
	private Long productId;
	private Integer amount;
	private double price;
	private double exitPrice;
	private Status status;
}
