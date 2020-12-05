package com.fpt.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "[ChatDetails]")
public class ChatDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Chat chat;
    @Column(name = "read_status", columnDefinition = "NVARCHAR(50) NOT NULL")
    private String readStatus;

    public ChatDetails(Integer id, Users user, Chat chat, String readStatus) {
        this.id = id;
        this.user = user;
        this.chat = chat;
        this.readStatus = readStatus;
    }

    public ChatDetails() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }
}
