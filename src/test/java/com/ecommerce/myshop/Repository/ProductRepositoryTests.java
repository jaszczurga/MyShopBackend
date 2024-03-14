package com.ecommerce.myshop.Repository;

import com.ecommerce.myshop.dao.ProductRepository;
import com.ecommerce.myshop.entity.Product;
import com.ecommerce.myshop.entity.ProductCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTests{

    @Autowired
    private ProductRepository productRepository;

    //JUnit test for saving a product
    @DisplayName( "Test for saving a product")
    @Test
    public void givenProductObject_whenSave_thenReturnSavedProduct(){

        Product product = Product.builder()
                .productName("Test Product")
                .productPrice(1000)
                .productDescription("Test Description")
                .productStockQuantity( 10)
                .category( ProductCategory.builder()
                        .categoryName("Test Category2")
                        .build()
                )
                .build();

        Product savedProduct = productRepository.save(product);

        Assertions.assertThat(savedProduct).isNotNull();
        Assertions.assertThat(savedProduct.getId()).isGreaterThan(0);

    }

}
