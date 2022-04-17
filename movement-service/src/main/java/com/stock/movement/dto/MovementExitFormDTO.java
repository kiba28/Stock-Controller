package com.stock.movement.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovementExitFormDTO {

	@Positive
	@NotBlank
	private Integer amount;
	@NotBlank
	private Long productId;
	
}
