package com.ecommerce.myshop.integration;

import com.ecommerce.myshop.dao.ProductCategoryRepository;
import com.ecommerce.myshop.dao.ProductRepository;
import com.ecommerce.myshop.dataTranferObject.ProductDto;
import com.ecommerce.myshop.entity.Product;
import com.ecommerce.myshop.entity.ProductCategory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MyShopControllerTestsIntegration extends AbstractionBasedTest {



    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductDto productDto;
    private Product product;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        productCategoryRepository.deleteAll();
        this.productDto = ProductDto.builder()
                .productName( "Test Product" )
                .productPrice( 1000 )
                .productDescription( "Test Description" )
                .productStockQuantity( 10 )
                .category( ProductCategory.builder().categoryName( "test1" ).build() )
                .images( List.of() )
                .build();
        this.product = Product.builder()
                .productName( "Test Product" )
                .productPrice( 1000 )
                .productDescription( "Test Description" )
                .productStockQuantity( 10 )
                .category( ProductCategory.builder().categoryName( "test1" ).build() )
                .images( List.of() )
                .build();
    }

    @Test
    @DisplayName("Test for saving a product")
    public void givenProductObject_whenCreateProduct_thenReturnSavedProduct() throws Exception {
        //given - precoditions for the test

        //when - action or the behavior to be tested
        ResultActions response = mockMvc.perform( post( "/api/action/saveProduct" )
                .contentType( "application/json" )
                .content( objectMapper.writeValueAsString( productDto ) )
        );

        //then - the expected result
        response.andDo( MockMvcResultHandlers.print() )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.id" , notNullValue() ) )
                .andExpect( jsonPath( "$.productName" , is( productDto.getProductName() ) ) )
                .andExpect( jsonPath( "$.productPrice" , is( productDto.getProductPrice() ) ) )
                .andExpect( jsonPath( "$.productDescription" , is( productDto.getProductDescription() ) ) )
                .andExpect( jsonPath( "$.productStockQuantity" , is( productDto.getProductStockQuantity() ) ) );
    }

    @Test
    public void givenListOfProducts_whenGetAllProducts_thenReturnProductsResponse() throws Exception {
        //given - precoditions for the test
        List<Product> expectedProducts = List.of(
                Product.builder()
                        .productName( "Product 1" )
                        .productPrice( 100 )
                        .productDescription( "Description 1" )
                        .productStockQuantity( 10 )
                        .category( ProductCategory.builder().categoryName( "test1" ).build() )
                        .images( List.of() )
                        .build() ,
                Product.builder()
                        .productName( "Product 2" )
                        .productPrice( 200 )
                        .productDescription( "Description 2" )
                        .productStockQuantity( 20 )
                        .category( ProductCategory.builder().categoryName( "test2" ).build() )
                        .images( List.of() )
                        .build()
        );
        productRepository.saveAll( expectedProducts );

        //when - action or the behavior to be tested
        ResultActions response = mockMvc.perform( get( "/api/action/products" )
                .contentType( "application/json" ) );

        //then - the expected result
        response.andDo( MockMvcResultHandlers.print() )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.content" , notNullValue() ) )
                .andExpect( jsonPath( "$.content[0].productName" , is( "Product 1" ) ) )
                .andExpect( jsonPath( "$.content[0].productPrice" , is( 100.0 ) ) )
                .andExpect( jsonPath( "$.content[0].productDescription" , is( "Description 1" ) ) )
                .andExpect( jsonPath( "$.content[0].productStockQuantity" , is( 10 ) ) )
                .andExpect( jsonPath( "$.content[1].productName" , is( "Product 2" ) ) )
                .andExpect( jsonPath( "$.content[1].productPrice" , is( 200.0 ) ) )
                .andExpect( jsonPath( "$.content[1].productDescription" , is( "Description 2" ) ) )
                .andExpect( jsonPath( "$.content[1].productStockQuantity" , is( 20 ) ) );
    }

    //JUnit test for get product by id
    @Test
    @DisplayName("get product by id operation")
    public void givenProductId_whenGetProductById_thenReturnProductObject() throws Exception {
        //given - precoditions for the test
          productRepository.save( product );
        //when - action or the behavior to be tested
        ResultActions response = mockMvc.perform( get( "/api/action/product/" + product.getId() )
                .contentType( "application/json" )
        );

        //then - the expected result
        response.andDo( MockMvcResultHandlers.print() )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.productName" , is( "Test Product" ) ) )
                .andExpect( jsonPath( "$.productPrice" , is( 1000.0 ) ) )
                .andExpect( jsonPath( "$.productDescription" , is( "Test Description" ) ) )
                .andExpect( jsonPath( "$.productStockQuantity" , is( 10 ) ) );
    }

    @Test
    public void givenProductDtoAndProductId_whenUpdateProduct_thenReturnUpdatedProduct() throws Exception {

        ProductCategory category =  ProductCategory.builder().categoryName( "testing category" ).build();
        productCategoryRepository.save( category );
        //given - precoditions for the test
        Long productId = productRepository.save( product ).getId();
        productDto.setProductName( "Updated Product" );
        productDto.setProductPrice( 2000 );
        productDto.setProductDescription( "Updated Description" );
        productDto.setProductStockQuantity( 20 );
        productDto.setCategory( category);

        //when - action or the behavior to be tested
        ResultActions response = mockMvc.perform( patch( "/api/action/updateProduct/" + productId )
                .contentType( "application/json" )
                .content( objectMapper.writeValueAsString( productDto ) ) );

        //then - the expected result
        response.andDo( MockMvcResultHandlers.print() )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.id" , is( productId.intValue() ) ) )
                .andExpect( jsonPath( "$.productName" , is( productDto.getProductName() ) ) )
                .andExpect( jsonPath( "$.productPrice" , is( productDto.getProductPrice() ) ) )
                .andExpect( jsonPath( "$.productDescription" , is( productDto.getProductDescription() ) ) )
                .andExpect( jsonPath( "$.productStockQuantity" , is( productDto.getProductStockQuantity() ) ) );
    }


}
