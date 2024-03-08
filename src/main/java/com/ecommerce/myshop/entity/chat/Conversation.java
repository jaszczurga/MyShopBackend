package com.ecommerce.myshop.entity.chat;


import com.ecommerce.myshop.entity.Authentication.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table (name = "conversations")
@Data
public class Conversation{

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer conversationId;

    @ManyToOne
    @JoinColumn (name = "user1_id", referencedColumnName = "id")
    private User user1;

    @ManyToOne
    @JoinColumn (name = "user2_id", referencedColumnName = "id")
    private User user2;
}
