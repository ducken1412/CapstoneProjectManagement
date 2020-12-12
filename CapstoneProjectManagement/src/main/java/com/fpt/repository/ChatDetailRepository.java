package com.fpt.repository;

import com.fpt.entity.Chat;
import com.fpt.entity.ChatDetails;
import com.fpt.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ChatDetailRepository extends JpaRepository<ChatDetails,Integer> {
    @Query(value = "SELECT COUNT(DISTINCT cd.chat.roomId ) FROM ChatDetails cd WHERE cd.user.id = ?1 AND cd.readStatus = 'unread'")
    int findNumberNewMessage(String userId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE chat_details cd SET cd.read_status = 'read' WHERE cd.chat_id IN (SELECT c.id FROM chat c WHERE c.room = ?1) AND cd.user_id = ?2", nativeQuery = true)
    int updateChatStatusRead(String roomId, String userId);
}
