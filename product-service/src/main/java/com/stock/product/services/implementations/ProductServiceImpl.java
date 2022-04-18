package com.stock.product.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
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
	@Transactional
	public ProductWithStockDTO saveProduct(ProductFormDTO body) {

		body.setCategory(categoryRepository.findById(body.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category not found " + body.getCategoryId())));

		Product product = new Product();
		BeanUtils.copyProperties(body, product);
		product = repository.save(product);
		Stock stock = new Stock();

		stock.setProductId(product.getId());
		stockProxy.saveStock(stock);

		return mapper.map(product, ProductWithStockDTO.class);
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
		if (body.getCategoryId() > 0) {
			body.setCategory(categoryRepository.findById(body.getCategoryId())
					.orElseThrow(() -> new ResourceNotFoundException("Category not found " + body.getCategoryId())));
			BeanUtils.copyProperties(body, product);
			return mapper.map(repository.save(product), ProductDTO.class);
		}
		BeanUtils.copyProperties(body, product, "category");

		return mapper.map(repository.save(product), ProductDTO.class);
	}

	@Override
	public ProductWithStockDTO findById(Long id) {
		Product product = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
		ResponseEntity<Stock> stockReponseEntity = stockProxy.searchStock(product.getId());
		Stock stock = stockReponseEntity.getBody();
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

}
