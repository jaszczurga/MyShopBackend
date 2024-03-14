package com.ecommerce.myshop.Service;

import com.ecommerce.myshop.Exceptions.ConflictException;
import com.ecommerce.myshop.Exceptions.IllegalArgumentException;
import com.ecommerce.myshop.Exceptions.NotFoundException;
import com.ecommerce.myshop.dao.ImageRepository;
import com.ecommerce.myshop.dao.ProductCategoryRepository;
import com.ecommerce.myshop.dao.ProductRepository;
import com.ecommerce.myshop.dataTranferObject.ProductDto;
import com.ecommerce.myshop.entity.Product;
import com.ecommerce.myshop.entity.ProductCategory;
import com.ecommerce.myshop.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {


    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductCategoryRepository productCategoryRepository;
    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ProductServiceImpl productService;


    private ProductDto productDto;
    private Product product;

    @BeforeEach
    public void setUp() {
        productDto = ProductDto.builder()
                .productName( "Test Product" )
                .productPrice( 1000 )
                .productDescription( "Test Description" )
                .productStockQuantity( 10 )
                .category( ProductCategory.builder()
                        .categoryName( "Test Category" )
                        .build()
                )
                .images( new ArrayList<>() )
                .build();
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

    //JUnit test for
    @DisplayName("Test for saving a productDto")
    @Test
    public void givenProductDtoObject_whenSaveProduct_thenReturnProductObject() {
        // Arrange
        given( productRepository.save( any( Product.class ) ) ).willAnswer( invocation -> invocation.getArgument( 0 ) );

        // Act
        Product result = productService.saveProduct( productDto );

        // Assert
        assertThat( result ).isNotNull();
        assertThat( result.getProductName() ).isEqualTo( productDto.getProductName() );
        assertThat( result.getProductPrice() ).isEqualTo( productDto.getProductPrice() );
        assertThat( result.getProductDescription() ).isEqualTo( productDto.getProductDescription() );
        assertThat( result.getProductStockQuantity() ).isEqualTo( productDto.getProductStockQuantity() );
        assertThat( result.getCategory().getCategoryName() ).isEqualTo( productDto.getCategory().getCategoryName() );
    }

    @DisplayName("Test for NotFoundException when saving a productDto with non-existing category id")
    @Test
    public void givenProductDto_whenSaveProduct_thenThrowsNotFoundException() {
        // Arrange
        given( productRepository.save( any( Product.class ) ) ).willThrow( new NotFoundException( "Category id not found in database." ) );

        // Act & Assert
        NotFoundException exception = assertThrows( NotFoundException.class , () -> productService.saveProduct( productDto ) );
        assertTrue( exception.getMessage().contains( "Category id not found in database." ) );
    }

    @DisplayName("Test for ConflictException when saving a productDto with existing category name")
    @Test
    public void givenProductDto_whenSaveProduct_thenThrowsConflictException() {
        // Arrange
        given( productRepository.save( any( Product.class ) ) ).willThrow( new ConflictException( "Category name already exists." ) );

        // Act & Assert
        ConflictException exception = assertThrows( ConflictException.class , () -> productService.saveProduct( productDto ) );
        assertTrue( exception.getMessage().contains( "Category name already exists." ) );
        //verify(productRepository, never()).save(any(Product.class));
    }

    //JUnit test for getAllProducts
    @Test
    public void givenProductList_whenGetAllProducts_thenReturnProductList() {
        //given - precoditions for the test
        Page productPage = new PageImpl<>( List.of( product , product ) );
        given( productRepository.findAll( any( Pageable.class ) ) ).willReturn( productPage );
        Pageable pageable = PageRequest.of( 0 , 10 );

        //when - action or the behavior to be tested
        List<Product> products = productService.getAllProducts( pageable ).getContent();

        //then - the expected result
        assertThat( products ).isNotNull();
        assertThat( products.size() ).isEqualTo( 2 );
    }

    //JUnit test for getAllProducts empty list
    @Test
    public void givenEmptyProductList_whenGetAllProducts_thenReturnEmptyProductList() {
        //given - precoditions for the test
        Page productPage = new PageImpl<>( List.of() );
        given( productRepository.findAll( any( Pageable.class ) ) ).willReturn( productPage );

        //when - action or the behavior to be tested
        List<Product> products = productService.getAllProducts( PageRequest.of( 0 , 10 ) ).getContent();

        //then - the expected result
        assertThat( products ).isNotNull();
        assertThat( products.size() ).isEqualTo( 0 );
    }

    //JUnit test for get product by id
    @Test
    public void givenProductId_whenGetProductById_thenReturnProductWithGivenId() {
        //given - precoditions for the test
        given( productRepository.findById( any( Long.class ) ) ).willReturn( java.util.Optional.of( product ) );

        //when - action or the behavior to be tested
        Product foundProduct = productService.getProductById( 1L );

        //then - the expected result
        assertThat( foundProduct ).isNotNull();
        assertThat( foundProduct.getId() ).isEqualTo( product.getId() );
    }

    //JUnit test for

@Test
public void givenNullProductId_whenUpdateProduct_thenThrowIllegalArgumentException() {
    // Act & Assert
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> productService.updateProduct(productDto, null));
    assertTrue(exception.getMessage().contains("Product id is null"));
}

//JUnit test for deleting Product
    @DisplayName( "Test for deleting a product and return nothing")
    @Test
    public void givenProductId_whenDeleteProduct_thenNothing(){
        //given - precoditions for the test
        given( productRepository.findById( any( Long.class ) ) ).willReturn( Optional.of( product ) );
        willDoNothing().given( productRepository ).deleteById( 1L );

        //when - action or the behavior to be tested
        productService.deleteProduct( 1L );

        //then - the expected result
        verify( productRepository,times( 1 ) ).deleteById( 1L );
    }

    //JUnit test for deleting product with wrong id
        @Test
        public void givenWrongProductId_whenDeleteProduct_thenThrowNotFoundException(){
            //given - precoditions for the test
            given( productRepository.findById( any( Long.class ) ) ).willReturn( Optional.empty() );

            //when - action or the behavior to be tested
            NotFoundException exception = assertThrows( NotFoundException.class, () -> productService.deleteProduct( 1L ) );

            //then - the expected result
            assertTrue( exception.getMessage().contains("No such element found in database.") );
        }


}

