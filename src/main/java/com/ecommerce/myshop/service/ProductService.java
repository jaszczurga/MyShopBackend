package com.ecommerce.myshop.service;

import com.ecommerce.myshop.dataTranferObject.ProductToSave;
import com.ecommerce.myshop.entity.Product;

public interface ProductService {
    //method to store product in database
    Product saveProduct(ProductToSave receivedProduct);
}
