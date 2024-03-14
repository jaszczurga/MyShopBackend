package com.ecommerce.myshop.dataTranferObject;

import com.ecommerce.myshop.entity.ImageModel;
import com.ecommerce.myshop.entity.ProductCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductDto {
    private String productName;
    private String productDescription;
    private double productPrice;
    private int productStockQuantity;
    private ProductCategory category;
    private List<ImageModel> images;
}
