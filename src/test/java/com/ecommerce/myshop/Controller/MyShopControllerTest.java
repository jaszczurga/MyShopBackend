package com.ecommerce.myshop.Controller;

import com.ecommerce.myshop.config.Authentication.SecurityConfiguration;
import com.ecommerce.myshop.controller.MyShopController;
import com.ecommerce.myshop.dao.Authentication.UserRepository;
import com.ecommerce.myshop.dataTranferObject.ProductDto;
import com.ecommerce.myshop.entity.Product;
import com.ecommerce.myshop.service.Authentication.JwtService;
import com.ecommerce.myshop.service.ProductService;
import com.ecommerce.myshop.service.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MyShopController.class)
@ImportAutoConfiguration(classes = SecurityConfiguration.class)
public class MyShopControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserRepository userRepository; // Mock the UserRepository

    @Autowired
    private ObjectMapper objectMapper;

    //JUnit test for saving product
    @Test
    @DisplayName("Test for saving a product")
    public void givenProductObject_whenCreateProduct_thenReturnSavedProduct() throws Exception {
        //given - precoditions for the test
        ProductDto product = ProductDto.builder()
                .productName( "Test Product" )
                .productPrice( 1000 )
                .productDescription( "Test Description" )
                .productStockQuantity( 10 )
                .build();
        BDDMockito.given( productService.saveProduct( any( ProductDto.class ) ) ).willAnswer( invocation -> {
            ProductDto productDto = invocation.getArgument( 0 );
            return Product.builder()
                    .id( 1L )
                    .productName( productDto.getProductName() )
                    .productPrice( productDto.getProductPrice() )
                    .productDescription( productDto.getProductDescription() )
                    .productStockQuantity( productDto.getProductStockQuantity() )
                    .build();
        } );
        //when - action or the behavior to be tested
        ResultActions response = mockMvc.perform( post( "/api/action/saveProduct" )
                .contentType( "application/json" )
                .content( objectMapper.writeValueAsString( product ) )
        );

        //then - the expected result
        response.andDo( MockMvcResultHandlers.print() )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.id" , notNullValue() ) )
                .andExpect( jsonPath( "$.productName" , is( product.getProductName() ) ) )
                .andExpect( jsonPath( "$.productPrice" , is( product.getProductPrice() ) ) )
                .andExpect( jsonPath( "$.productDescription" , is( product.getProductDescription() ) ) )
                .andExpect( jsonPath( "$.productStockQuantity" , is( product.getProductStockQuantity() ) ) );
    }

    @Test
    public void givenListOfProducts_whenGetAllProducts_thenReturnProductsResponse() throws Exception {
        //given - precoditions for the test
        List<Product> expectedProducts = List.of(
                Product.builder()
                        .id( 1L )
                        .productName( "Product 1" )
                        .productPrice( 100 )
                        .productDescription( "Description 1" )
                        .productStockQuantity( 10 )
                        .build() ,
                Product.builder()
                        .id( 2L )
                        .productName( "Product 2" )
                        .productPrice( 200 )
                        .productDescription( "Description 2" )
                        .productStockQuantity( 20 )
                        .build()
        );
        Page<Product> productPage = new PageImpl<>( expectedProducts );
        BDDMockito.given( productService.getAllProducts( any( Pageable.class ) ) ).willReturn( productPage );

        //when - action or the behavior to be tested
        ResultActions response = mockMvc.perform( get( "/api/action/products" )
                .contentType( "application/json" ) );

        //then - the expected result
        response.andDo( MockMvcResultHandlers.print() )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.content" , notNullValue() ) )
                .andExpect( jsonPath( "$.content[0].id" , is( 1 ) ) )
                .andExpect( jsonPath( "$.content[0].productName" , is( "Product 1" ) ) )
                .andExpect( jsonPath( "$.content[0].productPrice" , is( 100.0 ) ) )
                .andExpect( jsonPath( "$.content[0].productDescription" , is( "Description 1" ) ) )
                .andExpect( jsonPath( "$.content[0].productStockQuantity" , is( 10 ) ) )
                .andExpect( jsonPath( "$.content[1].id" , is( 2 ) ) )
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
        Product product = Product.builder()
                .id( 1L )
                .productName( "Test Product" )
                .productPrice( 1000 )
                .productDescription( "Test Description" )
                .productStockQuantity( 10 )
                .build();
        BDDMockito.given( productService.getProductById( 1L ) ).willReturn( product );

        //when - action or the behavior to be tested
        ResultActions response = mockMvc.perform( get( "/api/action/product/1" )
                .contentType( "application/json" )
        );

        //then - the expected result
        response.andDo( MockMvcResultHandlers.print() )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.id" , is( 1 ) ) )
                .andExpect( jsonPath( "$.productName" , is( "Test Product" ) ) )
                .andExpect( jsonPath( "$.productPrice" , is( 1000.0 ) ) )
                .andExpect( jsonPath( "$.productDescription" , is( "Test Description" ) ) )
                .andExpect( jsonPath( "$.productStockQuantity" , is( 10 ) ) );
    }

    @Test
    public void givenProductDtoAndProductId_whenUpdateProduct_thenReturnUpdatedProduct() throws Exception {
        //given - precoditions for the test
        Long productId = 1L;
        ProductDto productDto = ProductDto.builder()
                .productName( "Updated Product" )
                .productPrice( 2000 )
                .productDescription( "Updated Description" )
                .productStockQuantity( 20 )
                .build();

        Product expectedProduct = Product.builder()
                .id( productId )
                .productName( productDto.getProductName() )
                .productPrice( productDto.getProductPrice() )
                .productDescription( productDto.getProductDescription() )
                .productStockQuantity( productDto.getProductStockQuantity() )
                .build();
        BDDMockito.given( productService.updateProduct( any( ProductDto.class ) , any(Long.class) ) ).willReturn( expectedProduct );

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

    @Test
    public void givenImageId_whenDeleteImage_thenReturnResponseEntity() throws Exception {
        //given - precoditions for the test
        Long imageId = 1L;
        BDDMockito.given( productService.deleteImage( imageId ) ).willReturn( ResponseEntity.ok().build() );

        //when - action or the behavior to be tested
        ResultActions response = mockMvc.perform( delete( "/api/action/deleteImage/" + imageId )
                .contentType( "application/json" ) );

        //then - the expected result
        response.andDo( MockMvcResultHandlers.print() )
                .andExpect( status().isOk() );
    }
}
