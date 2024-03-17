package com.ecommerce.myshop.Repository;

import com.ecommerce.myshop.dao.ProductRepository;
import com.ecommerce.myshop.entity.Product;
import com.ecommerce.myshop.entity.ProductCategory;
import static org.assertj.core.api.Assertions.assertThat;

import com.ecommerce.myshop.integration.AbstractContainerBasedTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

//findByJPQL
@DataJpaTest
@AutoConfigureTestDatabase (replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTests  extends AbstractContainerBasedTest {

    @Autowired
    private ProductRepository productRepository;

    private Product product;


    @BeforeEach
    public void setUp(){
        product = Product.builder()
                .productName( "Test Product" )
                .productPrice( 1000 )
                .productDescription( "Test Description" )
                .productStockQuantity( 10 )
                .category( ProductCategory.builder()
                        .categoryName( "Test Category" )
                        .build()
                )
                .build();
    }

    //JUnit test for saving a product
    @DisplayName ("Test for saving a product")
    @Test
    public void givenProductObject_whenSave_thenReturnSavedProduct() {

        Product savedProduct = productRepository.save( product );

        assertThat( savedProduct ).isNotNull();
        assertThat( savedProduct.getId() ).isGreaterThan( 0 );

    }

    //JUnit test for
    @DisplayName ("get all products operation")
    @Test
    public void givenProductList_whenFindAll_thenProductList() {
        //given - precoditions for the test

        Product product2 = Product.builder()
                .productName( "Test Product2" )
                .productPrice( 1000 )
                .productDescription( "Test Description2" )
                .productStockQuantity( 10 )
                .category( ProductCategory.builder()
                        .categoryName( "Test Category2" )
                        .build()
                )
                .build();
        productRepository.save( product );
        productRepository.save( product2 );
        //when - action or the behavior to be tested

        List<Product> products = productRepository.findAll();

        //then - the expected result
        assertThat( products ).isNotNull();
        assertThat( products.size() ).isEqualTo( 2 );
    }

    //JUnit test for get product by id
    @DisplayName ("get product by id operation")
    @Test
    public void givenProductId_whenFindById_thenProduct() {
        //given - precoditions for the test
        Product savedProduct = productRepository.save( product );

        //when - action or the behavior to be tested
        Product foundProduct = productRepository.findById( savedProduct.getId() ).get();

        //then - the expected result
        assertThat( foundProduct ).isNotNull();
        assertThat( foundProduct.getId() ).isEqualTo( savedProduct.getId() );
    }

    //JUnit test for get products by keyword
    @Test
    public void givenKeyWork_whenFindByProductNameContaining_thenProductList() {
        //given - precoditions for the test
        product.setProductName( "Test Product1");
        productRepository.save( product );
        Pageable pageable = PageRequest.of( 0 , 10 );

        //when - action or the behavior to be tested
        List<Product> products = productRepository.findByProductNameContaining( "Test Product1" , pageable ).getContent();

        //then - the expected result
        assertThat( products ).isNotNull();
        assertThat( products.size() ).isEqualTo( 1 );
    }

    //JUnit test for update product
    @Test
    @DisplayName( "Test for updating a product")
    public void givenProductObject_whenUpdatingProduct_thenReturnUpdatedProduct() {
        //given - precoditions for the test

        Product savedProduct = productRepository.save( product );
        savedProduct.setProductName( "Updated Product" );

        //when - action or the behavior to be tested
        Product updatedProduct = productRepository.save( savedProduct );

        //then - the expected result
        assertThat( updatedProduct ).isNotNull();
        assertThat( updatedProduct.getId() ).isEqualTo( savedProduct.getId() );
        assertThat( updatedProduct.getProductName() ).isEqualTo( "Updated Product" );
    }

    //JUnit test for deleting product
    @Test
    @DisplayName( "Test for deleting a product")
    public void given_when_then() {
        //given - precoditions for the test

        Product savedProduct = productRepository.save( product );

        //when - action or the behavior to be tested
        productRepository.deleteById( savedProduct.getId() );

        //then - the expected result
        assertThat( productRepository.findById( savedProduct.getId() ) ).isEmpty();
    }


}
