package com.ecommerce.myshop.controller;

import com.ecommerce.myshop.dataTranferObject.CategoryDto;
import com.ecommerce.myshop.dataTranferObject.ImageDto;
import com.ecommerce.myshop.dataTranferObject.ProductDto;
import com.ecommerce.myshop.entity.ImageModel;
import com.ecommerce.myshop.entity.Product;
import com.ecommerce.myshop.entity.ProductCategory;
import com.ecommerce.myshop.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping ("/api/action")
@CrossOrigin("http://localhost:4200")
public class MyShopController {

    ProductService productService;

    public MyShopController(ProductService productService) {
        this.productService = productService;
    }

    //endpoint to get all products
    @GetMapping ("/products")
    Page<Product> getAllProducts(Pageable pageable) {
        return productService.getAllProducts(pageable);
    }

    //endpoint to get all categories
    @GetMapping ("/categories")
    Page<ProductCategory> getAllCategories(Pageable pageable) {
        return productService.getAllCategories(pageable);
    }

    //endpoint to get products containing name
    @GetMapping ("/productsContainingName")
    Page<Product> getProductsContainingName(@RequestParam String name, Pageable pageable) {
        return productService.getProductsContainingName(name, pageable);
    }

    //endpoint to get products by category id
    @GetMapping ("/productsByCategoryId")
    Page<Product> getProductsByCategoryId(@RequestParam Long categoryId, Pageable pageable) {
        return productService.getProductsByCategoryId(categoryId, pageable);
    }


    //endpoint to save product
     @PostMapping ("/saveProduct")
     public Product saveProduct(@RequestBody ProductDto receivedProduct) {
         return productService.saveProduct(receivedProduct);
     }

     //endpoint to save category
     @PostMapping ("/saveCategory")
     public ProductCategory saveCategory(@RequestBody CategoryDto receivedCategory) {
        return productService.saveCategory(receivedCategory);
    }

     //endpoint to delete product
    @DeleteMapping ("/deleteProduct/{productId}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long productId) {
        return productService.deleteProduct(productId);
    }

    //endpoint to delete category
    @DeleteMapping ("/deleteCategory/{categoryId}")
    public ResponseEntity<ProductCategory> deleteCategory(@PathVariable Long categoryId) {
        return productService.deleteCategory(categoryId);
    }

    //endpoint to update product
    @PatchMapping ("/updateProduct/{productId}")
    public Product updateProduct(@RequestBody ProductDto receivedProduct, @PathVariable Long productId) {
          return productService.updateProduct(receivedProduct, productId);
    }

    //endpoint to update category
    @PatchMapping ("/updateCategory/{categoryId}")
    public ProductCategory updateCategory(@RequestBody CategoryDto receivedCategory, @PathVariable Long categoryId) {
          return productService.updateCategory(receivedCategory, categoryId);
    }

    //endpoint to get product by id
    @GetMapping ("/product/{productId}")
    public Product getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    //endpoint to get image by id
    @GetMapping ("/image/{productId}")
    public Page<ImageDto> getImagesByProductId(@PathVariable Long productId, Pageable pageable) {
            return productService.getImagesByProductId(productId, pageable);
    }

    //endpoint to delete image by id
    @DeleteMapping ("/deleteImage/{imageId}")
    public ResponseEntity<ImageModel> deleteImage(@PathVariable Long imageId) {
        return productService.deleteImage(imageId);
    }


}


