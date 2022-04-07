package com.stock.product.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.stock.product.dto.ProductDTO;
import com.stock.product.dto.ProductFormDTO;

public interface ProductService {

	ProductDTO saveProduct(ProductFormDTO body);

	Page<ProductDTO> listProducts(PageRequest pageRequest);

	ProductDTO updateProduct(Long id, ProductFormDTO body);

	ProductDTO findById(Long id);

	void deleteProduct(Long id);
}
