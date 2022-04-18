package com.stock.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Stock {

	@Id
	private Long productId;
	private double lastEntrancePrice;
	private double lastExitPrice;
	private int stockQuantity;

}
