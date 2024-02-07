package com.ecommerce.myshop.dataTranferObject;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ImageDto {
    private Long id;
    private String name;
    private String type;
    private byte[] picByte;

    public ImageDto() {
    }

    public ImageDto( Long id ,String name, String type, byte[] picByte) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.picByte = picByte;
    }

}
