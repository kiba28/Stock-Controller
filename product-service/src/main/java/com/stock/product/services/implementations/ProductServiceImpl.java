package com.stock.product.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.stock.product.dto.ProductDTO;
import com.stock.product.dto.ProductFormDTO;
import com.stock.product.entities.Product;
import com.stock.product.exceptions.ResourceNotFoundException;
import com.stock.product.proxy.StockProxy;
import com.stock.product.repositories.CategoryRepository;
import com.stock.product.repositories.ProductRepository;
import com.stock.product.response.Stock;
import com.stock.product.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository repository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private StockProxy stockProxy;

	@Autowired
	private ModelMapper mapper;

	@Override
	public ProductDTO saveProduct(ProductFormDTO body) {
		body.setCategory(categoryRepository.findById(body.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category not found " + body.getCategoryId())));

		Product productSaved = repository.save(mapper.map(body, Product.class));

		Stock stockSaved = new Stock();

		stockSaved.setProductId(productSaved.getId());
		stockSaved.setStockQuantity(0);
		stockProxy.saveStock(stockSaved);

		return mapper.map(productSaved, ProductDTO.class);
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
		product.setUnity(body.getUnity());
		product.setCategory(categoryRepository.findById(body.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category not found " + body.getCategoryId())));

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
