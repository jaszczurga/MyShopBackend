package com.ecommerce.myshop.dataTranferObject;

import com.ecommerce.myshop.entity.ProductCategory;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductToSave {
    private String productName;
    private String productDescription;
    private String imageUrl;
    private double productPrice;
    private int productStockQuantity;
    private String productImage;
    private ProductCategory category;
}
