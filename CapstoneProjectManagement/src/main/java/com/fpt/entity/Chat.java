package com.fpt.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "[Chat]")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Integer id;
    @Column(name = "content", columnDefinition = "NVARCHAR(256) NOT NULL")
    private String content;
    @Column(name = "room", columnDefinition = "NVARCHAR(50) NOT NULL")
    private String roomId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "chat")
    private List<ChatDetails> chatDetail;

    public Chat() {
    }

    public Chat(Integer id, String content, String roomId, Users user, List<ChatDetails> chatDetail) {
        this.id = id;
        this.content = content;
        this.roomId = roomId;
        this.user = user;
        this.chatDetail = chatDetail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public List<ChatDetails> getChatDetail() {
        return chatDetail;
    }

    public void setChatDetail(List<ChatDetails> chatDetail) {
        this.chatDetail = chatDetail;
    }
}
