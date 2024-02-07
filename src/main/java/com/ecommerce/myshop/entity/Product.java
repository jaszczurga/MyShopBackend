package com.ecommerce.myshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "product")
//@Data // Lombok annotation to generate getters, setters, constructors, toString, hash, equals, etc.
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue (strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column (name = "product_id")
    private Long id;

    @Column (name = "name")
    private String productName;

    @Column (name = "description")
    private String productDescription;

    @Column (name = "price")
    private double productPrice;

    @Column (name = "stock_quantity")
    private int productStockQuantity;

//    @Column(name = "product_img")
//    private String productImage;


    @JsonIgnoreProperties ("products")
    @ManyToOne (cascade = {CascadeType.PERSIST , CascadeType.MERGE , CascadeType.REFRESH})
    @JoinColumn (name = "category_id")
    private ProductCategory category;

    @OneToMany (cascade = CascadeType.ALL, mappedBy = "product")
    private List<ImageModel> images;


    //metohd to add image to product
    public void addImage(ImageModel imageModel) {

        if (images != null) {
            if (images == null) {
                images = new ArrayList<>();
            }
            imageModel.setProduct( this );
            images.add( imageModel );

        }
    }
}
