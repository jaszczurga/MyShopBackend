package com.ecommerce.myshop.dataTranferObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ListDto{
    private List<UserDto> list;
}
