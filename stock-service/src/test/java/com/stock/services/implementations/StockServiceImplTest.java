package com.stock.services.implementations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.stock.builders.StockBuilder;
import com.stock.dto.StockDTO;
import com.stock.dto.StockFormDTO;
import com.stock.entity.Stock;
import com.stock.exceptions.ResourceNotFoundException;
import com.stock.repository.StockRepository;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class StockServiceImplTest {

	static final Long ID_INEXISTENT = 5L;

	@MockBean
	private StockRepository stockRepository;

	@Autowired
	private StockServiceImpl stockService;

	@Test
	public void ShouldSearchAnStockById() {

		Stock stock = StockBuilder.getStock();

		when(stockRepository.findById(anyLong())).thenReturn(Optional.of(stock));

		StockDTO dto = this.stockService.search(1L);

		assertThat(dto.getProductId()).isNotNull();
		assertThat(dto.getPrice()).isEqualTo(stock.getPrice());
		assertThat(dto.getExitPrice()).isEqualTo(stock.getExitPrice());
		assertThat(dto.getStockQuantity()).isEqualTo(stock.getStockQuantity());

	}

	@Test
	public void ShouldThrowException_WhenSearchStockByIdInexistent() {

		when(stockRepository.findById(anyLong())).thenReturn(Optional.empty());

		assertThatExceptionOfType(ResourceNotFoundException.class)
				.isThrownBy(() -> this.stockService.search(ID_INEXISTENT));

	}

	@Test
	public void ShouldSaveAnStock() {

		StockFormDTO body = StockBuilder.getStockFormDTO();

		Stock stock = StockBuilder.getStock();

		when(stockRepository.save(any(Stock.class))).thenReturn(stock);

		StockDTO dto = this.stockService.save(body);

		assertThat(dto.getProductId()).isNotNull();
		assertThat(dto.getPrice()).isEqualTo(stock.getPrice());
		assertThat(dto.getExitPrice()).isEqualTo(stock.getExitPrice());
		assertThat(dto.getStockQuantity()).isEqualTo(stock.getStockQuantity());

	}

	@Test
	public void ShouldUpdateAnStock() {

		StockFormDTO body = StockBuilder.getStockFormDTO();
		body.setPrice(2.45);
		body.setStockQuantity(24);

		Stock stock = StockBuilder.getStock();

		when(stockRepository.findById(anyLong())).thenReturn(Optional.of(stock));
		when(stockRepository.save(any(Stock.class))).thenReturn(stock);

		StockDTO dto = this.stockService.update(1L, body);

		assertThat(dto.getProductId()).isNotNull();
		assertThat(dto.getPrice()).isEqualTo(body.getPrice());
		assertThat(dto.getExitPrice()).isEqualTo(body.getExitPrice());
		assertThat(dto.getStockQuantity()).isEqualTo(body.getStockQuantity());

	}

	@Test
	public void ShouldThrowException_WhenUpdateStockByIdInexistent() {

		StockFormDTO body = StockBuilder.getStockFormDTO();
		body.setPrice(2.45);
		body.setStockQuantity(24);

		Stock stock = StockBuilder.getStock();

		when(stockRepository.findById(anyLong())).thenReturn(Optional.empty());
		when(stockRepository.save(any(Stock.class))).thenReturn(stock);

		assertThatExceptionOfType(ResourceNotFoundException.class)
				.isThrownBy(() -> this.stockService.update(ID_INEXISTENT, body));

	}

	@Test
	public void ShouldListAllProductsInStock() {

		Stock stock = StockBuilder.getStock();

		List<Stock> list = Arrays.asList(stock, stock);
		Page<Stock> stockAsPage = new PageImpl<>(list);

		when(this.stockRepository.findAll(any(Pageable.class))).thenReturn(stockAsPage);

		Page<StockDTO> stockDtoAsPage = stockService.listAsPage(PageRequest.of(0, 12, Direction.ASC, "id"));
		StockDTO stockDto = stockDtoAsPage.getContent().get(0);

		assertThat(stockDtoAsPage.getContent().size()).isGreaterThan(0);
		assertThat(stockDto.getProductId()).isNotNull();
		assertThat(stockDto.getPrice()).isEqualTo(stock.getPrice());
		assertThat(stockDto.getExitPrice()).isEqualTo(stock.getExitPrice());
		assertThat(stockDto.getStockQuantity()).isEqualTo(stock.getStockQuantity());

	}

	@Test
	public void ShouldDeleteAnStockById_WhenStockQuantityIsZero() {

		Stock stock = StockBuilder.getStock();
		stock.setStockQuantity(0);

		when(this.stockRepository.findById(anyLong())).thenReturn(Optional.of(stock));

		this.stockService.delete(1L);

		verify(this.stockRepository, times(1)).findById(1L);

	}

	@Test
	public void ShouldThrowException_WhenDeleteAnStockByIdIfStillHaveProducts() {

		Stock stock = StockBuilder.getStock();

		when(this.stockRepository.findById(anyLong())).thenReturn(Optional.of(stock));

		assertThatExceptionOfType(ResourceNotFoundException.class)
		.isThrownBy(() -> this.stockService.delete(1L));

	}

	@Test
	public void ShouldThrowException_WhenDeleteStockByIdInexistent() {

		Stock stock = StockBuilder.getStock();
		stock.setStockQuantity(0);

		when(this.stockRepository.findById(anyLong())).thenReturn(Optional.empty());

		assertThatExceptionOfType(ResourceNotFoundException.class)
				.isThrownBy(() -> this.stockService.delete(ID_INEXISTENT));

	}

}
