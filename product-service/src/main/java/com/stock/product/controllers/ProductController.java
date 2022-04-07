package com.stock.product.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stock.product.dto.ProductDTO;
import com.stock.product.dto.ProductFormDTO;
import com.stock.product.services.ProductService;

@RestController
@RequestMapping("/product-service")
public class ProductController {

	@Autowired
	private Environment environment;

	@Autowired
	private ProductService productService;

	@PostMapping
	public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductFormDTO product) {

		ProductDTO prod = productService.saveProduct(product);

		return ResponseEntity.status(HttpStatus.CREATED).body(prod);
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductFormDTO product) {

		ProductDTO prod = productService.updateProduct(id, product);

		return ResponseEntity.status(HttpStatus.OK).body(prod);

	}

	@GetMapping
	public ResponseEntity<Page<ProductDTO>> listAllProductsAsPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage,
				org.springframework.data.domain.Sort.Direction.valueOf(direction), orderBy);

		Page<ProductDTO> list = productService.listProducts(pageRequest);

		System.out.println(environment.getProperty("local.server.port"));

		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> searchProduct(@PathVariable Long id) {

		ProductDTO dto = productService.findById(id);

		return ResponseEntity.ok().body(dto);

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long id) {

		productService.deleteProduct(id);

		return ResponseEntity.noContent().build();

	}

}
