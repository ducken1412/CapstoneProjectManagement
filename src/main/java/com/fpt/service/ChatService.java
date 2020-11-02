package com.fpt.service;

import com.fpt.entity.Chat;

import java.util.List;

public interface ChatService {

	Chat findById(Integer id);

	boolean deleteComment(Integer id);

	boolean save(Chat chat);

	List<Chat> findAll();

	List<Chat> findByRoomId(String roomId);

}