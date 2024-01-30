package com.ecommerce.myshop.service;

import com.ecommerce.myshop.dataTranferObject.CategoryToSave;
import com.ecommerce.myshop.dataTranferObject.ProductToSave;
import com.ecommerce.myshop.entity.Product;
import com.ecommerce.myshop.entity.ProductCategory;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    //method to store product in database
    Product saveProduct(ProductToSave receivedProduct);

    //method to store category in database
    ProductCategory saveCategory(CategoryToSave receivedProduct);

    //method to delete product from database
    ResponseEntity<String> deleteProduct(Long productId);

    //method to delete category from database
    ResponseEntity<String> deleteCategory(Long categoryId);

    Product updateProduct(ProductToSave receivedProduct, Long productId);

    public ProductCategory updateCategory(CategoryToSave receivedCategory , Long categoryId);

}
