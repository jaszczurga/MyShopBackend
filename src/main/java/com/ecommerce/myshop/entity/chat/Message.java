package com.ecommerce.myshop.entity.chat;

import com.ecommerce.myshop.entity.Authentication.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "messages")
@Data
public class Message{

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer messageId;

    @ManyToOne
    @JoinColumn (name = "conversation_id", referencedColumnName = "conversation_id")
    private Conversation conversation;

    @ManyToOne
    @JoinColumn (name = "user_id", referencedColumnName = "id")
    private User user;

    @Column (name = "content")
    private String content;

    @Column (name = "timestamp")
    private Date timestamp;
}

