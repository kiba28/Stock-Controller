package com.stock.product.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.stock.product.dto.CategoryDTO;
import com.stock.product.dto.CategoryFormDTO;
import com.stock.product.entities.Category;
import com.stock.product.exceptions.ResourceNotFoundException;
import com.stock.product.repositories.CategoryRepository;
import com.stock.product.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository repository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public CategoryDTO save(CategoryFormDTO body) {

		return mapper.map(repository.save(mapper.map(body, Category.class)), CategoryDTO.class);
	}

	@Override
	public Page<CategoryDTO> listCategories(PageRequest pageRequest) {
		Page<Category> page = repository.findAll(pageRequest);

		List<CategoryDTO> list = page.getContent().stream().map(Category -> mapper.map(Category, CategoryDTO.class))
				.collect(Collectors.toList());
		return new PageImpl<>(list);
	}

	@Override
	public CategoryDTO updateCategory(Long id, CategoryFormDTO body) {
		Category category = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
		category.setName(body.getName());
		return mapper.map(repository.save(category), CategoryDTO.class);
	}

	@Override
	public CategoryDTO findById(Long id) {
		Category category = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
		return mapper.map(category, CategoryDTO.class);
	}

	@Override
	public void deleteCategory(Long id) {
		Category category = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
		repository.delete(category);
	}

}
