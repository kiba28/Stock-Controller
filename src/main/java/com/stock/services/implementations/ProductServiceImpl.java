package com.stock.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.stock.dto.ProductDTO;
import com.stock.dto.ProductFormDTO;
import com.stock.entities.Product;
import com.stock.exceptions.ResourceNotFoundException;
import com.stock.repositories.CategoryRepository;
import com.stock.repositories.ProductRepository;
import com.stock.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository repository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public ProductDTO saveProduct(ProductFormDTO body) {
		body.setCategory(categoryRepository.findById(body.getCategoryID())
				.orElseThrow(() -> new ResourceNotFoundException("Category not found " + body.getCategoryID())));
		return mapper.map(repository.save(mapper.map(body, Product.class)), ProductDTO.class);
	}

	@Override
	public Page<ProductDTO> listProducts(PageRequest pageRequest) {
		Page<Product> page = repository.findAll(pageRequest);

		List<ProductDTO> list = page.getContent().stream().map(Product -> mapper.map(Product, ProductDTO.class))
				.collect(Collectors.toList());
		return new PageImpl<>(list);
	}

	@Override
	public ProductDTO updateProduct(Long id, ProductFormDTO body) {
		Product product = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
		product.setName(body.getName());
		product.setMinStock(body.getMinStock());
		product.setQuantityStock(body.getQuantityStock());
		product.setUnity(body.getUnity());
		product.setPrice(body.getPrice());
		product.setCategory(categoryRepository.findById(body.getCategoryID())
				.orElseThrow(() -> new ResourceNotFoundException("Category not found " + body.getCategoryID())));

		return mapper.map(repository.save(product), ProductDTO.class);
	}

	@Override
	public ProductDTO findById(Long id) {
		Product product = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
		return mapper.map(product, ProductDTO.class);
	}

	@Override
	public void deleteProduct(Long id) {
		Product product = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
		repository.delete(product);
	}

}
