package com.stock.movement.dto;

import java.time.LocalDateTime;

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
	private Long productId;
	private Status status;
	private Double total;
	private LocalDateTime createdAt;

}
