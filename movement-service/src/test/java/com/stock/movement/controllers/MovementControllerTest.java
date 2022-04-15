package com.stock.movement.controllers;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import com.stock.movement.dto.MovementDTO;
import com.stock.movement.dto.MovementFormDTO;
import com.stock.movement.enums.Status;
import com.stock.movement.services.MovementService;

import io.restassured.http.ContentType;

@WebMvcTest(MovementController.class)
class MovementControllerTest {
	
	@Autowired
	private MovementController controller;
	
	@MockBean
	private MovementService movementService;
	
	@BeforeEach
	public void setup() {
		standaloneSetup(this.controller);
		MovementDTO dto =new MovementDTO();
	}

	@Test
	void deveriaRetornaSucesso_QuandoBuscarUmMoviemnto() {
		MovementDTO dto =new MovementDTO();
		 dto.setId(1L);
		 dto.setAmount(2);
		 dto.setPrice(10.2);
		 dto.setExitPrice(16.5);
		 dto.setProductId(2L);
		 dto.setStatus(Status.ENTRANCE);
		
		when(this.movementService.findById(1L)).thenReturn(dto);
		
		given()
		  .accept(ContentType.JSON)
		.when() 
		  .get("/movement-service/{id}", 1L)
		.then()
		  .statusCode(HttpStatus.OK.value()) ;
	}
	

	@Test
	void deveriaRetornaSucesso_QuandoSalvarUmMoviemntoDeEntrada() {
		 MovementFormDTO dto =new MovementFormDTO();
		
		 dto.setAmount(2);
		 dto.setPrice(10.2);
		 dto.setPercentage(20.5);
		 dto.setProductId(2L);
		 dto.setStatus(Status.ENTRANCE);
		 
		 MovementDTO mdto =new MovementDTO();
		 
		copia(dto, mdto);
		 
		
		when(this.movementService.save(dto)).thenReturn(mdto);
		
		given()
		  .accept(ContentType.JSON)
		.when() 
		  .get("/movement-service")
		.then()
		  .statusCode(HttpStatus.OK.value()) ;
	}

	public void copia(MovementFormDTO dto, MovementDTO EntiDTO) {
		EntiDTO.setId(1L);
		EntiDTO.setAmount(dto.getAmount());
		EntiDTO.setExitPrice((dto.getPercentage()* dto.getPrice())/100 +dto.getPrice());
		EntiDTO.setPrice(dto.getPrice());
		EntiDTO.setProductId(dto.getProductId());
		EntiDTO.setStatus(dto.getStatus());
	}
}
