package com.ecommerce.myshop.integration;

import com.ecommerce.myshop.dao.Authentication.UserRepository;
import com.ecommerce.myshop.dao.ProductCategoryRepository;
import com.ecommerce.myshop.dao.ProductRepository;
import com.ecommerce.myshop.dao.checkout.OrderRepository;
import com.ecommerce.myshop.dataTranferObject.ProductDto;
import com.ecommerce.myshop.dataTranferObject.checkout.PurchaseDto;
import com.ecommerce.myshop.entity.Authentication.User;
import com.ecommerce.myshop.entity.Checkout.Address;
import com.ecommerce.myshop.entity.Checkout.Customer;
import com.ecommerce.myshop.entity.Checkout.Order;
import com.ecommerce.myshop.entity.Checkout.OrderItem;
import com.ecommerce.myshop.entity.Product;
import com.ecommerce.myshop.entity.ProductCategory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MyShopControllerTestsIntegration extends AbstractContainerBasedTest {



    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductDto productDto;
    private Product product;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
        productRepository.deleteAll();
        productCategoryRepository.deleteAll();
        userRepository.deleteAll();
        this.productDto = ProductDto.builder()
                .productName( "Test Product" )
                .productPrice( 1000 )
                .productDescription( "Test Description" )
                .productStockQuantity( 1000 )
                .category( ProductCategory.builder().categoryName( "test1" ).build() )
                .images( List.of() )
                .build();
        this.product = Product.builder()
                .productName( "Test Product" )
                .productPrice( 1000 )
                .productDescription( "Test Description" )
                .productStockQuantity( 1000 )
                .category( ProductCategory.builder().categoryName( "test1" ).build() )
                .images( List.of() )
                .build();
        //create a user
//        userRepository.save( User.builder()
//                .email( "admin2@admin.com" )
//                .password( "admin" )
//                .build() );
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
        product.setProductStockQuantity( 10 );
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

    @Test@DisplayName("Test for updating a product")
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

    @Test
    @DisplayName( "Test for placing an order" )
    public void givenPurchaseDto_whenPlaceOrder_thenReturnPurchaseResponse() throws Exception {
        //given - precoditions for the test

        //save example Product to database
        product.setProductStockQuantity( 20 );
        Product p = productRepository.save( product );
        Order order = new Order();
        order.setTotalQuantity( 1 );
        order.setTotalPrice( new java.math.BigDecimal( 1000 ) );
        Set<OrderItem> lista = Set.of( OrderItem.builder()
                .product( p )
                .quantity( 1 )
                .build() );

        Address address = Address.builder()
                .street( "Test Street" )
                .city( "Test City" )
                .state( "Test State" )
                .country( "Test Country" )
                .zipCode( "Test ZipCode" )
                .build();
        Customer customer = Customer.builder()
                .email( "admin@admin.com" )
                .firstName( "Test" )
                .lastName( "Test" )
                .build();

        PurchaseDto purchaseDto = PurchaseDto.builder()
                .order(order )
                .orderItems( lista)
                .address( address )
                .customer( customer )
                .build();

        //when - action or the behavior to be tested
        ResultActions response = mockMvc.perform( post( "/api/checkout/purchase" )
                .contentType( "application/json" )
                .content( objectMapper.writeValueAsString( purchaseDto ) )
        );

        //then - the expected result
        response.andDo( MockMvcResultHandlers.print() )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.orderTrackingNumber" , notNullValue() ) );
    }
    @Test
    @DisplayName( "Test for placing an order with concurrently users and with enough stock" )
    public void givenPurchaseDto_whenPlaceOrderConcurrently_thenReturnPurchaseResponse() throws Exception {

        int numberOfUsers = 10;
        int stockQuantityOfTestingProduct = 10;
        List<String> emails = new ArrayList<>();

        for (int i = 0; i < numberOfUsers; i++) {
            emails.add("test" + (i + 1) + "@user.com");
        }

        for (String email : emails) {
            userRepository.save(User.builder()
                    .email(email)
                    .password("admin")
                    .build());
        }

        // Given - preconditions for the test
        product.setProductStockQuantity(stockQuantityOfTestingProduct);
        // Save example Product to database
        Product p = productRepository.save(product);

        Order order = new Order();
        order.setTotalQuantity(1);
        order.setTotalPrice(new java.math.BigDecimal(1000));
        Set<OrderItem> orderItems = Set.of(OrderItem.builder()
                .product(p)
                .quantity(1)
                .build());

        Address address = Address.builder()
                .street("Test Street")
                .city("Test City")
                .state("Test State")
                .country("Test Country")
                .zipCode("Test ZipCode")
                .build();

        List<PurchaseDto> purchaseDtoList = new ArrayList<>();
        for (int i = 0; i < numberOfUsers; i++) {
            PurchaseDto purchaseDto = PurchaseDto.builder()
                    .order(order)
                    .orderItems(orderItems)
                    .address(address)
                    .customer(Customer.builder()
                            .email(emails.get(i))
                            .firstName("Test")
                            .lastName("Test")
                            .build())
                    .build();
            purchaseDtoList.add(purchaseDto);
        }

        // When - action or the behavior to be tested
        ExecutorService executor = Executors.newFixedThreadPool(numberOfUsers);
        List<Future<ResultActions>> futures = new ArrayList<>();

        for (PurchaseDto purchaseDto : purchaseDtoList) {
            Future<ResultActions> future = executor.submit(() -> mockMvc.perform(post("/api/checkout/purchase")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(purchaseDto))
            ));
            futures.add(future);
        }

        // Then - the expected result
        for (Future<ResultActions> future : futures) {
            ResultActions response = future.get();
            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.orderTrackingNumber", notNullValue()));
        }

        executor.shutdown();
    }

    @Test
    @DisplayName( "Test for placing an order with concurrently users and without enough stock")
    public void givenPurchaseDto_whenPlaceOrderConcurrently_thenReturnPurchaseResponseWithFailure() throws Exception {

        int numberOfUsers = 10;
        int stockQuantityOfTestingProduct = 1;
        List<String> emails = new ArrayList<>();

        for (int i = 0; i < numberOfUsers; i++) {
            emails.add("test" + (i + 1) + "@user.com");
        }

        for (String email : emails) {
            userRepository.save(User.builder()
                    .email(email)
                    .password("admin")
                    .build());
        }

        // Given - preconditions for the test
        product.setProductStockQuantity(stockQuantityOfTestingProduct);
        // Save example Product to database
        Product p = productRepository.save(product);

        Order order = new Order();
        order.setTotalQuantity(1);
        order.setTotalPrice(new java.math.BigDecimal(1000));
        Set<OrderItem> orderItems = Set.of(OrderItem.builder()
                .product(p)
                .quantity(1)
                .build());

        Address address = Address.builder()
                .street("Test Street")
                .city("Test City")
                .state("Test State")
                .country("Test Country")
                .zipCode("Test ZipCode")
                .build();

        List<PurchaseDto> purchaseDtoList = new ArrayList<>();
        for (int i = 0; i < numberOfUsers; i++) {
            PurchaseDto purchaseDto = PurchaseDto.builder()
                    .order(order)
                    .orderItems(orderItems)
                    .address(address)
                    .customer(Customer.builder()
                            .email(emails.get(i))
                            .firstName("Test")
                            .lastName("Test")
                            .build())
                    .build();
            purchaseDtoList.add(purchaseDto);
        }

        // When - action or the behavior to be tested
        ExecutorService executor = Executors.newFixedThreadPool(numberOfUsers);
        List<Future<ResultActions>> futures = new ArrayList<>();

        for (PurchaseDto purchaseDto : purchaseDtoList) {
            Future<ResultActions> future = executor.submit(() -> mockMvc.perform(post("/api/checkout/purchase")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(purchaseDto))
            ));
            futures.add(future);
        }

        int successfullResponses = 0;
        int failedResponses = 0;

        // Then - the expected result
        for (Future<ResultActions> future : futures) {
            ResultActions response = future.get();
            if(response.andReturn().getResponse().getStatus() ==200){
                successfullResponses++;
            }else {
                failedResponses++;
            }
        }

        assertTrue(successfullResponses==stockQuantityOfTestingProduct);
        assertTrue(failedResponses == numberOfUsers - stockQuantityOfTestingProduct);
        assertTrue( productRepository.findById(p.getId()).get().getProductStockQuantity() == 0);

        executor.shutdown();
    }




}
