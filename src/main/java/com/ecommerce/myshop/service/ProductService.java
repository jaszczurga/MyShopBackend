package com.ecommerce.myshop.service;

import com.ecommerce.myshop.dataTranferObject.ProductToSave;
import com.ecommerce.myshop.entity.Product;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    //method to store product in database
    Product saveProduct(ProductToSave receivedProduct);

    //method to delete product from database
    ResponseEntity<String> deleteProduct(Long productId);

    //method to delete category from database
    ResponseEntity<String> deleteCategory(Long categoryId);

}
