package com.stock.movement.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MovementFormExitDTO {

	@Positive
	@NotNull
	private Integer amount;
	@NotNull
	private Long productId;
	
	public MovementFormExitDTO(MovementDTO entity) {
		
	}
}
