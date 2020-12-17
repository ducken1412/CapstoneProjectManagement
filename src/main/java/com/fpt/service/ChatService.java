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

	List<ChatDTO> findChatPrivateByUserId(String userId);

	String findRoomChatPrivate(String roomId1, String roomId2);

	ChatDTO findChatTrainingDeptAndHeads(String userId);

	List<Users> findUsersInRoomSpecial();

	ChatDTO findChatGroupCap(String userId, String room);

	List<ChatDTO> findChatGroupCapSupervisor(String userId);

}