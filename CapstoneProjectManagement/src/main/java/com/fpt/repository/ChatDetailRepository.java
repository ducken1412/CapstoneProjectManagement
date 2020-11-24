package com.fpt.repository;

import com.fpt.entity.Chat;
import com.fpt.entity.ChatDetails;
import com.fpt.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatDetailRepository extends JpaRepository<ChatDetails,Integer> {
    @Query(value = "SELECT COUNT(DISTINCT cd.chat.roomId ) FROM ChatDetails cd WHERE cd.user.id = ?1 AND cd.readStatus = 'unread'")
    int findNumberNewMessage(String userId);
}
