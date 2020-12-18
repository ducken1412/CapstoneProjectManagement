package com.fpt.repository;

import com.fpt.dto.ChatDTO;
import com.fpt.entity.Chat;
import com.fpt.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {
    List<Chat> findByRoomId(String roomId);

    @Query(value = "SELECT DISTINCT(c.user) FROM Chat c WHERE c.roomId = ?1")
    List<Users> findUsersInRoom(String roomId);

    @Query(value = "SELECT p.id id, p.title title, cd.read_status readStatus, c.type type FROM posts p INNER JOIN chat c ON p.id = (SELECT SUBSTRING_INDEX(c.room, '_', -1)) INNER JOIN chat_details cd ON c.id = cd.chat_id WHERE cd.user_id = ?1 AND c.type = 'group' GROUP BY p.id, p.title, cd.read_status, c.type", nativeQuery = true)
    List<ChatDTO> findChatsByUserId(String userId);

    @Query(value = "SELECT c.`room` id, (SELECT us.user_name FROM users us WHERE us.id = c.`user_id`) title, cd.read_status readStatus, c.type type FROM users u INNER JOIN chat c ON u.id = c.`user_id` INNER JOIN chat_details cd ON c.id = cd.chat_id WHERE cd.user_id = ?1 AND c.`user_id` != ?1 AND c.type = 'specific' GROUP BY c.`room`, cd.read_status, c.`user_id`", nativeQuery = true)
    List<ChatDTO> findChatPrivateByUserId(String userId);

    @Query(value = "SELECT DISTINCT id, title, readStatus,type FROM (SELECT \n" +
            "  c.`room` id,\n" +
            "  'Heads And Training Department' title,\n" +
            "  cd.read_status readStatus,\n" +
            "  c.type type \n" +
            "FROM\n" +
            "  users u \n" +
            "  INNER JOIN chat c \n" +
            "    ON u.id = c.`user_id` \n" +
            "  INNER JOIN chat_details cd \n" +
            "    ON c.id = cd.chat_id \n" +
            "WHERE cd.user_id = ?1 \n" +
            "  AND c.room = 'gr_tr_dep_heads' AND  cd.read_status = 'unread' \n" +
            "GROUP BY c.`room`,\n" +
            "  cd.read_status,\n" +
            "  c.`user_id`, c.type) tmp", nativeQuery = true)
    ChatDTO findChatTrainingDeptAndHeads(String userId);

    @Query(value = "SELECT DISTINCT id, title, readStatus,type FROM (SELECT \n" +
            "  c.`room` id,\n" +
            "  (SELECT cp.name FROM `capstone_projects` cp INNER JOIN `capstone_project_details` cpd ON cp.`id` = cpd.capstone_project_id WHERE cpd.user_id = cd.`user_id`) title,\n" +
            "  cd.read_status readStatus,\n" +
            "  c.type type \n" +
            "FROM\n" +
            "  users u \n" +
            "  INNER JOIN chat c \n" +
            "    ON u.id = c.`user_id` \n" +
            "  INNER JOIN chat_details cd \n" +
            "    ON c.id = cd.chat_id \n" +
            "WHERE cd.user_id = ?1 \n" +
            "  AND c.room = ?2 AND  cd.read_status = 'unread' \n" +
            "GROUP BY c.`room`,\n" +
            "  cd.read_status,\n" +
            "  c.`user_id`,c.type) tmp", nativeQuery = true)
    ChatDTO findChatGroupCap(String userId, String room);

    @Query(value = "SELECT \n" +
            "    c.`room` id,\n" +
            "    (SELECT cp.name FROM `capstone_projects` cp INNER JOIN `capstone_project_details` cpd ON cp.`id` = cpd.capstone_project_id WHERE  cpd.capstone_project_id = (SELECT SUBSTRING_INDEX(c.`room`, '_', -1)) GROUP BY cp.name) title,\n" +
            "    cd.read_status readStatus,\n" +
            "    c.type TYPE \n" +
            "  FROM\n" +
            "    users u \n" +
            "    INNER JOIN chat c \n" +
            "      ON u.id = c.`user_id` \n" +
            "    INNER JOIN chat_details cd \n" +
            "      ON c.id = cd.chat_id \n" +
            "  WHERE cd.user_id = ?1 \n" +
            "    AND c.room LIKE 'cap%' AND cd.read_status = 'unread' \n" +
            "  GROUP BY c.`room`,\n" +
            "    cd.read_status,\n" +
            "    c.`user_id` ,c.type", nativeQuery = true)
    List<ChatDTO> findChatGroupCapSupervisor(String userId);

    @Query(value = "SELECT DISTINCT c.roomId FROM Chat c WHERE c.roomId = ?1 OR c.roomId = ?2")
    String findRoomChatPrivate(String roomId1, String roomId2);

    @Query("SELECT ru.userRoleKey.user FROM UserRoles ru WHERE ru.userRoleKey.role.name = 'training_dep' OR ru.userRoleKey.role.name = 'head'")
    List<Users> findUsersInRoomSpecial();
}
