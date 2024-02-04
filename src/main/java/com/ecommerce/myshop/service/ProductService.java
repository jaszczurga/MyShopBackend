package com.ecommerce.myshop.service;

import com.ecommerce.myshop.dataTranferObject.CategoryDto;
import com.ecommerce.myshop.dataTranferObject.ProductDto;
import com.ecommerce.myshop.entity.Product;
import com.ecommerce.myshop.entity.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    //method to store product in database
    Product saveProduct(ProductDto receivedProduct);

    //method to store category in database
    ProductCategory saveCategory(CategoryDto receivedProduct);

    //method to delete product from database
    ResponseEntity<String> deleteProduct(Long productId);

    //method to delete category from database
    ResponseEntity<String> deleteCategory(Long categoryId);

    Product updateProduct(ProductDto receivedProduct, Long productId);

    public ProductCategory updateCategory(CategoryDto receivedCategory , Long categoryId);

    //method to get all products from database
    Page<Product> getAllProducts(Pageable pageable);

    //method to get all categories from database
    Page<ProductCategory> getAllCategories(Pageable pageable);

}
