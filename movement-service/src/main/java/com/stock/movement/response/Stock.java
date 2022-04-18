package com.stock.movement.response;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

	@Id
	private Long productId;
	private double lastEntrancePrice;
	private double lastExitPrice;
	private int stockQuantity;

	public void entrance(Integer qtd) {
		this.stockQuantity += qtd;
	}

	public void exit(Integer qtd) {
		this.stockQuantity -= qtd;
	}
}

