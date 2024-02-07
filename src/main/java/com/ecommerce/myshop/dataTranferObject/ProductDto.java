package com.ecommerce.myshop.dataTranferObject;

import com.ecommerce.myshop.entity.ImageModel;
import com.ecommerce.myshop.entity.ProductCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDto {
    private String productName;
    private String productDescription;
    private double productPrice;
    private int productStockQuantity;
    private ProductCategory category;
    private List<ImageModel> images;
}
