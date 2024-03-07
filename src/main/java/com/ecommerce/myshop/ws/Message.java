package com.ecommerce.myshop.ws;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Message{
    private String content;
    private String receiverId;
    private String senderId;
}
