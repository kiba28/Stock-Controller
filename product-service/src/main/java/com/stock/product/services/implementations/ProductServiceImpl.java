package com.stock.product.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stock.product.dto.ProductDTO;
import com.stock.product.dto.ProductFormDTO;
import com.stock.product.dto.ProductWithStockDTO;
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

	//	Product productSaved = repository.save(mapper.map(body, Product.class));

		Product entity = new Product();
		copyDtoToEntity(body, entity);
	 	Product productSaved = repository.save(entity);
		Stock stockSaved = new Stock();

		stockSaved.setProductId(productSaved.getId());
		stockSaved.setStockQuantity(0);
		stockProxy.saveStock(stockSaved);
		
		ProductDTO dto = new ProductDTO(productSaved);

		return dto;

	//	return mapper.map(productSaved, ProductDTO.class);
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
	public ProductWithStockDTO findById(Long id) {
		Product product = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
		ResponseEntity<Stock> stockEntity = stockProxy.searchStock(product.getId());
		Stock stock = stockEntity.getBody();
		ProductWithStockDTO productStock = mapper.map(product, ProductWithStockDTO.class);
		productStock.setStockQuantity(stock.getStockQuantity());
		productStock.setPrice(stock.getPrice());
		productStock.setExitPrice(stock.getExitPrice());
		
		return productStock;
	}

	@Override
	public void deleteProduct(Long id) {
		Product product = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
		
		stockProxy.deleteStock(id);
		repository.delete(product);
	}
	
	private void copyDtoToEntity(ProductFormDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setUnity(dto.getUnity());
		entity.setCategory(dto.getCategory());
		
	}

}
