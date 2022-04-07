package com.stock.product.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
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

import com.stock.product.dto.CategoryDTO;
import com.stock.product.dto.CategoryFormDTO;
import com.stock.product.services.CategoryService;

@RestController
@RequestMapping("/category-service")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping
	public ResponseEntity<CategoryDTO> saveCategory(@RequestBody CategoryFormDTO category) {

		CategoryDTO saved = categoryService.save(category);

		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}
	
	@PutMapping(value = "{id}")
	public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryFormDTO category) {

		CategoryDTO saved = categoryService.updateCategory(id, category);

		return ResponseEntity.status(HttpStatus.OK).body(saved);
	}

	@GetMapping
	public ResponseEntity<Page<CategoryDTO>> listAllCategoriesAsPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		Page<CategoryDTO> list = categoryService.listCategories(pageRequest);

		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@GetMapping(value = "{id}")
	public ResponseEntity<CategoryDTO> saveCategory(@PathVariable Long id) {

		CategoryDTO saved = categoryService.findById(id);

		return ResponseEntity.ok(saved);
	}
	
	@DeleteMapping(value = "{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {

		categoryService.deleteCategory(id);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
}
