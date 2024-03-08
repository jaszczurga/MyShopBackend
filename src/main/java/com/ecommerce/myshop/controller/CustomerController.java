package com.ecommerce.myshop.controller;

import com.ecommerce.myshop.dataTranferObject.ListDto;
import com.ecommerce.myshop.dataTranferObject.UserDto;
import com.ecommerce.myshop.entity.Authentication.User;
import com.ecommerce.myshop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RestController
@RequestMapping ("/api/users")
public class CustomerController{


    final  private UserService userService;

    //get all users endpoint
    @GetMapping ("/users")
    public ResponseEntity<ListDto> getAllUsers(@RequestParam(required = false,defaultValue = "0") int pageOfUsers,
                                               @RequestParam(required = false,defaultValue = "1") int numberOfUsers) {
        Pageable pageable = PageRequest.of(pageOfUsers, numberOfUsers);
        ListDto listDto = new ListDto();
        Page<User> users = userService.getAllUsers(pageable);

        listDto.setList( users.stream().map(user ->
                new UserDto(
                        user.getId().toString(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail()))
                .toList());
        return ResponseEntity.ok(listDto);
    }



}
