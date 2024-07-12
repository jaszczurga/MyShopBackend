package com.ecommerce.myshop.Controller;

import static org.assertj.core.api.Assertions.assertThat;
import com.ecommerce.myshop.controller.MyShopController;
import com.ecommerce.myshop.dao.Authentication.UserRepository;
import com.ecommerce.myshop.dataTranferObject.Authentication.RegisterRequest;
import com.ecommerce.myshop.entity.Authentication.Role;
import com.ecommerce.myshop.entity.Authentication.User;
import com.ecommerce.myshop.service.Authentication.JwtService;
import com.ecommerce.myshop.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(controllers = MyShopController.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository; // Mock the UserRepository

    @Autowired
    private ObjectMapper objectMapper;

    String email = "test@setup.com";


    @BeforeEach
    public void setUp() throws Exception {
        if(userRepository.findByEmail( email ).isEmpty()){
            RegisterRequest req = RegisterRequest.builder()
                    .firstName( "testFN" )
                    .lastName( "testLN" )
                    .email( email )
                    .password( "password" )
                    .build();

            ResultActions response = mockMvc.perform( post( "/api/v1/auth/register" )
                    .contentType( "application/json" )
                    .content( objectMapper.writeValueAsString( req ) )
            );
            response.andExpect( status().isOk() );
        }



    }

    @Test
    @DisplayName( "deleting user accounts" )
    public void givenUserEmail_whenRequestForDeletingAccount_thenDeleteUserFromDataBase() throws Exception {
        //given

        //when
        ResultActions response = mockMvc.perform( delete( "/api/v1/auth/delete")
                .param( "email",email )
                .contentType( "application/json" )
        );

        //then
        response.andExpect( status().isOk() );

        boolean isDeleted = userRepository.findByEmail( email ).isEmpty();
        assertThat( isDeleted ).isTrue();
    }




}
