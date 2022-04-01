package com.stock.services;

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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.stock.builders.CategoryBuilder;
import com.stock.builders.ProductBuilder;
import com.stock.dto.ProductDTO;
import com.stock.dto.ProductFormDTO;
import com.stock.entities.Product;
import com.stock.exceptions.ResourceNotFoundException;
import com.stock.repositories.CategoryRepository;
import com.stock.repositories.ProductRepository;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ProductServiceImplTest {

	@Autowired
	private ProductServiceImpl service;

	@MockBean
	private ProductRepository repository;

	@MockBean
	CategoryRepository categoryRepository;

	@Test
	public void deveriaInserirUmProduto() {
		Product product = ProductBuilder.getProduct();

		when(this.categoryRepository.findById(anyLong())).thenReturn(Optional.of(CategoryBuilder.getCategory()));
		when(this.repository.save(any(Product.class))).thenReturn(product);

		ProductDTO productDto = this.service.saveProduct(ProductBuilder.getProductFormDTO());

		assertThat(productDto.getId()).isNotNull();
		assertThat(productDto.getName()).isEqualTo(product.getName());
		assertThat(productDto.getPrice()).isEqualTo(product.getPrice());
		assertThat(productDto.getUnity()).isEqualTo(product.getUnity());
		assertThat(productDto.getMinStock()).isEqualTo(product.getMinStock());
		assertThat(productDto.getCategory()).isEqualTo(product.getCategory());
	}

	@Test
	public void naoDeveriaInserirUmProdutoPoisNaoExisteCategoriaComOIdInformado() {
		Product product = ProductBuilder.getProduct();

		when(this.categoryRepository.findById(anyLong())).thenReturn(Optional.empty());
		when(this.repository.save(any(Product.class))).thenReturn(product);

		assertThatExceptionOfType(ResourceNotFoundException.class)
				.isThrownBy(() -> this.service.saveProduct(ProductBuilder.getProductFormDTO()));
	}

	@Test
	public void deveriaBuscarTodosOsProdutosComPaginacao() {
		Product product = ProductBuilder.getProduct();
		List<Product> list = Arrays.asList(product, product);
		Page<Product> productAsPage = new PageImpl<>(list);

		when(this.repository.findAll(any(Pageable.class))).thenReturn(productAsPage);

		Page<ProductDTO> productDtoAsPage = service.listProducts(PageRequest.of(0, 12, Direction.ASC, "name"));
		ProductDTO productDto = productDtoAsPage.getContent().get(0);

		assertThat(productDto.getId()).isNotNull();
		assertThat(productDto.getName()).isEqualTo(product.getName());
		assertThat(productDto.getPrice()).isEqualTo(product.getPrice());
		assertThat(productDto.getUnity()).isEqualTo(product.getUnity());
		assertThat(productDto.getMinStock()).isEqualTo(product.getMinStock());
		assertThat(productDto.getCategory()).isEqualTo(product.getCategory());
	}

	@Test
	public void deveriaAtualizarUmProduto() {
		Product product = ProductBuilder.getProduct();
		ProductFormDTO productForm = ProductBuilder.getProductFormDTO();
		productForm.setName("Mochila da barbie");
		productForm.setPrice(69.99);

		when(this.categoryRepository.findById(anyLong())).thenReturn(Optional.of(CategoryBuilder.getCategory()));
		when(this.repository.findById(anyLong())).thenReturn(Optional.of(product));
		when(this.repository.save(any(Product.class))).thenReturn(product);

		ProductDTO productDto = this.service.updateProduct(1L, productForm);

		assertThat(productDto.getId()).isNotNull();
		assertThat(productDto.getName()).isEqualTo(productForm.getName());
		assertThat(productDto.getPrice()).isEqualTo(productForm.getPrice());
		assertThat(productDto.getUnity()).isEqualTo(productForm.getUnity());
		assertThat(productDto.getMinStock()).isEqualTo(productForm.getMinStock());
		assertThat(productDto.getCategory()).isEqualTo(productForm.getCategory());
	}

	@Test
	public void naoDeveriaAtualizarUmProdutoPoisNaoExisteProdutoComOIdInformado() {
		when(this.repository.findById(anyLong())).thenReturn(Optional.empty());

		assertThatExceptionOfType(ResourceNotFoundException.class)
				.isThrownBy(() -> this.service.updateProduct(2L, ProductBuilder.getProductFormDTO()));
	}

	@Test
	public void naoDeveriaAtualizarUmProdutoPoisNaoExisteCategoriaComOIdInformado() {
		Product product = ProductBuilder.getProduct();

		when(this.repository.findById(anyLong())).thenReturn(Optional.of(product));
		when(this.categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

		assertThatExceptionOfType(ResourceNotFoundException.class)
				.isThrownBy(() -> this.service.updateProduct(1L, ProductBuilder.getProductFormDTO()));
	}

	@Test
	public void deveriaEncontrarUmProdutoPeloId() {
		Product product = ProductBuilder.getProduct();

		when(this.repository.findById(anyLong())).thenReturn(Optional.of(product));

		ProductDTO productDto = this.service.findById(1L);

		assertThat(productDto.getId()).isNotNull();
		assertThat(productDto.getName()).isEqualTo(product.getName());
		assertThat(productDto.getPrice()).isEqualTo(product.getPrice());
		assertThat(productDto.getUnity()).isEqualTo(product.getUnity());
		assertThat(productDto.getMinStock()).isEqualTo(product.getMinStock());
		assertThat(productDto.getCategory()).isEqualTo(product.getCategory());
	}

	@Test
	public void naoDeveriaEncontrarUmProdutoPoisNaoExisteProdutoComOIdInformado() {
		when(this.repository.findById(anyLong())).thenReturn(Optional.empty());

		assertThatExceptionOfType(ResourceNotFoundException.class).isThrownBy(() -> this.service.findById(2L));
	}

	@Test
	public void deveriaDeletarUmProdutoPeloId() {
		Product product = ProductBuilder.getProduct();

		when(this.repository.findById(anyLong())).thenReturn(Optional.of(product));

		this.service.deleteProduct(1L);

		verify(this.repository, times(1)).delete(product);
	}

	@Test
	public void naoDeveriaDeletarUmProdutoPoisNaoExisteProdutoComOIdInformado() {
		when(this.repository.findById(anyLong())).thenReturn(Optional.empty());

		assertThatExceptionOfType(ResourceNotFoundException.class).isThrownBy(() -> this.service.deleteProduct(2L));
	}

}
