package com.fpt.repository;

import com.fpt.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Integer> {
    public List<Chat> findByRoomId(String roomId);
}
