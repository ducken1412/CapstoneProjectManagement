package com.fpt.service;

import com.fpt.dto.ChatDTO;
import com.fpt.entity.Chat;
import com.fpt.entity.Users;

import java.util.List;

public interface ChatService {

	Chat findById(Integer id);

	boolean deleteComment(Integer id);

	boolean save(Chat chat);

	List<Chat> findAll();

	List<Chat> findByRoomId(String roomId);

	List<Users> findUsersInRoom(String roomId);

	List<ChatDTO> findChatsByUserId(String userId);

}