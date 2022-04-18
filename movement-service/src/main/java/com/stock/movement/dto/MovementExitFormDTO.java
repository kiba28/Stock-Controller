package com.stock.movement.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovementExitFormDTO {

	@Positive
	@NotNull
	private Integer amount;
	@NotNull
	private Long productId;
	
}
