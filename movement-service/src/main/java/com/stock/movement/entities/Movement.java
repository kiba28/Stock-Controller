package com.stock.movement.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.stock.movement.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Movement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer amount;
	private double price;
	private double exitPrice;
	private Long productId;
	@Enumerated(EnumType.STRING)
	private Status status;
	private LocalDateTime createdAt = LocalDateTime.now();
	
	
	
	public double percenctagePrice(double perc) {
		this.exitPrice= price+ (price * (perc/100)); 
		return exitPrice;
	}

}