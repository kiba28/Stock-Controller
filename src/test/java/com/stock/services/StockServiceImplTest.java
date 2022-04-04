package com.stock.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.stock.builders.StockBuilder;
import com.stock.dto.StockDTO;
import com.stock.entities.Stock;
import com.stock.exceptions.ResourceNotFoundException;
import com.stock.repositories.StockRepository;
import com.stock.services.implementations.StockServiceImpl;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class StockServiceImplTest {

	@Autowired
	private StockServiceImpl service;

	@MockBean
	StockRepository repository;

	@Test
	void deveriaBuscarUmEstoquePeloId() {
		Stock stock = StockBuilder.getStock();

		when(this.repository.findById(anyLong())).thenReturn(Optional.of(stock));

		StockDTO stockDto = this.service.findById(1L);

		assertThat(stockDto.getProductId()).isNotNull();
		assertThat(stockDto.getPrice()).isEqualTo(stock.getPrice());
		assertThat(stockDto.getExitPrice()).isEqualTo(stock.getExitPrice());
		assertThat(stockDto.getStockQuantity()).isEqualTo(stock.getStockQuantity());
	}

	@Test
	public void naoDeveriaBuscarUmEstoquePoisNaoExisteEstoqueComOIdInformado() {
		when(this.repository.findById(anyLong())).thenReturn(Optional.empty());

		assertThatExceptionOfType(ResourceNotFoundException.class)
				.isThrownBy(() -> this.service.findById(1L));
	}
}
