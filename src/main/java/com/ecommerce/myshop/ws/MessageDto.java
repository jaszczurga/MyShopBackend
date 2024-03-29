package com.ecommerce.myshop.ws;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MessageDto{

    private String receiverId;
    private String content;
    private String senderId;
    private Date timestamp;
}
