package com.fpt.repository;

import com.fpt.dto.ChatDTO;
import com.fpt.entity.Chat;
import com.fpt.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Integer> {
    List<Chat> findByRoomId(String roomId);
    @Query(value = "SELECT DISTINCT(c.user) FROM Chat c WHERE c.roomId = ?1")
    List<Users> findUsersInRoom(String roomId);
    @Query(value = "SELECT p.id id, p.title title, cd.read_status readStatus, c.type type FROM posts p INNER JOIN chat c ON p.id = (SELECT SUBSTRING_INDEX(c.room, '_', -1)) INNER JOIN chat_details cd ON c.id = cd.chat_id WHERE cd.user_id = ?1 GROUP BY p.id, p.title, cd.read_status", nativeQuery = true)
    List<ChatDTO> findChatsByUserId(String userId);

    @Query(value = "SELECT c.`room` id, (SELECT us.user_name FROM users us WHERE us.id = c.`user_id`) title, cd.read_status readStatus, c.type type FROM users u INNER JOIN chat c ON u.id = c.`user_id` INNER JOIN chat_details cd ON c.id = cd.chat_id WHERE cd.user_id = ?1 AND c.`user_id` != ?1 AND c.type = 'specific' GROUP BY c.`room`, cd.read_status", nativeQuery = true)
    List<ChatDTO> findChatPrivateByUserId(String userId);

    @Query(value = "SELECT DISTINCT c.roomId FROM Chat c WHERE c.roomId = ?1 OR c.roomId = ?2")
    String findRoomChatPrivate(String roomId1, String roomId2);
}
