package com.stock.services;

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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.stock.builders.MovementBuilder;
import com.stock.builders.StockBuilder;
import com.stock.dto.MovementDTO;
import com.stock.dto.MovementFormDTO;
import com.stock.entities.Movement;
import com.stock.entities.Stock;
import com.stock.exceptions.ResourceNotFoundException;
import com.stock.repositories.MovementRepository;
import com.stock.repositories.StockRepository;
import com.stock.services.implementations.MovementServiceImpl;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class MovementServiceImplTest {

	private static final long _ID_INEXISTANT = 2L;

	@Autowired
	private MovementServiceImpl service;

	@MockBean
	private StockRepository stockRepo;

	@MockBean
	private MovementRepository movementRepo;

	@Test
	public void shouldSaveAnMovement() {

		Movement mov = MovementBuilder.getMovement();
		MovementFormDTO movForm = MovementBuilder.getMovementFormDTO();
		Stock sto = StockBuilder.getStock();

		when(this.stockRepo.findById(anyLong())).thenReturn(Optional.of(sto));
		when(this.stockRepo.save(any(Stock.class))).thenReturn(sto);
		when(this.movementRepo.save(any(Movement.class))).thenReturn(mov);

		MovementDTO movDto = this.service.save(movForm);

		assertThat(movDto.getId()).isNotNull();
		assertThat(movDto.getProductId()).isEqualTo(movForm.getProductId());

	}

	@Test
	public void shouldNotSaveAnMovementWhenStockIdIsInexistant() {

		Movement mov = MovementBuilder.getMovement();
		MovementFormDTO movForm = MovementBuilder.getMovementFormDTO();
		Stock sto = StockBuilder.getStock();
		mov.setId(10L);

		when(this.stockRepo.findById(anyLong())).thenReturn(Optional.empty());
		when(this.stockRepo.save(any(Stock.class))).thenReturn(sto);
		when(this.movementRepo.save(any(Movement.class))).thenReturn(mov);

		assertThatExceptionOfType(ResourceNotFoundException.class)
				.isThrownBy(() -> this.service.save(MovementBuilder.getMovementFormDTO()));

	}

	@Test
	public void shouldUpdateAnMovement() {
		Movement mov = MovementBuilder.getMovement();
		MovementFormDTO movForm = MovementBuilder.getMovementFormDTO();
		Stock sto = StockBuilder.getStock();

		when(this.movementRepo.findById(anyLong())).thenReturn(Optional.of(mov));
		when(this.stockRepo.findById(anyLong())).thenReturn(Optional.of(sto));
		when(this.stockRepo.save(any(Stock.class))).thenReturn(sto);
		when(this.movementRepo.save(any(Movement.class))).thenReturn(mov);

		MovementDTO movDto = this.service.save(movForm);

		assertThat(movDto.getId()).isNotNull();

	}

	@Test
	public void shouldNotUpadateAnMovementByIdInexistant() {

		Movement mov = MovementBuilder.getMovement();
		MovementFormDTO movForm = MovementBuilder.getMovementFormDTO();
		Stock sto = StockBuilder.getStock();

		when(this.movementRepo.findById(anyLong())).thenReturn(Optional.empty());
		when(this.stockRepo.findById(anyLong())).thenReturn(Optional.empty());
		when(this.stockRepo.save(any(Stock.class))).thenReturn(sto);
		when(this.movementRepo.save(any(Movement.class))).thenReturn(mov);

		assertThatExceptionOfType(ResourceNotFoundException.class)
				.isThrownBy(() -> this.service.save(MovementBuilder.getMovementFormDTO()));
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

		Page<MovementDTO> movementDtoAsPage = service.listEntrance(PageRequest.of(0, 12, Direction.ASC, "id"));

		MovementDTO movDto = movementDtoAsPage.getContent().get(0);

		assertThat(movementDtoAsPage.getContent().size()).isGreaterThan(0);
		assertThat(movDto.getId()).isNotNull();
		assertThat(movDto.getProductId()).isEqualTo(mov.getProductId());
		assertThat(movDto.getAmount()).isEqualTo(mov.getAmount());
		assertThat(movDto.getPrice()).isEqualTo(mov.getPrice());
		assertThat(movDto.getExitPrice()).isEqualTo(mov.getExitPrice());
		assertThat(movDto.getStatus()).isEqualTo(mov.getStatus());

	}

	@Test
	public void shouldDeleteAnMovementById() {

		Movement mov = MovementBuilder.getMovement();
		Stock sto = StockBuilder.getStock();

		when(this.movementRepo.findById(anyLong())).thenReturn(Optional.of(mov));
		when(this.stockRepo.findById(anyLong())).thenReturn(Optional.of(sto));

		this.service.deleteEntrance(1L);

		verify(this.movementRepo, times(1)).delete(mov);

	}

	@Test
	public void shouldNotDeleteAnMovementByIdInexistant() {

		when(this.movementRepo.findById(anyLong())).thenReturn(Optional.empty());
		when(this.stockRepo.findById(anyLong())).thenReturn(Optional.empty());

		assertThatExceptionOfType(ResourceNotFoundException.class)
				.isThrownBy(() -> this.service.deleteEntrance(_ID_INEXISTANT));

	}

}
