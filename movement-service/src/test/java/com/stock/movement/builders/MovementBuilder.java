package com.stock.movement.builders;

import java.time.LocalDateTime;

import com.stock.movement.dto.MovementDTO;
import com.stock.movement.dto.MovementFormDTO;
import com.stock.movement.entities.Movement;
import com.stock.movement.enums.Status;



public final class MovementBuilder {

	public static Movement getMovement() {

		Movement mov = new Movement();
		mov.setId(1L);
		mov.setProductId(1L);
		mov.setAmount(1);
		mov.setPrice(2.55);
		mov.setExitPrice(2.57);
		mov.setCreatedAt(LocalDateTime.now());
		mov.setStatus(Status.EXIT);

		return mov;

	}

	public static MovementDTO getMovementDTO() {

		MovementDTO mov = new MovementDTO();
		mov.setId(1L);
		mov.setProductId(1L);
		mov.setAmount(1);
		mov.setPrice(2.55);
		mov.setExitPrice(2.57);
		mov.setStatus(Status.EXIT);

		return mov;

	}

	public static MovementFormDTO getMovementFormDTO() {

		MovementFormDTO mov = new MovementFormDTO();
		mov.setProductId(1L);
		mov.setAmount(1);
		mov.setPrice(2.55);
		mov.setPercentage(2.57);
		mov.setStatus(Status.EXIT);

		return mov;

	}

}
