package com.ecommerce.myshop.dataTranferObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class UserDto{
    private String id;
    private String firstName;
    private String lastName;
    private String email;
}
