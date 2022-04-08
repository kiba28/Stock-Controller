package com.stock.product.dto;

import java.io.Serializable;

import com.stock.product.entities.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String unity;
	private Category category;
	
	public ProductDTO(Product entity){
		this.id = entity.getId();
		this.name = entity.getName();
		this.unity = entity.getUnity();
	        this.category = entity.getCategory();
	}

}
