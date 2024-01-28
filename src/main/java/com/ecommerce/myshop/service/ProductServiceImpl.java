package com.ecommerce.myshop.service;

import com.ecommerce.myshop.dao.ProductCategoryRepository;
import com.ecommerce.myshop.dao.ProductRepository;
import com.ecommerce.myshop.dataTranferObject.ProductToSave;
import com.ecommerce.myshop.entity.Product;
import com.ecommerce.myshop.entity.ProductCategory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    @Transactional
    public Product saveProduct(ProductToSave receivedProduct) {

        //create product from received product
        Product product = createProductFromDTO(receivedProduct);

        //assign category to product with condition if category id is null if not null assign category to product
        //if category id is null create new category and assign it to product
        assignCategoryToProduct(receivedProduct, product);
        return productRepository.save(product);
    }

    private Product createProductFromDTO(ProductToSave receivedProduct) {
        Product product = new Product();
        product.setProductName(receivedProduct.getProductName());
        product.setProductDescription(receivedProduct.getProductDescription());
        product.setProductPrice(receivedProduct.getProductPrice());
        product.setProductImage(receivedProduct.getProductImage());
        product.setProductStockQuantity(receivedProduct.getProductStockQuantity());
        return product;
    }

    private void assignCategoryToProduct(ProductToSave receivedProduct, Product product) {
        if (receivedProduct.getCategory().getId() == null) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setCategoryName(receivedProduct.getCategory().getCategoryName());
            product.setCategory(productCategory);
        } else {
            product.setCategory(productCategoryRepository.findById((long) receivedProduct.getCategory().getId()).get());
        }
    }

}
