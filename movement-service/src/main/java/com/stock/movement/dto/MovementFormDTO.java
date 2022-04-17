package com.stock.movement.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import com.stock.movement.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovementFormDTO {

	@Positive
	@NotBlank
	private Integer amount;
	@NotBlank
	private double price;
	@NotBlank
	private Long productId;
	@NotBlank
	private Status status;
}
