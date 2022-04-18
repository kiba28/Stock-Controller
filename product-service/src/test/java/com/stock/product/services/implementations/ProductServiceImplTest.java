package com.stock.product.services.implementations;

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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.stock.product.builders.CategoryBuilder;
import com.stock.product.builders.ProductBuilder;
import com.stock.product.builders.StockBuilder;
import com.stock.product.dto.ProductDTO;
import com.stock.product.dto.ProductFormDTO;
import com.stock.product.dto.ProductWithStockDTO;
import com.stock.product.entities.Product;
import com.stock.product.exceptions.ResourceNotFoundException;
import com.stock.product.proxy.StockProxy;
import com.stock.product.repositories.CategoryRepository;
import com.stock.product.repositories.ProductRepository;
import com.stock.product.response.Stock;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ProductServiceImplTest {

	@Autowired
	private ProductServiceImpl service;

	@MockBean
	private StockProxy stockProxy;

	@MockBean
	private ProductRepository repository;

	@MockBean
	private CategoryRepository categoryRepository;

	@Test
	public void shouldInsertAProduct() {
		Product product = ProductBuilder.getProduct();

		when(this.categoryRepository.findById(anyLong())).thenReturn(Optional.of(CategoryBuilder.getCategory()));
		when(this.repository.save(any(Product.class))).thenReturn(product);
		when(this.stockProxy.saveStock(any(Stock.class)))
				.thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(StockBuilder.getStock()));

		ProductWithStockDTO productDto = this.service.saveProduct(ProductBuilder.getProductFormDTO());

		assertThat(productDto.getId()).isNotNull();
		assertThat(productDto.getName()).isEqualTo(product.getName());
		assertThat(productDto.getUnity()).isEqualTo(product.getUnity());
		assertThat(productDto.getCategoryName()).isEqualTo(product.getCategory().getName());
		verify(this.stockProxy, times(1)).saveStock(StockBuilder.getStock());
	}

	@Test
	public void shouldNotInsertAProductBecauseThereIsNoCategoryWithTheInformedId() {
		Product product = ProductBuilder.getProduct();

		when(this.categoryRepository.findById(anyLong())).thenReturn(Optional.empty());
		when(this.repository.save(any(Product.class))).thenReturn(product);

		assertThatExceptionOfType(ResourceNotFoundException.class)
				.isThrownBy(() -> this.service.saveProduct(ProductBuilder.getProductFormDTO()));
	}

	@Test
	public void shouldSearchAllProductsWithPagination() {
		Product product = ProductBuilder.getProduct();
		List<Product> list = Arrays.asList(product, product);
		Page<Product> productAsPage = new PageImpl<>(list);

		when(this.repository.findAll(any(Pageable.class))).thenReturn(productAsPage);

		Page<ProductDTO> productDtoAsPage = service.listProducts(PageRequest.of(0, 12, Direction.ASC, "name"));
		ProductDTO productDto = productDtoAsPage.getContent().get(0);

		assertThat(productDtoAsPage.getContent().size()).isGreaterThan(0);
		assertThat(productDto.getId()).isNotNull();
		assertThat(productDto.getName()).isEqualTo(product.getName());
		assertThat(productDto.getUnity()).isEqualTo(product.getUnity());
		assertThat(productDto.getCategory()).isEqualTo(product.getCategory());
	}

	@Test
	public void shouldUpdateAProductWithCategory() {
		Product product = ProductBuilder.getProduct();
		ProductFormDTO productForm = ProductBuilder.getProductFormDTO();
		productForm.setName("Mochila da barbie");

		when(this.categoryRepository.findById(anyLong())).thenReturn(Optional.of(CategoryBuilder.getCategory()));
		when(this.repository.findById(anyLong())).thenReturn(Optional.of(product));
		when(this.repository.save(any(Product.class))).thenReturn(product);

		ProductDTO productDto = this.service.updateProduct(1L, productForm);

		assertThat(productDto.getId()).isNotNull();
		assertThat(productDto.getName()).isEqualTo(productForm.getName());
		assertThat(productDto.getUnity()).isEqualTo(productForm.getUnity());
		assertThat(productDto.getCategory()).isEqualTo(productForm.getCategory());
	}

	@Test
	public void shouldUpdateAProductWithoutCategory() {
		Product product = ProductBuilder.getProduct();
		ProductFormDTO productForm = ProductBuilder.getProductFormDTO();
		productForm.setName("Mochila da barbie");
		productForm.setCategoryId(0);

		when(this.categoryRepository.findById(anyLong())).thenReturn(Optional.of(CategoryBuilder.getCategory()));
		when(this.repository.findById(anyLong())).thenReturn(Optional.of(product));
		when(this.repository.save(any(Product.class))).thenReturn(product);

		ProductDTO productDto = this.service.updateProduct(1L, productForm);

		assertThat(productDto.getId()).isNotNull();
		assertThat(productDto.getName()).isEqualTo(productForm.getName());
		assertThat(productDto.getUnity()).isEqualTo(productForm.getUnity());
		assertThat(productDto.getCategory()).isEqualTo(productForm.getCategory());
	}

	@Test
	public void shouldNotUpdateAProductBecauseThereIsNoProductWithTheInformedId() {
		when(this.repository.findById(anyLong())).thenReturn(Optional.empty());

		assertThatExceptionOfType(ResourceNotFoundException.class)
				.isThrownBy(() -> this.service.updateProduct(2L, ProductBuilder.getProductFormDTO()));
	}

	@Test
	public void shouldNotUpdateAProductBecauseThereIsNoCategoryWithTheInformedId() {
		Product product = ProductBuilder.getProduct();

		when(this.repository.findById(anyLong())).thenReturn(Optional.of(product));
		when(this.categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

		assertThatExceptionOfType(ResourceNotFoundException.class)
				.isThrownBy(() -> this.service.updateProduct(1L, ProductBuilder.getProductFormDTO()));
	}

	@Test
	public void shouldFindAProductById() {
		Product product = ProductBuilder.getProduct();
		Stock stock = StockBuilder.getStock();

		when(this.repository.findById(anyLong())).thenReturn(Optional.of(product));
		when(this.stockProxy.searchStock(anyLong())).thenReturn(ResponseEntity.status(HttpStatus.OK).body(stock));

		ProductWithStockDTO producWithStocktDto = this.service.findById(1L);

		assertThat(producWithStocktDto.getId()).isNotNull();
		assertThat(producWithStocktDto.getName()).isEqualTo(product.getName());
		assertThat(producWithStocktDto.getUnity()).isEqualTo(product.getUnity());
		assertThat(producWithStocktDto.getPrice()).isEqualTo(stock.getPrice());
		assertThat(producWithStocktDto.getExitPrice()).isEqualTo(stock.getExitPrice());
		assertThat(producWithStocktDto.getStockQuantity()).isEqualTo(stock.getStockQuantity());
	}

	@Test
	public void ShouldNotFindAProductBecauseThereIsNoProductWithTheInformedId() {
		when(this.repository.findById(anyLong())).thenReturn(Optional.empty());

		assertThatExceptionOfType(ResourceNotFoundException.class).isThrownBy(() -> this.service.findById(2L));
	}

	@Test
	public void shouldDeleteAProductById() {
		Product product = ProductBuilder.getProduct();

		when(this.repository.findById(anyLong())).thenReturn(Optional.of(product));

		this.service.deleteProduct(1L);

		verify(this.repository, times(1)).delete(product);
	}

	@Test
	public void ShouldNotDeleteAProductBecauseThereIsNoProductWithTheInformedId() {
		when(this.repository.findById(anyLong())).thenReturn(Optional.empty());

		assertThatExceptionOfType(ResourceNotFoundException.class).isThrownBy(() -> this.service.deleteProduct(2L));
	}

}