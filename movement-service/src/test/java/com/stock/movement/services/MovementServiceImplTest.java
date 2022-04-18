package com.stock.movement.services;

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
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.stock.movement.builders.MovementBuilder;
import com.stock.movement.builders.StockBuilder;
import com.stock.movement.dto.MovementDTO;
import com.stock.movement.dto.MovementFormDTO;
import com.stock.movement.entities.Movement;
import com.stock.movement.enums.Status;
import com.stock.movement.exceptions.ResourceNotFoundException;
import com.stock.movement.proxy.StockProxy;
import com.stock.movement.repository.MovementRepository;
import com.stock.movement.response.Stock;
import com.stock.movement.services.implementations.MovementServiceImpl;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class MovementServiceImplTest {

	private static final long _ID_INEXISTANT = 2L;

	@Autowired
	private MovementServiceImpl service;

	@MockBean
	private StockProxy stockRepo;

	@MockBean
	private MovementRepository movementRepo;

	@Test
	public void shouldSaveAExitMovement() {

		Movement mov = MovementBuilder.getMovement();
		MovementFormDTO movForm = MovementBuilder.getMovementFormDTO();
		ResponseEntity<Stock> sto = StockBuilder.getStock();

		when(this.stockRepo.searchStock(anyLong())).thenReturn(sto);
		when(this.movementRepo.save(any(Movement.class))).thenReturn(mov);

		MovementDTO movDto = this.service.save(movForm);

		assertThat(movDto.getId()).isNotNull();
		assertThat(movDto.getProductId()).isEqualTo(movForm.getProductId());
		assertThat(movDto.getAmount()).isEqualTo(movForm.getAmount());
		assertThat(movDto.getPrice()).isEqualTo(movForm.getPrice());
		assertThat(movDto.getStatus()).isEqualTo(movForm.getStatus());

	}

	@Test
	public void shouldSaveAEntranceMovement() {

		Movement mov = MovementBuilder.getMovement();
		mov.setStatus(Status.ENTRANCE);
		MovementFormDTO movForm = MovementBuilder.getMovementFormDTO();
		movForm.setStatus(Status.ENTRANCE);
		ResponseEntity<Stock> sto = StockBuilder.getStock();

		when(this.stockRepo.searchStock(anyLong())).thenReturn(sto);
		when(this.movementRepo.save(any(Movement.class))).thenReturn(mov);

		MovementDTO movDto = this.service.save(movForm);

		assertThat(movDto.getId()).isNotNull();
		assertThat(movDto.getProductId()).isEqualTo(movForm.getProductId());
		assertThat(movDto.getAmount()).isEqualTo(movForm.getAmount());
		assertThat(movDto.getPrice()).isEqualTo(movForm.getPrice());
		assertThat(movDto.getStatus()).isEqualTo(movForm.getStatus());

	}

	@Test
	public void shouldNotSaveAExitMovementBeacuseAmountIsGreatherThanStockQuantity() {

		Movement mov = MovementBuilder.getMovement();
		MovementFormDTO movForm = MovementBuilder.getMovementFormDTO();
		movForm.setAmount(50);
		ResponseEntity<Stock> sto = StockBuilder.getStock();

		when(this.stockRepo.searchStock(anyLong())).thenReturn(sto);
		when(this.movementRepo.save(any(Movement.class))).thenReturn(mov);

		assertThatExceptionOfType(ResourceNotFoundException.class).isThrownBy(() -> this.service.save(movForm));
	}

	@Test
	public void shouldUpdateAExitMovementWithTheSameStock() {
		Movement mov = MovementBuilder.getMovement();
		MovementFormDTO movForm = MovementBuilder.getMovementFormDTO();
		movForm.setPrice(2.43);
		ResponseEntity<Stock> sto = StockBuilder.getStock();

		when(this.movementRepo.findById(anyLong())).thenReturn(Optional.of(mov));
		when(this.stockRepo.searchStock(anyLong())).thenReturn(sto);
		when(this.movementRepo.save(any(Movement.class))).thenReturn(mov);

		MovementDTO movDto = this.service.updateMovement(1L, movForm);

		assertThat(movDto.getId()).isNotNull();
		assertThat(movDto.getProductId()).isEqualTo(mov.getProductId());
		assertThat(movDto.getPrice()).isEqualTo(mov.getPrice());

	}

	@Test
	public void shouldUpdateAEntranceMovementWithTheSameStock() {
		Movement mov = MovementBuilder.getMovement();
		mov.setStatus(Status.ENTRANCE);
		MovementFormDTO movForm = MovementBuilder.getMovementFormDTO();
		movForm.setPrice(2.43);
		movForm.setStatus(Status.ENTRANCE);
		ResponseEntity<Stock> sto = StockBuilder.getStock();

		when(this.movementRepo.findById(anyLong())).thenReturn(Optional.of(mov));
		when(this.stockRepo.searchStock(anyLong())).thenReturn(sto);
		when(this.movementRepo.save(any(Movement.class))).thenReturn(mov);

		MovementDTO movDto = this.service.updateMovement(2L, movForm);

		assertThat(movDto.getId()).isNotNull();
		assertThat(movDto.getProductId()).isEqualTo(mov.getProductId());
		assertThat(movDto.getPrice()).isEqualTo(mov.getPrice());

	}

	@Test
	public void shouldUpdateAExitMovementWithDiferentStock() {
		Movement mov = MovementBuilder.getMovement();
		MovementFormDTO movForm = MovementBuilder.getMovementFormDTO();
		movForm.setPrice(2.43);
		movForm.setProductId(2L);
		ResponseEntity<Stock> sto = StockBuilder.getStock();
		ResponseEntity<Stock> sto2 = StockBuilder.getStock();
		sto2.getBody().setProductId(2L);

		when(this.movementRepo.findById(anyLong())).thenReturn(Optional.of(mov));
		when(this.stockRepo.searchStock(1L)).thenReturn(sto);
		when(this.stockRepo.searchStock(2L)).thenReturn(sto2);
		when(this.movementRepo.save(any(Movement.class))).thenReturn(mov);

		MovementDTO movDto = this.service.updateMovement(1L, movForm);

		assertThat(movDto.getId()).isNotNull();
		assertThat(movDto.getProductId()).isEqualTo(movForm.getProductId());
		assertThat(movDto.getPrice()).isEqualTo(movForm.getPrice());
	}

	@Test
	public void shouldUpdateAEntranceMovementWithDiferentStock() {
		Movement mov = MovementBuilder.getMovement();
		mov.setStatus(Status.ENTRANCE);
		MovementFormDTO movForm = MovementBuilder.getMovementFormDTO();
		movForm.setPrice(2.43);
		movForm.setProductId(2L);
		movForm.setStatus(Status.ENTRANCE);
		ResponseEntity<Stock> sto = StockBuilder.getStock();
		ResponseEntity<Stock> sto2 = StockBuilder.getStock();
		sto2.getBody().setProductId(2L);

		when(this.movementRepo.findById(anyLong())).thenReturn(Optional.of(mov));
		when(this.stockRepo.searchStock(1L)).thenReturn(sto);
		when(this.stockRepo.searchStock(2L)).thenReturn(sto2);
		when(this.movementRepo.save(any(Movement.class))).thenReturn(mov);

		MovementDTO movDto = this.service.updateMovement(1L, movForm);

		assertThat(movDto.getId()).isNotNull();
		assertThat(movDto.getProductId()).isEqualTo(movForm.getProductId());
		assertThat(movDto.getPrice()).isEqualTo(movForm.getPrice());
	}
	
	@Test
	public void shouldNotUpdateAExitMovementBeacuseAmountIsGreatherThanStockQuantity() {
		Movement mov = MovementBuilder.getMovement();
		MovementFormDTO movForm = MovementBuilder.getMovementFormDTO();
		movForm.setAmount(50);
		ResponseEntity<Stock> sto = StockBuilder.getStock();

		when(this.movementRepo.findById(anyLong())).thenReturn(Optional.of(mov));
		when(this.stockRepo.searchStock(1L)).thenReturn(sto);
		when(this.movementRepo.save(any(Movement.class))).thenReturn(mov);

		assertThatExceptionOfType(ResourceNotFoundException.class)
				.isThrownBy(() -> this.service.updateMovement(1L, movForm));
	}

	@Test
	public void shouldNotUpdateAExitMovementWithDiferentStockBeacuseAmountIsGreatherThanStockQuantity() {
		Movement mov = MovementBuilder.getMovement();
		MovementFormDTO movForm = MovementBuilder.getMovementFormDTO();
		movForm.setPrice(2.43);
		movForm.setProductId(2L);
		movForm.setAmount(50);
		ResponseEntity<Stock> sto = StockBuilder.getStock();
		ResponseEntity<Stock> sto2 = StockBuilder.getStock();
		sto2.getBody().setProductId(2L);

		when(this.movementRepo.findById(anyLong())).thenReturn(Optional.of(mov));
		when(this.stockRepo.searchStock(1L)).thenReturn(sto);
		when(this.stockRepo.searchStock(2L)).thenReturn(sto2);
		when(this.movementRepo.save(any(Movement.class))).thenReturn(mov);

		assertThatExceptionOfType(ResourceNotFoundException.class)
				.isThrownBy(() -> this.service.updateMovement(1L, movForm));
	}

	@Test
	public void shouldNotUpadateAMovementByIdInexistant() {

		Movement mov = MovementBuilder.getMovement();

		when(this.movementRepo.findById(anyLong())).thenReturn(Optional.empty());
		when(this.movementRepo.save(any(Movement.class))).thenReturn(mov);

		assertThatExceptionOfType(ResourceNotFoundException.class)
				.isThrownBy(() -> this.service.updateMovement(_ID_INEXISTANT, MovementBuilder.getMovementFormDTO()));
	}

	@Test
	public void shouldFindAnMovementById() {

		Movement mov = MovementBuilder.getMovement();

		when(this.movementRepo.findById(anyLong())).thenReturn(Optional.of(mov));

		MovementDTO movDto = this.service.findById(1L);

		assertThat(movDto.getId()).isNotNull();
		assertThat(movDto.getProductId()).isEqualTo(mov.getProductId());

	}

	@Test
	public void shouldNotFindAnMovementByIdInexistant() {

		when(this.movementRepo.findById(anyLong())).thenReturn(Optional.empty());

		assertThatExceptionOfType(ResourceNotFoundException.class)
				.isThrownBy(() -> this.service.findById(_ID_INEXISTANT));

	}

	@Test
	public void shouldListAllMovementsByPageable() {

		Movement mov = MovementBuilder.getMovement();

		List<Movement> list = Arrays.asList(mov, mov);
		Page<Movement> movementAsPage = new PageImpl<>(list);

		when(this.movementRepo.findAll(any(org.springframework.data.domain.Pageable.class))).thenReturn(movementAsPage);

		Page<MovementDTO> movementDtoAsPage = service.listMovements(PageRequest.of(0, 12, Direction.ASC, "id"));

		MovementDTO movDto = movementDtoAsPage.getContent().get(0);

		assertThat(movementDtoAsPage.getContent().size()).isGreaterThan(0);
		assertThat(movDto.getId()).isNotNull();
		assertThat(movDto.getProductId()).isEqualTo(mov.getProductId());
		assertThat(movDto.getAmount()).isEqualTo(mov.getAmount());
		assertThat(movDto.getPrice()).isEqualTo(mov.getPrice());
		assertThat(movDto.getStatus()).isEqualTo(mov.getStatus());

	}

	@Test
	public void shouldDeleteAnExitMovementById() {

		Movement mov = MovementBuilder.getMovement();
		mov.setPrice(10.55);
		Movement mov2 = MovementBuilder.getMovement();
		mov2.setId(2L);

		ResponseEntity<Stock> stock = StockBuilder.getStock();

		when(this.stockRepo.searchStock(anyLong())).thenReturn(stock);
		when(this.movementRepo.findTopByStatusOrderByIdDesc(any(Status.class))).thenReturn(mov);
		when(this.movementRepo.findTopByOrderByIdDesc()).thenReturn(mov2);

		service.deleteLastMovement();

		assertThat(mov.getPrice()).isEqualTo(stock.getBody().getExitPrice());
		verify(this.movementRepo, times(1)).delete(mov2);

	}

	@Test
	public void shouldDeleteAnEntranceMovementById() {

		Movement mov = MovementBuilder.getMovement();
		mov.setPrice(2.57);
		mov.setStatus(Status.ENTRANCE);
		Movement mov2 = MovementBuilder.getMovement();
		mov2.setId(2L);
		mov2.setStatus(Status.ENTRANCE);

		ResponseEntity<Stock> stock = StockBuilder.getStock();

		when(this.stockRepo.searchStock(anyLong())).thenReturn(stock);
		when(this.movementRepo.findTopByStatusOrderByIdDesc(any(Status.class))).thenReturn(mov);
		when(this.movementRepo.findTopByOrderByIdDesc()).thenReturn(mov2);

		service.deleteLastMovement();

		assertThat(mov.getPrice()).isEqualTo(stock.getBody().getExitPrice());
		verify(this.movementRepo, times(1)).delete(mov2);

	}

}
