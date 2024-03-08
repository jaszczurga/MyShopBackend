package com.ecommerce.myshop.dataTranferObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class UserDto{
    private String id;
    private String firstName;
    private String lastName;
    private String email;
}
