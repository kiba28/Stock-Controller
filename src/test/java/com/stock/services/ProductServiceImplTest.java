package com.stock.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.stock.builders.CategoryBuilder;
import com.stock.builders.ProductBuilder;
import com.stock.entities.Product;
import com.stock.entities.dto.ProductDTO;
import com.stock.repositories.CategoryRepository;
import com.stock.repositories.ProductRepository;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ProductServiceImplTest {

	@Autowired
	private ProductServiceImpl service;

	@MockBean
	private ProductRepository repository;

	@MockBean
	CategoryRepository categoryRepository;

	@Test
	public void deveriaInserirUmAssociado() {
		Product product = ProductBuilder.getProduct();

		when(this.categoryRepository.findById(anyLong()))
				.thenReturn(Optional.of(CategoryBuilder.getCategory()));
		when(this.repository.save(any(Product.class))).thenReturn(product);

		ProductDTO associateDto = this.service.saveProduct(ProductBuilder.getProductFormDTO());

		assertThat(associateDto.getId()).isNotNull();
		assertThat(associateDto.getName()).isEqualTo(product.getName());
		assertThat(associateDto.getPrice()).isEqualTo(product.getPrice());
		assertThat(associateDto.getUnity()).isEqualTo(product.getUnity());
		assertThat(associateDto.getMinStock()).isEqualTo(product.getMinStock());
		assertThat(associateDto.getCategory()).isEqualTo(product.getCategory());
		
	}
}
