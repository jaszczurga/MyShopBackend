package com.ecommerce.myshop.controller;

import com.ecommerce.myshop.dataTranferObject.ProductToSave;
import com.ecommerce.myshop.entity.Product;
import com.ecommerce.myshop.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/action")
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

}
