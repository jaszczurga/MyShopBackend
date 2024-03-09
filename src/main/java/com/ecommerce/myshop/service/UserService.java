package com.ecommerce.myshop.service;

import com.ecommerce.myshop.entity.Authentication.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService{

    Page<User> getAllUsers(Pageable pageable);

}
