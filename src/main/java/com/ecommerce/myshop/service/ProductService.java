package com.ecommerce.myshop.service;

import com.ecommerce.myshop.dataTranferObject.CategoryDto;
import com.ecommerce.myshop.dataTranferObject.ImageDto;
import com.ecommerce.myshop.dataTranferObject.ProductDto;
import com.ecommerce.myshop.entity.ImageModel;
import com.ecommerce.myshop.entity.Product;
import com.ecommerce.myshop.entity.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface ProductService {
    //method to store product in database
    Product saveProduct(ProductDto receivedProduct);

    //method to store category in database
    ProductCategory saveCategory(CategoryDto receivedProduct);

    //method to delete product from database
    ResponseEntity<Product> deleteProduct(Long productId);

    //method to delete category from database
    ResponseEntity<ProductCategory> deleteCategory(Long categoryId);

    Product updateProduct(ProductDto receivedProduct, Long productId);

    public ProductCategory updateCategory(CategoryDto receivedCategory , Long categoryId);

    //method to get all products from database
    Page<Product> getAllProducts(Pageable pageable);

    //method to get all categories from database
    Page<ProductCategory> getAllCategories(Pageable pageable);

    //get products containing name
    Page<Product> getProductsContainingName(String name, Pageable pageable);

    //get products by category id
    Page<Product> getProductsByCategoryId(Long categoryId, Pageable pageable);

    //get product by id
    Product getProductById(Long productId);

   Page<ImageDto> getImagesByProductId(Long imageId,Pageable pageable);

}
