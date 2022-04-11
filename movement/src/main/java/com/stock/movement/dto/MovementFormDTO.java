package com.stock.movement.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.stock.movement.enums.Status;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MovementFormDTO {

	@Positive
	@NotNull
	private Integer amount;
	@NotNull
	private double price;
	@NotNull
	private double exitPrice;
	@NotNull
	private Long productId;
	private Status status;
	
}
