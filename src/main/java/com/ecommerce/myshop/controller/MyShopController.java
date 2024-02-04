package com.ecommerce.myshop.controller;

import com.ecommerce.myshop.dataTranferObject.CategoryToSave;
import com.ecommerce.myshop.dataTranferObject.ProductToSave;
import com.ecommerce.myshop.entity.Product;
import com.ecommerce.myshop.entity.ProductCategory;
import com.ecommerce.myshop.service.ProductService;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/action")
@CrossOrigin(origins = "http://localhost:4200")
public class MyShopController {

    ProductService productService;

    public MyShopController(ProductService productService) {
        this.productService = productService;
    }

    //endpoint to save product
     @PostMapping ("/saveProduct")
     public Product saveProduct(@RequestBody ProductToSave receivedProduct) {
         return productService.saveProduct(receivedProduct);
     }

     //endpoint to save category
     @PostMapping ("/saveCategory")
     public ProductCategory saveCategory(@RequestBody CategoryToSave receivedCategory) {
        return productService.saveCategory(receivedCategory);
    }

     //endpoint to delete product
    @DeleteMapping ("/deleteProduct/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        return productService.deleteProduct(productId);
    }

    //endpoint to delete category
    @DeleteMapping ("/deleteCategory/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        return productService.deleteCategory(categoryId);
    }

    //endpoint to update product
    @PatchMapping ("/updateProduct/{productId}")
    public Product updateProduct(@RequestBody ProductToSave receivedProduct, @PathVariable Long productId) {
          return productService.updateProduct(receivedProduct, productId);
    }

    //endpoint to update category
    @PatchMapping ("/updateCategory/{categoryId}")
    public ProductCategory updateCategory(@RequestBody CategoryToSave receivedCategory, @PathVariable Long categoryId) {
          return productService.updateCategory(receivedCategory, categoryId);
    }

    @GetMapping("productsByCategory/{id}")
    public Page<Product> productsByCategory(@PathVariable String id, Pageable pageable){
        return productService.productsByCategory(id, pageable);
    }

   @GetMapping("productsByName/{name}")
    public Page<Product> productsByName(@PathVariable String name, Pageable pageable){
        return productService.productsByName(name, pageable);
    }

    @GetMapping("categoryByName/{name}")
    public Page<ProductCategory> categoriesByName(@PathVariable String name, Pageable pageable){
        return productService.categoriesByName(name, pageable);
    }



}
