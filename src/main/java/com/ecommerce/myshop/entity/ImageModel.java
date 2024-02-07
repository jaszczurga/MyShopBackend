package com.ecommerce.myshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table (name = "image_table")
public class ImageModel {

    public ImageModel(String name, String type, byte[] picByte, Long productId) {
        this.name = name;
        this.type = type;
        this.picByte = picByte;
    }

    @Id
    @Column (name = "id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;
    //image bytes can have large lengths so we specify a value
    //which is more than the default length for picByte column


    @Column(name = "pic_byte", length = 1000)
    private byte[] picByte;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;



    public ImageModel() {

    }
}
