package com.stock.movement.response;

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
	private double price;
	private double exitPrice;
	private int stockQuantity;

	public void entrance(Integer qtd) {
		this.stockQuantity += qtd;
	}

	public void exit(Integer qtd) {
		this.stockQuantity -= qtd;
	}
}

