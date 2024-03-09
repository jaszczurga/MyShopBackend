package com.ecommerce.myshop.entity.chat;

import com.ecommerce.myshop.entity.Authentication.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn (name = "conversation_id", referencedColumnName = "conversationId")
    private Conversation conversation;

    @ManyToOne
    @JoinColumn (name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn (name = "user_sender_id", referencedColumnName = "id")
    private User userSender;



    @Column (name = "content")
    private String content;

    @Column (name = "timestamp")
    private Date timestamp;

    @PrePersist
    protected void onCreate() {
        timestamp = new Date();
    }
}

