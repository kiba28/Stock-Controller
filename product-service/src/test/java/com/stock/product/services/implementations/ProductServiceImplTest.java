//package com.stock.product.services.implementations;
//
//import static org.hamcrest.CoreMatchers.any;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.when;
//
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import com.stock.product.builders.CategoryBuilder;
//import com.stock.product.builders.ProductBuilder;
//import com.stock.product.dto.ProductDTO;
//import com.stock.product.dto.ProductFormDTO;
//import com.stock.product.entities.Category;
//import com.stock.product.entities.Product;
//import com.stock.product.repositories.CategoryRepository;
//import com.stock.product.repositories.ProductRepository;
//
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//class ProductServiceImplTest {
//	
//	@Autowired
//	private ProductServiceImpl productService;
//	
//	@MockBean
//	private ProductRepository productRepository;
//
//	@MockBean
//	private CategoryRepository categoryRepository;
//
//	
//	@Test
//	public void ShouldInsertAProduct() {
//		
//		Category category = new CategoryBuilder().getCategory();
//
//		ProductFormDTO form = new ProductBuilder().getProductFormDTO();
//		
//		when(this.categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
//		when(form.setCategory(this.categoryRepository.findById(form.getCategoryId()).get().getId()))
//		
//		ProductDTO dto = productService.saveProduct(form);
//		
//		assertEquals(form.getName(), dto.getName());
//		
//	}
//
//}
